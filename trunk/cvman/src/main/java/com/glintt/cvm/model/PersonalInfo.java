package com.glintt.cvm.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hr_xml._3.CountryCodeEnumType;
import org.hr_xml._3.GenderCodeEnumType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.hr_xml._3.MaritalStatusCodeEnumType;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

@Entity
public class PersonalInfo extends AbstractOwnedEntity<Person> {
    private static final long serialVersionUID = 6066626402959959669L;

    private byte[] picture;

    @ManyToOne(cascade = CascadeType.ALL)
    private Person owner;

    @OneToOne(cascade = CascadeType.ALL)
    private PersonName name;
    @OneToOne(cascade = CascadeType.ALL)
    private PersonName fatherName;
    @OneToOne(cascade = CascadeType.ALL)
    private PersonName motherName;
    private GenderCodeEnumType gender;
    private MaritalStatusCodeEnumType maritalStatus;
    private CountryCodeEnumType citizenshipCountry;

    private LanguageCodeEnumType primaryLanguage;

    @OneToMany(cascade = CascadeType.PERSIST)
    private Collection<PersonContacts> contacts;

    @OneToOne(cascade = CascadeType.ALL)
    private BirthInfo birthInfo;

    @Override
    public void setOwner(Person owner) {
        if (owner == null) {
            throw new IllegalArgumentException("owner can't be null");
        }
        this.owner = owner;
    }

    @Override
    public Person getOwner() {
        if (this.owner == null) {
            throw new IllegalStateException("entity owner was not yet set");
        }
        return this.owner;
    }

    public PersonName getName() {
        return this.name;
    }

    public void setName(PersonName name) {
        this.name = name;
    }

    public PersonName getFatherName() {
        return this.fatherName;
    }

    public void setFatherName(PersonName fatherName) {
        this.fatherName = fatherName;
    }

    public PersonName getMotherName() {
        return this.motherName;
    }

    public void setMotherName(PersonName motherName) {
        this.motherName = motherName;
    }

    public GenderCodeEnumType getGender() {
        return this.gender;
    }

    public void setGender(GenderCodeEnumType genderType) {
        this.gender = genderType;
    }

    public MaritalStatusCodeEnumType getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusCodeEnumType maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public CountryCodeEnumType getCitizenshipCountry() {
        return this.citizenshipCountry;
    }

    public void setCitizenshipCountry(CountryCodeEnumType citizenshipCountry) {
        this.citizenshipCountry = citizenshipCountry;
    }

    public LanguageCodeEnumType getPrimaryLanguage() {
        return this.primaryLanguage;
    }

    public void setPrimaryLanguage(LanguageCodeEnumType primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public BirthInfo getBirthInfo() {
        return this.birthInfo;
    }

    public void setBirthInfo(BirthInfo birthInfo) {
        this.birthInfo = birthInfo;
    }

    public byte[] getPicture() {
        return this.picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Entity
    public static class BirthInfo extends AbstractPojo {
        private static final long serialVersionUID = 8418850177040426201L;

        private String birthDate;
        @OneToOne(cascade = CascadeType.ALL)
        private Location birthLocation;

        public String getBirthDate() {
            return this.birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public Location getBirthLocation() {
            return this.birthLocation;
        }

        public void setBirthLocation(Location birthLocation) {
            this.birthLocation = birthLocation;
        }
    }

    public PersonContacts getPersonContacts(PersonContactsProfile contactsProfile) {
        Collection<PersonContacts> contacts = this.contacts;
        if (contacts != null) {
            for (PersonContacts personContacts : contacts) {
                if (personContacts.getContactsProfile().equals(contactsProfile)) {
                    return personContacts;
                }
            }
        }
        return null;
    }

    public void addPersonContacts(PersonContacts contacts) {
        if (contacts == null) {
            return;
        }
        if (this.contacts == null) {
            this.contacts = new ArrayList<PersonContacts>();
        }
        this.contacts.add(contacts);
    }

    public enum PersonContactsProfile {
        PERSONAL, BUSINESS
    }

}
