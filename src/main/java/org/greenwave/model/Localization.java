package org.greenwave.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Localization implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_localization;
	
	private double latitude;
	
	private double longitude;
	
	private String address;
	

	private String region;
	
	private String department;
	

	private String country;	
	
	public Localization() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Localization(double latitude, double longitude, String address, String region, String department,
			String country) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.region = region;
		this.department = department;
		this.country = country;
	}
	
	public Long getId_localization() {
		return id_localization;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
}

