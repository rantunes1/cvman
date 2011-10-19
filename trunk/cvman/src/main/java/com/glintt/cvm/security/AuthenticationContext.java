package com.glintt.cvm.security;

import javax.servlet.http.HttpServletRequest;

import org.vaadin.appfoundation.authentication.data.User;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.service.UserServices;
import com.vaadin.ui.Component;

public interface AuthenticationContext {

	void setUserServices(UserServices userServices);

	void setFormAuthenticator(FormAuthenticator formAuthenticator);

	void setRequestAuthenticator(RequestAuthenticator requestAuthenticator);

	UserConnection getUserConnection();

	User authenticateForm(String username, String password) throws ApplicationException;

	/**
	 * 
	 * @param providerId
	 * @param request
	 * @param callbackPage
	 *            the page to be called by the provider at the end of the
	 *            authentication process.
	 * @return the url to initiate the provider's oauth authentication process.
	 *         this is usually an url to the provider login page.
	 * @throws ApplicationException
	 */
	String authenticateOAuthProvider(String providerId, HttpServletRequest request, Class<? extends Component> callbackPage)
			throws ApplicationException;

	void signin(String verifier) throws ApplicationException;
}