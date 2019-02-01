package org.greenwave.business.interfaces;

import java.util.List;

import org.greenwave.model.DataDomain;

public interface DataMeasureBusiness {
	
	public List<String> listDataTypeFromOther(String category);
	public List<String> listDataTypeByDomain(DataDomain domain);
	public List<String> listDataTypeByDomainAndCategory(String category,DataDomain domain);
	
	public Double findRatioASW(String substance);
	public Double findRatioDSW(String substance);
	
	public Double findRatioODSW(String substance);
	public Double findRatioOASW(String substance);
	
	public Double getMin(String substance);
	public Double getMax(String substance);
	public Long getIdFromName(String name);
}
