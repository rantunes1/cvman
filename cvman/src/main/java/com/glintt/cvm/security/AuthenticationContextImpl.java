package com.glintt.cvm.security;

import org.springframework.beans.factory.annotation.Required;

import com.glintt.cvm.service.UserServices;

public class AuthenticationContextImpl implements AuthenticationContext {

	private transient UserServices userServices;
	private transient FormAuthenticator formAuthenticator;
	private transient RequestAuthenticator requestAuthenticator;
	private transient OAuthRequest oauthRequest;
	private transient UserConnection userConnection;

	@Override
	public FormAuthenticator getFormAuthenticator() {
		return this.formAuthenticator;
	}

	@Override
	@Required
	public void setFormAuthenticator(FormAuthenticator formAuthenticator) {
		this.formAuthenticator = formAuthenticator;
	}

	@Override
	public UserServices getUserServices() {
		return this.userServices;
	}

	@Override
	@Required
	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	@Override
	public RequestAuthenticator getRequestAuthenticator() {
		return this.requestAuthenticator;
	}

	@Override
	public void setRequestAuthenticator(RequestAuthenticator requestAuthenticator) {
		this.requestAuthenticator = requestAuthenticator;
	}

	@Override
	public UserConnection getUserConnection() {
		return this.userConnection;
	}

	@Override
	public void setUserConnection(UserConnection userConnection) {
		this.userConnection = userConnection;
	}

	@Override
	public OAuthRequest getOAuthRequest() {
		return this.oauthRequest;
	}

	@Override
	public void setOauthRequest(OAuthRequest oauthRequest) {
		this.oauthRequest = oauthRequest;
	}

}
