package com.glintt.cvm.web;

import org.vaadin.navigator7.Navigator;
import org.vaadin.navigator7.interceptor.ExceptionPage;
import org.vaadin.navigator7.interceptor.Interceptor;
import org.vaadin.navigator7.interceptor.PageInvocation;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUserInfo;
import com.glintt.cvm.ui.pages.ConnectUserPage;
import com.glintt.cvm.ui.pages.CreateUserPage;
import com.glintt.cvm.ui.pages.HomePage;
import com.glintt.cvm.ui.pages.LoginPage;
import com.vaadin.ui.Component;

public class CVLoginInterceptor implements Interceptor {
	@Override
	public void intercept(PageInvocation pageInvocation) {
		System.out.println("----> RUNNING LOGIN INTERCEPTOR");
		Class<? extends Component> pageClass = pageInvocation.getPageClass();
		Navigator navigator = pageInvocation.getNavigator();
		String params = pageInvocation.getParams();
		if (pageClass.equals(ExceptionPage.class)) {
			// @todo check if this is ok
			navigator.navigateTo(HomePage.class, params);
		} else {
			CVUserInfo userInfo = CVApplication.getCurrent().getUserInfo();
			if (userInfo.isUserLogged()) {
				System.out.println("USER IS LOGGED");
				if (userInfo.isUserConnected()) {
					System.out.println("USER IS LOGGED AND CONNECTED");
					if (isUnauthenticatedOnlyPage(pageClass)) {
						if (pageClass.equals(LoginPage.class) && params != null) {
							// logout
							CVApplication.getCurrent().logout();
							navigator.navigateTo(HomePage.class, null);
						} else if (pageClass.equals(HomePage.class)) {
							pageInvocation.invoke();
						} else {
							navigator.navigateTo(HomePage.class, params);
						}
					} else {
						pageInvocation.invoke();
					}
				} else {
					System.out.println("USER IS LOGGED BUT *NOT* CONNECTED");
					// @todo show connect account button on UI
					if (isUnauthenticatedOnlyPage(pageClass)) {
						if (pageClass.equals(LoginPage.class) && params != null) {
							// logout
							CVApplication.getCurrent().logout();
						} else if (pageClass.equals(HomePage.class)) {
							pageInvocation.invoke();
						} else {
							navigator.navigateTo(HomePage.class, params);
						}
					} else {
						pageInvocation.invoke();
					}
				}
			} else {
				System.out.println("USER IS *NOT* LOGGED");
				if (userInfo.isUserConnected()) {
					System.out.println("USER IS *NOT* LOGGED BUT IS CONNECTED");
					// @todo add params to be shown on page
					if (pageClass.equals(ConnectUserPage.class)) {
						pageInvocation.invoke();
					} else {
						navigator.navigateTo(ConnectUserPage.class, params);
					}
				} else {
					System.out.println("USER IS *NOT* LOGGED AND *NOT* CONNECTED");
					if (userInfo.hasOAuthRequest()) {
						System.out.println("USER IS *NOT* LOGGED AND *NOT* CONNECTED BUT HAS AN AUTH REQUEST");
						if (params != null) {
							String[] parsedParams = params.substring(params.indexOf("?") + 1).split("&");
							params = null;
							for (String parsedParam : parsedParams) {
								if ("oauth_verifier".equals(parsedParam.split("=")[0])) {
									String[] splittedParsedParams = parsedParam.split("=");
									if (splittedParsedParams.length == 2) {
										try {
											CVApplication.getCurrent().completeOAuthAuthentication(parsedParam.split("=")[1],
													pageClass);
											break;
										} catch (ApplicationException ignored) {
											ignored.printStackTrace();
										}
									} else {
										// user cancelled the request
										userInfo.setOAuthRequest(null); // invalidate
																		// request
										navigator.navigateTo(HomePage.class);

									}
								}
							}
						}
					} else {
						System.out.println("USER IS *NOT* LOGGED AND *NOT* CONNECTED AND HAS *NOT* AN AUTH REQUEST");
						if (isUnauthenticatedOnlyPage(pageClass)) {
							pageInvocation.setParams(null);
							pageInvocation.invoke();
						} else {
							if (pageClass.equals(LoginPage.class)) {
								pageInvocation.invoke();
							} else {
								navigator.navigateTo(LoginPage.class, params);
							}
						}
					}
				}
			}
		}
	}

	private boolean isUnauthenticatedOnlyPage(Class<? extends Component> pageClass) {
		return pageClass.equals(LoginPage.class) || pageClass.equals(CreateUserPage.class);
	}
}