package org.greenwave.business;

import java.util.List;

import org.greenwave.business.interfaces.DataCategoryBusiness;
import org.greenwave.model.DataCategory;
import org.greenwave.model.DataDomain;
import org.greenwave.repository.DataCategoryRepository;
import org.greenwave.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataCategoryBusinessImpl implements DataCategoryBusiness {
	
	@Autowired
	private DataCategoryRepository dataCategoryRepository;
	
	@Override
	public DataCategory findById(Long id) {
		return dataCategoryRepository.findOneById(id);
	}

	@Override
	public List<DataCategory> findByDomain(DataDomain domain) {
		return dataCategoryRepository.findByDomain(domain);
	}

}
