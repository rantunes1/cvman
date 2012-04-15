package com.glintt.cvm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.glintt.cvm.model.CVUser;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public void signin() {
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupForm(final WebRequest request, final HttpServletResponse response) {
		Connection<?> connection = ProviderSignInUtils.getConnection(request);
		if (connection != null) {
			request.setAttribute("message", "@TODO", WebRequest.SCOPE_REQUEST);
		} else {
			request.setAttribute("message", "@TODO", WebRequest.SCOPE_REQUEST);
		}
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String signup(@RequestBody final CVUser user, final HttpServletRequest request, final HttpServletResponse response) {
		throw new UnsupportedOperationException();
	}

}
