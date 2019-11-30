package org.hbs.rezoom.dao;

import org.hbs.rezoom.bean.model.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesDao extends CrudRepository<Roles, String>
{

}
