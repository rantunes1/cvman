package com.glintt.cvm.security;

import org.springframework.social.connect.Connection;

import com.glintt.cvm.exception.ApplicationException;

public interface RequestAuthenticator extends Authenticator {
	OAuthRequest authenticate(String providerId, String callbackURI) throws ApplicationException;

	Connection<?> signIn(OAuthRequest oauthRequest, String verifier) throws ApplicationException;

}
