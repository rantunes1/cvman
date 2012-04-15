package com.glintt.cvm.service;

import org.vaadin.appfoundation.authentication.data.User;

import com.glintt.cvm.model.UserType;
import com.glintt.cvm.security.NamedRole;
import com.glintt.cvm.security.UserConnection;

public interface UserServices<U extends User> {

	U findUserByUsername(String username);

	U findUserByTypeAndUsername(UserType type, String username);

	U findUserByUserId(Long userId);

	U createUser(U user, UserType type, NamedRole role);

	UserConnection createUserConnection(String providerId, String providerUserId);

	UserConnection findUserConnectionByOAuthProvider(String providerId, String providerUserId);

	UserConnection findUserConnectionByUserId(Long userId);

}