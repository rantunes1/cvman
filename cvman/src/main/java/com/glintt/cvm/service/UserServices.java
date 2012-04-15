package com.glintt.cvm.service;

import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.Role;
import com.glintt.cvm.model.UserConnection;

public interface UserServices<U extends CVUser> {

	U findByUsername(String username);

	void createUser(U user, Role distintRole);

	UserConnection findUserConnectionByUsernamePassword(String username, String encodedPassword);

	UserConnection findUserConnectionById(Long userConnectionId);
}