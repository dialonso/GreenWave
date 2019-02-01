package org.greenwave.controller.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

public class ContactForm {
	
	@Email
	private String email;
	
	@NotNull
	private String object;
	
	@NotNull
	private String content;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getObject() {
		return object;
	}
	
	public void setObject(String object) {
		this.object = object;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

}
