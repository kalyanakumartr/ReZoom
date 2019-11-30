package org.hbs.sender;

import org.hbs.rezoom.bean.MessageFormBean;
import org.hbs.rezoom.bean.SMSResponseBean;
import org.hbs.rezoom.security.resource.IPath;

public interface ISMSGateway extends IPath
{
	SMSResponseBean sendSMSMessages(MessageFormBean messageFormBean);

	String callBackResponse(String responseUrl, String token, String messageId);
}
