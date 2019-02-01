package org.greenwave.controller.form;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;


public class DateForm {

	@Valid
	@Past(message="La date de début ne peut pas être dans le futur.")
	private Date startDate;
	
	@Valid
	@Past(message="La date de fin ne peut pas être dans le futur.")
	private Date endDate;
	
	private List<String> po_measures;
	
	private List<String> ot_measures;
	
	@NotNull
	private String method; 
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public List<String> getPo_measures() {
		return po_measures;
	}
	
	public void setPo_measures(List<String> po_measures) {
		this.po_measures = po_measures;
	}
	
	public List<String> getOt_measures() {
		return ot_measures;
	}
	
	public void setOt_measures(List<String> ot_measures) {
		this.ot_measures = ot_measures;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}	
	
}

