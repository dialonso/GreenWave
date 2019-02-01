package org.greenwave.repository;

import java.util.Date;
import java.util.List;

import org.greenwave.model.Data;
import org.greenwave.model.DataCategory;
import org.greenwave.model.DataDomain;
import org.greenwave.model.Localization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DataRepository extends JpaRepository<Data, Long> {
	
	//recupérer les données selon le type d'eau
	@Query("select d from Data d where d.category_data =:x order by d.measure_date desc")
	public Page<Data> getDataByWaterType(@Param("x")DataCategory waterType, Pageable pageable);
	
	//Recupérer les données selon le nom d'utilisateur
	@Query("select d from Data d where d.user.fName =:x order by d.measure_date desc")
	public Page<Data> getDataCreatedByUser(@Param("x")String UserName, Pageable pageable);
	
	//Recupérer les données selon le type de données 
	@Query("select d from Data d where d.type =:x order by d.measure_date desc")
	public Page<Data> getDataByType(@Param("x")String dataType, Pageable pageable);

	//Recupérer les données selon le nom d'utilisateur
	@Query("select d from Data d where d.user.id =:x order by d.measure_date desc")
	public List<Data> findAllByUser(@Param("x")Long idUser);	

	//Recupérer les données par endroit et par type selon le nom d'utilisateur
	@Query("select d from Data d where d.user.id =:x group by d.id_data, d.type, d.localization_data.longitude, d.localization_data.latitude order by d.measure_date desc")
	public List<Data> findAllByUserAndCoordinates(@Param("x")Long idUser);
	
	//Recupérer la dernière donnée saisie par endroit et par utilisateur
	@Query("select d from Data d join d.localization_data l where d.user.id =:x and d.measure_date in(select max(d.measure_date) from Data d join d.localization_data l group by l.latitude, l.longitude) group by d.id_data, l.latitude, l.longitude order by d.measure_date desc")
	public List<Data> findLastDataByCoordinateAndUser(@Param("x")Long idUser);
	
	//Recupérer la dernière donnée saisie par endroit
	@Query("select d from Data d join d.localization_data l where d.domain =:x and d.measure_date in(select max(d.measure_date) from Data d join d.localization_data l group by l.latitude, l.longitude) group by d.id_data, l.latitude, l.longitude order by d.measure_date desc")
	public List<Data> findLastDataByCoordinate(@Param("x")DataDomain domain);
	
	//Recupérer la dernière donnée saisie par endroit par type
	@Query("select d from Data d join d.localization_data l where d.type in(:x) and d.measure_date in(select max(d.measure_date) from Data d join d.localization_data l group by l.latitude, l.longitude) group by d.id_data, l.latitude, l.longitude order by d.measure_date desc")
	public List<Data> findLastDataByCoordinateAndType(@Param("x")List<String> data_names);
	
	//Recupérer les données selon le nom d'utilisateur et le type de donnée PASSER EN CLEF ETRANGERE
	@Query("select d from Data d where  d.type =:x and d.user.id =:y order by d.measure_date desc")
	public List<Data> findByType(@Param("x")String dataType, @Param("y")Long idUser);

	//Recupérer les données selon le nom d'utilisateur et le type d'eau WATER TYPE EXISTE PLUS (CATEGORY CLEF ETRANGERE)
	@Query("select d from Data d where  d.category_data =:x and d.user.id =:y order by d.measure_date desc")
	public List<Data> findByWaterType(@Param("x")DataCategory waterType, @Param("y")Long idUser);
	
	@Query("select d from Data d where d.type =:x and d.category_data=:k and year(d.measure_date) =:y and d.localization_data in :z order by d.measure_date asc")
	public List<Data> findByTypeUserYear(@Param("x")String dataType, @Param("y")int year,@Param("z")List<Localization> ids,@Param("k")String water);

	// Count data entered for a given department
	@Query("select count(*) from Data d join d.localization_data l where l.department = :x")
	public int countDataPerDepartment(@Param("x")String department);
	
	// Get earliest date
	@Query("select min(d.measure_date) from Data d")
	public Date getEarliestDate();
	
	// Get latest date
	@Query("select max(d.measure_date) from Data d")
	public Date getLatestDate();
	
	// Get data between dates
	@Query("select d from Data d where d.domain =:z and date(d.measure_date) between :x and :y")
	public List<Data> getDataBetweenDates(@Param("x") Date startDate, @Param("y") Date endDate, @Param("z") DataDomain domain);
	
	// Get data to export, department, dates
	@Query("select d from Data d where d.localization_data.department in(:x) and d.type in(:a) and date(d.measure_date) between :y and :z")
	public List<Data> getDataBetweenDatesPerTypeAndDepartment(@Param("x")List<String> departments, @Param("y") Date startDate, @Param("z") Date endDate, @Param("a") List<String> data_names);
	
	// Get total number of data
	@Query("select count(*) from Data d")
	public int countAllData();
	
	// Get data specific type and date for every user in a specific admin organization
	@Query("select d from Data d where d.user.id in(select id from SimpleUser where admin.organization.id_organization = :y) or d.user.id in(select id from Admin where organization.id_organization = :y) and date(d.measure_date) = :z and d.type = :a")
	public List<Data> getDataInOrgPerDateType(@Param("y")Long id_org, @Param("z")Date creationDate, @Param("a")String type);
	
	// Get data to export, department, dates
	@Query("select d from Data d join d.localization_data l where date(d.measure_date) between :y and :z and d.type in(:a) group by d.id_data, l.latitude, l.longitude order by d.type asc, d.measure_date desc")
	public List<Data> getLastDataBetweenDatesPerType(@Param("y") Date startDate, @Param("z") Date endDate, @Param("a") List<String> data_names);
	
	@Query("select count(*) from Data d where d.type=:x and d.value <:y and d.category_data=:z and  date(d.measure_date) between :a and :b ")
	public int countOkData(@Param("x") String name, @Param("y") double value ,@Param("a") Date startDate, @Param("b") Date endDate,@Param("z") DataCategory waterType);
	
	@Query("select count(*) from Data d where d.type=:x and d.value >:y and d.category_data=:z and  date(d.measure_date) between :a and :b ")
	public int countDangerousData(@Param("x") String name, @Param("y") double value ,@Param("a") Date startDate, @Param("b") Date endDate,@Param("z") DataCategory waterType);
	
	@Query("select count(*) from Data d where d.type=:x and d.value >=:y and d.value<=:z and d.category_data=:m and  date(d.measure_date) between :a and :b ")
	public int countAcceptableData(@Param("x") String name, @Param("y") double valueDown , @Param("z") double valueUp ,@Param("a") Date startDate, @Param("b") Date endDate,@Param("m") DataCategory waterType);
	
	@Query("select d.value FROM Data d where d.type =:x and d.category_data =:y and d.localization_data in:z and date(d.measure_date) between :a and :b")
	public List<Double> globalAvg(@Param("x") String name,@Param("y") DataCategory category,@Param("z")List<Localization> ids, @Param("a") Date start, @Param("b") Date end);
	
	@Query("select count(*) from Data d where d.type=:x and  date(d.measure_date) between :a and :b and d.category_data =:y and d.localization_data in:z")
	public int countData(@Param("x") String name,@Param("a") Date startDate, @Param("b") Date endDate, @Param("y") DataCategory waterType,  @Param("z") List<Localization> ids);		
	
	@Query("select d from Data d where d.localization_data.id_localization =:x ")
	public Data findByidloc(@Param("x")Long id_localization);

}
