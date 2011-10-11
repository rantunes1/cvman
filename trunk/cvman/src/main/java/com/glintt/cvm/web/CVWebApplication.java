package com.glintt.cvm.web;

import java.io.Serializable;
import java.util.Collection;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Required;
import org.vaadin.navigator7.interceptor.Interceptor;
import org.vaadin.navigator7.interceptor.NavigationWarningInterceptor;
import org.vaadin.navigator7.interceptor.PageChangeListenersInterceptor;
import org.vaadin.navigator7.interceptor.ParamChangeListenerInterceptor;
import org.vaadin.navigator7.interceptor.ParamInjectInterceptor;

import com.vaadin.ui.Component;

public class CVWebApplication extends SpringWebApplication implements Serializable {

	private static final long serialVersionUID = 810165665564746584L;

	private transient Collection<Interceptor> interceptors;
	private transient Collection<Class<? extends Component>> pages;

	private transient PageChangeListenersInterceptor pageChangeListenerInterceptor;

	public void setInterceptors(Collection<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}

	@Required
	public void setPages(Collection<Class<? extends Component>> pages) {
		this.pages = pages;
	}

	public PageChangeListenersInterceptor getPageChangeListenerInterceptor() {
		return this.pageChangeListenerInterceptor;
	}

	@PostConstruct
	protected void registerDependencies() {
		super.navigatorConfig.getInterceptorList().clear();
		registerInterceptor(new NavigationWarningInterceptor());
		registerInterceptor(this.pageChangeListenerInterceptor = new PageChangeListenersInterceptor());

		if (this.interceptors != null) {
			for (Interceptor interceptor : this.interceptors) {
				registerInterceptor(interceptor);
			}
		}

		registerInterceptor(new ParamChangeListenerInterceptor());
		registerInterceptor(new ParamInjectInterceptor());

		registerPages(this.pages.toArray(new Class[] {}));
	}
}
