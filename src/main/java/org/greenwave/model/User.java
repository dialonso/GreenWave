package org.greenwave.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class User {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	protected Long id;
	
	@NotBlank
	protected String fName;
	
	@NotBlank
	protected String lName;
	
	@NotBlank
	protected String job;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_account")
	protected Account account;
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	protected Collection<Data> data;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_localization")
	protected Localization localization_data;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String fName, String lName, String job, Account account, 
			Localization localization_data) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.job = job;
		this.account = account;
		this.localization_data = localization_data;
	}
	
	public Long getId() {
		return id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Collection<Data> getData() {
		return data;
	}

	public void setData(Collection<Data> data) {
		this.data = data;
	}	
	
	public Localization getLocalization_data() {
		return localization_data;
	}

	public void setLocalization_data(Localization localization_data) {
		this.localization_data = localization_data;
	}	
	
}

