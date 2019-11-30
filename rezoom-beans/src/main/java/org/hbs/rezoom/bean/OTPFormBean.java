package org.hbs.rezoom.bean;

import org.hbs.rezoom.bean.model.Users;

public class OTPFormBean extends APIStatus
{
	private static final long	serialVersionUID	= 2473153333582197660L;

	public String				id;

	public String				otp;

	public Users				user;

	public String				tokenUrl;
}
