package org.hbs.rezoom.admin.bo;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.util.concurrent.ExecutionException;

import org.hbs.rezoom.bean.OTPFormBean;

public interface OTPBo extends Serializable
{
	String generateOTP(OTPFormBean otpForm) throws InvalidKeyException;

	String validateOTP(OTPFormBean otpForm) throws ExecutionException;
}
