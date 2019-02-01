package org.greenwave.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Role {

	@Id
	private String role;
	
	@NotBlank
	private String description;
	
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Role(String role, String description) {
		super();
		this.role = role;
		this.description = description;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
