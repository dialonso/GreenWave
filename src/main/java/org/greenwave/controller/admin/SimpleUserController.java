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
import org.greenwave.controller.adapter.LocalizationAdapter;
import org.greenwave.controller.form.UserForm;
import org.greenwave.model.Account;
import org.greenwave.model.Admin;
import org.greenwave.model.Localization;
import org.greenwave.model.Role;
import org.greenwave.model.SimpleUser;
import org.greenwave.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class SimpleUserController {
	
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountBusiness accountBusiness;
	
	@Autowired
	LocalizationBusiness locBusiness;
	
	@Autowired
	SimpleUserBusiness simpleUserBusiness;
	
	@Autowired
	AdminBusiness adminBusiness;
	
	@Autowired
	OrganizationBusiness orgBusiness;
	
	@Autowired
	SimpleUserBusiness simpleUBusiness;

	@Secured(value={"ROLE_ADMIN","ROLE_ORGANIZATION"})
	@RequestMapping(value = "/userDelete", method = RequestMethod.GET, produces = "application/json")
	public String delete_user(@RequestParam("id") int id) {
		
		Long idUser = new Long(id);
		
		JsonObject jObj = new JsonObject();
		SimpleUser s_user = simpleUBusiness.findById(idUser);
		
		if (s_user == null)
		{
			jObj.addProperty("success", false);
			return jObj.toString();
		}
		
		Account acc = accountBusiness.check(s_user.getAccount().getLogin());
		
		if (acc == null)
		{
			jObj.addProperty("success", false);
			return jObj.toString();
		}
		
		accountBusiness.delete(acc.getId_account());
		simpleUBusiness.delete(idUser);
		
		jObj.addProperty("success", true);

		return jObj.toString();
	}
	
	@Secured(value={"ROLE_ADMIN","ROLE_ORGANIZATION"})
	@RequestMapping(value = "/userForm", method = RequestMethod.GET)
	public String form(Map<String, Object> model, UserForm userForm, HttpSession session) {

		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();	
		
		model.put("username", username);
		
		return "admin/users/form";
	}
	
	@Secured(value={"ROLE_ADMIN","ROLE_ORGANIZATION"})
    @RequestMapping(value = "/userSimpleValidate", method = RequestMethod.POST)
    public String formValidate(@Valid UserForm userForm, BindingResult bindingResult, Model model, HttpSession session) {
    	if (bindingResult.hasErrors()) {
    		// if the fields are invalid (Cf. conditions in UserForm class)
            return "admin/users/form";
        }
    	
    	// get username of connected admin
    	SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		
		boolean isAdmin = false;
		
		for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()){
			if (ga.getAuthority().equals("ROLE_ADMIN"))
				isAdmin = true;
		}
		
		if (userForm.getAdress() == null || userForm.getConfirmPassword() == null || userForm.getForName() == null
				|| userForm.getLastName() == null || userForm.getOccup() == null 
				|| userForm.getPassword() == null || userForm.getUsername() == null)
		{
        	model.addAttribute("error_exists", "Veuillez saisir des données valides pour chaque champ.");
        	return "admin/users/form";
		}
		
		if (userForm.getAdress().trim().length() != userForm.getAdress().length() 
				|| userForm.getConfirmPassword().trim().length() != userForm.getConfirmPassword().length() 
				|| userForm.getForName().trim().length() != userForm.getForName().length() 
				|| userForm.getLastName().trim().length() != userForm.getLastName().length() 
				|| userForm.getOccup().trim().length() != userForm.getOccup().length() 
				|| userForm.getPassword().trim().length() != userForm.getPassword().length() 
				|| userForm.getUsername().trim().length() != userForm.getUsername().length())
		{
        	model.addAttribute("error_exists", "Vous ne pouvez pas saisir d'espace.");
        	return "admin/users/form";
		}
		
    	Account acc;
    	
        try{
        	// check if username already taken
	        acc = accountRepository.checkAccount(userForm.getUsername().trim());
        }
        catch(Exception e){
        	// SQL error
        	model.addAttribute("error_exists", e.toString());
        	return "admin/users/form";
        }	               
        
        if (acc != null)	
        {
        	// username already taken
        	model.addAttribute("error_exists", "Ce nom d'utilisateur est déjà utilisé !");
        	return "admin/users/form";
        }
        
        if (userForm.getPassword().trim().length() < 8 || userForm.getConfirmPassword().trim().length() < 8)
        {
        	model.addAttribute("error_exists", "Un mot de passe doit faire au moins 8 caractères pour être sécurisé.");
        	return "admin/users/form";
        }
        
        if (!userForm.getPassword().trim().equals(userForm.getConfirmPassword().trim()))
        {
        	model.addAttribute("error_exists", "Les mots de passe ne sont pas identiques !");
        	return "admin/users/form";
        }
        
        // insert simple user in DB
        Role r =new Role("USER", "");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userForm.getPassword().trim());
        Account account =new Account(userForm.getUsername(), hashedPassword, new Date(), new ArrayList<Role>(){{
        	add(r);
        }});
        
        Admin admin = null;
        if(isAdmin)
        	 admin = adminBusiness.findByIdAccount(username);        	
        else
        	admin = adminBusiness.findByOrganization(orgBusiness.find(username).getName(), 0, 1).getContent().get(0);
        
        // instantiate localization class
        Localization loc = new Localization();
        // instantiate HttpRequests class for GMap requests
        HttpRequestsController httpReq = new HttpRequestsController();
        
        try {
        	// url for reverse geocoding using the address given in the form
        	String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+userForm.getAdress().replaceAll("[\\s|\\u00A0]+", "%20")+"&key=AIzaSyCq5FZHcVvfCVLQJZI2L9XePNQD0V33_4g";
        		System.out.println("step 1");
        	// Gson handler
        	GsonBuilder gsonBuilder = new GsonBuilder();
        	System.out.println("step 2");
        	// use custom Json deserializer from LocalizationAdapter
 		    Gson gson = gsonBuilder.registerTypeAdapter(Localization.class, new LocalizationAdapter()).create();
 		    System.out.println(url);
 		    // make the requests
			String localization_infos = httpReq.sendGet(url);
			System.out.println(localization_infos);
			if (localization_infos == "error")
			{
				model.addAttribute("error_exists", "Problème de géolocalisation. Réessayez plus tard !");
				return "admin/users/form";
			}
			
			// get the infos and populate the Localization instance
			loc = gson.fromJson(localization_infos, Localization.class);			
		} catch (Exception e) {
			// API error or invalid address, abort user registration
			e.printStackTrace();
			model.addAttribute("error_exists", "Adresse invalide ou problème de géolocalisation. Réessayez plus tard !");
			return "admin/users/form";
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
        
        SimpleUser s_user =new SimpleUser(userForm.getForName().trim(), userForm.getLastName().trim(), userForm.getOccup().trim(), loc, account, admin);
        
        accountBusiness.create(account);
        adminBusiness.create(admin);
        simpleUserBusiness.create(s_user);        
		
        model.addAttribute("success", "Utilisateur enregistré avec succès.");
        return "admin/users/form";	        
    }   
	
	@Secured(value={"ROLE_ADMIN","ROLE_ORGANIZATION"})
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String list_user(Map<String, Object> model, HttpSession session) {
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		java.util.List<String> roles= new ArrayList<>();
		boolean isAdmin = false;

		for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()){
			roles.add(ga.getAuthority());

			if (ga.getAuthority().equals("ROLE_ADMIN"))
				isAdmin = true;
		}
		
		model.put("username", username);
		String department = "";
		
		try{
			// get users
			if (isAdmin)
				department = adminBusiness.findByIdAccount(username).getLocalization_data().getDepartment();
			else
				department = orgBusiness.find(username).getLocalization_org().getDepartment();
			
			Page<SimpleUser> pageUsers = simpleUserBusiness.findAllInDepartment(department, 0, 4);
			// send users to view
			model.put("listUsers", pageUsers.getContent());
		}catch (Exception e) {
			model.put("exception",e);
		}		
		
		return "admin/users/list";
	}


}
