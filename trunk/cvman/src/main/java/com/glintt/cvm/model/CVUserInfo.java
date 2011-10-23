package com.glintt.cvm.model;

import com.glintt.cvm.security.OAuthRequest;
import com.glintt.cvm.security.UserConnection;
import com.glintt.cvm.security.UserInfo;

public class CVUserInfo implements UserInfo<CVUser> {
	private static final long serialVersionUID = 1053671742991310906L;

	private transient OAuthRequest oauthRequest;

	private UserConnection userConnection;
	private CVUser user;

	@Override
	public UserConnection getUserConnection() {
		return this.userConnection;
	}

	@Override
	public void setUserConnection(UserConnection userConnection) {
		this.userConnection = userConnection;

	}

	@Override
	public CVUser getUser() {
		return this.user;
	}

	@Override
	public void setUser(CVUser user) {
		this.user = user;
	}

	@Override
	public boolean isUserLogged() {
		return this.user != null;
	}

	@Override
	public boolean isUserConnected() {
		return this.userConnection != null;
	}

	@Override
	public boolean hasOAuthRequest() {
		return this.oauthRequest != null;
	}

	@Override
	public void setOAuthRequest(OAuthRequest oauthRequest) {
		this.oauthRequest = oauthRequest;
	}

	@Override
	public OAuthRequest getOAuthRequest() {
		return this.oauthRequest;
	}

	@Override
	public String getAuthenticationURL() {
		OAuthRequest oauthRequest = getOAuthRequest();
		return oauthRequest == null ? null : oauthRequest.getUrl();
	}
}
