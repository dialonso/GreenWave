package org.greenwave.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class DataFile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_file;
	
	@NotBlank
	private String link;
	
	@NotBlank
	private Long maxFileSize;
	
	@NotBlank
	private String type;
	
	@NotBlank
	private Long fileSize;
	
	@ManyToOne
	@JoinColumn(name="id_data")
	private Data data;
	
	public DataFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DataFile(String link, Long maxFileSize, String type, Long fileSize) {
		super();
		this.link = link;
		this.maxFileSize = maxFileSize;
		this.type = type;
		this.fileSize = fileSize;
	}

	public DataFile(String link, Long maxFileSize, String type, Long fileSize, Data data) {
		super();
		this.link = link;
		this.maxFileSize = maxFileSize;
		this.type = type;
		this.fileSize = fileSize;
		this.data = data;
	}
	
	public Long getId_file() {
		return id_file;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Long getMaxFileSize() {
		return maxFileSize;
	}
	
	public void setMaxFileSize(Long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Long getFileSize() {
		return fileSize;
	}
	
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	
	public Data getData() {
		return data;
	}
	
	public void setData(Data data) {
		this.data = data;
	}
	
}

