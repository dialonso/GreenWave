package org.greenwave.controller.rest;

import org.greenwave.business.interfaces.DataBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.greenwave.model.Data;

@RestController
public class FormRestController {
	
	@Autowired
	private DataBusiness dataRepository;
	
	@RequestMapping(value="/Form",method=RequestMethod.POST)
	public String AddFormulaire(@RequestBody Data data) {
		if(data != null) {
			dataRepository.create(data);
		return "Succes Creation";
	
		}else
			return "Error Creation";
	}

}
