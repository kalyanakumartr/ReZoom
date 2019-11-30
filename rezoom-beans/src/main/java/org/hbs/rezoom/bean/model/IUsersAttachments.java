package org.hbs.rezoom.bean.model;

import org.hbs.rezoom.util.ICRUDBean;

public interface IUsersAttachments extends ICommonDateAndStatusFields, ICommonFileUpload, ICRUDBean
{
	public IUsers getUsers();

	public void setUsers(IUsers users);

}
