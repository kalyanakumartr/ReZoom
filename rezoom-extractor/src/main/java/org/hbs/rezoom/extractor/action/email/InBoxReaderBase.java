package org.hbs.rezoom.extractor.action.email;

import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.MimeMessage;

import org.hbs.rezoom.bean.model.channel.ConfigurationEmail;
import org.hbs.rezoom.event.service.GenericKafkaProducer;
import org.hbs.rezoom.extractor.action.core.InBoxReader;
import org.hbs.rezoom.security.resource.IPath.EMedia;
import org.hbs.rezoom.security.resource.IPath.ETopic;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class InBoxReaderBase implements InBoxReader
{
	private static final long	serialVersionUID	= 2428143934833300387L;

	@Autowired
	GenericKafkaProducer		gKafkaProducer;

	public InBoxReaderBase()
	{
		super();
	}

	protected Store authenticateMailAndConnect(ConfigurationEmail config) throws NoSuchProviderException, MessagingException
	{
		try
		{
			Store store;
			Properties props = new Properties();

			Map<String, String[]> map = config.getSource().props();
			props.setProperty(map.get("Hostname")[0], config.getHostAddress().isEmpty() ? map.get("Hostname")[1] : config.getHostAddress());
			props.setProperty(map.get("Port")[0], config.getPort().isEmpty() ? map.get("Port")[1] : config.getPort());
			props.setProperty(map.get("Protocol")[0], config.getProtocol());

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
				System.out.println(config.getFromId().trim() + " Inbox Connected :: Unread Message Count :: " + folder.getUnreadMessageCount());
			}
			return store;
		}
		finally
		{
		}
	}

	protected void pushToQueue(String producerId, UIDFolder _UIDFolder, MimeMessage[] messages) throws MessagingException
	{
		for (MimeMessage message : messages)
		{
			gKafkaProducer.sendMessage(ETopic.Attachment, EMedia.Email, new UIDMimeMessage(producerId, _UIDFolder, message));
		}
	}

}