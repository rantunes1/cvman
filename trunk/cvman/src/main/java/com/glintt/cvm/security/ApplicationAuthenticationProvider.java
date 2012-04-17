package com.glintt.cvm.security;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.Role;
import com.glintt.cvm.model.UserConnection;
import com.glintt.cvm.service.UserServices;

public class ApplicationAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider implements SignInAdapter {

	private PasswordEncoder passwordEncoder;
	private LDAPAuthenticator<?> ldapAuthenticator;
	private UserServices<CVUser> userServices;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (!userDetails.getPassword().equals(authentication.getCredentials())) {
			throw new InsufficientAuthenticationException("current password doesn't match supplied one.");
		}
	}

	protected CVUser retrieveFromService(String username) {
		CVUser user = this.userServices.findByUsername(username);
		this.logger.debug("Authenticating using Service : " + user);
		return user;
	}

	public CVUser retriveFromLDAP(String username, String rawPassword) {
		CVUser user = null;
		try {
			user = getLdapAuthenticator().authenticate(username, rawPassword);
		} catch (ApplicationException aex) {
			this.logger.debug("error authenticating on LDAP", aex);
		}
		this.logger.debug("Authenticating using LDAP : " + user);
		return user;
	}

	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		// todo Auto-generated method stub
		return null;
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication == null) {
			throw new BadCredentialsException("no authentication token provider");
		}

		Object credentials = authentication.getCredentials();
		if (credentials == null) {
			throw new BadCredentialsException("no credentials provided");
		}

		String rawPassword = credentials.toString();
		String encodedPassword = getPasswordEncoder().encode(rawPassword);

		this.logger.debug("Authenticating user : " + username);

		CVUser user = retrieveFromService(username);
		if (user != null) {
			if (!this.passwordEncoder.matches(rawPassword, user.getPassword())) {
				user = null;
			}
		} else {
			CVUser ldapUser = retriveFromLDAP(username, rawPassword);

			if (ldapUser != null) { // Internal User
				if (user == null) {
					user = ldapUser;
					user.setUsername(username);
					user.setPassword(encodedPassword);
					this.userServices.createUser(user, Role.TYPE_INTERNAL);
				}
			}
		}

		UserConnection userConnection = this.userServices.findUserConnectionByUsernamePassword(username, encodedPassword);

		if (userConnection != null) {
			if (user == null) {
				user = new CVUser();
				user.setUsername(username);
				user.setPassword(encodedPassword);
				this.userServices.createUser(user, Role.TYPE_SOCIAL);
			}

			Long connectionUserId = userConnection.getUserId();
			if (connectionUserId == null) {
				userConnection.setUserId(user.getId());
				this.userServices.updateUserConnection(userConnection);
			} else if (connectionUserId != user.getId()) {
				throw new SecurityException("user doesn't match user connection!");
			}
		}
		if (user == null) {
			throw new BadCredentialsException("invalid username or password");
		}
		return new User(username, rawPassword, true, true, true, true, user.getRoles());
	}

	public PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	@Required
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public LDAPAuthenticator<?> getLdapAuthenticator() {
		return this.ldapAuthenticator;
	}

	@Required
	public void setLdapAuthenticator(LDAPAuthenticator<?> ldapAuthenticator) {
		this.ldapAuthenticator = ldapAuthenticator;
	}

	public UserServices<CVUser> getUserServices() {
		return this.userServices;
	}

	@Required
	public void setUserServices(UserServices<CVUser> userServices) {
		this.userServices = userServices;
	}

}
