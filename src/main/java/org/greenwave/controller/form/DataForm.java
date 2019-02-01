package org.greenwave.controller.form;

import org.greenwave.model.DataDomain;

public class DataForm {
	
private double value;
	
	private DataDomain domain;
	
	private Long category;
	
	private String measure;
	
	private String adresse;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public DataDomain getDomain() {
		return domain;
	}

	public void setDomain(DataDomain domain) {
		this.domain = domain;
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

}
