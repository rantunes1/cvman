package com.glintt.cvm.model;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.security.core.GrantedAuthority;

@Entity
@XmlRootElement(name = "role")
public class Role extends AbstractEntity implements GrantedAuthority {
	private static final long serialVersionUID = -629241514938514030L;

	public static final Role ADMINISTRATOR = new Role("ROLE_ADMINISTRATOR");
	public static final Role MANAGER = new Role("ROLE_MANAGER");
	public static final Role USER = new Role("ROLE_USER");
	public static final Role TYPE_INTERNAL = new Role("ROLE_TYPE_INTERNAL");
	public static final Role TYPE_SOCIAL = new Role("ROLE_TYPE_SOCIAL");

	private String name;

	public Role() {
	}

	private Role(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name == null ? "UNKNOWN" : this.name;
	}

	public void setName(String name) {
		this.name = name;
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
		if (!super.equals(obj)) {
			return false;
		}
		Role other = (Role) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String getAuthority() {
		return getName();
	}
}
