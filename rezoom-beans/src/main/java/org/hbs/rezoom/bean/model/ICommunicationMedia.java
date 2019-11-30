package org.hbs.rezoom.bean.model;

import org.hbs.rezoom.security.resource.IPath.EMediaType;
import org.hbs.rezoom.util.EBusinessKey;

public interface ICommunicationMedia extends EBusinessKey, ICommunicationMediaBase
{

	String getMediaId();

	EMediaType getMediaType();

	void setMediaId(String mediaId);

	void setMediaType(EMediaType mediaType);

}