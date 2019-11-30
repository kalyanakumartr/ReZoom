package org.hbs.rezoom.bean;

import org.hbs.rezoom.bean.model.application.CustomerProducer;
import org.springframework.security.core.Authentication;

public class ProducerFormBean extends APIStatus
{

	private static final long	serialVersionUID	= 5862342185767193951L;

	public CustomerProducer		producer			= new CustomerProducer();

	public CustomerProducer		repoProducer;

	public void updateRepoProducer(Authentication auth)
	{
	}
}
