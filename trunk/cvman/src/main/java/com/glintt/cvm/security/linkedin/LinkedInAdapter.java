package com.glintt.cvm.security.linkedin;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

public class LinkedInAdapter implements ApiAdapter<LinkedIn> {

	/**
	 * Implements {@link Connection#test()} for connections to the given API.
	 * 
	 * @param api
	 *            the API binding
	 * @return true if the API is functional, false if not
	 */
	@Override
	public boolean test(LinkedIn api) {
		return false;
	}

	/**
	 * Sets values for {@link ConnectionKey#getProviderUserId()},
	 * {@link Connection#getDisplayName()}, {@link Connection#getProfileUrl()},
	 * and {@link Connection#getImageUrl()} for connections to the given API.
	 * 
	 * @param api
	 *            the API binding
	 * @param values
	 *            the connection values to set
	 * @throws ApiException
	 *             if there is a problem fetching connection information from
	 *             the provider.
	 */
	@Override
	public void setConnectionValues(LinkedIn api, ConnectionValues values) {

	}

	/**
	 * Implements {@link Connection#fetchUserProfile()} for connections to the
	 * given API. Should never return null. If the provider's API does not
	 * expose user profile data, this method should return
	 * {@link UserProfile#EMPTY}.
	 * 
	 * @param api
	 *            the API binding
	 * @return the service provider user profile
	 * @throws ApiException
	 *             if there is a problem fetching a user profile from the
	 *             provider.
	 * @see UserProfileBuilder
	 */
	@Override
	public UserProfile fetchUserProfile(LinkedIn api) {
		return null;
	}

	/**
	 * Implements {@link Connection#updateStatus(String)} for connections to the
	 * given API. If the provider does not have a status concept calling this
	 * method should have no effect.
	 * 
	 * @param api
	 *            the API binding
	 * @param message
	 *            the status message
	 * @throws ApiException
	 *             if there is a problem updating the user's status on the
	 *             provider.
	 */
	@Override
	public void updateStatus(LinkedIn api, String message) {

	}

}