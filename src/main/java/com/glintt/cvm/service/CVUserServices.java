package com.glintt.cvm.service;

import java.util.HashMap;
import java.util.Map;

import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.UserType;
import com.glintt.cvm.security.ApplicationRoles;
import com.glintt.cvm.security.NamedRole;
import com.glintt.cvm.security.UserConnection;

public class CVUserServices implements UserServices<CVUser> {

	@Override
	public CVUser findUserByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		return FacadeFactory.getFacade().find(CVUser.class, userId);
	}

	private CVUser findUserByQuery(String query, Map<String, Object> parameters) {
		return FacadeFactory.getFacade().find(query, parameters);
	}

	private UserConnection findUserConnectionByQuery(String query, Map<String, Object> parameters) {
		return FacadeFactory.getFacade().find(query, parameters);
	}

	private NamedRole findRoleByQuery(String query, Map<String, Object> parameters) {
		return FacadeFactory.getFacade().find(query, parameters);
	}

	@Override
	public CVUser findUserByUsername(String username) {
		if (username == null) {
			return null;
		}
		String query = "SELECT u FROM CVUser u WHERE u.username = :username";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		return findUserByQuery(query, parameters);
	}

	@Override
	public CVUser findUserByTypeAndUsername(UserType userType, String username) {
		if (username == null) {
			return null;
		}
		String query = "SELECT u FROM CVUser u WHERE u.username = :username and u.usertype = :usertype";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		parameters.put("usertype", userType);
		return findUserByQuery(query, parameters);
	}

	private NamedRole findRoleByName(String roleName) {
		if (roleName == null) {
			return null;
		}
		String query = "SELECT r FROM NamedRole r WHERE r.name = :name";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", roleName);
		return findRoleByQuery(query, parameters);
	}

	@Override
	public CVUser createUser(CVUser user, UserType type, NamedRole role) {
		if (user == null) {
			return null;
		}
		if (type == null) {
			if (user.getUserType() == null) {
				user.setUserType(UserType.EXTERNAL);
			} else {
				// @todo user already has a type set. use this?
			}
		} else {
			user.setUserType(type);
		}

		if (role == null) {
			if (user.getRole() == null) {
				role = ApplicationRoles.USER;
			} else {
				// @todo user already has a role set. use this?
				role = user.getRole();
			}
		}

		// get a managed entity copy of role from database
		NamedRole existingRole = findRoleByName(role.getIdentifier());
		user.setRole((existingRole != null) ? existingRole : role);

		FacadeFactory.getFacade().store(user);

		return user;
	}

	@Override
	public UserConnection findUserConnectionByOAuthProvider(String providerId, String providerUserId) {
		if (providerId == null || providerUserId == null) {
			return null;
		}
		String query = "SELECT uc FROM UserConnection uc WHERE uc.providerId = :providerId AND uc.providerUserId = :providerUserId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("providerId", providerId);
		parameters.put("providerUserId", providerUserId);
		return findUserConnectionByQuery(query, parameters);
	}

	@Override
	public UserConnection findUserConnectionByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		String query = "SELECT uc FROM UserConnection uc WHERE uc.userId = :userId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", userId);
		return findUserConnectionByQuery(query, parameters);
	}

	@Override
	public UserConnection createUserConnection(String providerId, String providerUserId) {
		UserConnection userConnection = new UserConnection();
		userConnection.setProviderId(providerId);
		userConnection.setProviderUserId(providerUserId);
		FacadeFactory.getFacade().store(userConnection);
		return userConnection;
	}
}
