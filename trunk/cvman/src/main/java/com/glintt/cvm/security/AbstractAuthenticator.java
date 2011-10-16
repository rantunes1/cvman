package com.glintt.cvm.security;

import org.springframework.beans.factory.annotation.Required;

import com.glintt.cvm.service.UserServices;

public abstract class AbstractAuthenticator implements Authenticator {

	private transient UserServices userServices;

	@Override
	@Required
	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;

	}

	protected UserServices getUserServices() {
		return this.userServices;
	}

}
