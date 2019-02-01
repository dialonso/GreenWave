package org.greenwave.controller.form;

import java.sql.Date;

import org.greenwave.business.interfaces.DataCategoryBusiness;
import org.greenwave.model.DataCategory;
import org.springframework.beans.factory.annotation.Autowired;

public class ChartForm {
	
	@Autowired
	DataCategoryBusiness dataCategoryBusiness;
	
	private DataCategory waterCat;
	private Long waterCatId;
	private String typeData;
	private String typeDataPolluting;
	private String place;
	private Date startDate;
	private Date endDate;
	
	public String getTypeData() {
		return typeData;
	}

	public void setTypeData(String typeData) {
		this.typeData = typeData;
	}

	public String getTypeDataPolluting() {
		return typeDataPolluting;
	}

	public void setTypeDataPolluting(String typeDataPolluting) {
		this.typeDataPolluting = typeDataPolluting;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public DataCategory getwaterCat() {
		return waterCat;
	}

	public void setwaterCat(DataCategory water) {
		this.waterCat = water;
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

	public Long getWaterCatId() {
		return waterCatId;
	}

	public void setWaterCatId(Long waterCatId) {
		this.waterCatId = waterCatId;
	}

}
