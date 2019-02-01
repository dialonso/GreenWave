package org.greenwave.controller;

import java.util.Map;

import org.greenwave.business.interfaces.AdminBusiness;
import org.greenwave.business.interfaces.DataBusiness;
import org.greenwave.business.interfaces.DataMeasureBusiness;
import org.greenwave.business.interfaces.LocalizationBusiness;
import org.greenwave.business.interfaces.SimpleUserBusiness;
import org.greenwave.controller.form.ContactForm;
import org.greenwave.model.Data;
import org.greenwave.model.Localization;
import org.greenwave.web.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Controller
public class DefaultController {
	
	@Autowired
	DataBusiness dataBusiness;
	
	@Autowired
	DataMeasureBusiness dataMeasuresBusiness;
	
	@Autowired
	AdminBusiness adminBusiness;
	
	@Autowired
	SimpleUserBusiness simpleUBusiness;

	@Autowired
	LocalizationBusiness locBusiness;

    @GetMapping("/")
    public String index(Map<String, Object> model) {
    	int totalData = dataBusiness.countTotalData();
		int totalCountries = locBusiness.findAllCountries();
		int totalResearchers = adminBusiness.findAllReasearchers() + simpleUBusiness.findAllReasearchers();
	
			
		String[] labels = {"pays", "chercheur", "mesure"};
		if (totalData > 1)
			labels[2] = "mesures";
		if (totalResearchers > 1)
			labels[1] = "chercheurs";
		
		model.put("labels", labels);
		model.put("total_data", totalData);
		model.put("total_countries", totalCountries);
		model.put("total_researchers", totalResearchers);

        return "/index";
    }
    
    @GetMapping("/contact")
    public String contact(Map<String, Object> model, ContactForm contactForm) {
        return "/contact";
    }
    
    @GetMapping("publicArea")
    public String publicArea() {

        return "/publicArea";
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String error() {
        return "/error";
    }

}
