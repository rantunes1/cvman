package com.glintt.cvm;

import java.io.Serializable;

import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.WebApplication;
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
        registerPages(new Class[] { HomePage.class, CreateUserPage.class });
    }

    @Override
    public void registerInterceptors() {
        // 1st interceptor to call: check if user really wants to quit.
        registerInterceptor(new NavigationWarningInterceptor());

        registerInterceptor(this.pageChangeListenerInterceptor = new PageChangeListenersInterceptor());

        registerInterceptor(new Interceptor() {
            @Override
            public void intercept(PageInvocation pageInvocation) {

                if (pageInvocation.getPageClass().equals(CreateUserPage.class)
                        || ((CVApplication) NavigableApplication.getCurrent()).isUserLogged()) {
                    pageInvocation.invoke();
                } else {
                    pageInvocation.getNavigator().placePage(new LoginPage(), pageInvocation.getParams(),
                            pageInvocation.isNeedToChangeUri());
                }
            }
        });

        super.registerInterceptors(); // register default interceptors.
    }

    public PageChangeListenersInterceptor getPageChangeListenerInterceptor() {
        return this.pageChangeListenerInterceptor;
    }
}
