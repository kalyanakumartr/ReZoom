package org.hbs.rezoom.bean.model.channel;

import java.sql.Timestamp;

import org.hbs.rezoom.bean.model.ICommonDateAndStatusFields;
import org.hbs.rezoom.bean.model.IMessages;
import org.hbs.rezoom.bean.model.IProducersBase;
import org.hbs.rezoom.util.ICRUDBean;

public interface IMessagesChannel extends ICommonDateAndStatusFields, IProducersBase, ICRUDBean, IMessages
{
	public String getExpiryDate();

	public Timestamp getNextDeliveryDate();

	public String getScheduledDate();

	public void setExpiryDate(String expiryDate);

	public void setNextDeliveryDate(Timestamp nextDeliveryDate);

}