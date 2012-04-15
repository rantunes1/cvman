package com.glintt.cvm.security;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUser;

public interface LDAPAuthenticator<U extends CVUser> extends Authenticator {

	U authenticate(String username, String plainTextPassword) throws ApplicationException;

}