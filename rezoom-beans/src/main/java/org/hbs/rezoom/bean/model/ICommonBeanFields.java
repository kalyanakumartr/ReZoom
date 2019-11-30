package org.hbs.rezoom.bean.model;

public interface ICommonBeanFields extends ICommonDateAndStatusFields
{

	public IUsers getCreatedUser();

	public IUsers getModifiedUser();

	public void setCreatedUser(IUsers createdUser);

	public void setModifiedUser(IUsers modifiedUser);

}
