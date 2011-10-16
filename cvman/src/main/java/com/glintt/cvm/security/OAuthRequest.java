package com.glintt.cvm.security;

import java.io.Serializable;

import org.springframework.social.oauth1.OAuthToken;

public class OAuthRequest implements Serializable {
	private static final long serialVersionUID = 7637527389796953647L;

	private String providerId;
	private OAuthToken token;
	private String url;

	public OAuthToken getToken() {
		return this.token;
	}

	public void setToken(OAuthToken token) {
		this.token = token;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

}
