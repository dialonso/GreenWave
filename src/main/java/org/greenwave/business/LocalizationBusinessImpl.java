package org.greenwave.business;

import java.util.List;

import org.greenwave.repository.LocalizationRepository;
import org.greenwave.business.interfaces.LocalizationBusiness;
import org.greenwave.model.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalizationBusinessImpl implements LocalizationBusiness {
	
	@Autowired
	private LocalizationRepository localizationRepository;

	@Override
	public Localization create(Localization localization) {
		return localizationRepository.save(localization);
	}

	@Override
	public Localization update(Long id) {
		Localization loc = localizationRepository.getOne(id);
		// loc.setRegion(region);
		// loc.setComplete_address(complete_address);
		// loc.setCountry(country);
		// loc.setLatitude(latitude);
		// loc.setLongitude(longitude);
		// loc.setDepartment(department);
		return localizationRepository.save(loc);
	}

	@Override
	public void delete(Long id) {
		localizationRepository.delete(id);		
	}

	@Override
	public Localization findByGpsCoords(double lat, double lng) {
		Localization loc = localizationRepository.getLocByGPSCoords(lat, lng);
		return loc;
	}

	@Override
	public List<String> findAllDepartement() {
		return localizationRepository.getAllLoc();
	}

	@Override
	public List<Localization> listIdLocationFromDepName(String depName) {
		return localizationRepository.getIdFromDepName(depName);
	}

	@Override
	public int findAllCountries() {
		return localizationRepository.countAllCountries();
	}
	
	@Override
	public List<Localization> getLngLatId() {
		return localizationRepository.getLngLatId(); 
	}

}

