package com.glintt.cvm.security.linkedin;

import java.util.List;

public class LinkedInConnections {

	private List<LinkedInProfile> connections;

	public LinkedInConnections(List<LinkedInProfile> connections) {
		this.connections = connections;
	}

	public List<LinkedInProfile> getConnections() {
		return this.connections;
	}
}
