package org.greenwave.controller;

import java.util.List;
import java.util.Map;

import org.greenwave.controller.form.DataOpenForm;
import org.greenwave.controller.form.wikiForm;
import org.greenwave.model.DataDomain;
import org.greenwave.model.Wiki;
import org.greenwave.repository.WikiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping(value= "/Wiki")
public class WikiController {
	
	@Autowired
	private WikiRepository wikiRepository;
	
	@RequestMapping(value="/wikiLike")
	public String wikiLike(Map<String, Object> model ,wikiForm wikiForm) {
		List<Wiki> doc=wikiRepository.findByWikiDomain(DataDomain.values()[0]);
		model.put("name", doc);
		
		List<Wiki> docAir=wikiRepository.findByWikiDomain(DataDomain.values()[1]);
		model.put("nameAir", docAir);
		
		List<Wiki> docEarth=wikiRepository.findByWikiDomain(DataDomain.values()[2]);
		model.put("nameEarth", docEarth);
		
		String test = wikiForm.getName();
		System.out.println(test);
		model.put("mesureSel",test);
		
		List<Wiki> wiki =wikiRepository.findIndicator(test);
 		model.put("wikiInfoList", wiki);
		
		return "wiki";
		
	}
	
	
	@RequestMapping(value="/wiki")
	public String wiki(Map<String, Object> model ,wikiForm wikiForm) {
		List<Wiki> doc=wikiRepository.findByWikiDomain(DataDomain.values()[0]);
		model.put("name", doc);
		
		List<Wiki> docAir=wikiRepository.findByWikiDomain(DataDomain.values()[1]);
		model.put("nameAir", docAir);
		
		List<Wiki> docEarth=wikiRepository.findByWikiDomain(DataDomain.values()[2]);
		model.put("nameEarth", docEarth);
		
		String test = wikiForm.getName();
		System.out.println(test);
		model.put("mesureSel",test);
		
		Wiki wiki =wikiRepository.findByName(test);
 		model.put("wikiInfo", wiki);
		
		return "wiki";
		
	}


}
