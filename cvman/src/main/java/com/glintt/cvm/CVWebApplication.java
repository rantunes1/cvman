package com.glintt.cvm;

import java.io.Serializable;

import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.WebApplication;
import org.vaadin.navigator7.interceptor.ExceptionPage;
import org.vaadin.navigator7.interceptor.Interceptor;
import org.vaadin.navigator7.interceptor.NavigationWarningInterceptor;
import org.vaadin.navigator7.interceptor.PageChangeListenersInterceptor;
import org.vaadin.navigator7.interceptor.PageInvocation;

import com.glintt.cvm.ui.pages.CreateUserPage;
import com.glintt.cvm.ui.pages.HomePage;
import com.glintt.cvm.ui.pages.LoginPage;

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

                if (pageInvocation.getPageClass().equals(ExceptionPage.class)) {
                    pageInvocation.getNavigator().navigateTo(HomePage.class, pageInvocation.getParams());
                } else {
                    boolean isUserLogged = ((CVApplication) NavigableApplication.getCurrent()).isUserLogged();
                    if (!isUserLogged) {
                        if (pageInvocation.getPageClass().equals(CreateUserPage.class)
                                || pageInvocation.getPageClass().equals(LoginPage.class)) {
                            pageInvocation.invoke();
                        } else {
                            pageInvocation.getNavigator().navigateTo(LoginPage.class, pageInvocation.getParams());
                        }
                    } else if (pageInvocation.getPageClass().equals(LoginPage.class) && pageInvocation.getParams() == null) {
                        pageInvocation.getNavigator().navigateTo(HomePage.class, pageInvocation.getParams());
                    } else if (pageInvocation.getPageClass().equals(CreateUserPage.class)) {
                        pageInvocation.getNavigator().navigateTo(HomePage.class, pageInvocation.getParams());
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
