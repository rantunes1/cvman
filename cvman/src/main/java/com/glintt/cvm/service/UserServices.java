package com.glintt.cvm.service;

import com.glintt.cvm.model.CVUser;

public interface UserServices {

	CVUser findByUsername(String username);

	CVUser findByOAuthProvider(UserConnection userConnection);

}