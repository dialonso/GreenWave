package org.greenwave.controller.admin;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.greenwave.business.DataCategoryBusinessImpl;
import org.greenwave.business.interfaces.DataBusiness;
import org.greenwave.business.interfaces.DataMeasureBusiness;
import org.greenwave.business.interfaces.LocalizationBusiness;
import org.greenwave.controller.adapter.DataAdapter;
import org.greenwave.controller.form.ChartForm;
import org.greenwave.controller.form.DataOpenForm;
import org.greenwave.controller.form.DateForm;
import org.greenwave.controller.form.ExportDataForm;
import org.greenwave.model.Data;
import org.greenwave.model.DataCategory;
import org.greenwave.model.DataDomain;
import org.greenwave.model.Localization;
import org.greenwave.service.ChartService;
import org.greenwave.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;

import org.greenwave.business.interfaces.DataCategoryBusiness;

@Controller
public class DataAirController {
	
    @Autowired
    DataBusiness dataBusiness;
    
	@Autowired
	DataMeasureBusiness dataMeasuresBusiness;
	
	@Autowired
	LocalizationBusiness localizationBusiness;
	
	@Autowired
	DataCategoryBusiness DataCategoryBusiness;
	
	@Autowired
	ChartService chartService;
	
	@Autowired
	private DataService dataService;

	@RequestMapping("/dataExportAir")
	public String export(Map<String, Object> model, HttpSession session, ExportDataForm exportDataForm) {
		
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		DataDomain[] dd = DataDomain.values();
		System.out.println(dd[0]);//NOT CONCIDERING BY REqUEST
		List<String> ot_measures = dataMeasuresBusiness.listDataTypeByDomainAndCategory("OTHER_SUBSTANCES",dd[1]);
		List<String> po_measures = dataMeasuresBusiness.listDataTypeByDomainAndCategory("POLLUTING_SUBSTANCES",dd[1]);
		List<String> departments = localizationBusiness.findAllDepartement();
		
		model.put("username", username);
		model.put("ot_measures", ot_measures);
		model.put("po_measures", po_measures);
		model.put("departments_names", departments);			
		
		return "/admin/data/exportAir";
	}
	
