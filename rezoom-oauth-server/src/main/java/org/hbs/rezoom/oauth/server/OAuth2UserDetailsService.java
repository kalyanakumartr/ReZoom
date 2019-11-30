package org.hbs.rezoom.oauth.server;

import org.hbs.rezoom.bean.model.IUsers;
import org.hbs.rezoom.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OAuth2UserDetailsService implements UserDetailsService
{
	@Autowired
	UserDao userDao;

	@Override
	public IOAuth2UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
	{
		IUsers user = userDao.findByEmailOrMobileOrUserId(userName);
		if (user == null)
		{
			throw new UsernameNotFoundException("Login Info " + userName + " not found");
		}
		return new OAuth2UserDetails(user);
	}
}
