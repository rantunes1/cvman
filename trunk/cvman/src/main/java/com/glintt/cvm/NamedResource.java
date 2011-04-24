package com.glintt.cvm;

import javax.persistence.Entity;

import org.vaadin.appfoundation.authorization.Resource;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

@Entity
public class NamedResource extends AbstractPojo implements Resource {
    private static final long serialVersionUID = 690602830785948947L;

    private final String name;

    public NamedResource() {
        this("UNKNOWN");
    }

    public NamedResource(String resourceName) {
        this.name = resourceName;
    }

    @Override
    public String getIdentifier() {
        return this.name;
    }

}
