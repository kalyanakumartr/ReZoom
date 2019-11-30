package org.hbs.rezoom.dao;

import org.hbs.rezoom.bean.model.application.IncomingData;
import org.springframework.data.repository.CrudRepository;

public interface IncomingDao extends CrudRepository<IncomingData, String>
{

}
