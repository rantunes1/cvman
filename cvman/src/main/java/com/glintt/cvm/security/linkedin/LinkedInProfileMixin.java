package com.glintt.cvm.security.linkedin;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedInProfileMixin {

	@JsonCreator
	public LinkedInProfileMixin(
			@JsonProperty("id") String id,
			@JsonProperty("firstName") String firstName,
			@JsonProperty("lastName") String lastName,
			@JsonProperty("headline") String headline,
			@JsonProperty("industry") String industry,
			@JsonProperty("publicProfileUrl") String publicProfileUrl,
			@JsonProperty("siteStandardProfileRequest") @JsonDeserialize(using = RequestUrlDeserializer.class) String standardProfileUrl,
			@JsonProperty("pictureUrl") String profilePictureUrl) {
	}

	private static class RequestUrlDeserializer extends JsonDeserializer<String> {
		@Override
		public String deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			return jp.readValueAsTree().get("url").getValueAsText();
		}
	}
}
