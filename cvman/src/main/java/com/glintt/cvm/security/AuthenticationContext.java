package com.glintt.cvm.security;

import com.glintt.cvm.service.UserServices;

public interface AuthenticationContext {

	UserServices getUserServices();

	void setUserServices(UserServices userServices);

	FormAuthenticator getFormAuthenticator();

	void setFormAuthenticator(FormAuthenticator formAuthenticator);

	RequestAuthenticator getRequestAuthenticator();

	void setRequestAuthenticator(RequestAuthenticator requestAuthenticator);

	UserConnection getUserConnection();

	void setUserConnection(UserConnection userConnection);

	OAuthRequest getOAuthRequest();

	void setOauthRequest(OAuthRequest oauthRequest);

}