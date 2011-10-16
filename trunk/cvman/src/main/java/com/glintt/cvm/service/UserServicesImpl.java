package com.glintt.cvm.service;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.glintt.cvm.model.CVUser;

public class UserServicesImpl implements UserServices {

	@Override
	public CVUser findByUsername(String username) {
		if (username == null) {
			return null;
		}
		String query = "SELECT u FROM CVUser u WHERE u.username = :username";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		return FacadeFactory.getFacade().find(query, parameters);
	}

	@Override
	public CVUser findByOAuthProvider(UserConnection userConnection) {
		if (userConnection == null) {
			return null;
		}
		String providerId = userConnection.getProviderId();
		String providerUserId = userConnection.getProviderUserId();
		if (providerId == null || providerUserId == null) {
			return null;
		}
		String query = "SELECT uc FROM UserConnection uc WHERE uc.providerId = :providerId and uc.providerUserId = :providerUserId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("providerId", providerId);
		parameters.put("userProviderId", providerUserId);
		userConnection = FacadeFactory.getFacade().find(query, parameters);
		if (userConnection == null) {
			// user is not yet connected to this application. should be
			// redirected to create user account page
			// @todo check if user profile can be fetched at this point
			return null;
		}
		return FacadeFactory.getFacade().find(CVUser.class, userConnection.getUserId());
	}
}
