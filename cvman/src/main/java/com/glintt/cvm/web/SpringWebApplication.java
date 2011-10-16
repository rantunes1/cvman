package com.glintt.cvm.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.vaadin.navigator7.WebApplication;

public class SpringWebApplication extends WebApplication {

	private ApplicationContext applicationContext;

	public static synchronized void init(ServletConfig servletConfig, ServletContext servletContext, ClassLoader classLoader) {
		final WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);

		SpringWebApplication webApplication;
		if (staticReference == null) {
			webApplication = webApplicationContext.getBean("webapp", SpringWebApplication.class);
			if (webApplication == null) {
				throw new RuntimeException(
						"Error initialising Navigator7 Web Application. Please check that a  bean definition for id 'webapp' is present on  the application context file.");
			}
		} else {
			webApplication = (SpringWebApplication) staticReference;
		}
		servletContext.setAttribute(WEBAPPLICATION_CONTEXT_ATTRIBUTE_NAME, webApplication);
		webApplication.applicationContext = webApplicationContext;
		staticReference = webApplication;
	}

	public static SpringWebApplication getCurrent() {
		return (SpringWebApplication) WebApplication.getCurrent();
	}

	public static <T> T getBean(final Class<T> beanType) {
		return getCurrent().applicationContext.getBean(beanType);
	}

	public static <T> T getBean(final String beanId, final Class<T> beanType) {
		return getCurrent().applicationContext.getBean(beanId, beanType);
	}

	public static void beforeService(final HttpServletRequest request, final HttpServletResponse response,
			final ServletContext servletContext) throws ServletException, IOException {
		WebApplication.beforeService(request, response, servletContext);
	}

	public static void afterService(final HttpServletRequest request, final HttpServletResponse response,
			final ServletContext servletContext) throws ServletException, IOException {
		WebApplication.afterService(request, response, servletContext);
	}

}
