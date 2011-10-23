package com.glintt.cvm.security;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.CVUserInfo;
import com.glintt.cvm.model.UserType;
import com.glintt.cvm.service.UserServices;
import com.vaadin.ui.Component;

public class CVSecurityContext implements SecurityContext<CVUser, CVUserInfo> {

	private transient UserServices<CVUser> userServices;
	private transient RequestAuthenticator requestAuthenticator;
	private transient LDAPAuthenticator ldapAuthenticator;
	private transient PasswordEncryptor passwordEncryptor;

	@Override
	@Required
	public void setUserServices(UserServices<CVUser> userServices) {
		this.userServices = userServices;
	}

	@Override
	@Required
	public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
		this.passwordEncryptor = passwordEncryptor;
	}

	@Override
	public void setRequestAuthenticator(RequestAuthenticator requestAuthenticator) {
		this.requestAuthenticator = requestAuthenticator;
	}

	@Override
	public void setLDAPAuthenticator(LDAPAuthenticator ldapAuthenticator) {
		this.ldapAuthenticator = ldapAuthenticator;
	}

	@Override
	public CVUserInfo authenticateForm(CVUserInfo userInfo, String username, String password) throws ApplicationException {
		if (userInfo == null || username == null) {
			return userInfo;
		}

		// check for user on database
		String encryptedPassword = this.passwordEncryptor.encryptPassword(password);
		CVUser user = this.userServices.findUserByUsername(username);

		if (user != null) {
			if (!this.passwordEncryptor.checkPassword(password, user.getPassword())) {
				user = null;
			}
		} else if (this.ldapAuthenticator != null) {
			// try to login on ldap
			// @todo inject authenticator
			LDAPAuthenticator ldapAuthenticator = new CVLDAPAuthenticator();
			CVUser ldapUser = ldapAuthenticator.authenticate(username, password);

			if (ldapUser != null) {
				user = this.userServices.findUserByTypeAndUsername(UserType.INTERNAL, ldapUser.getUsername());
				if (user == null) {
					// local user doesn't exist yet but user logged in
					// successful on ldap. create a new internal user:
					user = ldapUser;
					user.setUsername(username);
					user.setPassword(encryptedPassword);
					user.setUserType(UserType.INTERNAL);
					user.setRole(ApplicationRoles.USER);

					FacadeFactory.getFacade().store(user);
				} else {
					// @todo analyse this scenario. user logged with ldap from a
					// previous failed local login.
					// either the account registered internally doesn't belongs
					// to the current user or the user
					// changed its password on ldap before logging in. check if
					// searching by username is sufficient
					// to exclude duplicated users. evaluate using also the
					// e-mail accout when searching the internal user!
				}
			} else {
				// both user and ldap users are null
			}
		}

		UserConnection userConnection = userInfo.getUserConnection();
		if (userConnection == null && user != null) {
			userConnection = this.userServices.findUserConnectionByUserId(user.getId());
		}

		if (userConnection != null) {
			if (user == null) {
				user = new CVUser();
				user.setUsername(username);
				user.setPassword(encryptedPassword);
				user.setUserType(UserType.SOCIAL);
				user.setRole(ApplicationRoles.USER);
				FacadeFactory.getFacade().store(user);
			}

			Long connectionUserId = userConnection.getUserId();
			if (connectionUserId == null) {
				userConnection.setUserId(user.getId());
				FacadeFactory.getFacade().store(userConnection);
			} else if (connectionUserId != user.getId()) {
				throw new SecurityException("user doesn't match user connection!");
			}
		}

		userInfo.setUser(user);
		userInfo.setUserConnection(userConnection);
		return userInfo;
	}

	@Override
	public CVUserInfo authenticateOAuthProvider(CVUserInfo userInfo, String providerId, HttpServletRequest request,
			Class<? extends Component> callbackPage) throws ApplicationException {
		if (this.requestAuthenticator != null && userInfo != null) {
			userInfo.setOAuthRequest(this.requestAuthenticator.authenticate(providerId, request, callbackPage));
		}
		return userInfo;
	}

	@Override
	public CVUserInfo signin(CVUserInfo userInfo, String verifier) throws ApplicationException {
		if (this.requestAuthenticator == null || userInfo == null) {
			return null;
		}

		if (userInfo.isUserLogged() && userInfo.isUserConnected()) {
			return userInfo; // nothing to do. user is already authenticated.
		}
		if (!userInfo.hasOAuthRequest()) {
			return userInfo; // nothing to do. user doesn't have an oauth
								// request yet.
		}

		OAuthRequest oauthRequest = userInfo.getOAuthRequest();
		userInfo.setOAuthRequest(null); // consume current oauth request

		Connection<?> connection = this.requestAuthenticator.signIn(oauthRequest, verifier);
		ConnectionKey key = connection.getKey();

		String providerId = key.getProviderId();
		String providerUserId = key.getProviderUserId();
		UserConnection userConnection = userInfo.getUserConnection();
		if (userConnection == null) {
			userConnection = this.userServices.findUserConnectionByOAuthProvider(providerId, providerUserId);
		}
		if (userConnection == null) {
			userConnection = this.userServices.createUserConnection(providerId, providerUserId);
		}
		userInfo.setUserConnection(userConnection);

		User user = userInfo.getUser();
		if (user == null) {
			user = this.userServices.findUserByUserId(userConnection.getUserId());
		}

		if (user == null) {
			ConnectionData data = connection.createData();
			UserProfile profile = connection.fetchUserProfile();
			String name = connection.getDisplayName();
			String imageURL = connection.getImageUrl();
			String profileURL = connection.getProfileUrl();
			// @todo
			// store new 'SOCIAL' user
			// update userConnection with newly created userId
			// store userConnection
		}

		return userInfo;

	}
}
