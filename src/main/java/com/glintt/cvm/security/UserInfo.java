package com.glintt.cvm.security;

import java.io.Serializable;

import org.vaadin.appfoundation.authentication.data.User;


public interface UserInfo<U extends User> extends Serializable {

	void setUserConnection(UserConnection userConnection);

	UserConnection getUserConnection();

	void setUser(U user);

	U getUser();

	boolean isUserLogged();

	boolean isUserConnected();

	boolean hasOAuthRequest();

	void setOAuthRequest(OAuthRequest oauthRequest);

	OAuthRequest getOAuthRequest();

	String getAuthenticationURL();

}
