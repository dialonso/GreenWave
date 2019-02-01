package org.greenwave.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class SimpleUser extends User {
	 
	@ManyToOne
	@JoinColumn(name="id_admin", referencedColumnName="id")
	private Admin admin;
	
	public SimpleUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SimpleUser(String fName, String lName, String job, Localization loc, Account account, Admin admin) {
		super(fName, lName, job, account, loc);
		this.admin=admin;
		// TODO Auto-generated constructor stub
	}
	
	public SimpleUser(String fName, String lName, String job, Localization loc, Account account) {
		super(fName, lName, job, account, loc);
	}
	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
}

