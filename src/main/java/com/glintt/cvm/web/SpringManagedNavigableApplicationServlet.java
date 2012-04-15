package com.glintt.cvm.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

public class SpringManagedNavigableApplicationServlet extends AbstractApplicationServlet {
	private static final long serialVersionUID = 5274222240867785050L;

	private Class<? extends Application> applicationClass;

	@SuppressWarnings("unchecked")
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletConfig
				.getServletContext());
		if (applicationContext == null) {
			throw new ServletException("Unable to read Spring Context configuration file. Please check your web.xml configuration.");
		}

		this.applicationClass = (Class<? extends Application>) applicationContext.getType("application");
		if (this.applicationClass == null) {
			throw new ServletException(
					"Error initialising Vaadin Application. Please check that a  bean definition for id 'application' is present on  the application context file.");
		}

		SpringWebApplication.init(servletConfig, getServletContext(), getClassLoader());

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SpringWebApplication.beforeService(request, response, getServletContext());
		super.service(request, response);
		SpringWebApplication.afterService(request, response, getServletContext());
	}

	@Override
	protected Application getNewApplication(HttpServletRequest request) throws ServletException {
		try {
			return SpringWebApplication.getBean(getApplicationClass());
		} catch (ClassNotFoundException cnfex) {
			throw new ServletException("application class not found. ", cnfex);
		}
	}

	@Override
	protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
		if (this.applicationClass == null) {
			throw new ClassNotFoundException("application class was not initialised.");
		}
		return this.applicationClass;
	}
}
