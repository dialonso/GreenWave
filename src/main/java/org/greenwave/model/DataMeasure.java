package org.greenwave.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import org.greenwave.model.DataDomain;

@Entity
public class DataMeasure implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_substance;
	
	@NotBlank
	private String name_substance;
	
	@NotBlank
	private String category_substance;
	

	private String unity;
	

	private Double min;
	

	private Double max;
	
	private Double acceptableRatioSW;
	

	private Double acceptableRatioOSW;
	

	private Double dangerousRatioSW;
	

	private Double dangerousRatioOSW;
	
	private DataDomain domain;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="type_data")
	private Collection<Data> datas;
	
	public DataMeasure() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataMeasure(Long id_substance, String name_substance, String category_substance, String unity, Double min, Double max,
					 Double acceptableRatioSW, Double acceptableRatioOSW, Double dangerousRatioSW, Double dangerousRatioOSW) {
		super();
		this.name_substance = name_substance;
		this.category_substance = category_substance;
		this.unity = unity;
		this.min = min;
		this.max = max;
		this.acceptableRatioSW = acceptableRatioSW;
		this.acceptableRatioOSW = acceptableRatioOSW;
		this.dangerousRatioSW = dangerousRatioSW;
		this.dangerousRatioOSW = dangerousRatioOSW;
	}
	
	public Long getId_substance() {
		return id_substance;
	}

	public String getName_substance() {
		return name_substance;
	}

	public void setName_substance(String name_substance) {
		this.name_substance = name_substance;
	}

	public String getCategory_substance() {
		return category_substance;
	}

	public void setCategory_substance(String category_substance) {
		this.category_substance = category_substance;
	}

	public String getUnity() {
		return unity;
	}

	public void setUnity(String unity) {
		this.unity = unity;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getAcceptableRatioSW() {
		return acceptableRatioSW;
	}

	public void setAcceptableRatioSW(Double acceptableRatioSW) {
		this.acceptableRatioSW = acceptableRatioSW;
	}

	public Double getAcceptableRatioOSW() {
		return acceptableRatioOSW;
	}

	public void setAcceptableRatioOSW(Double acceptableRatioOSW) {
		this.acceptableRatioOSW = acceptableRatioOSW;
	}

	public Double getDangerousRatioSW() {
		return dangerousRatioSW;
	}

	public void setDangerousRatioSW(Double dangerousRatioSW) {
		this.dangerousRatioSW = dangerousRatioSW;
	}

	public Double getDangerousRatioOSW() {
		return dangerousRatioOSW;
	}

	public void setDangerousRatioOSW(Double dangerousRatioOSW) {
		this.dangerousRatioOSW = dangerousRatioOSW;
	}

	public Collection<Data> getDatas() {
		return datas;
	}

	public void setDatas(Collection<Data> datas) {
		this.datas = datas;
	}	
	
	public DataDomain getDomain() {
        return domain;
    }

    public void setDomain(DataDomain domain) {
        this.domain = domain;
    }
	
}

