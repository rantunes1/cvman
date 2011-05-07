package com.glintt.cvm.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hr_xml._3.LanguageCodeEnumType;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

import com.glintt.cvm.api.Owner;

@Entity
public class Person extends AbstractPojo implements Owner {
    private static final long serialVersionUID = 8039258127166847667L;

    private final LanguageCodeEnumType languageId;

    private Long userId;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private Collection<PersonalInfo> personalInfos;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)
    private Collection<ProfessionalInfo> professionalInfos;

    public Person() {
        this(LanguageCodeEnumType.fromValue(Lang.getLocale().getCountry().toLowerCase()));
    }

    public Person(LanguageCodeEnumType languageId) {
        this.languageId = languageId;
    }

    @Override
    public LanguageCodeEnumType getLanguageId() {
        return this.languageId;
    }

    public PersonalInfo getPersonalInfo() {
        if (this.personalInfos != null) {
            for (PersonalInfo pi : this.personalInfos) {
                if (pi.isOwnedBy(this)) {
                    return pi;
                }
            }
        }
        return null;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        if (personalInfo == null) {
            throw new IllegalArgumentException("personal info can't be null");
        }
        personalInfo.setOwner(this);
        if (this.personalInfos == null) {
            this.personalInfos = new ArrayList<PersonalInfo>();
        }
        this.personalInfos.add(personalInfo);
    }

    public ProfessionalInfo getProfessionalInfo() {
        if (this.professionalInfos != null) {
            for (ProfessionalInfo pi : this.professionalInfos) {
                if (pi.isOwnedBy(this)) {
                    return pi;
                }
            }
        }
        return null;
    }

    public void setProfessionalInfo(ProfessionalInfo professionalInfo) {
        if (professionalInfo == null) {
            throw new IllegalArgumentException("professional info can't be null");
        }
        professionalInfo.setOwner(this);
        if (this.professionalInfos == null) {
            this.professionalInfos = new ArrayList<ProfessionalInfo>();
        }
        this.professionalInfos.add(professionalInfo);
    }

    protected Collection<PersonalInfo> getPersonalInfos() {
        return this.personalInfos;
    }

    protected void setPersonalInfos(Collection<PersonalInfo> personalInfos) {
        this.personalInfos = personalInfos;
    }

    protected Collection<ProfessionalInfo> getProfessionalInfos() {
        return this.professionalInfos;
    }

    protected void setProfessionalInfos(Collection<ProfessionalInfo> professionalInfos) {
        this.professionalInfos = professionalInfos;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
