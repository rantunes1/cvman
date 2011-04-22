package com.glintt.cvm.model;

import javax.persistence.Entity;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;

/**
 * A collection of data representing a person name.
 * 
 * @author rantunes
 * 
 */
@Entity
public class PersonName extends AbstractPojo {
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
}