package com.glintt.cvm.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
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
	@OneToMany(mappedBy = "role")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		NamedRole other = (NamedRole) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
