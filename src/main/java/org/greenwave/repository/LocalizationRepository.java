package org.greenwave.repository;

import java.util.List;

import org.greenwave.model.Localization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocalizationRepository extends JpaRepository<Localization, Long> {
	
	@Query("select l from Localization l where l.latitude=:x and l.longitude=:y")
	public Localization getLocByGPSCoords(@Param("x")double latitude, @Param("y")double longitude);
	
	@Query("select distinct l.department from Localization l")
	public List<String> getAllLoc();
	
	@Query("select l from Localization l where l.department=:x order by l.department asc")
	public List<Localization> getIdFromDepName(@Param("x")String depName);
	
	@Query("select count(distinct l.country) from Localization l")
	public int countAllCountries();
	
	//@Query("select id_localization , latitude, longitude from localization")
	@Query("select l from Localization l")
	public List<Localization> getLngLatId();
	
}

