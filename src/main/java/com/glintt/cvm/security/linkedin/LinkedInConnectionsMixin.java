package com.glintt.cvm.security.linkedin;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkedInConnectionsMixin {

	public LinkedInConnectionsMixin(@JsonProperty("values") List<LinkedInProfile> connections) {
	}

}
