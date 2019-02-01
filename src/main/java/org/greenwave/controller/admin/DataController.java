package org.greenwave.controller.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.greenwave.business.interfaces.DataBusiness;
import org.greenwave.business.interfaces.DataCategoryBusiness;
import org.greenwave.business.interfaces.DataMeasureBusiness;
import org.greenwave.business.interfaces.LocalizationBusiness;
import org.greenwave.controller.adapter.LocalizationAdapter;
import org.greenwave.controller.form.DataForm;
import org.greenwave.model.Data;
import org.greenwave.model.DataCategory;
import org.greenwave.model.DataDomain;
import org.greenwave.model.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
public class DataController {
	
	@Autowired
	DataBusiness dataBusiness;

	@Autowired
	LocalizationBusiness locBusiness;
	
	@Autowired
	DataMeasureBusiness measBusiness;
	
	@Autowired
	DataCategoryBusiness categoryBusiness;

	@Secured(value={"ROLE_ADMIN"})
	@RequestMapping(value = "/dataDelete", method = RequestMethod.GET, produces = "application/json")
	public String delete(@RequestParam("id") int id) {
		
		Long idData = new Long(id);
		
		JsonObject jObj = new JsonObject();
		Data data = dataBusiness.findById(idData);
		
		if (data == null)
		{
			jObj.addProperty("success", false);
			return jObj.toString();
		}
		
		dataBusiness.delete(idData);
		
		jObj.addProperty("success", true);

		return jObj.toString();
	}

	@RequestMapping("/dataForm")
	public String form(Map<String, Object> model, HttpSession session, DataForm dataForm) throws UnsupportedEncodingException, IOException, ParseException {
		
		DataDomain[] dd = DataDomain.values();
		model.put("domains",dd);
		List<String> mw = measBusiness.listDataTypeByDomain(dd[0]);
		List<String> me = measBusiness.listDataTypeByDomain(dd[1]);
		List<String> ma = measBusiness.listDataTypeByDomain(dd[2]);
		
		model.put("measuresWater",mw);
		model.put("measuresEarth",me);
		model.put("mesuresAir", ma);
		
		List<DataCategory> catWater = categoryBusiness.findByDomain(dd[0]);
		List<DataCategory> catEarth = categoryBusiness.findByDomain(dd[1]);
		List<DataCategory> catAir = categoryBusiness.findByDomain(dd[2]);
		
		model.put("catWater",catWater);
		model.put("catEarth",catEarth);
		model.put("catAir", catAir);
		
		return "/admin/data/form";
	}
	
	@RequestMapping("/dataFormDatabase")
	public String formDatabase(Map<String, Object> model, HttpSession session, DataForm dataForm) throws UnsupportedEncodingException, IOException, ParseException {
		
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		java.util.List<String> roles= new ArrayList<>();
		boolean isAdmin = false;
		boolean isOrg = false;
		
		for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()){
			roles.add(ga.getAuthority());

			if (ga.getAuthority().equals("ROLE_ADMIN"))
				isAdmin = true;
			else if (ga.getAuthority().equals("ROLE_ORGANIZATION"))
				isOrg = true;
		}
		
	    model.put("username", username);
		
		
		DataDomain[] dd = DataDomain.values();
		model.put("domains",dd);
		
		List<String> mw = measBusiness.listDataTypeByDomain(dd[0]);
		List<String> me = measBusiness.listDataTypeByDomain(dd[1]);
		List<String> ma = measBusiness.listDataTypeByDomain(dd[2]);
		
		model.put("measuresWater",mw);
		model.put("measuresEarth",me);
		model.put("mesuresAir", ma);
		
		List<DataCategory> catWater = categoryBusiness.findByDomain(dd[0]);
		List<DataCategory> catEarth = categoryBusiness.findByDomain(dd[1]);
		List<DataCategory> catAir = categoryBusiness.findByDomain(dd[2]);
		
		model.put("catWater",catWater);

		model.put("catEarth",catEarth);
		model.put("catAir", catAir);
		

		DataDomain domaine = dataForm.getDomain();
		String mesure = dataForm.getMeasure();
		Double valeur = dataForm.getValue();
		
		String address = dataForm.getAdresse();
		
		Localization loc = new Localization();
		HttpRequestsController httpReq = new HttpRequestsController();
	    
	    try {
        	String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+address.replaceAll("[\\s|\\u00A0]+", "%20")+"&key=AIzaSyCq5FZHcVvfCVLQJZI2L9XePNQD0V33_4g";
        	GsonBuilder gsonBuilder = new GsonBuilder();
 		    Gson gson = gsonBuilder.registerTypeAdapter(Localization.class, new LocalizationAdapter()).create();
			String localization_infos = httpReq.sendGet(url);

			if (localization_infos == "error")
			{

			}
				
			loc = gson.fromJson(localization_infos, Localization.class);			
		} catch (Exception e) {
		
		}        
	    
	    Localization loc_exists = locBusiness.findByGpsCoords(loc.getLatitude(), loc.getLongitude());
        
	    // if the localization doesn't exist, add it in DB
        if (loc_exists == null)
        	locBusiness.create(loc);
        // if it already exists we make our new SimpleUser instance point to the existing one
        else
        	loc = loc_exists;
        
        Data data = new Data();
        data.setLocalization_data(loc);
        data.setDomain(domaine);
        data.setCategory_data(categoryBusiness.findById(dataForm.getCategory()));
        data.setType(mesure);
        
		//data.setUser("");
        
        data.setMeasure_date(new Date());
        data.setValue(valeur);
        dataBusiness.create(data);
        
        model.put("messageOD", "Donnée ajoutée avec succès");
		
        return "/admin/data/form";
	}

}
