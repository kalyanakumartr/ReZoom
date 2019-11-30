package org.hbs.rezoom.bean.model;

public interface IProducersCollabrate extends ICommonDateAndStatusFields, IProducersBase
{
	public long getAutoId();

	public void setAutoId(long autoId);

	public void setCollabrater(IProducers collabrater);

	public IProducers getCollabrater();

}
