package com.glintt.cvm.service;

import javax.persistence.Entity;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;

@Entity
public class UserConnection extends AbstractPojo {
	private static final long serialVersionUID = -2614960071448380871L;

	private String providerId;
	private String providerUserId;
	private Long userId;

	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return this.providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
