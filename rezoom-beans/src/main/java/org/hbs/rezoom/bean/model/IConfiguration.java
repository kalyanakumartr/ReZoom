package org.hbs.rezoom.bean.model;

import java.io.Serializable;

public interface IConfiguration extends Serializable
{
	public String getProducerId();

	public void setProducerId(String producerId);
}