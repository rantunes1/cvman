package com.glintt.cvm.security.linkedin;

import java.util.List;

import org.springframework.social.ApiBinding;

public interface LinkedIn extends ApiBinding {

	LinkedInProfile getProfile();

	List<LinkedInProfile> getConnections();
}
