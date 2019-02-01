package org.greenwave.repository;

import java.util.List;

import org.greenwave.model.DataCategory;
import org.greenwave.model.DataDomain;
import org.greenwave.model.DataOpen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DataOpenRepository extends JpaRepository<DataOpen, Long> {
	@Query("select c from DataOpen c where c.id =:x order by c.name asc")
	public DataOpen findOne(@Param("x")Long id);
	
	@Query("select c from DataOpen c  order by c.name asc")
	public List<DataOpen> findAll();

}
