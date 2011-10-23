package com.glintt.cvm.service;

import org.vaadin.appfoundation.authentication.data.User;

import com.glintt.cvm.model.UserType;
import com.glintt.cvm.security.UserConnection;

public interface UserServices<U extends User> {

	U findUserByUsername(String username);

	U findUserByTypeAndUsername(UserType internal, String username);

	U findUserByUserId(Long userId);

	UserConnection createUserConnection(String providerId, String providerUserId);

	UserConnection findUserConnectionByOAuthProvider(String providerId, String providerUserId);

	UserConnection findUserConnectionByUserId(Long userId);
}