package org.greenwave.business.interfaces;

import java.util.Date;
import java.util.List;

import org.greenwave.model.Data;
import org.greenwave.model.DataCategory;
import org.greenwave.model.DataDomain;
import org.greenwave.model.Localization;

public interface DataBusiness {
	
	public Data findById(Long id);
	
	public Data create(Data data);

	public Data update(Long id);

	public void delete(Long id);

	public List<Data> find(Long id);

	public List<Data> findByType(String dataType, Long idUser);
	
	public List<Data> findByUserAndType(Long idUser);

	public List<Data> findByWaterType(DataCategory waterType, Long idUser);
	
	public List<Data> findLastDataPerCoordsAndUser(Long idUser);
	
	public List<Data> findLastDataPerCoords(DataDomain domain);
	
	public List<Data> findLastDataPerCoordsAndType(List<String> data_names);
	
	public int countByDepartment(String department);

	public List<Data> findByTypeUserYear(String dataType, int year,List<Localization> ids, String water);
	
	public Date getEarliestDate();
	
	public Date getLatestDate();
	
	public List<Data> findBetweenDate(Date startDate, Date endDate,DataDomain domain);
	
	public List<Data> findBetweenDatesPerTypeAndDepartment(List<String> departments, Date startDate, Date endDate, List<String> data_names);
		
	public int countTotalData();
	
	public List<Data> findInOrgPerTypeAndDate(Long idOrg, Date creationDate, String type);
	
	public List<Data> findBetweenDatesPerType(List<String> data_names, Date startDate, Date endDate);
	
	public int countOKData(String name, double value, Date startDate, Date endDate,DataCategory waterType);
	
	public int countData(String name, Date startDate, Date endDate, DataCategory waterType,List<Localization> ids);
	
	public int countDangerousData(String name, double value, Date startDate, Date endDate,DataCategory waterType);
	
	public int countAcceptableData(String name, double valueDown,double valueUP, Date startDate, Date endDate,DataCategory waterType);
	
	public List<Double> avgCalcul(String name, DataCategory category, Date sdate, Date edate, List<Localization> ids);
	
	public Data findByIdloc(Long id_localization);

}
