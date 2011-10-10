package com.glintt.cvm.web;

import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.Navigator;
import org.vaadin.navigator7.interceptor.ExceptionPage;
import org.vaadin.navigator7.interceptor.Interceptor;
import org.vaadin.navigator7.interceptor.PageInvocation;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.ui.pages.createuser.CreateUserPage;
import com.glintt.cvm.ui.pages.login.LoginPage;
import com.glintt.cvm.ui.pages.main.HomePage;

public class LoginInterceptor implements Interceptor {
	@Override
	public void intercept(PageInvocation pageInvocation) {

		Class<?> pageClass = pageInvocation.getPageClass();
		Navigator navigator = pageInvocation.getNavigator();
		String params = pageInvocation.getParams();
		if (pageClass.equals(ExceptionPage.class)) {
			navigator.navigateTo(HomePage.class, params);
		} else {
			boolean isUserLogged = ((CVApplication) NavigableApplication.getCurrent()).isUserLogged();
			if (!isUserLogged) {
				if (pageClass.equals(CreateUserPage.class) || pageClass.equals(LoginPage.class)) {
					pageInvocation.invoke();
				} else {
					navigator.navigateTo(LoginPage.class, params);
				}
			} else if ((pageClass.equals(LoginPage.class) && params == null) || (pageClass.equals(CreateUserPage.class))) {
				navigator.navigateTo(HomePage.class, params);
			} else {
				pageInvocation.invoke();
			}
		}
	}
}
