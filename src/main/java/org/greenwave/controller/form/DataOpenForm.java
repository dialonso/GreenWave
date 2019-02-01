package org.greenwave.controller.form;

import java.sql.Date;

public class DataOpenForm {
	
	private String name;
	private Date date_last_call;
	private long id;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getDate_last_call() {
		return date_last_call;
	}
	
	public void setDate_last_call(Date date_last_call) {
		this.date_last_call = date_last_call;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
}
