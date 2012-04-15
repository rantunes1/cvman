package com.glintt.cvm.security.linkedin;

import java.util.List;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;

public class LinkedInApiBinding extends AbstractOAuth1ApiBinding implements LinkedIn {

	public LinkedInApiBinding(String consumerKey, String consumerSecret, String accessToken, String secret) {
		super(consumerKey, consumerSecret, accessToken, secret);
		registerLinkedInJsonModule();
	}

	@Override
	public LinkedInProfile getProfile() {
		return getRestTemplate()
				.getForObject(
						"https://api.linkedin.com/v1/people/~:(id,first-name,last-name,headline,industry,site-standard-profile-request,public-profile-url,picture-url)?format=json",
						LinkedInProfile.class);
	}

	@Override
	public List<LinkedInProfile> getConnections() {
		LinkedInConnections connections = getRestTemplate().getForObject(
				"https://api.linkedin.com/v1/people/~/connections?format=json", LinkedInConnections.class);
		return connections.getConnections();
	}

	private void registerLinkedInJsonModule() {
		List<HttpMessageConverter<?>> converters = getRestTemplate().getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new SimpleModule("LinkedInModule", new Version(1, 0, 0, null)) {
					@Override
					public void setupModule(SetupContext context) {
						context.setMixInAnnotations(LinkedInConnections.class, LinkedInConnectionsMixin.class);
						context.setMixInAnnotations(LinkedInProfile.class, LinkedInProfileMixin.class);
					}
				});
				jsonConverter.setObjectMapper(objectMapper);
			}
		}
	}

}