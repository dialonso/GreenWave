package org.greenwave.controller.rest;

import java.util.List;

import org.greenwave.business.interfaces.DataBusiness;
import org.greenwave.business.interfaces.LocalizationBusiness;
import org.greenwave.model.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.greenwave.model.Data;

@RestController
public class MapRestController {
	
	@Autowired 
	LocalizationBusiness localizationBusiness;
	
	@Autowired
	DataBusiness dataBusiness;
	
	@RequestMapping(value = "/MAP", method = RequestMethod.GET)
	public Data MapLocation(@RequestBody double latitude, @RequestBody double longitude) {
		List<Localization> loc=localizationBusiness.getLngLatId();
		
		for(Localization localization: loc) {
			double lat=localization.getLatitude();
			double longi=localization.getLongitude();
			double theta = longitude - longi;
			double dist = Math.sin(deg2rad(latitude)) * Math.sin(deg2rad(lat)) + Math.cos(deg2rad(latitude)) * Math.cos(deg2rad(lat)) * Math.cos(deg2rad(theta));
			dist = Math.acos(dist);
			dist = rad2deg(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
		  
			if(dist<= 10) {
			  Long id=localization.getId_localization();
			  Data data=dataBusiness.findByIdloc(id);
			  return data;
			}
		}
	
		return null;
	}
	
	private double rad2deg(double rad) 
	{
		return (rad * 180.0 / Math.PI);
	}
	
	private double deg2rad(double deg) 
	{
		return (deg * Math.PI / 180.0);
	}

}
