package org.greenwave.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class DataCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_category;
	
	@NotBlank
	private String name;
	
	private DataDomain domain;
	
	public Long getId_category() {
		return id_category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public DataDomain getDomain() {
        return domain;
    }

    public void setDomain(DataDomain domain) {
        this.domain = domain;
    }
    
}

