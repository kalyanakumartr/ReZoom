package org.hbs.sender.bo;

import java.io.IOException;

import javax.mail.MessagingException;

import org.hbs.rezoom.bean.MessageFormBean;
import org.hbs.rezoom.bean.model.IMessages;
import org.hbs.rezoom.security.resource.IPath.EMessageStatus;
import org.hbs.rezoom.security.resource.IPath.EReturn;
import org.hbs.rezoom.util.CustomException;
import org.springframework.security.core.Authentication;

public interface EmailSenderBo
{
	EMessageStatus sendEmailByMessage(Authentication auth, String token, IMessages message);

	EReturn sendEmailToUserOrGroup(Authentication auth, String token, MessageFormBean formBean) throws Exception;

	void updateMessageStatus(Authentication auth, String messageId, EMessageStatus messageStatus);

	EMessageStatus sendEmailByMessage(IMessages message) throws MessagingException, IOException, CustomException;

}
