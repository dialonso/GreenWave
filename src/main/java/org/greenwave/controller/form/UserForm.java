package org.greenwave.controller.form;

import javax.validation.constraints.Size;

public class UserForm {
	
	@Size(min=2, max=30, message="Le nom de famille doit être compris entre {min} et {max} caractères.")
    private String lastName;

    @Size(min=2, max=30, message="Le prénom doit être compris entre {min} et {max} caractères.")
    private String forName;
        
    @Size(min=2, max=30, message="L''adresse doit être comprise entre {min} et {max} caractères.")
    private String adress;

    @Size(min=5, max=50, message="Le nom d''utilisateur doit être compris entre {min} et {max} caractères.")
    private String username;
    
    @Size(min=8, max=50, message="Le mot de passe doit être compris entre {min} et {max} caractères.")
    private String password;
    
    @Size(min=8, max=50, message="Le mot de passe doit être compris entre {min} et {max} caractères.")
    private String confirmPassword;
    
    @Size(min=8, max=50, message="Le mot de passe doit être compris entre {min} et {max} caractères.")
    private String newPassword;
    
    @Size(min=2, max=30, message="Le job doit être compris entre {min} et {max} caractères.")
    private String occupation;
    
    private String org;
    
    public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    
    public String getForName() {
        return this.forName;
    }

    public void setForName(String name) {
        this.forName = name;
    }
    
    public String getAdress() {
        return this.adress;
    }

    public void setAdress(String addr) {
        this.adress = addr;
    }
    
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }
    
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getOccup() {
        return this.occupation;
    }

    public void setOccup(String occupation) {
        this.occupation = occupation;
    }
    
    public String getOrga() {
        return this.org;
    }

    public void setOrga(String organisation) {
        this.org= organisation;
    }

}