	@RequestMapping("/dataExportAirValidate")
	public String validateExport(Map<String, Object> model, HttpSession session,HttpServletResponse response, ExportDataForm exportDataForm) {
    	
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		
		model.put("username", username);	
		DataDomain[] dd = DataDomain.values();
		List<String> ot_measures = dataMeasuresBusiness.listDataTypeByDomainAndCategory("OTHER_SUBSTANCES",dd[1]);
		List<String> po_measures = dataMeasuresBusiness.listDataTypeByDomainAndCategory("POLLUTING_SUBSTANCES",dd[1]);
		List<String> departments = localizationBusiness.findAllDepartement();
		
		model.put("username", username);
		model.put("ot_measures", ot_measures);
		model.put("po_measures", po_measures);
		model.put("departments_names", departments);
		
		if (exportDataForm.getStartDate() == null || exportDataForm.getEndDate() == null || exportDataForm.getDepartments() == null 
				|| exportDataForm.getFile_type() == null || exportDataForm.getOther_measures() == null || exportDataForm.getPolluting_measures() == null)
		{
			model.put("error_exists", "Un ou plusieurs champs n'ont pas été remplis.");
			return "/admin/data/exportAir";
		}
		
		List<String> data_names = new ArrayList<String>(exportDataForm.getOther_measures());
		data_names.addAll(exportDataForm.getPolluting_measures());
		Date sDate = exportDataForm.getStartDate();
		Date eDate = exportDataForm.getEndDate();
		List<Data> datas_fetched = dataBusiness.findBetweenDatesPerTypeAndDepartment(exportDataForm.getDepartments(), sDate, eDate, data_names);
		
		if (datas_fetched == null || datas_fetched.size() == 0)
		{
			model.put("info", "Aucune donnée n'a pu être récupéré pour la période spécifiée.");
			return "/admin/data/exportAir";
		}
		
		Date today = new Date();
		SimpleDateFormat d_f = new SimpleDateFormat("dd-mm-yyyy_hh-mm-ss");
		if (exportDataForm.getFile_type().contains("Excel"))
		{
			String filename = "export_" + d_f.format(today) + ".xlsx";
			
			XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("GreenWave Data");
	         
	        List<String[]> datas = new ArrayList<String[]>();
	        String[] header = {"ID", "Type", "Valeur", "Date", "Département", "Latitude", "Longitude", "Utilisateur"};
	        datas.add(header);
	        
	        for (int i = 0; i < datas_fetched.size(); i++){
		    	String [] entries = {datas_fetched.get(i).getId_data().toString(), 
		    					   datas_fetched.get(i).getType(),
		    					   String.valueOf(datas_fetched.get(i).getValue()),
		    					   datas_fetched.get(i).getMeasure_date().toString(),
		    					   datas_fetched.get(i).getLocalization_data().getDepartment(),
		    					   String.valueOf(datas_fetched.get(i).getLocalization_data().getLatitude()),
		    					   String.valueOf(datas_fetched.get(i).getLocalization_data().getLongitude()),
		    					   null};
		    	datas.add(entries);
	        }
	        int rowCount = 0;
	         
	        for (String[] aData : datas) {
	            Row row = sheet.createRow(++rowCount);
	             
	            int columnCount = 0;
	             
	            for (String field : aData) {
	                Cell cell = row.createCell(++columnCount);
	                cell.setCellValue((String) field);
	            }		             
	        }		         
	         
	        try (FileOutputStream outputStream = new FileOutputStream(filename)) {
	            workbook.write(outputStream);
	        } catch (FileNotFoundException e) {					
				e.printStackTrace();
				model.put("error_exists", "Erreur lors de la création du fichier.");
				return "/admin/data/exportAir";
			} catch (IOException e) {
				e.printStackTrace();
				model.put("error_exists", "Erreur lors de l'écriture du fichier.");
				return "/admin/data/exportAir";
			}
	        
			model.put("success", "Données exportées avec succès, le fichier " + filename + " a été créé.");
		}
		else if(exportDataForm.getFile_type().contains("CSV"))
		{
			String filename = "export_" + d_f.format(today) + ".csv";
		    CSVWriter writer;
		
			try {
				writer = new CSVWriter(new FileWriter(filename), '\t');
				String[] header = {"ID", "Type", "Valeur", "Date", "Département", "Latitude", "Longitude", "Utilisateur"};
				writer.writeNext(header);
				
			    // feed in your array (or convert your data to an array)
			    for (int i = 0; i < datas_fetched.size(); i++){
			    	String[] entries = {datas_fetched.get(i).getId_data().toString(), 
			    					   datas_fetched.get(i).getType(),
			    					   String.valueOf(datas_fetched.get(i).getValue()),
			    					   datas_fetched.get(i).getMeasure_date().toString(),
			    					   datas_fetched.get(i).getLocalization_data().getDepartment(),
			    					   String.valueOf(datas_fetched.get(i).getLocalization_data().getLatitude()),
			    					   String.valueOf(datas_fetched.get(i).getLocalization_data().getLongitude()),
			    					   null};
			    	writer.writeNext(entries);
			    	
			    }
			    
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				model.put("error_exists", "Erreur lors de l'écriture du fichier .csv. Réessayez plus tard !");
				return "/admin/data/exportAir";
			}

			model.put("success", "Données exportées avec succès, le fichier " + filename + " a été créé.");
		}
		
		else
		{
			model.put("error_exists", "Type de fichier invalide.");
			return "/admin/data/exportAir";
		}
		
		return "/admin/data/exportAir";
	}
	
	@RequestMapping("/mapInteractiveAir")
	public String interactiveMap(Map<String, Object> model, HttpSession session, DateForm dateForm) {
		
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		model.put("username", username);
		DataDomain[] dd = DataDomain.values();
		List<Data> datas_tab = dataBusiness.findLastDataPerCoords(dd[1]);
		
		List<String> ot_measures = dataMeasuresBusiness.listDataTypeByDomain(dd[1]);
		
		model.put("ot_measures", ot_measures);
		
		// convert to JSON for client-side processing
		String json_data = dataService.datasToJson(datas_tab);
		model.put("datas", json_data);
		
		return "/admin/map/interactiveAir";
	}
	
