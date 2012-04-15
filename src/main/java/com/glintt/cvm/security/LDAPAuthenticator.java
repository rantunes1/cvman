package com.glintt.cvm.security;

import org.vaadin.appfoundation.authentication.data.User;

import com.glintt.cvm.exception.ApplicationException;

public interface LDAPAuthenticator extends Authenticator {

	<U extends User> U authenticate(String username, String plainTextPassword) throws ApplicationException;

}