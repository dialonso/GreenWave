package org.greenwave.repository;

import java.util.List;

import org.greenwave.model.Data;
import org.greenwave.model.DataDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DataMeasureRepository extends JpaRepository<Data, Long> {
	
	@Query("select d.name_substance from DataMeasure d where d.category_substance =:x order by d.name_substance asc")
	public List<String> findMeasure(@Param("x")String categorie);
	
	@Query("select d.name_substance from DataMeasure d where d.domain =:y and d.category_substance =:x order  by d.name_substance asc")
	public List<String> findMeasureByDomainAndCategory(@Param("x")String category,@Param("y")DataDomain domain);
	
	@Query("select d.name_substance from DataMeasure d where d.domain =:x order by d.name_substance asc")
	public List<String> findMeasureByDomain(@Param("x")DataDomain domain);
	
	@Query("select d.acceptableRatioSW from DataMeasure d where d.name_substance =:x")
	public Double findRatioASW(@Param("x")String substance);
	
	@Query("select d.dangerousRatioSW from DataMeasure d where d.name_substance =:x")
	public Double findRatioDSW(@Param("x")String substance);
	
	@Query("select d.acceptableRatioOSW from DataMeasure d where d.name_substance =:x")
	public Double findRatioOASW(@Param("x")String substance);
	
	@Query("select d.dangerousRatioOSW from DataMeasure d where d.name_substance =:x")
	public Double findRatioODSW(@Param("x")String substance);
	
	@Query("select d.min from DataMeasure d where d.name_substance = :x")
	public Double getMin(@Param("x")String name_substance);
	
	@Query("select d.max from DataMeasure d where d.name_substance = :x")
	public Double getMax(@Param("x")String name_substance);
	
	@Query("select d.id_substance from DataMeasure d where d.name_substance = :x")
	public Long getIdFromName(@Param("x")String name);
	
}
