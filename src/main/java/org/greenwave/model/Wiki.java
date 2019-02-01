package org.greenwave.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Wiki implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private DataDomain idDomain;
	private String nom;
	private String unite;
	private Double min;
	private Double max;
	private Double ratio1;
	private Double ratio2;
	private Double ratio3;
	private Double ratio4;
	private String description;
	private String risque;
	
	public Wiki() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Wiki(String nom, String unite, Double min, Double max, Double ratio1, Double ratio2, Double ratio3,
			Double ratio4, String description, String risque) {
		super();
		this.nom = nom;
		this.unite = unite;
		this.min = min;
		this.max = max;
		this.ratio1 = ratio1;
		this.ratio2 = ratio2;
		this.ratio3 = ratio3;
		this.ratio4 = ratio4;
		this.description = description;
		this.risque = risque;
	}
	
	public Long getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
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

	public Double getRatio1() {
		return ratio1;
	}

	public void setRatio1(Double ratio1) {
		this.ratio1 = ratio1;
	}

	public Double getRatio2() {
		return ratio2;
	}

	public void setRatio2(Double ratio2) {
		this.ratio2 = ratio2;
	}

	public Double getRatio3() {
		return ratio3;
	}

	public void setRatio3(Double ratio3) {
		this.ratio3 = ratio3;
	}

	public Double getRatio4() {
		return ratio4;
	}

	public void setRatio4(Double ratio4) {
		this.ratio4 = ratio4;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRisque() {
		return risque;
	}

	public void setRisque(String risque) {
		this.risque = risque;
	}
	
}
