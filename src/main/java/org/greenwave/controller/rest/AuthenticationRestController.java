package org.greenwave.controller.rest;

import org.greenwave.business.interfaces.AccountBusiness;
import org.greenwave.business.interfaces.AdminBusiness;
import org.greenwave.business.interfaces.SimpleUserBusiness;
import org.greenwave.model.Account;
import org.greenwave.model.Admin;
import org.greenwave.model.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationRestController {
	
	@Autowired
	private AccountBusiness accountBuisness;
	@Autowired
	private AdminBusiness adminBuisness;
	@Autowired
	private SimpleUserBusiness simpleUserBusiness;
	
	@RequestMapping(value="/Auth",method=RequestMethod.POST)
	public String validateLogin(@RequestBody Account account) {
		
		Account acc=new Account();
		Admin adm;
		SimpleUser simple;
		
		System.out.println("Current Account:" + account.getLogin());
		System.out.println("Current Account:" + account.getPassword());
		
		String s=account.getLogin();
		String a=account.getPassword();
		acc=accountBuisness.findByUsername(account.getLogin());
		System.out.println(acc); 

		if (acc != null) {
			try {
				adm = adminBuisness.findByIdAccount(acc.getLogin());
				return "Admin Account Auth Ok";

			} catch (Exception e) {
				simple = simpleUserBusiness.findByIdAccount(acc.getLogin());
				return "Simple User Auth Ok";
			}

		} else {
			return "Account not exist";
		}
	}

}
