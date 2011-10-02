package com.glintt.cvm;

import java.io.Serializable;

import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.Navigator;
import org.vaadin.navigator7.WebApplication;
import org.vaadin.navigator7.interceptor.ExceptionPage;
import org.vaadin.navigator7.interceptor.Interceptor;
import org.vaadin.navigator7.interceptor.NavigationWarningInterceptor;
import org.vaadin.navigator7.interceptor.PageChangeListenersInterceptor;
import org.vaadin.navigator7.interceptor.PageInvocation;

import com.glintt.cvm.ui.pages.createuser.CreateUserPage;
import com.glintt.cvm.ui.pages.login.LoginPage;
import com.glintt.cvm.ui.pages.main.HomePage;

public class CVWebApplication extends WebApplication implements Serializable {
    private static final long serialVersionUID = 810165665564746584L;

    private transient PageChangeListenersInterceptor pageChangeListenerInterceptor;

    public CVWebApplication() {
        registerPages(new Class[] { LoginPage.class, HomePage.class, CreateUserPage.class });
    }

    @Override
    public void registerInterceptors() {
        // 1st interceptor to call: check if user really wants to quit.
        registerInterceptor(new NavigationWarningInterceptor());

        registerInterceptor(this.pageChangeListenerInterceptor = new PageChangeListenersInterceptor());

        registerInterceptor(new Interceptor() {
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
        });

        super.registerInterceptors(); // register default interceptors.
    }

    public PageChangeListenersInterceptor getPageChangeListenerInterceptor() {
        return this.pageChangeListenerInterceptor;
    }
}