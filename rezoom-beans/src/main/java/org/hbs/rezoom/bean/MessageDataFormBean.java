package org.hbs.rezoom.bean;

import org.hbs.rezoom.bean.model.MessagesBase;

public class MessageDataFormBean extends APIStatus
{

	private static final long	serialVersionUID	= 1452795795821759203L;

	public MessagesBase			message;
	public String				searchParam;
	public String				userId;
	public String				groupId;
	public String				customerId;
	public long					fromDate;
	public long					toDate;
	public int					year;

}
