package org.hbs.rezoom.extractor.action.email;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;

import org.apache.commons.io.IOUtils;
import org.hbs.rezoom.bean.model.application.IncomingData;
import org.hbs.rezoom.bean.model.application.ResumeAttachments;
import org.hbs.rezoom.bean.model.application.ResumeAttachments.EResumeTrace;
import org.hbs.rezoom.bean.model.channel.ConfigurationEmail;
import org.hbs.rezoom.dao.IncomingDao;
import org.hbs.rezoom.event.service.KafkaEmailReferenceBean;
import org.hbs.rezoom.security.resource.IPath;
import org.hbs.rezoom.util.CommonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sun.mail.imap.IMAPFolder;

@Service
public class IncomingDataByEmail implements IPath
{
	private static final long	serialVersionUID	= -3529623337510779624L;

	private final Logger		logger				= LoggerFactory.getLogger(IncomingDataByEmail.class);

	private IncomingDao			incomingDao;

	@KafkaListener(topicPartitions = @TopicPartition(topic = ATTACHMENT_TOPIC, partitions = { "0" }), groupId = EMPLOYEE_ID, clientIdPrefix = EMAIL)
	public void consume(String uidJsonStr) throws IOException, MessagingException
	{
		logger.info(String.format("#### -> Consumed message -> %s", uidJsonStr));
		IMAPFolder imapFolder = null;
		try
		{
			KafkaEmailReferenceBean kafkaEmailRef = new Gson().fromJson(uidJsonStr, KafkaEmailReferenceBean.class);
			
			logger.info(kafkaEmailRef.messageNumber+" -    -    -   "+kafkaEmailRef.sentDate);
			
			ConfigurationEmail config = kafkaEmailRef.config;
			Store store = EmailConnectionHandler.getInstance().getStore(config.getProducerId() + config.getFromId());
			if (store == null)
			{
				Properties props = new Properties();
				Map<String, String[]> map = config.getSource().props();
				props.setProperty("mail.host", config.getHostAddress());
				props.setProperty("mail.port", config.getPort());
				props.setProperty("mail.store.protocol", config.getProtocol());
				/* Get the Session object for specific Mail Property. */
				Session session = Session.getInstance(props, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(config.getUserName().trim(), config.getPassword());
					}
				});
				/* Get a Store object that implements the specified protocol. */
				store = session.getStore(config.getProtocol().trim());
				/*
				 * Connect to the current host using the specified Email id and Password.
				 */
				store.connect();
				Folder[] folderArr = store.getDefaultFolder().list();
				for (Folder folder : folderArr)
				{
					System.out.println(config.getFromId().trim() + " Inbox Connected :: Unread Message Count :: " + folder + " ---- " + folder.getUnreadMessageCount());
					if (folder.getName().equalsIgnoreCase("inbox"))
					{
						break;
					}
				} 
				EmailConnectionHandler.getInstance().putStore(config.getProducerId() + config.getFromId(), store);
			}
			
