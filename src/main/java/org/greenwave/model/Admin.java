package org.greenwave.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Admin extends User {
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_organization")
	private Organization organization;
	
	@OneToMany(mappedBy="admin",fetch=FetchType.LAZY)
	private Collection<SimpleUser> simpleUsers;
	
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Admin(String fName, String lName, String job, Localization loc, Account account,
			Organization organization) {
		super(fName, lName, job, account, loc);
		this.organization = organization;
	}
	
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Collection<SimpleUser> getSimpleUsers() {
		return simpleUsers;
	}

	public void setSimpleUsers(Collection<SimpleUser> simpleUsers) {
		this.simpleUsers = simpleUsers;
	}
	
}
