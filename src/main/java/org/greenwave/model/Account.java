package org.greenwave.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_account;
	
	@NotBlank
	private String login;
	
	@NotBlank
	private String password;
	
	private Date createdAt;
	
	@ManyToMany
	@JoinTable(name="Account_ROLES")
	private Collection<Role> roles;	
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(String login, String password, Date createdAt, Collection<Role> roles) {
		super();
		this.login = login;
		this.password = password;
		this.createdAt = createdAt;
		this.roles = roles;
	}
	
	public Long getId_account() {
		return id_account;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
}

