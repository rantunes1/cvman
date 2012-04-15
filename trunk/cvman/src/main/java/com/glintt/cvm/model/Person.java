package com.glintt.cvm.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.hr_xml._3.LanguageCodeEnumType;

@Entity
@XmlRootElement(name = "person")
public class Person extends AbstractEntity {
	private static final long serialVersionUID = 8039258127166847667L;

	private LanguageCodeEnumType languageId;

	private Long userId;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "person_info_personal", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = { @JoinColumn(name = "personal_info_id") })
	private Collection<PersonalInfo> personalInfos;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "person_info_professional", joinColumns = { @JoinColumn(name = "person_id") }, inverseJoinColumns = { @JoinColumn(name = "profissional_info_id") })
	private Collection<ProfessionalInfo> professionalInfos;

	public LanguageCodeEnumType getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(LanguageCodeEnumType languageId) {
		this.languageId = languageId;
	}

	public Collection<PersonalInfo> getPersonalInfos() {
		return this.personalInfos;
	}

	public void setPersonalInfo(PersonalInfo personalInfo) {
		if (personalInfo == null) {
			throw new IllegalArgumentException("personal info can't be null");
		}
		if (this.personalInfos == null) {
			this.personalInfos = new ArrayList<PersonalInfo>();
		}
		this.personalInfos.add(personalInfo);
	}

	public Collection<ProfessionalInfo> getProfessionalInfo() {
		return this.professionalInfos;
	}

	public void setProfessionalInfo(ProfessionalInfo professionalInfo) {
		if (professionalInfo == null) {
			throw new IllegalArgumentException("professional info can't be null");
		}
		if (this.professionalInfos == null) {
			this.professionalInfos = new ArrayList<ProfessionalInfo>();
		}
		this.professionalInfos.add(professionalInfo);
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
