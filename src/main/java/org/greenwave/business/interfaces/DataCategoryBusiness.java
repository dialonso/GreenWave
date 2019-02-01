package org.greenwave.business.interfaces;

import java.util.List;

import org.greenwave.model.DataCategory;
import org.greenwave.model.DataDomain;

public interface DataCategoryBusiness {
	
	public DataCategory findById(Long id);
	
	public List<DataCategory> findByDomain(DataDomain domain);

}
