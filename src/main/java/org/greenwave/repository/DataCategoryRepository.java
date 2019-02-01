package org.greenwave.repository;

import java.util.List;

import org.greenwave.model.Data;
import org.greenwave.model.DataCategory;
import org.greenwave.model.DataDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DataCategoryRepository  extends JpaRepository<Data, Long>{
	
	@Query("select c from DataCategory c where c.id_category =:x order by c.name asc")
	public DataCategory findOneById(@Param("x")Long idcategory);
	
	@Query("select c from DataCategory c where c.domain =:x order by c.name asc")
	public List<DataCategory> findByDomain(@Param("x")DataDomain domain);
}

