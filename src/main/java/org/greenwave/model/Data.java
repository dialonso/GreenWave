package org.greenwave.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.greenwave.model.Localization;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_data;

    private DataDomain domain;
    
    @NotBlank
    private String type;
    
    private double value;
    
    private Date measure_date;
    
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_user")
	private User user;
	
	@OneToMany(mappedBy="data",fetch=FetchType.LAZY)
	private Collection<DataFile> files;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_localization")
	private Localization localization_data;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_category")
	private DataCategory category_data;
	
    public Long getId_data() {
        return id_data;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<DataFile> getFiles() {
		return files;
	}

	public void setFiles(Collection<DataFile> files) {
		this.files = files;
	}
	
	public DataDomain getDomain() {
        return domain;
    }

    public void setDomain(DataDomain domain) {
        this.domain = domain;
    }
    
    public DataCategory getCategory_data() {
		return category_data;
	}

	public void setCategory_data(DataCategory category_data) {
		this.category_data = category_data;
	}	
    
	public Date getMeasure_date() {
		return measure_date;
	}

	public void setMeasure_date(Date measure_date) {
		this.measure_date = measure_date;
	}

	public Localization getLocalization_data() {
		return localization_data;
	}

	public void setLocalization_data(Localization localization_data) {
		this.localization_data = localization_data;
	}
	
}

