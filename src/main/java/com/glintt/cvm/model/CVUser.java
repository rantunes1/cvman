package com.glintt.cvm.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.vaadin.appfoundation.authentication.data.User;

import com.glintt.cvm.security.NamedRole;

@Entity
public class CVUser extends User {

	private static final long serialVersionUID = 2299928442930679859L;

	private String mobileNumber;
	private String company;
	private String country;
	private String countryCode;

	private UserType userType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private NamedRole role;

	public UserType getUserType() {
		return this.userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public NamedRole getRole() {
		return this.role;
	}

	public void setRole(NamedRole role) {
		this.role = role;
	}
}
