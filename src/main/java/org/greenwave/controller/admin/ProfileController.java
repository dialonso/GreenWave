package org.greenwave.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.greenwave.business.interfaces.AccountBusiness;
import org.greenwave.business.interfaces.AdminBusiness;
import org.greenwave.business.interfaces.LocalizationBusiness;
import org.greenwave.business.interfaces.SimpleUserBusiness;
import org.greenwave.controller.adapter.LocalizationAdapter;
import org.greenwave.controller.form.UserForm;
import org.greenwave.model.Account;
import org.greenwave.model.Admin;
import org.greenwave.model.Localization;
import org.greenwave.model.SimpleUser;
import org.greenwave.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class ProfileController {
	
	@Autowired
	LocalizationBusiness locBusiness;
	
	@Autowired
	AccountBusiness accountBusiness;

	@Autowired
	AdminBusiness adminBusiness;
	
	@Autowired
	SimpleUserBusiness simpleUBusiness;

	@Secured(value={"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping("/profileUpdate")
	public String update(Map<String, Object> model, HttpSession session, UserForm userForm) {
    	
		SecurityContext securityContext=(SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
		String username=securityContext.getAuthentication().getName();
		
		boolean isAdmin = false;
		
		User user;
		
		for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()){
			if (ga.getAuthority().equals("ROLE_ADMIN"))
				isAdmin = true;
		}
		
		if (isAdmin)
			user = adminBusiness.findByIdAccount(username);
		else
			user = simpleUBusiness.findByIdAccount(username);
		
		model.put("username", username);
		model.put("user", user);
		
		User new_user = user;
		int nb_modifs = 0;
		
		// if user changed his username, check if it exists
		if (userForm.getUsername().trim() != username &&  userForm.getUsername().trim() != null && userForm.getUsername().trim().length() >= 5 && userForm.getUsername().trim().length() == userForm.getUsername().length())
		{
			Account acc;
	        try{
	        	// check if username already taken
		        acc = accountBusiness.check(userForm.getUsername().trim());
	        }
	        catch(Exception e){
	        	// SQL error
	        	model.put("error_exists", e.toString());
	        	return "/admin/profile";
	        }	               
	        
	        if (acc != null)	
	        {
	        	// username already taken
	        	model.put("error_exists", "Ce nom d'utilisateur est déjà utilisé !");
	        	return "/admin/profile";
	        }		        

	        new_user.getAccount().setLogin(userForm.getUsername().trim());
	        nb_modifs ++;
		}

		// if password changed
		if (userForm.getPassword().trim().length() >= 8 && userForm.getPassword().trim() != null && userForm.getPassword().trim().length() == userForm.getPassword().length())
		{
			// check if old password is correct and if new password and confirmation match
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        
	        // encrypt passwords
	        String currPassword = user.getAccount().getPassword(); // hashed current password
	        String hashedPassword = passwordEncoder.encode(userForm.getPassword().trim());
	        String hashedNewPassword = passwordEncoder.encode(userForm.getNewPassword().trim());
			// check if user entered valid password
	        if (!currPassword.equals(hashedPassword))
			{
				model.put("password_error", "Le mot de passe que vous avez spécifié est invalide.");
				return "/admin/profile";
			}
	        // check that the new password and the confirmation are the same
			else if(!userForm.getNewPassword().trim().equals(userForm.getConfirmPassword().trim()))
			{
				model.put("match_error", "Le nouveau mot de passe et la confirmation sont différents.");
				return "/admin/profile";
			}
	        // check if new password is the same as the old one
	        else if(hashedPassword.equals(hashedNewPassword))
	        {
				model.put("same_pass_error", "Veuillez saisir un nouveau mot de passe différent de l'ancien.");
				return "/admin/profile";
	        }
	        // if all of that works, encode new password
	        new_user.getAccount().setPassword(hashedNewPassword);
	        nb_modifs ++;
		}

		// if localization changed, we need to re-geolocate and validate the new address
		if (userForm.getAdress().trim() != user.getLocalization_data().getAddress() && userForm.getAdress().trim() != null && userForm.getAdress().trim().length() > 0
				&& userForm.getAdress().trim().length() == userForm.getAdress().length())
		{
			// instantiate localization class
	        Localization loc = new Localization();
	        // instantiate HttpRequests class for GMap requests
	        HttpRequestsController httpReq = new HttpRequestsController();
	        
	        try {
	        	// url for reverse geocoding using the address given in the form
	        	String url = "http://maps.googleapis.com/maps/api/geocode/json?address="+userForm.getAdress()+"&sensor=false";
	        		
	        	// Gson handler
	        	GsonBuilder gsonBuilder = new GsonBuilder();
	        	
	        	// use custom Json deserializer from LocalizationAdapter
	 		    Gson gson = gsonBuilder.registerTypeAdapter(Localization.class, new LocalizationAdapter()).create();
	 		    
	 		    // make the requests
				String localization_infos = httpReq.sendGet(url);
				
				// get the infos and populate the Localization instance
				loc = gson.fromJson(localization_infos, Localization.class);
				
			} catch (Exception e) {
				// API error or invalid address, abort admin registration
				e.printStackTrace();
	        	model.put("error_exists", "Adresse invalide ou problème de géolocalisation. Réessayez plus tard !");
	        	return "/admin/admins/index";
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
	        
	        new_user.setLocalization_data(loc);
	        nb_modifs ++;
		}
		
		// first name
		if (userForm.getForName().trim() != user.getfName().trim() && userForm.getForName().trim() != null  && userForm.getForName().trim().length() > 0
				&& userForm.getForName().trim().length() == userForm.getForName().length())
		{
			new_user.setfName(userForm.getForName().trim());
			nb_modifs ++;
		}
			
		
		// last name
		if (userForm.getLastName().trim() != user.getlName().trim() && userForm.getLastName().trim() != null && userForm.getLastName().trim().length() > 0
				&& userForm.getLastName().trim().length() == userForm.getLastName().length())
		{
			new_user.setlName(userForm.getLastName().trim());
			nb_modifs ++;
		}
		
		// job
		if (userForm.getOccup() != user.getJob() && user.getJob().length() != userForm.getOccup().length())
		{
			new_user.setJob(userForm.getOccup());
			nb_modifs ++;
		}
		
		if (isAdmin)
			// if user is admin call updateAdmin
			adminBusiness.update(user.getId(), (Admin) new_user);
		else
			// if not, call updateSimpleUser
			simpleUBusiness.update(user.getId(), (SimpleUser) new_user);			

		// send the newly modified user to the front-office
		model.put("user", new_user);
		
		// set up success message
		if (nb_modifs > 1)
			model.put("success", nb_modifs + " informations modifiées avec succès.");
		else if(nb_modifs == 1)
			model.put("success", nb_modifs + " information modifiée avec succès.");
		else if (nb_modifs == 0)
			model.put("info", "Aucune information n'a été modifiée.");
		
		return "/admin/profile";
	}

}
