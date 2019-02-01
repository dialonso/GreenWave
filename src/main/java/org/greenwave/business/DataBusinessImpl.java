package org.greenwave.business;

import java.util.Date;
import java.util.List;

import org.greenwave.business.interfaces.DataBusiness;
import org.greenwave.model.Data;
import org.greenwave.model.DataCategory;
import org.greenwave.model.DataDomain;
import org.greenwave.model.Localization;
import org.greenwave.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataBusinessImpl implements DataBusiness {
	
	@Autowired
	private DataRepository dataRepository;
	
	@Override
	public Data create(Data data) {
		return dataRepository.save(data);
	}

	@Override
	public Data update(Long id) {
		Data data = dataRepository.getOne(id);
		return dataRepository.save(data);
	}

	@Override
	public void delete(Long id) {
		dataRepository.delete(dataRepository.findOne(id));
	}

	@Override
	public List<Data> find(Long id) {
		return dataRepository.findAllByUser(id);
	}

	@Override
	public List<Data> findByType(String dataType, Long id) {
		return dataRepository.findByType(dataType, id);
	}

	@Override
	public List<Data> findByWaterType(DataCategory waterType, Long idUser) {
		return dataRepository.findByWaterType(waterType, idUser);
	}

	@Override
	public List<Data> findByUserAndType(Long idUser) {
		return dataRepository.findAllByUserAndCoordinates(idUser);

	}

	@Override
	public List<Data> findLastDataPerCoordsAndUser(Long idUser) {
		return dataRepository.findLastDataByCoordinateAndUser(idUser);

	}

	@Override
	public int countByDepartment(String department) {
		return dataRepository.countDataPerDepartment(department);

	}

	@Override
	public Date getEarliestDate() {
		return dataRepository.getEarliestDate();
	
	}

	@Override
	public Date getLatestDate() {
		return dataRepository.getLatestDate();

	}

	@Override
	public List<Data> findBetweenDate(Date startDate, Date endDate, DataDomain domain) {
		return dataRepository.getDataBetweenDates(startDate, endDate,domain);
	}

	@Override
	public List<Data> findLastDataPerCoords(DataDomain domain) {
		return dataRepository.findLastDataByCoordinate(domain);
	}

	@Override
	public List<Data> findBetweenDatesPerTypeAndDepartment(List<String> departments, Date startDate, Date endDate,
			List<String> data_names) {
		return dataRepository.getDataBetweenDatesPerTypeAndDepartment(departments, startDate, endDate, data_names);

	}

	@Override
	public Data findById(Long id) {
		return dataRepository.findOne(id);
	}

	@Override
	public int countTotalData() {
		return dataRepository.countAllData();
	}

	@Override
	public List<Data> findInOrgPerTypeAndDate(Long idOrg, Date creationDate, String type) {
		return dataRepository.getDataInOrgPerDateType(idOrg, creationDate, type);
	}

	@Override
	public List<Data> findBetweenDatesPerType(List<String> data_names, Date startDate, Date endDate) {
		return dataRepository.getLastDataBetweenDatesPerType(startDate, endDate, data_names);
	
	}

	@Override
	public List<Data> findLastDataPerCoordsAndType(List<String> data_names) {
		return dataRepository.findLastDataByCoordinateAndType(data_names);

	}

	@Override
	public int countOKData(String name, double value, Date startDate, Date endDate,DataCategory waterType) {
		// TODO Auto-generated method stub
		return dataRepository.countOkData(name, value, startDate, endDate,waterType);
	}
	
	@Override
	public int countDangerousData(String name, double value, Date startDate, Date endDate,DataCategory waterType) {
		// TODO Auto-generated method stub
		return dataRepository.countDangerousData(name, value, startDate, endDate,waterType);

	}
	
	@Override
	public int countAcceptableData(String name, double valueDown, double valueUp, Date startDate, Date endDate,DataCategory waterType) {
		// TODO Auto-generated method stub
		return dataRepository.countAcceptableData(name, valueDown, valueUp, startDate, endDate,waterType);
	}

	@Override
	public List<Data> findByTypeUserYear(String dataType, int year, List<Localization> ids, String water) {
		return dataRepository.findByTypeUserYear(dataType, year, ids,water);
	}

	@Override
	public int countData(String name, Date startDate, Date endDate, DataCategory waterType, List<Localization> ids) {
		return dataRepository.countData(name, startDate, endDate, waterType,ids);
	}

	@Override
	public List<Double> avgCalcul(String name, DataCategory category, Date sdate, Date edate, List<Localization> ids) {
		return dataRepository.globalAvg(name, category, ids, sdate,edate);
	}
	
	@Override
	public Data findByIdloc(Long id_localization) {
		return dataRepository.findByidloc(id_localization);
	}


}
