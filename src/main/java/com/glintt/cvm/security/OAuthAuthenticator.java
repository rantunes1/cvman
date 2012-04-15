package com.glintt.cvm.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.oauth1.AuthorizedRequestToken;
import org.springframework.social.oauth1.OAuth1Operations;
import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuth1Version;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.util.MultiValueMap;
import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.security.linkedin.LinkedInConnectionFactory;

public class OAuthAuthenticator implements RequestAuthenticator {

	// @todo move registry to injected bean to decouple this class from
	// references to linkedin (see class constructor)
	private final ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();

	public OAuthAuthenticator() {
		// @todo move configuration to external properties
		this.registry.addConnectionFactory(new LinkedInConnectionFactory("kmzlmdz3rmfs", "nk6OOeG07l40DsTi"));
	}

	private OAuth1ConnectionFactory<?> getOAuth1ConnectionFactory(String providerId) throws ApplicationException {
		ConnectionFactory<?> connectionFactory = this.registry.getConnectionFactory(providerId);
		if (connectionFactory instanceof OAuth1ConnectionFactory<?>) {
			return (OAuth1ConnectionFactory<?>) connectionFactory;
		}
		throw new ApplicationException("ConnectionFactory not supported");
	}

	@Override
	public OAuthRequest authenticate(String providerId, String callbackURI) throws ApplicationException {
		// @todo add support for oauth2
		OAuth1ConnectionFactory<?> connectionFactory = getOAuth1ConnectionFactory(providerId);

		return buildOAuth1Url(providerId, connectionFactory, callbackURI, null);
	}

	@Override
	public Connection<?> signIn(OAuthRequest oauthRequest, String verifier) throws ApplicationException {
		OAuth1ConnectionFactory<?> connectionFactory = getOAuth1ConnectionFactory(oauthRequest.getProviderId());
		AuthorizedRequestToken requestToken = new AuthorizedRequestToken(oauthRequest.getToken(), verifier);
		OAuthToken accessToken = connectionFactory.getOAuthOperations().exchangeForAccessToken(requestToken, null);
		Connection<?> connection = connectionFactory.createConnection(accessToken);
		if (connection.hasExpired()) {
			connection.refresh();
			if (connection.hasExpired()) {
				return null;
			}
		}
		return connection;
	}

	private OAuthRequest buildOAuth1Url(String providerId, OAuth1ConnectionFactory<?> connectionFactory, String callbackURI,
			MultiValueMap<String, String> additionalParameters) {
		OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth1Parameters parameters = new OAuth1Parameters(additionalParameters);
		if (oauthOperations.getVersion() == OAuth1Version.CORE_10_REVISION_A) {
			parameters.setCallbackUrl(callbackURI);
		}
		OAuthToken requestToken = fetchRequestToken(oauthOperations, parameters);
		OAuthRequest oauthRequest = new OAuthRequest();
		oauthRequest.setProviderId(providerId);
		oauthRequest.setToken(requestToken);
		oauthRequest.setUrl(oauthOperations.buildAuthenticateUrl(requestToken.getValue(), parameters));
		return oauthRequest;
	}

	private OAuthToken fetchRequestToken(OAuth1Operations oauthOperations, OAuth1Parameters parameters) {
		if (oauthOperations.getVersion() == OAuth1Version.CORE_10_REVISION_A) {
			return oauthOperations.fetchRequestToken(parameters.getCallbackUrl(), null);
		}
		return oauthOperations.fetchRequestToken(null, null);
	}
}
