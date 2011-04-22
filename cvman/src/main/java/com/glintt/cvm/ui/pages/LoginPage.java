package com.glintt.cvm.ui.pages;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.PageLink;

import com.glintt.cvm.ui.forms.AppLoginForm;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class LoginPage extends CustomComponent {

    private static final long serialVersionUID = 6289195975689211422L;

    public LoginPage() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSizeFull();
        setCompositionRoot(layout);

        LoginForm loginForm = new AppLoginForm(LoginPage.this);

        PageLink createUserLink = new PageLink(Lang.getMessage("Login.UI.create_new_account"), CreateUserPage.class);

        Panel loginPanel = new Panel(Lang.getMessage("Login.UI.caption"));
        loginPanel.setStyleName("loginPanel");
        loginPanel.addComponent(loginForm);
        loginPanel.addComponent(createUserLink);

        layout.addComponent(loginPanel);
        layout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
    }
}
