package org.hbs.sender.bo;

import org.hbs.rezoom.dao.SequenceDao;
import org.hbs.rezoom.dao.MessageDao;
import org.hbs.rezoom.dao.UserDao;
import org.hbs.rezoom.security.resource.IPath;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseSenderBoImpl implements IPath
{

	private static final long	serialVersionUID	= 833657530598194674L;

	@Autowired
	MessageDao					messageDao;

	@Autowired
	ConfigurationBo				configurationBo;

	@Autowired
	SequenceDao					sequenceDao;

	@Autowired
	UserDao						userDao;

	public BaseSenderBoImpl()
	{
		super();
	}

}