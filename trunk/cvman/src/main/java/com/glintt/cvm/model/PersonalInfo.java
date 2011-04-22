package com.glintt.cvm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hr_xml._3.ChannelCodeEnumType;
import org.hr_xml._3.CountryCodeEnumType;
import org.hr_xml._3.GenderCodeEnumType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.hr_xml._3.MaritalStatusCodeEnumType;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

@Entity
public class PersonalInfo extends AbstractOwnedEntity<Person> {
    private static final long serialVersionUID = 6066626402959959669L;

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

    private List<UserProfile> profiles;

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

    @Entity
    public static class BirthInfo extends AbstractPojo {
        private static final long serialVersionUID = 8418850177040426201L;

        private String birthDate;
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

    public UserProfile getProfile(UserProfileCode profileCode) {
        List<UserProfile> profiles = this.profiles;
        if (profiles != null) {
            for (UserProfile profile : profiles) {
                if (profile.getProfileCode().equals(profileCode)) {
                    return profile;
                }
            }
        }
        return null;
    }

    public void addProfile(UserProfile profile) {
        if (profile == null) {
            return;
        }
        if (this.profiles == null) {
            this.profiles = new ArrayList<UserProfile>();
        }
        this.profiles.add(profile);
    }

    public enum UserProfileCode {
        PERSONAL, BUSINESS
    }

    public static class UserProfile implements Serializable {
        private static final long serialVersionUID = -4260663480634580428L;

        private final UserProfileCode userProfileCode;

        private Address address;
        private Email email;
        private MobilePhone mobilePhone;
        private Telephone telephone;

        public UserProfile(UserProfileCode userProfileCode) {
            if (userProfileCode == null) {
                throw new IllegalArgumentException("user profile code can't be null");
            }
            this.userProfileCode = userProfileCode;
        }

        public UserProfileCode getProfileCode() {
            return this.userProfileCode;
        }

        public Address getAddress() {
            return this.address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Email getEmail() {
            return this.email;
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public MobilePhone getMobilePhone() {
            return this.mobilePhone;
        }

        public void setMobilePhone(MobilePhone mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public Telephone getTelephone() {
            return this.telephone;
        }

        public void setTelephone(Telephone telephone) {
            this.telephone = telephone;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.userProfileCode == null) ? 0 : this.userProfileCode.hashCode());
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
            UserProfile other = (UserProfile) obj;
            if (this.userProfileCode != other.userProfileCode)
                return false;
            return true;
        }

        private abstract static class PhoneChannel implements CommunicationChannel {
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

        public static class Email implements CommunicationChannel {
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

        public static class MobilePhone extends PhoneChannel {
            private static final long serialVersionUID = -1982803103647078318L;

            @Override
            public String getChannelCode() {
                return ChannelCodeEnumType.MOBILE_TELEPHONE.value();
            }

        }

        public static class Telephone extends PhoneChannel {
            private static final long serialVersionUID = 339523083834771863L;

            @Override
            public String getChannelCode() {
                return ChannelCodeEnumType.TELEPHONE.value();
            }

        }
    }

}
