package org.greenwave.controller.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {
	
	@Size(min=5, max=50, message="Le nom d''utilisateur doit être compris en {min} et {max} caractères.")
	@NotNull
	private String login;
	
	@Size(min=8, max=50, message="Le mot de passe doit être compris en {min} et {max} caractères.")
	@NotNull
	private String password;
	
	@NotNull
	private boolean remember_me;
	
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
	
	public boolean isRemember_me() {
		return remember_me;
	}
	
	public void setRemember_me(boolean remember_me) {
		this.remember_me = remember_me;
	}	
	
}

