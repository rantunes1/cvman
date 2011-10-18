package com.glintt.cvm.service;

import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.security.UserConnection;

public interface UserServices {

	CVUser findByUsername(String username);

	CVUser findByOAuthProvider(UserConnection userConnection);

}