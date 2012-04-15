package com.glintt.cvm.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A collection of data representing a person name.
 * 
 * @author rantunes
 * 
 */
@Entity
@XmlRootElement(name = "person-name")
public class PersonName extends AbstractEntity {
	private static final long serialVersionUID = -3716882767277644210L;

	private String firstName;
	private String surname;

	public PersonName() {
		this(null, null);
	}

	public PersonName(String firstName, String surname) {
		this.firstName = firstName;
		this.surname = surname;
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

	public String getFullName() {
		return getFullName(false);
	}

	public String getFullName(boolean surnameFirst) {
		return (surnameFirst) ? getSurname() + ", " + getFirstName() : getFirstName() + " " + getSurname();
	}
}