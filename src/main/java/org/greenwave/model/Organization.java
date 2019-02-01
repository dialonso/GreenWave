package org.greenwave.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Organization {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_organization;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String type;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	private Account account;
	
	@OneToMany(mappedBy="organization",fetch=FetchType.LAZY)
	private Collection<Admin> admins;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_localization")
	private Localization localization_org;
	
	public Organization(String name, String type, Account account, Collection<Admin> admins,
			Localization localization_org) {
		super();
		this.name = name;
		this.type = type;
		this.account = account;
		this.admins = admins;
		this.localization_org = localization_org;
	}

	public Organization() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getId_organization() {
		return id_organization;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Collection<Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(Collection<Admin> admins) {
		this.admins = admins;
	}
	
	public Localization getLocalization_org() {
		return localization_org;
	}

	public void setLocalization_org(Localization localization_org) {
		this.localization_org = localization_org;
	}
	
}

