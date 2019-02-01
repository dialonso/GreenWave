package org.greenwave.business.interfaces;

import java.util.List;

import org.greenwave.model.Localization;

public interface LocalizationBusiness {
	
	public Localization create(Localization localization);

	public Localization update(Long id);

	public void delete(Long id);
	
	public Localization findByGpsCoords(double lat, double lng);
	
	public List<String> findAllDepartement();
	
	public List<Localization> listIdLocationFromDepName(String depName);
	
	public int findAllCountries();
	
	public List<Localization> getLngLatId();

}
