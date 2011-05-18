package com.glintt.cvm.ui.pages.login;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.Navigator.NavigationEvent;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.PageLink;
import org.vaadin.navigator7.ParamChangeListener;
import org.vaadin.navigator7.uri.Param;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.CVLevelWindow;
import com.glintt.cvm.ui.forms.AppLoginForm;
import com.glintt.cvm.ui.pages.createuser.CreateUserPage;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@Page
public class LoginPage extends CustomComponent implements ParamChangeListener {

    private static final long serialVersionUID = 6289195975689211422L;

    @Param(pos = 0)
    String exit;

    public LoginPage() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSizeFull();

        LoginForm loginForm = new AppLoginForm(LoginPage.this);

        PageLink createUserLink = new PageLink(Lang.getMessage("Login.UI.create_new_account"), CreateUserPage.class);

        Panel loginPanel = new Panel(Lang.getMessage("Login.UI.caption"));
        loginPanel.setStyleName("loginPanel");
        loginPanel.addComponent(loginForm);
        loginPanel.addComponent(createUserLink);

        layout.addComponent(loginPanel);
        layout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

        setCompositionRoot(layout);
    }

    @Override
    public void paramChanged(NavigationEvent navigationEvent) {
        if (this.exit != null) {
            logout();
        }

    }

    private void logout() {
        CVApplication app = (CVApplication) NavigableApplication.getCurrent();
        app.setUser(null);
        ((CVLevelWindow) NavigableApplication.getCurrentNavigableAppLevelWindow()).refresh();
    }
}
