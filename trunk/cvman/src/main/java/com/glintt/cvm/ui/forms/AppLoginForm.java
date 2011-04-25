package com.glintt.cvm.ui.forms;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.NavigableApplication;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.CVLevelWindow;
import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.ui.pages.HomePage;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Window.Notification;

public class AppLoginForm extends LoginForm {
    private static final long serialVersionUID = 5700619769345646327L;

    public AppLoginForm(final CustomComponent container) {
        super();
        setStyleName("loginForm");

        addListener(new LoginForm.LoginListener() {
            private static final long serialVersionUID = 754194795438709240L;

            @Override
            public void onLogin(LoginEvent event) {
                String username = event.getLoginParameter("username");
                String password = event.getLoginParameter("password");

                if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
                    return;
                }

                boolean loginSuccess = false;
                try {
                    loginSuccess = checkLogin(username, password);
                } catch (ApplicationException aex) {
                    container.getWindow().showNotification(aex.getMessage(), Notification.TYPE_ERROR_MESSAGE);
                    return;
                }

                if (!loginSuccess) {
                    Notification notification = new Notification(Lang.getMessage("Login.ErrorMessage.invalid_username_password"),
                            Notification.TYPE_WARNING_MESSAGE);
                    notification.setPosition(Notification.POSITION_CENTERED_TOP);
                    notification.setDelayMsec(1000);
                    container.getWindow().showNotification(notification);
                    return;
                }

                ((CVLevelWindow) NavigableApplication.getCurrentNavigableAppLevelWindow()).refresh();

                NavigableApplication.getCurrentNavigableAppLevelWindow().getNavigator().navigateTo(HomePage.class);

            }
        });
    }

    private boolean checkLogin(String username, String password) throws ApplicationException {
        CVApplication app = (CVApplication) NavigableApplication.getCurrent();
        app.authenticate(username, password);
        return app.isUserLogged();
    }
}
