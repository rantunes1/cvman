package com.glintt.cvm.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hr_xml._3.ChannelCodeEnumType;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

import com.glintt.cvm.model.PersonalInfo.PersonContactsProfile;

@Entity
public class PersonContacts extends AbstractPojo {
    private static final long serialVersionUID = -4260663480634580428L;

    @Column(nullable = false)
    private PersonContactsProfile contactsProfile;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    private PersonContacts.Email email;
    @OneToOne(cascade = CascadeType.ALL)
    private PersonContacts.MobilePhone mobilePhone;
    @OneToOne(cascade = CascadeType.ALL)
    private PersonContacts.Telephone telephone;

    public void setContactsProfile(PersonContactsProfile contactsProfile) {
        this.contactsProfile = contactsProfile;
    }

    public PersonContactsProfile getContactsProfile() {
        return this.contactsProfile;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PersonContacts.Email getEmail() {
        return this.email;
    }

    public void setEmail(PersonContacts.Email email) {
        this.email = email;
    }

    public PersonContacts.MobilePhone getMobilePhone() {
        return this.mobilePhone;
    }

    public void setMobilePhone(PersonContacts.MobilePhone mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public PersonContacts.Telephone getTelephone() {
        return this.telephone;
    }

    public void setTelephone(PersonContacts.Telephone telephone) {
        this.telephone = telephone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.contactsProfile == null) ? 0 : this.contactsProfile.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonContacts other = (PersonContacts) obj;
        if (this.contactsProfile != other.contactsProfile)
            return false;
        return true;
    }

    @Entity
    private abstract static class PhoneChannel extends AbstractPojo implements CommunicationChannel {
        private static final long serialVersionUID = -1561925491294527379L;

        private String countryDialing;
        private String dialNumber;

        public String getCountryDialing() {
            return this.countryDialing;
        }

        public void setCountryDialing(String countryDialing) {
            this.countryDialing = countryDialing;
        }

        public String getDialNumber() {
            return this.dialNumber;
        }

        public void setDialNumber(String dialNumber) {
            this.dialNumber = dialNumber;
        }
    }

    @Entity
    public static class Email extends AbstractPojo implements CommunicationChannel {
        private static final long serialVersionUID = -6868849097595787124L;

        private String emailAddress;

        @Override
        public String getChannelCode() {
            return ChannelCodeEnumType.EMAIL.value();
        }

        public String getEmailAddress() {
            return this.emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }
    }

    @Entity
    public static class MobilePhone extends PersonContacts.PhoneChannel {
        private static final long serialVersionUID = -1982803103647078318L;

        @Override
        public String getChannelCode() {
            return ChannelCodeEnumType.MOBILE_TELEPHONE.value();
        }

    }

    @Entity
    public static class Telephone extends PersonContacts.PhoneChannel {
        private static final long serialVersionUID = 339523083834771863L;

        @Override
        public String getChannelCode() {
            return ChannelCodeEnumType.TELEPHONE.value();
        }

    }
}