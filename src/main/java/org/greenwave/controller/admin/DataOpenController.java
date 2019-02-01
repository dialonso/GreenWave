package org.greenwave.controller.admin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.greenwave.business.interfaces.AccountBusiness;
import org.greenwave.business.interfaces.AdminBusiness;
import org.greenwave.business.interfaces.DataBusiness;
import org.greenwave.business.interfaces.DataCategoryBusiness;
import org.greenwave.business.interfaces.DataMeasureBusiness;
import org.greenwave.business.interfaces.DataOpenBusiness;
import org.greenwave.business.interfaces.LocalizationBusiness;
import org.greenwave.business.interfaces.OrganizationBusiness;
import org.greenwave.business.interfaces.SimpleUserBusiness;
import org.greenwave.controller.adapter.LocalizationAdapter;
import org.greenwave.controller.form.DataOpenForm;
import org.greenwave.model.Data;
import org.greenwave.model.DataDomain;
import org.greenwave.model.DataOpen;
import org.greenwave.model.Localization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Controller
public class DataOpenController {
	
	@Autowired
	DataBusiness dataBusiness;
	
	@Autowired
	AdminBusiness adminBusiness;
	
	@Autowired
	SimpleUserBusiness simpleUBusiness;
	
	@Autowired
	DataCategoryBusiness dataCategoryBusiness;
	
	@Autowired
	OrganizationBusiness orgBusiness;
	
	@Autowired
	AccountBusiness accountBusiness;		

	@Autowired
	LocalizationBusiness locBusiness;
	
	@Autowired
	DataMeasureBusiness measBusiness;
	
	@Autowired
	DataOpenBusiness dataOpenBusiness;
	
	@RequestMapping("/dataOpen")
	public String apiSend(Map<String, Object> model, HttpSession session, DataOpenForm openDataForm) throws Exception {
	
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		java.util.List<String> roles= new ArrayList<>();
		boolean isAdmin = false;
		boolean isOrg = false;
		
		for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()) 
		{
			roles.add(ga.getAuthority());

			if (ga.getAuthority().equals("ROLE_ADMIN"))
				isAdmin = true;
			else if (ga.getAuthority().equals("ROLE_ORGANIZATION"))
				isOrg = true;
		}
		
	    model.put("username", username);

		Long idod = openDataForm.getId();
		
	    if(idod.equals(new Long(0))==false)
	    {
	    	
	    	DataOpen od = dataOpenBusiness.findById(idod);
			String urlBase =od.getUrl();
			System.out.println(urlBase);
			
			if(urlBase != null)
			{
				ArrayList retourOpenData = new ArrayList();
			
				DateFormat df = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss");
				int nbl =0;
				URL openaq = new URL(urlBase+"&date_from="+df.format(od.getDate_last_call()));
			
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(openaq.openStream(), "UTF-8"))) 
				{
					for (String line; (line = reader.readLine()) != null;) 
					{
				    	JSONObject obj = new JSONObject(line);
						JSONArray arr = obj.getJSONArray("results");
					
						for(int i=0;i<arr.length();i++)
			            {
			                JSONObject jsonObject1 = arr.getJSONObject(i);
			             
			                if(jsonObject1.has("coordinates")){
			                	JSONObject coord = new JSONObject(jsonObject1.optString("coordinates"));
			                	JSONObject date = new JSONObject(jsonObject1.optString("date"));
			                	Data dataadded = new Data();
			                	
			                	dataadded.setType(jsonObject1.optString("parameter"));//check if in base (get id too)
			                	dataadded.setValue(new Double(jsonObject1.optString("value")));
			                	
			                    Date dateobj = null;
			                
			                    dateobj = df.parse(date.optString("utc"));
			                	dataadded.setMeasure_date(dateobj);
			                	Long id;
			                	Localization loc = new Localization();
			                	Localization isthere = locBusiness.findByGpsCoords(new Double(coord.optString("latitude")), new Double(coord.optString("longitude")));
			                	
			                	if(isthere == null) 
			                	{
			                		HttpRequestsController httpReq = new HttpRequestsController();
			                		Localization loctemp = new Localization();
			                	
			                		String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+(jsonObject1.optString("location")).replaceAll("[\\s|\\u00A0]+", "%20")+"&key=AIzaSyCq5FZHcVvfCVLQJZI2L9XePNQD0V33_4g";
			        	        
			                		GsonBuilder gsonBuilder = new GsonBuilder();
			        	 		    Gson gson = gsonBuilder.registerTypeAdapter(Localization.class, new LocalizationAdapter()).create();
			        				String localization_infos = httpReq.sendGet(url);
			        		
			        				JSONObject objT = new JSONObject(localization_infos);
									JSONArray arrT = objT.getJSONArray("results");
								
			        				//System.out.println(localization_infos.length());
			        				if (arrT.length() == 0  )
			        				{
			        					continue;
			        				}
			        		
			        				loctemp = gson.fromJson(localization_infos, Localization.class);
			        				
		
			        				loc.setCountry(loctemp.getCountry());
			  
			        				loc.setDepartment(loctemp.getDepartment());
			        		
			        				loc.setRegion(loctemp.getRegion());
				                	loc.setAddress(jsonObject1.optString("location")+" "+jsonObject1.optString("city"));
				                	loc.setLatitude(new Double(coord.optString("latitude")));
				                	loc.setLongitude(new Double(coord.optString("longitude")));
				                	locBusiness.create(loc);
			                	}
			                	else 
			                	{
			                		loc =isthere;
			                	}
			                	dataadded.setLocalization_data(loc);
			                	dataadded.setDomain(DataDomain.values()[1]);
			                	
			                	dataadded.setCategory_data(dataCategoryBusiness.findByDomain(DataDomain.values()[1]).get(0));
			                	System.out.println("step 6");
			                	dataBusiness.create(dataadded);
			                	
			                	nbl =i;
			                	retourOpenData.add(" Paramètre : "+jsonObject1.optString("parameter")+" : "+jsonObject1.optString("value")+" position : lat:"+coord.optString("latitude")+" long:"+coord.optString("latitude"));
			                }
			            }
				    }
				  
				    Date date = new Date();
				    System.out.println(date);
				    od.setDate_last_call(date);
				    dataOpenBusiness.update(od); 
				    
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					model.put("messageOD","Erreur de traitement");
				}
			
				model.put("messageOD","Enregistrement de "+nbl+" lignes effecuté avec succès");
			}
		
	    }
	    
	    List<DataOpen> opendatas = dataOpenBusiness.findAll();
	    model.put("openList",opendatas);
	    
	    return "/admin/data/open";
	}
}
