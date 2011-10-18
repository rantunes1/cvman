package com.glintt.cvm.security;

import javax.servlet.http.HttpServletRequest;

import com.glintt.cvm.exception.ApplicationException;
import com.vaadin.ui.Component;

public interface RequestAuthenticator extends Authenticator {
	AuthenticationContext authenticate(AuthenticationContext authContext, String providerId, HttpServletRequest request,
			Class<? extends Component> callbackPage) throws ApplicationException;

	AuthenticationContext signIn(AuthenticationContext authContext, OAuthRequest oauthRequest, String verifier)
			throws ApplicationException;

}
