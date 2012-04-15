package com.glintt.cvm.security.linkedin;

import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuth1Template;

public class LinkedInServiceProvider extends AbstractOAuth1ServiceProvider<LinkedIn> {

	public LinkedInServiceProvider(String consumerKey, String consumerSecret) {
		super(consumerKey, consumerSecret, new OAuth1Template(consumerKey, consumerSecret,
				"https://api.linkedin.com/uas/oauth/requestToken", "https://www.linkedin.com/uas/oauth/authorize",
				"https://www.linkedin.com/uas/oauth/authenticate", "https://api.linkedin.com/uas/oauth/accessToken"));
	}

	@Override
	public LinkedIn getApi(String accessToken, String secret) {
		return new LinkedInApiBinding(getConsumerKey(), getConsumerSecret(), accessToken, secret);
	}

}