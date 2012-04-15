package com.glintt.cvm.security.linkedin;

import org.springframework.social.connect.support.OAuth1ConnectionFactory;

public class LinkedInConnectionFactory extends OAuth1ConnectionFactory<LinkedIn> {

	public LinkedInConnectionFactory(String consumerKey, String consumerSecret) {
		super("linkedin", new LinkedInServiceProvider(consumerKey, consumerSecret), new LinkedInAdapter());
	}
}
