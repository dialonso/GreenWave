package org.greenwave.business.interfaces;

import java.util.List;

import org.greenwave.model.DataOpen;

public interface DataOpenBusiness {
	
	public DataOpen findById(Long id);
	
	public List<DataOpen> findAll();
	
	public DataOpen update(DataOpen data);

}
