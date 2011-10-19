package com.glintt.cvm.security;

import org.vaadin.appfoundation.authentication.data.User;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUser;

public interface FormAuthenticator extends Authenticator {

	// @todo change parameter type to 'User'
	User authenticate(String username, String password, CVUser currentUser) throws ApplicationException;
}
