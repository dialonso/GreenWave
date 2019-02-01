package org.greenwave.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DataOpen implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String url;
	
	private String api_key;
	
	private String domain;
	
	private Date date_last_call;
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getApi_key() {
		return api_key;
	}
	
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	
	public Date getDate_last_call() {
		return date_last_call;
	}
	
	public void setDate_last_call(Date date_last_call) {
		this.date_last_call = date_last_call;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
}

