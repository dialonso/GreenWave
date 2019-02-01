package org.greenwave.repository;

import org.greenwave.model.DataFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DataFileRepository extends JpaRepository<DataFile, Long> {
	
	//Recupérer les fichiers selon le type de données choisi
	@Query("select f from DataFile f where f.data.type =:x order by f.data.measure_date desc")
	public Page<DataFile> getFileByDataType(@Param("x")String dataType, Pageable pageable);
		
	//Recupérer les fichiers selon le type de l'eau
	//@Query("select f from File f where f.data.water_category =:x order by f.data.measure_date desc")
	//public Page<File> getFileByWaterType(@Param("x")String waterCategory, Pageable pageable);

}
