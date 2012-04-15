package com.glintt.cvm.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hr_xml._3.CountryCodeEnumType;

@Entity
@XmlRootElement(name = "location")
public class Location extends AbstractEntity {
	private static final long serialVersionUID = 8733461371678155708L;

	private CountryCodeEnumType country;
	private String countryDivision;
	private String city;
	private String postalCode;

	@XmlElement
	public CountryCodeEnumType getCountry() {
		return this.country;
	}

	public void setCountry(CountryCodeEnumType country) {
		this.country = country;
	}

	@XmlElement
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement
	public String getCountryDivision() {
		return this.countryDivision;
	}

	public void setCountryDivision(String countryDivision) {
		this.countryDivision = countryDivision;
	}

	@XmlElement
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
