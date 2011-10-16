package com.glintt.cvm.security.linkedin;

import java.util.List;

import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;

public class LinkedInApiBinding extends AbstractOAuth1ApiBinding implements LinkedIn {

	public LinkedInApiBinding(String consumerKey, String consumerSecret, String accessToken, String secret) {
		super(consumerKey, consumerSecret, accessToken, secret);
	}

	@Override
	public String getProfileId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProfileUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedInProfile getProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LinkedInProfile> getConnections() {
		// TODO Auto-generated method stub
		return null;
	}

}