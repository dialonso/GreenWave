package org.greenwave.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.greenwave.business.interfaces.AccountBusiness;
import org.greenwave.business.interfaces.AdminBusiness;
import org.greenwave.business.interfaces.LocalizationBusiness;
import org.greenwave.business.interfaces.OrganizationBusiness;
import org.greenwave.business.interfaces.SimpleUserBusiness;
import org.greenwave.repository.AccountRepository;
import org.greenwave.model.Account;
import org.greenwave.model.Admin;
import org.greenwave.model.Localization;
import org.greenwave.model.Organization;
import org.greenwave.model.Role;
import org.greenwave.controller.adapter.LocalizationAdapter;
import org.greenwave.controller.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Controller
public class AdminController {
	
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountBusiness accountBusiness;
	
	@Autowired
	AdminBusiness adminBusiness;
	
	@Autowired
	SimpleUserBusiness simpleUserBusiness;
	
	@Autowired
	OrganizationBusiness organizationBusiness;
	
	@Autowired
	LocalizationBusiness locBusiness;
	
	@Secured(value={"ROLE_ORGANIZATION"})
	@RequestMapping(value = "/adminForm", method = RequestMethod.GET)
	public String form(Map<String, Object> model, UserForm userForm, HttpSession session) {
		
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		
		model.put("username", username);
		
		return "/admin/admins/form";
	}
	
	@Secured(value={"ROLE_ORGANIZATION"})
	@RequestMapping(value = "/adminList", method = RequestMethod.GET)
	public String list(Map<String, Object> model, HttpSession session) {
		
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		
		model.put("username", username);
		String department = "";
		
		try{
			department = organizationBusiness.find(username).getLocalization_org().getDepartment();
			System.out.println(organizationBusiness.find(username).getName());
			Page<Admin> pageAdmins = adminBusiness.findByOrganization(organizationBusiness.find(username).getName(),0,8);
			model.put("listAdmins", pageAdmins.getContent());
		}catch (Exception e) {
			model.put("exception",e);
		}		
		
		return "/admin/admins/list";
	}
	
	@Secured(value={"ROLE_ORGANIZATION"})
	@RequestMapping(value = "/adminDelete", method = RequestMethod.GET, produces = "application/json")
	public String delete_admin(@RequestParam("id") int id,Map<String, Object> model) {
		System.out.println("TEST TEST TEST");
		System.out.println(id);
		Long idUser = new Long(id);
		
		JsonObject jObj = new JsonObject();
		Admin admin = adminBusiness.findById(idUser);
		
		if (admin == null)
		{
			jObj.addProperty("success", false);
			return jObj.toString();
		}
		
		Account acc = accountBusiness.check(admin.getAccount().getLogin());
		
		if (acc == null)
		{
			jObj.addProperty("success", false);
			return jObj.toString();
		}
		try {
			acc.setRoles(null);
			accountBusiness.update(acc.getId_account(), acc);
			acc = accountBusiness.findByUsername(acc.getLogin());
			
			accountBusiness.delete(acc.getId_account());
			adminBusiness.delete(idUser);
		}catch (Exception e) {
			jObj.addProperty("success", false);
					}	
		
		jObj.addProperty("success", true);

		return "/admin/admins/list";
	}

	@Secured(value={"ROLE_ORGANIZATION"})
    @RequestMapping(value = "/createAdminValidate", method = RequestMethod.POST)
    public String validateUser(@Valid UserForm userForm, BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
        	// if the fields are invalid (Cf. conditions in UserForm class)
            return "/admin/admins/form";
        }   
        
        SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		
		model.addAttribute("username", username);
		
		if (userForm.getAdress() == null || userForm.getConfirmPassword() == null || userForm.getForName() == null
				|| userForm.getLastName() == null || userForm.getOccup() == null 
				|| userForm.getPassword() == null || userForm.getUsername() == null
				|| userForm.getOrga() == null)
		{
        	model.addAttribute("error_exists", "Veuillez saisir des données valides pour chaque champ.");
        	return "/admin/admins/form";
		}
		
