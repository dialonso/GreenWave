package org.greenwave.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.greenwave.business.interfaces.DataOpenBusiness;
import org.greenwave.model.DataOpen;
import org.greenwave.repository.DataOpenRepository;

@Service
public class DataOpenBusinessImpl implements DataOpenBusiness {
	
	@Autowired 
	private DataOpenRepository dataOpenRepository;

	@Override
	public DataOpen findById(Long id) {
		return dataOpenRepository.findOne(id);
	}

	@Override
	public List<DataOpen> findAll() {
		return dataOpenRepository.findAll();
	}

	@Override
	public DataOpen update(DataOpen data) {
		return dataOpenRepository.save(data);
	}

}
