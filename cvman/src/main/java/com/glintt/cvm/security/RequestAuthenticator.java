package com.glintt.cvm.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.social.connect.Connection;

import com.glintt.cvm.exception.ApplicationException;
import com.vaadin.ui.Component;

public interface RequestAuthenticator extends Authenticator {
	OAuthRequest authenticate(String providerId, HttpServletRequest request, Class<? extends Component> callbackPage)
			throws ApplicationException;

	Connection<?> signIn(OAuthRequest oauthRequest, String verifier) throws ApplicationException;

}
