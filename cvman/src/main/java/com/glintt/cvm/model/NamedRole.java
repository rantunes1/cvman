package com.glintt.cvm.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.persistence.data.AbstractPojo;

@Entity
public class NamedRole extends AbstractPojo implements Role {
    private static final long serialVersionUID = -629241514938514030L;

    private final String name;

    @Transient
    private final Set<Role> roles = new HashSet<Role>();

    public NamedRole() {
        this("UNKNOWN");
    }

    public NamedRole(String roleName) {
        this.name = roleName;
    }

    @Override
    public String getIdentifier() {
        return this.name;
    }

    @Override
    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public Set<Role> getRoles() {
        return new HashSet<Role>(this.roles);
    }

    @Override
    public void removeRole(Role role) {
        this.roles.remove(role);
    }

    @Override
    public void setRoles(Set<Role> roles) {
        roles.clear();
        roles.addAll(roles);
    }

}