			try {
				imapFolder = (IMAPFolder) store.getFolder("inbox");

				// Open the Folder.
				if (!imapFolder.isOpen())
					imapFolder.open(Folder.READ_ONLY);

				//Date initDateTime = null, startTime = null, endTime = null;

				SearchTerm searchTerm = new SearchTerm() {

					@Override
					public boolean match(Message message)
					{
						try
						{
							logger.info(message.getMessageNumber()+" - "+ kafkaEmailRef.messageNumber+"   -  "+kafkaEmailRef.sentDate+"  -   "+message.getSentDate());
							if(message.getMessageNumber()== kafkaEmailRef.messageNumber && message.getSentDate().equals(kafkaEmailRef.sentDate)) {
								logger.info(message.getMessageNumber()+"");
								return true;
							}
						}
						catch (MessagingException e)
						{
							e.printStackTrace();
							return false;
						}
						return false;
					}
				};
				Message[] messages = (Message[]) imapFolder.search(searchTerm);
				
				IncomingData incomingData = processMultiPart(new UIDMimeMessage(config.getProducerId(),imapFolder,messages[0]));
				logger.info(incomingData.getAttachmentList().toString());
				
			}
			catch (FolderClosedException excep)
			{
				excep.printStackTrace();
			}
			catch (MessagingException excep)
			{
				excep.printStackTrace();
			}
			finally
			{
				try
				{
					try
					{
						Thread.sleep(10000);
					}
					catch (InterruptedException ie)
					{
					}
					if (imapFolder != null && imapFolder.isOpen())
					{
						imapFolder.close(true);
					}
					if (store != null)
					{
						store.close();
					}
				}
				catch (MessagingException e)
				{
					e.printStackTrace();
				}
			}

			
			/*if (IMAPMessage.class.isInstance(uidMessag
			 * e.message))
			 */
			//{
				/*
				 * If the mail is auto generated mail, then the message.getSentDate() should be
				 * null. So we use the message.getReceivedDate() to get the sentDate
				 */
				/*Date sentDate = uidMessage.message.getSentDate() == null ? uidMessage.message.getReceivedDate() : uidMessage.message.getSentDate();

				if (uidMessage.message.getContentType() != null)
				{
					Address[] froms = uidMessage.message.getFrom();

					String candidateEmailId = null;
					DataExtractor.getInstance(uidMessage.producerId).dataFramer(RegExFor.Email, Arrays.toString(froms));

					if (CommonValidator.isNullOrEmpty(candidateEmailId))
					{
						candidateEmailId = froms == null ? "" : ((InternetAddress) froms[0]).getAddress();
					}

					if (CommonValidator.isNotNullNotEmpty(candidateEmailId) && CommonValidator.isNotNullNotEmpty(uidMessage.message.getSubject()))
					{
						IncomingData incomingData = processMultiPart(uidMessage);

						if (CommonValidator.isNotNullNotEmpty(incomingData))
						{
							incomingData.setCandidateEmail(candidateEmailId);
							incomingData.setMedia(EMedia.Email);
							incomingData.setIncomingStatus(EIncomingStatus.New);
							incomingData.setSubject(uidMessage.message.getSubject());
							incomingData.setSentTime(sentDate.getTime());
							incomingData.setUniqueId(uidMessage.uniqueId);
							incomingDao.save(incomingData);
						}
					}
				}
			}*/
		}
		finally
		{
			//uidMessage.message = null;
		}
	}

	private IncomingData processMultiPart(UIDMimeMessage uidMsg)
	{
		Set<ResumeAttachments> attachmentsSet = null;
		FileOutputStream outStream = null;
		try
		{
			BodyPart bodyPart = null;
			attachmentsSet = new HashSet<ResumeAttachments>();
			Multipart multipart = (Multipart) uidMsg.message.getContent();
			ResumeAttachments attachment = null;
			for (int idx = 0; idx < multipart.getCount(); idx++)
			{
				bodyPart = multipart.getBodyPart(idx);

				if (CommonValidator.isNotNullNotEmpty(bodyPart, bodyPart.getFileName()) && CommonValidator.isEqual(bodyPart.getDisposition(), Part.ATTACHMENT))
				{
					if (bodyPart.getSize() > 4113632)
					{
						break;
					}

					uidMsg.setFileName(bodyPart.getFileName());
					outStream = uidMsg.getOutputStream();

					IOUtils.copy(bodyPart.getInputStream(), outStream);

					if (bodyPart.getInputStream() != null)
						bodyPart.getInputStream().close();

					if (outStream != null)
						outStream.close();

					attachment = new ResumeAttachments();
					attachment.setUploadFileName(bodyPart.getFileName());
					attachment.setStatus(true);
					attachment.setTrace(EResumeTrace.YetToTrace);
					attachment.setUploadFileFolderURL(uidMsg.getOutputPath());
					File file = uidMsg.getOutputFile();// Don't Change Position

					attachment.setUploadFileSize(file.length());
					attachment.setUploadFileDate(new Timestamp(System.currentTimeMillis()));
					attachment.setUploadFileLastModifiedDate(new Timestamp(file.lastModified()));
					attachmentsSet.add(attachment);

				}

			}
			IncomingData incomingData = new IncomingData();
			incomingData.setAttachmentList(attachmentsSet);
			return incomingData;
		}
		catch (MessagingException | IOException excep)
		{
			excep.printStackTrace();
		}
		return null;
	}
}
