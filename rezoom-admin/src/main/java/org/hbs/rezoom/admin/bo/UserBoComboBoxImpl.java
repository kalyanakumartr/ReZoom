package org.hbs.rezoom.admin.bo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hbs.rezoom.bean.UserFormBean;
import org.hbs.rezoom.bean.model.City;
import org.hbs.rezoom.bean.model.Country;
import org.hbs.rezoom.bean.model.State;
import org.hbs.rezoom.bean.path.IErrorAdmin;
import org.hbs.rezoom.dao.CityDao;
import org.hbs.rezoom.dao.CountryDao;
import org.hbs.rezoom.dao.StateDao;
import org.hbs.rezoom.dao.UserDao;
import org.hbs.rezoom.security.resource.IPath;
import org.hbs.rezoom.util.CommonValidator;
import org.hbs.rezoom.util.LabelValueBean;
import org.hbs.rezoom.util.LabelValueBean.ELabelValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

public abstract class UserBoComboBoxImpl implements UserBo, IErrorAdmin, IPath
{
	private static final long	serialVersionUID	= 1160466715298198052L;

	private final Logger		logger				= LoggerFactory.getLogger(UserBoComboBoxImpl.class);

	@Autowired
	protected UserDao			userDao;

	@Autowired
	protected CountryDao		countryDao;

	@Autowired
	protected StateDao			stateDao;

	@Autowired
	protected CityDao			cityDao;

	public UserBoComboBoxImpl()
	{
		super();
	}

	@Override
	public List<LabelValueBean> getCountryList(Authentication auth, UserFormBean userFormBean)
	{
		logger.info("UserBoComboBoxImpl getCountryList starts:::", userFormBean.searchParam);
		List<Country> countryList = new ArrayList<Country>();
		List<LabelValueBean> _LBList = new ArrayList<LabelValueBean>();
		if (CommonValidator.isNotNullNotEmpty(userFormBean.searchParam))
		{
			countryList = countryDao.getCountryList(EWrap.Percent.enclose(userFormBean.searchParam));
		}
		else
		{
			countryList = countryDao.getCountryList("%%");
		}

		Collections.sort(countryList);

		for (Country country : countryList)
		{
			_LBList.add(new LabelValueBean(country, country.getCountryName(), country.getCountry(), country.getCountry()));
		}
		logger.info("UserBoComboBoxImpl getCountryList ends :::", _LBList.size());
		return _LBList;
	}

	@Override
	public List<LabelValueBean> getStateList(Authentication auth, UserFormBean userFormBean)
	{

		logger.info("UserBoComboBoxImpl getStateList starts:::", userFormBean.searchParam);
		List<State> stateList = new ArrayList<State>();
		List<LabelValueBean> _LBList = new ArrayList<LabelValueBean>();
		if (CommonValidator.isNotNullNotEmpty(userFormBean.searchParam))
		{
			stateList = stateDao.getStateList(EWrap.Percent.enclose(userFormBean.searchParam), userFormBean.country);
		}
		else
		{
			stateList = stateDao.getStateList(userFormBean.country);
		}

		Collections.sort(stateList);

		for (State state : stateList)
		{
			_LBList.add(new LabelValueBean(state, state.getState(), state.getState()));
		}
		logger.info("UserBoComboBoxImpl getStateList ends :::", _LBList.size());
		return _LBList;
	}

	@Override
	public List<LabelValueBean> getCityList(Authentication auth, UserFormBean userFormBean)
	{
		logger.info("UserBoComboBoxImpl getCityList starts:::", userFormBean.searchParam);
		List<City> cityList = new ArrayList<City>();
		List<LabelValueBean> _LBList = new ArrayList<LabelValueBean>();
		if (CommonValidator.isNotNullNotEmpty(userFormBean.searchParam))
		{
			cityList = cityDao.getCityList(userFormBean.state, EWrap.Percent.enclose(userFormBean.searchParam));
		}
		else
		{
			cityList = cityDao.getCityList(userFormBean.state);
		}

		Collections.sort(cityList);

		for (City city : cityList)
		{
			_LBList.add(new LabelValueBean(city, city.getCity() + EWrap.Brace.enclose(city.getState()), city.getZipCode()));
		}
		logger.info("UserBoComboBoxImpl getCityList ends :::", _LBList.size());
		return _LBList;
	}

	@Override
	public Collection<LabelValueBean> getUsersBySearchParam(Authentication auth, UserFormBean ufBean)
	{
		logger.info("UserBoComboBoxImpl getUsersBySearchParam starts:::", ufBean.searchParam);
		Map<String, LabelValueBean> userMap = new TreeMap<String, LabelValueBean>();

		logger.info("UserBoComboBoxImpl getUsersBySearchParam ends :::", userMap.size());
		return userMap.values();
	}

	public Collection<LabelValueBean> getUsers(Authentication auth, UserFormBean ufBean)
	{
		logger.info("UserBoComboBoxImpl getUsers starts:::", ufBean.media);
		List<Object[]> nameList = userDao.fetchUsersByUserNameOrEmailId(EAuth.User.getProducerId(auth), EWrap.Percent.enclose(ufBean.searchParam));
		logger.info("UserBoComboBoxImpl getUsers ends:::", nameList.size());
		return ELabelValue.Combo.toList(nameList);
	}

}