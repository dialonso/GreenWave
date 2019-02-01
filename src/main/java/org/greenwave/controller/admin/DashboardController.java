package org.greenwave.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.greenwave.business.interfaces.AccountBusiness;
import org.greenwave.business.interfaces.AdminBusiness;
import org.greenwave.business.interfaces.DataBusiness;
import org.greenwave.business.interfaces.OrganizationBusiness;
import org.greenwave.business.interfaces.SimpleUserBusiness;
import org.greenwave.model.Admin;
import org.greenwave.model.DataDomain;
import org.greenwave.model.Organization;
import org.greenwave.model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {		
	
	@Autowired
	DataBusiness dataBusiness;

	@Autowired
	AdminBusiness adminBusiness;
	
	@Autowired
	SimpleUserBusiness simpleUBusiness;
	
	@Autowired
	OrganizationBusiness orgBusiness;
	
	@Autowired
	AccountBusiness accountBusiness;	

	@GetMapping("/dashboard")
    public String dashboard(Map<String, Object> model, HttpSession session) {
        
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
		
		// Numbers displayed on the dashboard pages
	    Admin user = null;
	    SimpleUser s_user = null;
	    Organization org = null;
	    
		if (isAdmin)
			user = adminBusiness.findByIdAccount(username);
		else if (isOrg)
			org = orgBusiness.find(username);
		else
			s_user = simpleUBusiness.findByIdAccount(username);
		
	    String department = "";
		String region = "";
		
	    if (user != null){
	    	department = user.getLocalization_data().getDepartment();
	    	region = user.getLocalization_data().getRegion();
	    }

	    else if(s_user != null){
	    	department = s_user.getLocalization_data().getDepartment();
	    	region = s_user.getLocalization_data().getRegion();
	    }
	    
	    else if(org != null){
	    	department = org.getLocalization_org().getDepartment();
	    	region = org.getLocalization_org().getRegion();
	    }
	    
	    model.put("dep", region);
	    
	    int measures = 0, nb_admins_in_department = 0, nb_users_in_department = 0, nb_org_in_department = 0;
	    if (department != "")
	    {
		    measures = dataBusiness.countByDepartment(department);
		    nb_admins_in_department  = adminBusiness.countInDepartment(department);
		    nb_users_in_department = simpleUBusiness.countInDepartment(department);
		    nb_org_in_department = orgBusiness.countInDepartment(department);
	    }
	    
		int numbers[] = {measures, nb_admins_in_department, nb_users_in_department, nb_org_in_department};
		model.put("number", numbers);
		
		// Labels for the dashboard numbers
		String label_measures = "mesure";
		if (measures > 1)
			label_measures = "mesures";
		
		String label_admins = "administrateur";
		if (nb_admins_in_department > 1)
			label_admins = "administrateurs";
		
		String label_users = "utilisateur";
		if (nb_users_in_department > 1)
			label_users = "utilisateurs";
		
		String label_mairies = "mairie";
		if (nb_org_in_department > 1)
			label_mairies = "mairies";
		
		String dashboard_numbers[] = {label_measures, label_admins, label_users, label_mairies};
		model.put("dashboard_numbers", dashboard_numbers);
		
		// Titles of the graphs
		
		String userText = "Graphique d'évolution du nombre de mesures pour la région "+region;
		String accountText = "Graphique d'évolution du nombre de comptes pour la région "+region;
		String graph_titles[] = {userText,accountText};
		String graph_ids[] = {"userChart","accountChart"};
		
		String graph_captions[]={"Mesures effectuées ","Comptes créés"};

		
		model.put("graph_titles", graph_titles);
		model.put("graph_ids", graph_ids);
		model.put("graph_captions", graph_captions);
		
		int[] measureData = new int[12];
		String expectedPattern = "yyyy-MM-dd";
		SimpleDateFormat formatter = new SimpleDateFormat(expectedPattern);
		DataDomain[] dd = DataDomain.values();
		
		 try {
			measureData[0]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-01-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-01-31"),dd[0]).size();
			measureData[1]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-02-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-02-31"),dd[0]).size();
			measureData[2]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-03-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-03-31"),dd[0]).size();
			measureData[3]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-04-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-04-31"),dd[0]).size();
			measureData[4]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-05-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-05-31"),dd[0]).size();
			measureData[5]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-06-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-06-31"),dd[0]).size();
			measureData[6]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-07-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-07-31"),dd[0]).size();
			measureData[7]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-08-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-08-31"),dd[0]).size();
			measureData[8]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-09-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-09-31"),dd[0]).size();
			measureData[9]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-10-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-10-31"),dd[0]).size();
			measureData[10]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-11-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-11-31"),dd[0]).size();
			measureData[11]= dataBusiness.findBetweenDate(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-12-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-12-31"),dd[0]).size();		 
		 } catch (ParseException e) {
			e.printStackTrace();
		}
		 
		 int[] accountData = new int[12];
		 
		 try {
			accountData[0]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-01-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-01-31"));
			accountData[1]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-02-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-02-31"));
			accountData[2]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-03-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-03-31"));
			accountData[3]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-04-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-04-31"));
			accountData[4]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-05-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-05-31"));
			accountData[5]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-06-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-06-31"));
			accountData[6]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-07-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-07-31"));
			accountData[7]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-08-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-08-31"));
			accountData[8]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-09-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-09-31"));
			accountData[9]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-10-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-10-31"));
			accountData[10]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-11-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-11-31"));
			accountData[11]=accountBusiness.countBetweenDatas(formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-12-01"),formatter.parse(Calendar.getInstance().get(Calendar.YEAR)+"-12-31"));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		List<int[]> datas= new ArrayList<int[]>();
		datas.add(measureData);
		datas.add(accountData);
		
		model.put("data",datas);

		return "/admin/dashboard";
    }
	
}
