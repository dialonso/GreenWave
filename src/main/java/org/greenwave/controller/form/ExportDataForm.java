package org.greenwave.controller.form;

import java.sql.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

public class ExportDataForm {
	
	@NotNull(message="Ce champ est requis.")
	private List<String> departments;
	
	@NotNull(message="Ce champ est requis.")
	private List<String> other_measures;
	
	@NotNull(message="Ce champ est requis.")
	private List<String> polluting_measures;
	
	@NotNull(message="Ce champ est requis.")
	private Date startDate;
	
	@NotNull(message="Ce champ est requis.")
	private Date endDate;
	
	@NotNull(message="Ce champ est requis.")
	private String file_type;
	
	public String getFile_type() {
		return file_type;
	}
	
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}
	
	public List<String> getDepartments() {
		return departments;
	}
	
	public void setDepartments(List<String> departments) {
		this.departments = departments;
	}
	
	public List<String> getOther_measures() {
		return other_measures;
	}
	
	public void setOther_measures(List<String> other_measures) {
		this.other_measures = other_measures;
	}
	
	public List<String> getPolluting_measures() {
		return polluting_measures;
	}
	
	public void setPolluting_measures(List<String> polluting_measures) {
		this.polluting_measures = polluting_measures;
	}
	
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
	
}
