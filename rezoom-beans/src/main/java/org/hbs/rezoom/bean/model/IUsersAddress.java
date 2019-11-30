package org.hbs.rezoom.bean.model;

import org.hbs.rezoom.util.ICRUDBean;

public interface IUsersAddress extends IAddress, ICRUDBean
{
	public IUsers getUsers();

	public void setUsers(IUsers users);

}
