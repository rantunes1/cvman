package com.glintt.cvm.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.glintt.cvm.model.PersonalInfo.PersonContactsProfile;

@Entity
public class OrganizationContacts extends PersonContacts {
	private static final long serialVersionUID = 1708475272749492868L;

	private String role;

	@OneToOne(cascade = CascadeType.ALL)
	private PersonName name;

	public OrganizationContacts() {
		setContactsProfile(PersonContactsProfile.BUSINESS);
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public PersonName getName() {
		return this.name;
	}

	public void setName(PersonName name) {
		this.name = name;
	}

}
