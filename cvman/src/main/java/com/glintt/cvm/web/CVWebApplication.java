package com.glintt.cvm.web;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Required;
import org.vaadin.navigator7.interceptor.Interceptor;
import org.vaadin.navigator7.interceptor.NavigationWarningInterceptor;
import org.vaadin.navigator7.interceptor.PageChangeListenersInterceptor;
import com.glintt.cvm.ui.pages.createuser.CreateUserPage;
import com.glintt.cvm.ui.pages.login.LoginPage;
import com.glintt.cvm.ui.pages.main.HomePage;

public class CVWebApplication extends SpringWebApplication implements Serializable {

	private static final long serialVersionUID = 810165665564746584L;

	private transient Collection<Interceptor> interceptors;

	private transient PageChangeListenersInterceptor pageChangeListenerInterceptor;

	public CVWebApplication() {
		registerPages(new Class[] { LoginPage.class, HomePage.class, CreateUserPage.class });
	}

	@Required
	public void setInterceptors(Collection<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}

	@Override
	public void registerInterceptors() {
		// 1st interceptor to call: check if user really wants to quit.
		registerInterceptor(new NavigationWarningInterceptor());

		registerInterceptor(this.pageChangeListenerInterceptor = new PageChangeListenersInterceptor());

		registerInterceptor(new LoginInterceptor());

		super.registerInterceptors(); // register default interceptors.
	}

	public PageChangeListenersInterceptor getPageChangeListenerInterceptor() {
		return this.pageChangeListenerInterceptor;
	}
}