		if (userForm.getAdress().trim().length() != userForm.getAdress().length() 
				|| userForm.getConfirmPassword().trim().length() != userForm.getConfirmPassword().length() 
				|| userForm.getForName().trim().length() != userForm.getForName().length() 
				|| userForm.getLastName().trim().length() != userForm.getLastName().length() 
				|| userForm.getOccup().trim().length() != userForm.getOccup().length() 
				|| userForm.getPassword().trim().length() != userForm.getPassword().length() 
				|| userForm.getUsername().trim().length() != userForm.getUsername().length()
				|| userForm.getOrga().trim().length() != userForm.getOrga().length())
		{
        	model.addAttribute("error_exists", "Vous ne pouvez pas saisir d'espace.");
        	return "/admin/admins/form";
		}
		
        Account acc;
        try{
        	// check if username already taken
	        acc = accountRepository.checkAccount(userForm.getUsername().trim());
        }
        catch(Exception e){
        	// SQL error
        	model.addAttribute("error_exists", e.toString());
        	return "/admin/admins/form";
        }	               
        
        if (acc != null)	
        {
        	// username already taken
        	model.addAttribute("error_exists", "Ce nom d'utilisateur est déjà utilisé !");
        	return "/admin/admins/form";
        }
        
        if (!userForm.getPassword().trim().equals(userForm.getConfirmPassword().trim()))
        {
        	model.addAttribute("error_exists", "Les mots de passe ne sont pas identiques !");
        	return "/admin/admins/form";
        }
        
        // Insertion of admin in database	        
        Organization org = organizationBusiness.find(username);
        Role r = new Role("ADMIN", "ADMINISTRATOR ROLE");
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userForm.getPassword().trim());
        
        Account account =new Account(userForm.getUsername().trim(),hashedPassword, new Date(), new ArrayList<Role>(){{
        	add(r);
        }});
        
        // instantiate localization class
        Localization loc = new Localization();
        // instantiate HttpRequests class for GMap requests
        HttpRequestsController httpReq = new HttpRequestsController();
        
        try {
        	// url for reverse geocoding using the address given in the form
        	String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+userForm.getAdress().replaceAll(" ","%20")+"&sensor=false&key=AIzaSyAQxIwyIQ3OOmkIhjaelmoeo2NzlIs6PmM";
        		System.out.println(url);
        	// Gson handler

        	GsonBuilder gsonBuilder = new GsonBuilder();
        	
        	// use custom Json deserializer from LocalizationAdapter
 		    Gson gson = gsonBuilder.registerTypeAdapter(Localization.class, new LocalizationAdapter()).create();
 		    
 		    // make the requests
			String localization_infos = httpReq.sendGet(url);
			System.out.println(localization_infos);
			if (localization_infos == "error")
			{
				model.addAttribute("error_exists", "Problème de géolocalisation. Réessayez plus tard !");
				return "/admin/admins/form";
			}
			// get the infos and populate the Localization instance
			loc = gson.fromJson(localization_infos, Localization.class);
			
		} catch (Exception e) {
			// API error or invalid address, abort admin registration
			e.printStackTrace();
        	model.addAttribute("error_exists", "Adresse invalide ou problème de géolocalisation. Réessayez plus tard !");
        	return "/admin/admins/form";
		}        
        
        // this new Localization instance will serve as a test to see if the localization we got from the reverse geocoding already exists
        // this ensures that we have unique localization in our DB
        // we use GPS coordinates to be ensure unicity
        Localization loc_exists = locBusiness.findByGpsCoords(loc.getLatitude(), loc.getLongitude());
	        
        // if the localization doesn't exist, add it in DB
        if (loc_exists == null)
        	locBusiness.create(loc);
        // if it already exists we make our new SimpleUser instance point to the existing one
        else
        	loc = loc_exists;
        
        Admin admin = new Admin(userForm.getForName().trim(), userForm.getLastName().trim(), userForm.getOccup().trim(), loc, account, org);
        
        accountBusiness.create(account);
        adminBusiness.create(admin);
        
        model.addAttribute("success", "Administrateur enregistré avec succès.");
        return "/admin/admins/form";
    }
	
	@Bean
	public static PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
}

