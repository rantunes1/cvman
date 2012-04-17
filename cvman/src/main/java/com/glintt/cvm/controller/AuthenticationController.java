package com.glintt.cvm.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.security.ApplicationAuthenticationProvider;

@Controller
public class AuthenticationController {
	private static final String REDIRECT_TO_HOMEPAGE = "redirect:/";

	@Inject
	ApplicationAuthenticationProvider authenticationProvider;

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public void signin() {
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelMap signup(ModelMap model, WebRequest request) {
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
		if (connection != null) {
			// @todo ???
		} else {
			// @todo ???
		}
		SignupForm form = (SignupForm) model.get("signupForm");
		if (form == null) {
			form = new SignupForm();
			model.put("signupForm", form);
		}
		return model;
	}

	@RequestMapping(value = "/signup/external", method = RequestMethod.POST)
	public String signupExternal(SignupForm form, BindingResult formBinding, WebRequest request, final HttpServletResponse response) {
		throw new UnsupportedOperationException();
	}

	@RequestMapping(value = "/signup/internal", method = RequestMethod.POST)
	public String signupInternal(SignupForm form, BindingResult formBinding, Model model, WebRequest request, HttpSession session) {
		Assert.notNull(session, "trying to signup without a valid session");
		ProviderSignInAttempt psia = (ProviderSignInAttempt) session.getAttribute(ProviderSignInAttempt.class.getCanonicalName());
		if (psia == null) {
			return REDIRECT_TO_HOMEPAGE;
		}
		Connection<?> connection = psia.getConnection();
		if (connection == null) {
			return REDIRECT_TO_HOMEPAGE;
		}
		ConnectionKey key = connection.getKey();
		if (key == null) {
			return REDIRECT_TO_HOMEPAGE;
		}

		String username = form.getUsername();
		CVUser user = this.authenticationProvider.retriveFromLDAP(username, form.getPassword());
		if (user == null) {
			return "signup";
		}
		this.authenticationProvider.getUserServices().connectUser(username, key.getProviderId(), key.getProviderUserId());
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, null));
		ProviderSignInUtils.handlePostSignUp(username, request);
		return REDIRECT_TO_HOMEPAGE;
	}

}
