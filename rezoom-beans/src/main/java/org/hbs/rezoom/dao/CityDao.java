package org.hbs.rezoom.dao;

import java.util.List;

import org.hbs.rezoom.bean.model.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDao extends CrudRepository<City, String>
{
	@Query("From City where state.state = ?0 and city Like ?1 and status = true")
	List<City> getCityList(String state, String city);

	@Query("From City where state.state = ?0 and status = true")
	List<City> getCityList(String state);
}
