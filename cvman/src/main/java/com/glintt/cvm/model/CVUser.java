package com.glintt.cvm.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.vaadin.appfoundation.authentication.data.User;

@Entity
public class CVUser extends User {

    private static final long serialVersionUID = 2299928442930679859L;

    private UserType userType;

    private String mobileNumber;
    private String telephoneNumber;
    private String firstName;
    private String surname;
    private String company;
    private String country;
    private String countryCode;
    private String streetAddress;
    private String locality;
    private String postalCode;

    private byte[] picture;

    @OneToOne(cascade = CascadeType.ALL)
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

    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getStreetAddress() {
        return this.streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getLocality() {
        return this.locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public byte[] getPicture() {
        return this.picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public NamedRole getRole() {
        return this.role;
    }

    public void setRole(NamedRole role) {
        this.role = role;
    }

}
