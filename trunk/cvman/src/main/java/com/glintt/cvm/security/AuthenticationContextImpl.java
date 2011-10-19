package com.glintt.cvm.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.vaadin.appfoundation.authentication.data.User;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.service.UserServices;
import com.vaadin.ui.Component;

public class AuthenticationContextImpl implements AuthenticationContext {

	private transient UserServices userServices;
	private transient FormAuthenticator formAuthenticator;
	private transient RequestAuthenticator requestAuthenticator;
	private transient OAuthRequest oauthRequest;
	private transient UserConnection userConnection;

	@Override
	@Required
	public void setFormAuthenticator(FormAuthenticator formAuthenticator) {
		this.formAuthenticator = formAuthenticator;
	}

	@Override
	@Required
	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	@Override
	public void setRequestAuthenticator(RequestAuthenticator requestAuthenticator) {
		this.requestAuthenticator = requestAuthenticator;
	}

	@Override
	public UserConnection getUserConnection() {
		return this.userConnection;
	}

	@Override
	public User authenticateForm(String username, String password) throws ApplicationException {
		if (username == null) {
			return null;
		}
		CVUser user = this.userServices.findByUsername(username);
		return this.formAuthenticator.authenticate(username, password, user);
	}

	@Override
	public String authenticateOAuthProvider(String providerId, HttpServletRequest request, Class<? extends Component> callbackPage)
			throws ApplicationException {
		if (this.requestAuthenticator != null) {
			this.oauthRequest = this.requestAuthenticator.authenticate(providerId, request, callbackPage);
			if (this.oauthRequest != null) {
				return this.oauthRequest.getUrl();
			}
		}
		return null;
	}

	@Override
	public void signin(String verifier) throws ApplicationException {
		if (this.requestAuthenticator == null || this.oauthRequest == null) {
			return;
		}

		Connection<?> connection = this.requestAuthenticator.signIn(this.oauthRequest, verifier);
		ConnectionKey key = connection.getKey();

		UserConnection userConnection = new UserConnection();
		userConnection.setProviderId(key.getProviderId());
		userConnection.setProviderUserId(key.getProviderUserId());
		CVUser user = this.userServices.findByOAuthProvider(userConnection);
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
		this.userConnection = userConnection;
		this.oauthRequest = null;
	}

}
