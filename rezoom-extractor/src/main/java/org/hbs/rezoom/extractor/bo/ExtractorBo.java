package org.hbs.rezoom.extractor.bo;

import java.io.Serializable;
import java.util.List;

import org.hbs.rezoom.bean.model.IConfiguration;
import org.hbs.rezoom.bean.model.channel.DataExtractorPattern;
import org.hbs.rezoom.security.resource.IPath.EMedia;
import org.hbs.rezoom.security.resource.IPath.EMediaMode;

public interface ExtractorBo extends Serializable
{
	List<IConfiguration> getConfigurationList(EMedia eMedia, EMediaMode eMediaMode);

	long getLastEmailSentDate(String customerAccountMail);

	List<DataExtractorPattern> getEmailExtractors(String producerId);
}
