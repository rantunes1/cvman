package com.glintt.cvm.security.linkedin;

import java.io.Serializable;

public class LinkedInProfile implements Serializable {
	private static final long serialVersionUID = 1013933512703886048L;

	private final String id;
	private final String firstName;
	private final String lastName;
	private final String headline;
	private final String industry;
	private final String standardProfileUrl;
	private final String publicProfileUrl;
	private final String profilePictureUrl;

	public LinkedInProfile(String id, String firstName, String lastName, String headline, String industry, String publicProfileUrl,
			String standardProfileUrl, String profilePictureUrl) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.headline = headline;
		this.industry = industry;
		this.publicProfileUrl = publicProfileUrl;
		this.standardProfileUrl = standardProfileUrl;
		this.profilePictureUrl = profilePictureUrl;
	}

	public String getId() {
		return this.id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getHeadline() {
		return this.headline;
	}

	public String getIndustry() {
		return this.industry;
	}

	public String getStandardProfileUrl() {
		return this.standardProfileUrl;
	}

	public String getPublicProfileUrl() {
		return this.publicProfileUrl;
	}

	public String getProfilePictureUrl() {
		return this.profilePictureUrl;
	}
}
