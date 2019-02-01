package org.greenwave.business;

import java.util.List;

import org.greenwave.repository.DataMeasureRepository;
import org.greenwave.repository.DataRepository;
import org.greenwave.business.interfaces.DataMeasureBusiness;
import org.greenwave.model.Admin;
import org.greenwave.model.DataDomain;
import org.greenwave.model.DataMeasure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import org.greenwave.business.interfaces.DataMeasureBusiness;
import org.greenwave.model.DataDomain;

@Service
public class DataMeasureBusinessImpl implements DataMeasureBusiness {

	@Autowired
	private DataMeasureRepository dataMeasureRepository;
	
	//list dataType of OTHER_SUBSTANCES category
	@Override
	public List<String> listDataTypeFromOther(String category) {
		return dataMeasureRepository.findMeasure(category);
	}

	@Override
	public Double findRatioASW(String substance) {
		return dataMeasureRepository.findRatioASW(substance);
	}
	
	@Override
	public Long getIdFromName(String name) {
		return dataMeasureRepository.getIdFromName(name);
	}
	
	@Override
	public Double findRatioDSW(String substance) {
		return dataMeasureRepository.findRatioDSW(substance);
	}
	
	@Override
	public Double findRatioOASW(String substance) {
		return dataMeasureRepository.findRatioOASW(substance);
	}
	
	@Override
	public Double findRatioODSW(String substance) {
		return dataMeasureRepository.findRatioODSW(substance);
	}

	@Override
	public Double getMin(String substance) {
		return dataMeasureRepository.getMin(substance);
	}

	@Override
	public Double getMax(String substance) {
		return dataMeasureRepository.getMax(substance);
	}

	@Override
	public List<String> listDataTypeByDomain(DataDomain domain) {
		return dataMeasureRepository.findMeasureByDomain(domain);
	}

	@Override
	public List<String> listDataTypeByDomainAndCategory(String category,DataDomain domain ) {
		return dataMeasureRepository.findMeasureByDomainAndCategory(category,domain);
	}

	
}
