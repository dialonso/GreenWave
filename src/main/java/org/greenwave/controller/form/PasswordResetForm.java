package org.greenwave.controller.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class PasswordResetForm {
		
	@NotNull
	@Email(message="Merci de saisir une adresse mail valide.")
	private String email;
	
	@NotNull
    @Size(min=5, max=50, message="Le nom d''utilisateur doit être compris entre {min} et {max} caractères.")
	private String login;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

