package com.glintt.cvm.security;

import org.jasypt.util.password.PasswordEncryptor;
import org.vaadin.appfoundation.authentication.data.User;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.service.UserServices;

public interface SecurityContext<U extends User, UI extends UserInfo<U>> {

	void setUserServices(UserServices<U> userServices);

	UserServices<U> getUserServices();

	void setLDAPAuthenticator(LDAPAuthenticator ldapAuthenticator);

	LDAPAuthenticator getLDAPAuthenticator();

	void setRequestAuthenticator(RequestAuthenticator requestAuthenticator);

	RequestAuthenticator getRequestAuthenticator();

	void setPasswordEncryptor(PasswordEncryptor passwordEncryptor);

	PasswordEncryptor getPasswordEncryptor();

	UI authenticateForm(UI userInfo, String username, String password) throws ApplicationException;

	UI authenticateNewUser(UI userInfo, U newUser) throws ApplicationException;

	UI authenticateOAuthProvider(UI userInfo, String providerId, String callbackURI) throws ApplicationException;

	UI signin(UI userInfo, String verifier) throws ApplicationException;
}