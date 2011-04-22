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

    public Long getOwnerId() {
        Owner owner = getOwner();
        return (owner != null) ? getOwner().getId() : null;
    }

    public boolean isOwnedBy(O owner) {
        if (this.getOwner() == null || owner == null) {
            return false;
        }
        if (this.getLanguageId() == null || owner.getLanguageId() == null) {
            return false;
        }
        if (this.getOwnerId() == null || owner.getId() == null) {
            return false;
        }
        return this.getLanguageId().value().equals(owner.getLanguageId().value()) && this.id.equals(owner.getId());
    }
}
