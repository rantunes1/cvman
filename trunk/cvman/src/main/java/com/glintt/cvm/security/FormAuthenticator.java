package com.glintt.cvm.security;

import org.vaadin.appfoundation.authentication.data.User;

import com.glintt.cvm.exception.ApplicationException;

public interface FormAuthenticator extends Authenticator {
	User authenticate(String username, String password) throws ApplicationException;
}
