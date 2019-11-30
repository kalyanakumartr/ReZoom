package org.hbs.sender.bo;

import java.io.IOException;

import org.hbs.rezoom.bean.MessageFormBean;
import org.hbs.rezoom.bean.SMSResponseBean;
import org.hbs.rezoom.bean.model.channel.IChannelMessages;
import org.hbs.rezoom.bean.model.clickatell.SMSCallBackFormBean;
import org.hbs.rezoom.security.resource.IPath.EReturn;
import org.hbs.rezoom.util.CustomException;
import org.springframework.security.core.Authentication;

public interface SMSSenderBo
{
	SMSResponseBean sendSMSByMessage(Authentication auth, IChannelMessages message) throws CustomException, IOException, ClassNotFoundException;

	EReturn sendSMSToUserOrGroup(Authentication auth, String token, MessageFormBean messageFormBean) throws Exception;

	void updateSMSStatus(SMSCallBackFormBean callBackFormBean);
}