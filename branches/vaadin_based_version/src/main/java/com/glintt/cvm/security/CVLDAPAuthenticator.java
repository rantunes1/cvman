package com.glintt.cvm.security;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.exception.SecurityException;
import com.glintt.cvm.model.CVUser;

public class CVLDAPAuthenticator implements LDAPAuthenticator {

	@Override
	public CVUser authenticate(String username, String plainTextPassword) throws ApplicationException {
		Hashtable<String, String> authEnv = new Hashtable<String, String>(11);
		String dn = "glintt\\" + username;
		String ldapURL = "ldap://gpdc02.glintt.com:389/OU=SITC,DC=glintt,DC=com?sAMAccountName?sub?(objectClass=*)";

		authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		authEnv.put(Context.PROVIDER_URL, ldapURL);
		authEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
		authEnv.put(Context.SECURITY_PRINCIPAL, dn);
		if (plainTextPassword == null) {
			plainTextPassword = "";
		}
		authEnv.put(Context.SECURITY_CREDENTIALS, plainTextPassword);

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
			user.setName(attrs.get("displayName").get().toString());
			user.setEmail(attrs.get("mail").get().toString());
			user.setMobileNumber(attrs.get("mobile").get().toString());
			user.setCompany(attrs.get("company").get().toString());
			user.setCountry(attrs.get("co").get().toString());
			user.setCountryCode(attrs.get("c").get().toString());

			return user;
		} catch (CommunicationException cex) {
			System.out.println("Authentication Server is not reachable");
			throw new SecurityException(
					"Authentication server could not be reached. Please check your network connection and try again.", cex);
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
