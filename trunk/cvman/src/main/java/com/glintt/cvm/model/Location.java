package com.glintt.cvm.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hr_xml._3.CountryCodeEnumType;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

@Entity
public class Location extends AbstractPojo {
    private static final long serialVersionUID = 8733461371678155708L;

    private CountryCodeEnumType country;
    private String countryDivision;
    private String city;
    private String postalCode;

    public CountryCodeEnumType getCountry() {
        return this.country;
    }

    public void setCountry(CountryCodeEnumType country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryDivision() {
        return this.countryDivision;
    }

    public void setCountryDivision(String countryDivision) {
        this.countryDivision = countryDivision;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