	@RequestMapping("/mapChangeAir")
	public String changeMap(Map<String, Object> model, HttpSession session, DateForm dateForm) {
    	
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		model.put("username", username);
		
		try{
			Date sDate = dateForm.getStartDate();
			Date eDate = dateForm.getEndDate();
			
			model.put("firstdate", sDate);
			model.put("lastdate", eDate);						
			DataDomain[] dd = DataDomain.values();
			List<Data> datas_tab = dataBusiness.findBetweenDate(sDate, eDate,dd[1]);
			List<String> ot_measures = dataMeasuresBusiness.listDataTypeByDomain(dd[1]);
			
			model.put("ot_measures", ot_measures);


			
			// convert to JSON for client-side processing
			String json_data = dataService.datasToJson(datas_tab);
			model.put("datas", json_data);
			

			
			model.put("firstdate", sDate);
			model.put("lastdate", eDate);
			
			// concatene both lists (polluting and non polluting)
			List<String> data_names = new ArrayList<String>(dateForm.getOt_measures());
			
			if (dateForm.getMethod().equals("last"))
			{
				// user wants the last data taken for each location (disregard date fields)
				datas_tab = dataBusiness.findLastDataPerCoordsAndType(data_names);
				// convert to JSON for client-side processing
				json_data = dataService.datasToJson(datas_tab);
				model.put("datas", json_data);
			}
			
			else if(dateForm.getMethod().equals("all"))
			{
				// all: user wants all the data taken for each location between two dates
				// conc: user wants the concentrations (pin color will vary whether the value is OK or dangerous)
				datas_tab = dataBusiness.findBetweenDatesPerType(data_names, sDate, eDate);
				
				// we need an array with each location represented once (for easier processing client-side), sending the Map object to thymeleaf would be a nightmare
				List<Double[]> locs = new ArrayList<Double[]>();
				
				for (int i = 0; i < datas_tab.size(); i++){
					Double[] localization = {datas_tab.get(i).getLocalization_data().getLatitude(), datas_tab.get(i).getLocalization_data().getLongitude()};
					
					// we create custom code to check if the array of double is in our array of locations (not the map, the array)
					boolean exists = false;
					for (int j = 0; j < locs.size(); j++)
					{
						if (locs.get(j)[0].equals(localization[0]) && locs.get(j)[1].equals(localization[1]))
						{
							exists = true;
							break;
						}							
					}
					
					// if it's not in the array, we simply add it
					if (!exists)
						locs.add(localization);
				}
				
				// convert data to json (since we have an array of Data object)
		        json_data = dataService.datasToJson(datas_tab);
		        // send the jsonified data and the array of locations
				model.put("datas", json_data);
				model.put("locs", locs);
			}

		    model.put("method", dateForm.getMethod());
			
			
			if (datas_tab.size() == 0)
				model.put("info", "Aucune donnée pour la période selectionnée.");			
		}catch(Exception e){
			//model.put("error_exists", "Type de donnée invalide, merci de saisir une date au format dd/mm/yyyy.");
		}

		return "/admin/map/interactiveAir";
	}
	
	// Register type adapter (Cf. class DataAdapter) and jsonify our list of Data
	// Takes a list of Data, returns a Json string with the properties specified in the DataAdapter class
	public String datasToJson(List<Data> datas) {  
	    GsonBuilder gsonBuilder = new GsonBuilder();
	    Gson gson = gsonBuilder.registerTypeAdapter(Data.class, new DataAdapter()).create();
	    return gson.toJson(datas);
	}  
	
	@RequestMapping("/airCharts")
	public String apiSend(Map<String, Object> model, HttpSession session,ChartForm chartDateForm) {
		
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		model.put("username", username);
		
		DataDomain[] dd = DataDomain.values();;
		List<String> typesPolluting= new ArrayList<String>();
		typesPolluting =  dataMeasuresBusiness.listDataTypeByDomainAndCategory("POLLUTING_SUBSTANCES", dd[1]);

		List<String> typesClassics= new ArrayList<String>();
		typesClassics = dataMeasuresBusiness.listDataTypeByDomainAndCategory("OTHER_SUBSTANCES", dd[1]);

		List<String> locList= new ArrayList<String>();
		locList.addAll(localizationBusiness.findAllDepartement());
		
		
		model.put("deplist", locList);
		model.put("typeslistclassics",typesClassics);
		model.put("typeslistpolluting",typesPolluting);
		List<DataCategory> cat= DataCategoryBusiness.findByDomain(dd[1]);
		model.put("type",cat);
		
		return "/admin/charts/chartsAir";
	}
	
