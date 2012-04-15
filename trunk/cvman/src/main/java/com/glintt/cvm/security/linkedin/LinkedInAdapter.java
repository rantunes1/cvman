package com.glintt.cvm.security.linkedin;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.web.client.HttpClientErrorException;

public class LinkedInAdapter implements ApiAdapter<LinkedIn> {

	@Override
	public boolean test(LinkedIn linkedin) {
		try {
			linkedin.getProfile();
		} catch (HttpClientErrorException e) {
			return false;
		}
		return true;
	}

	@Override
	public void setConnectionValues(LinkedIn linkedin, ConnectionValues values) {
		LinkedInProfile profile = linkedin.getProfile();
		values.setProviderUserId(profile.getId());
		values.setDisplayName(profile.getFirstName() + " " + profile.getLastName());
		values.setProfileUrl(profile.getPublicProfileUrl());
		values.setImageUrl(profile.getProfilePictureUrl());
	}

	@Override
	public UserProfile fetchUserProfile(LinkedIn linkedin) {
		LinkedInProfile profile = linkedin.getProfile();
		return new UserProfileBuilder().setName(profile.getFirstName() + " " + profile.getLastName()).build();
	}

	@Override
	public void updateStatus(LinkedIn linkedin, String message) {
		// @todo implement
		throw new UnsupportedOperationException();
	}

}