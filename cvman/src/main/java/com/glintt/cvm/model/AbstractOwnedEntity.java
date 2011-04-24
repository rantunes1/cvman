package com.glintt.cvm.model;

import javax.persistence.MappedSuperclass;

import org.hr_xml._3.LanguageCodeEnumType;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

@MappedSuperclass
public abstract class AbstractOwnedEntity<O extends Owner> extends AbstractPojo {
    private static final long serialVersionUID = -9039330855876697906L;

    public abstract void setOwner(O owner);

    public abstract O getOwner();

    public LanguageCodeEnumType getLanguageId() {
        return getOwner().getLanguageId();
    }

    public boolean isOwnedBy(O owner) {
        Owner currentOwner = getOwner();
        return currentOwner != null && currentOwner.equals(owner);
    }
}