	@RequestMapping("/changeAirCharts")
	public String changeAirCharts(Map<String, Object> model,@Valid ChartForm chartDateForm, HttpSession session) {
		
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		model.put("username", username);
		
		DataDomain[] dd = DataDomain.values();;
		List<String> typesPolluting= new ArrayList<String>();
		typesPolluting =  dataMeasuresBusiness.listDataTypeByDomainAndCategory("POLLUTING_SUBSTANCES", dd[1]);

		List<String> typesClassics= new ArrayList<String>();
		typesClassics = dataMeasuresBusiness.listDataTypeByDomainAndCategory("OTHER_SUBSTANCES", dd[1]);

		List<String> locList= new ArrayList<String>();
		locList.addAll(localizationBusiness.findAllDepartement());
		
		
		model.put("deplist", locList);
		model.put("typeslistclassics",typesClassics);
		model.put("typeslistpolluting",typesPolluting);
		model.put("waterCatId", DataCategoryBusiness.findByDomain(dd[2]));
		List<DataCategory> cat= DataCategoryBusiness.findByDomain(dd[1]);
		model.put("type",cat);
		
		// get data from forms info
		String theData=chartDateForm.getTypeData();
		model.put("donnees", theData);
		double[] datas = new double[12];
		double[] minList= new double[12];
		double[] maxList= new double[12];
		
		try{
			List<Localization> idsSelected =  new ArrayList<Localization>();
			idsSelected=localizationBusiness.listIdLocationFromDepName(chartDateForm.getPlace());
			model.put("ids",idsSelected);	
			DataCategory dataCat = DataCategoryBusiness.findById(chartDateForm.getWaterCatId());
			String[] label= null;
			label=chartService.labelForChartsGenerator(chartDateForm.getStartDate(),chartDateForm.getEndDate());
			model.put("label", label);

			if(dataBusiness.countData(theData, chartDateForm.getStartDate(), chartDateForm.getEndDate(),dataCat, idsSelected)!=0){ 
				//if there is data we calculate values
			
				datas=chartService.avgCalculator(chartDateForm.getStartDate(),chartDateForm.getEndDate() , dataCat, theData,label, idsSelected);
				minList=chartService.minCalculator(chartDateForm.getStartDate(),chartDateForm.getEndDate() , dataCat, theData,label, idsSelected);
				maxList=chartService.maxCalculator(chartDateForm.getStartDate(),chartDateForm.getEndDate() , dataCat, theData,label, idsSelected);
				Integer[]numberdatas = chartService.countDatasperMonth(chartDateForm.getStartDate(),chartDateForm.getEndDate() , dataCat, theData,label, idsSelected);
				model.put("mins", minList);
				model.put("maxs", maxList);
				model.put("dataarrayavg",datas);
				model.put("number", numberdatas);
				
			/*	if(acceptableRatio != null && dangerousRatio != null){
					//if ratio are set for this data we can calculate them
					model.put("taux", true); //put to true to allow display of the link in the view 
					double[] tauxValue = new double[3];
					Arrays.fill(tauxValue, 0);
					double[] numberForTaux = new double[3];
					numberForTaux=tauxCalculator(chartDateForm.getStartDate(),chartDateForm.getEndDate(),dataCat,chartDateForm.getTypeData());
					model.put("numberForTaux", numberForTaux);
					tauxValue=percentageCalculator(numberForTaux);
					model.put("valuesForTaux", tauxValue);
				}*/
			}
			else {
				//else we put null value in the model (default value use in the html)
				
				model.put("dataarrayavg", null);
				model.put("mins", null);
				model.put("maxs", null);
			}
		}
		catch (Exception e)
		{
			model.put("info", "Aucune donnée à afficher pour l'utilisateur connecté.");
		}
		
		return "/admin/charts/chartsAir";
	}
}
