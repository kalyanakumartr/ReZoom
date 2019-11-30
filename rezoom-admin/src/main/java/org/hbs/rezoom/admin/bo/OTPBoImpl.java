package org.hbs.rezoom.admin.bo;

import java.security.InvalidKeyException;
import java.util.concurrent.ExecutionException;

import org.hbs.rezoom.admin.OTPService;
import org.hbs.rezoom.bean.OTPFormBean;
import org.hbs.rezoom.bean.model.Users;
import org.hbs.rezoom.dao.UserDao;
import org.hbs.rezoom.event.service.GenericKafkaProducer;
import org.hbs.rezoom.security.resource.IPath.EMedia;
import org.hbs.rezoom.security.resource.IPath.ETemplate;
import org.hbs.rezoom.security.resource.IPath.ETopic;
import org.hbs.rezoom.util.ServerUtilFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OTPBoImpl implements OTPBo
{
	private static final long	serialVersionUID	= 1949352771664273090L;

	@Autowired
	protected OTPService		otpService;

	@Autowired
	GenericKafkaProducer		gKafkaProducer;

	@Autowired
	protected UserDao			userDao;

	@SuppressWarnings("unused")
	@Autowired
	private ServerUtilFactory	serverUtilFactory;

	@Override
	public String generateOTP(OTPFormBean otpFormBean) throws InvalidKeyException
	{
		Users user = userDao.findByEmailOrMobileOrUserId(otpFormBean.user.getUserId());
		if (user == null)
			throw new InvalidKeyException("Invalid user Id");
		otpFormBean.user = user;
		String[] otps = otpService.generate(user.getUserId(), 6);
		otpFormBean.id = otps[0];
		otpFormBean.user.setOtp(otps[1]);
		userDao.save(user);
		try
		{
			gKafkaProducer.sendMessage(ETopic.Internal, EMedia.SMS, ETemplate.SMS_OTP, otpFormBean);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return otps[0];
	}

	@Override
	public String validateOTP(OTPFormBean otpFormBean) throws ExecutionException
	{
		return otpService.validate(otpFormBean.id, otpFormBean.user.getOtp(), otpFormBean.otp).name();
	}
}
