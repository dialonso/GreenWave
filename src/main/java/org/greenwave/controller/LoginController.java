package org.greenwave.controller;

import java.util.Properties;
import java.util.Random;

import javax.validation.Valid;

import org.greenwave.business.interfaces.AccountBusiness;
import org.greenwave.business.interfaces.AdminBusiness;
import org.greenwave.business.interfaces.SimpleUserBusiness;
import org.greenwave.model.Account;
import org.greenwave.model.Admin;
import org.greenwave.model.SimpleUser;
import org.greenwave.controller.form.LoginForm;
import org.greenwave.controller.form.PasswordResetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController<HttpServHttpServletRequest> {
	
	@Autowired
	AccountBusiness accBusiness;
	
	@Autowired
	AdminBusiness adminBusiness;
	
	@Autowired
	SimpleUserBusiness simpleUSerBusiness;
	
	@RequestMapping("/login")
	public String login() {		
		return "/login";
	}
	
	
	@RequestMapping("/login/error")
	public String login_error(Model model) {
		model.addAttribute("error_exists", "Nom d'utilisateur ou mot de passe invalide !");
		return "/login";
	}
	
	//Login form validation
	@RequestMapping(value = "/login/validate")
    public String validateLogin(@Valid LoginForm userForm, BindingResult bindingResult, Model model) {
    	
        if (bindingResult.hasErrors()) {
        	//if errors in form fields
        	return "/login";
        }

        Account acc;
        Admin adm;
        SimpleUser simple;
        
        try{
        	//check if account exists
	        acc = accBusiness.find(userForm.getLogin(), userForm.getPassword());
	        System.out.println(acc);
        }
        catch(Exception e){
        	//SQL error
        	model.addAttribute("error_exists", e.toString());
        	return "/login";
        }	               
        
        //if account exists, go to dashboard -> connection successful
        if (acc != null){
    		try{
    			adm = adminBusiness.findByIdAccount(acc.getLogin());
    			model.addAttribute("username", adm.getfName()+" "+adm.getlName());
    			
    		}catch (Exception e) {

    			simple = simpleUSerBusiness.findByIdAccount(acc.getLogin());
            	model.addAttribute("username", simple.getfName()+" "+simple.getlName());
			}	    	
			
			//Numbers displayed on the dashboard pages
			int numbers[] = {5874, 32, 6, 21};
			model.addAttribute("number", numbers);
			
			//Labels for the dashboard numbers
			String dashboard_numbers[] = {"mesures", "administrateurs", "utilisateurs", "mairies"};
			model.addAttribute("dashboard_numbers", dashboard_numbers);
			
			 //Titles of the graphs
			String graph_titles[] = {"Graphique d'évolution du nombre d'utilisateurs"};
			model.addAttribute("graph_titles", graph_titles);
        	return "/admin/dashboard";
        }
        else{
        	 //if account doesn't exist, redirect to login page with error 
        	model.addAttribute("error_exists", "Nom d'utilisateur ou mot de passe invalide.");
        	return "/login";
        }
    }
    
    //Error messages handling
   	@RequestMapping("/reset_password")
  	public String reset_password(Model model, PasswordResetForm passwordResetForm) {
  		return "/reset_password";
  	}
  	
	@RequestMapping(value = "/reset_passwordValidate")
	public String validatePassword(@Valid PasswordResetForm psdResetForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			//if errors in form fields
			return "/reset_password";
		}
		
		// get account using the username specified in the form
		Account acc = accBusiness.findByUsername(psdResetForm.getLogin());
		
		if (acc == null)
		{
			model.addAttribute("error_exists", "Nom d'utilisateur inconnu.");
			return "/reset_password";
		}
		
		// generate new password (8 characters long)
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHYJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 8; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String new_pass = sb.toString();	
		
		// encrypt new password an update account
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        String hashedNPassword = passwordEncoder.encode(new_pass); 
        acc.setPassword(hashedNPassword);
        
        accBusiness.update(acc.getId_account(), acc);        
        
        // specify email content
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(psdResetForm.getEmail());
        msg.setFrom("projet.greenwave@gmail.com");
        msg.setSubject("Réinitialisation de mot de passe");
        msg.setText(
            "Cher/Chère utilisateur(trice) de GreenWave, \n\n"+
            "nous avons reçu une demande de récupération de mot de passe provenant de votre compte."+
            " Suite à celle-ci, nous avons réinitialisé votre mot de passe.\n"+
            " Votre nouveau mot de passe est donc: " + new_pass + ".\n\n" +
            " Cordialement, l'équipe GreenWave.");
        System.out.println(new_pass);
        // try to connect SMTP server
        try{
        	JavaMailSenderImpl sender = new JavaMailSenderImpl();
        	sender.setHost("smtp.gmail.com");
        	sender.setPort(587);
        	sender.setProtocol("smtp");
        	sender.setUsername("projet.greenwave@gmail.com");
        	sender.setPassword("greenwave08");
        	
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            
            sender.setJavaMailProperties(props);
            
            sender.send(msg);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
            model.addAttribute("error_exists", "Erreur lors de l'envoi du mail.");
         //   return "/reset_password";
        }
        
		model.addAttribute("success", "Un email contenant votre nouveau mot de passe vous a été envoyé.");
		return "/reset_password";
	} 		

}
