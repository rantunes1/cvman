package com.glintt.cvm.web;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.vaadin.appfoundation.authentication.data.User;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.exception.SecurityException;
import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.UserType;
import com.glintt.cvm.security.ApplicationRoles;
import com.glintt.cvm.security.Authenticator;

public class CVAuthenticator implements Authenticator {

	@Override
	public User authenticate(String username, String password) throws ApplicationException {
		CVUser authenticatedUser = null;
		ApplicationException appEx = null;
		try {
			// try to authenticate user on LDAP
			authenticatedUser = authenticateOnLDAP(username, password);
		} catch (ApplicationException aex) {
			appEx = aex;
		}
		// locate user on local storage
		String query = "SELECT u FROM CVUser u WHERE u.username = :username";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		CVUser user = FacadeFactory.getFacade().find(query, parameters);

		if (user == null) {
			if (authenticatedUser == null) {
				// unable to authenticate user. throw exception
				throw new SecurityException(Lang.getMessage("Login.ErrorMessage.authentication_failed"));
			} else {
				// user is not yet registered locally. create a new record
				// for the internal user.
				// password will not be saved.
				user = authenticatedUser;
				user.setUserType(UserType.INTERNAL);
				user.setPassword(null);
				FacadeFactory.getFacade().store(user);
			}
		} else {
			if (authenticatedUser == null) {
				// user exists on local storage but remote authentication
				// failed.
				if (UserType.INTERNAL.equals(user.getUserType())) {
					// @todo review this policy. passwords are not being checked
					// and we're relying
					// on the fact that 'username' must be unique
				} else if (UserType.EXTERNAL.equals(user.getUserType())) {
					if (password == null || !password.equals(user.getPassword())) {
						// unable to authenticate user. re-throw exception
						if (appEx != null) {
							throw appEx;
						} else {
							throw new SecurityException(Lang.getMessage("Login.ErrorMessage.authentication_failed"));
						}
					}
				} else {
					// unable to authenticate user. re-throw exception
					if (appEx != null) {
						throw appEx;
					} else {
						throw new SecurityException(Lang.getMessage("Login.ErrorMessage.authentication_failed"));
					}
				}
			} else {
				// nothing to do
			}
		}

		if (user != null && user.getRole() == null) {
			user.setRole(ApplicationRoles.USER); // defaults to user role if no
													// role was set before
		}
		return user;
	}

	private CVUser authenticateOnLDAP(String username, String password) throws ApplicationException {
		Hashtable<String, String> authEnv = new Hashtable<String, String>(11);
		String dn = "glintt\\" + username;
		String ldapURL = "ldap://gpdc02.glintt.com:389/OU=SITC,DC=glintt,DC=com?sAMAccountName?sub?(objectClass=*)";

		authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		authEnv.put(Context.PROVIDER_URL, ldapURL);
		authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		authEnv.put(Context.SECURITY_PRINCIPAL, dn);
		authEnv.put(Context.SECURITY_CREDENTIALS, password);

		try {
			InitialDirContext ctx = new InitialDirContext(authEnv);

			String searchFilter = "(&(objectClass=user)(" + "sAMAccountName" + "=" + username + "))";
			String[] attrIDs = { "cn", "co", "c", "l", "company", "displayName", "givenName", "name", "mail", "mobile",
					"telephoneNumber", "sn", "streetAddress", "postalCode", "distinguishedname" };
			SearchControls ctls = new SearchControls();
			ctls.setReturningAttributes(attrIDs);
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> sr = ctx.search("", searchFilter, ctls);
			SearchResult searchResult = (sr.hasMore()) ? sr.next() : null;
			if (searchResult == null) {
				throw new SecurityException("unable to find requested user");
			}
			Attributes attrs = searchResult.getAttributes();

			CVUser user = new CVUser();
			user.setUsername(username);
			user.setName(attrs.get("displayName").get().toString());
			user.setEmail(attrs.get("mail").get().toString());
			user.setMobileNumber(attrs.get("mobile").get().toString());
			user.setTelephoneNumber(attrs.get("telephoneNumber").get().toString());
			user.setFirstName(attrs.get("givenName").get().toString());
			user.setSurname(attrs.get("sn").get().toString());
			user.setCompany(attrs.get("company").get().toString());
			user.setCountry(attrs.get("co").get().toString());
			user.setCountryCode(attrs.get("c").get().toString());
			user.setStreetAddress(attrs.get("streetAddress").get().toString());
			user.setLocality(attrs.get("l").get().toString());
			user.setPostalCode(attrs.get("postalCode").get().toString());

			return user;
		} catch (CommunicationException cex) {
			System.out.println("Authentication Server is not reachable");
			throw new ApplicationException(
					"Authentication server could not be reached. Please check your network connection and try again.");
		} catch (AuthenticationException aex) {
			System.out.println("Authentication failed!");
			throw new SecurityException(aex);
		} catch (NamingException nex) {
			System.out.println("Something went wrong!");
			nex.printStackTrace();
			throw new SecurityException(nex);
		}
	}

}
