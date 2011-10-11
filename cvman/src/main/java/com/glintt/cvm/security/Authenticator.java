package com.glintt.cvm.security;

import org.vaadin.appfoundation.authentication.data.User;

import com.glintt.cvm.exception.ApplicationException;

public interface Authenticator {

	User authenticate(String username, String password) throws ApplicationException;
}
