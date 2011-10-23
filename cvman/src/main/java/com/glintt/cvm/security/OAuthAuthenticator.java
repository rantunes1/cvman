package com.glintt.cvm.security;

import javax.servlet.http.HttpServletRequest;

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
import org.vaadin.navigator7.PageLink;
import org.vaadin.navigator7.PageResource;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.security.linkedin.LinkedInConnectionFactory;
import com.vaadin.ui.Component;

public class OAuthAuthenticator implements RequestAuthenticator {

	// @todo move registry to injected bean to decouple this class from
	// references to linkedin (see class constructor)
	private final ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
	private String applicationUrl;

	public OAuthAuthenticator() {
		// @todo move configuration to external properties
		this.registry.addConnectionFactory(new LinkedInConnectionFactory("kmzlmdz3rmfs", "nk6OOeG07l40DsTi"));
	}

	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}

	private OAuth1ConnectionFactory<?> getOAuth1ConnectionFactory(String providerId) throws ApplicationException {
		ConnectionFactory<?> connectionFactory = this.registry.getConnectionFactory(providerId);
		if (connectionFactory instanceof OAuth1ConnectionFactory<?>) {
			return (OAuth1ConnectionFactory<?>) connectionFactory;
		}
		throw new ApplicationException("ConnectionFactory not supported");
	}

	@Override
	public OAuthRequest authenticate(String providerId, HttpServletRequest request, Class<? extends Component> callbackPage)
			throws ApplicationException {
		// @todo add support for oauth2
		OAuth1ConnectionFactory<?> connectionFactory = getOAuth1ConnectionFactory(providerId);
		String callbackURI = callbackPage != null ? ((PageResource) new PageLink(null, callbackPage).getResource()).getURL() : "";
		return buildOAuth1Url(providerId, connectionFactory, request, callbackURI, null);
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

	private OAuthRequest buildOAuth1Url(String providerId, OAuth1ConnectionFactory<?> connectionFactory,
			HttpServletRequest request, String callbackURI, MultiValueMap<String, String> additionalParameters) {
		OAuth1Operations oauthOperations = connectionFactory.getOAuthOperations();
		OAuth1Parameters parameters = new OAuth1Parameters(additionalParameters);
		if (oauthOperations.getVersion() == OAuth1Version.CORE_10_REVISION_A) {
			parameters.setCallbackUrl(callbackUrl(request, callbackURI));
		}
		OAuthToken requestToken = fetchRequestToken(oauthOperations, parameters);
		OAuthRequest oauthRequest = new OAuthRequest();
		oauthRequest.setProviderId(providerId);
		oauthRequest.setToken(requestToken);
		oauthRequest.setUrl(oauthOperations.buildAuthenticateUrl(requestToken.getValue(), parameters));
		return oauthRequest;
	}

	private String callbackUrl(HttpServletRequest request, String callbackURI) {
		String baseURL;
		if (this.applicationUrl != null) {
			baseURL = this.applicationUrl;
		} else {
			if (request != null) {
				StringBuffer requestURLSB = null;
				try {
					requestURLSB = request.getRequestURL();
				} catch (Exception ignored) {
				}
				if (requestURLSB == null) {
					baseURL = request.getProtocol() + "://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/"
							+ request.getContextPath();
				} else {
					String requestURL = requestURLSB.toString();
					baseURL = requestURL.substring(0, requestURL.indexOf(request.getPathInfo()));
				}
			} else {
				// @todo throw exception?
				baseURL = "";
			}
		}
		if (callbackURI != null) {
			baseURL += callbackURI;
		}
		return baseURL;
	}

	private OAuthToken fetchRequestToken(OAuth1Operations oauthOperations, OAuth1Parameters parameters) {
		if (oauthOperations.getVersion() == OAuth1Version.CORE_10_REVISION_A) {
			return oauthOperations.fetchRequestToken(parameters.getCallbackUrl(), null);
		}
		return oauthOperations.fetchRequestToken(null, null);
	}
}
