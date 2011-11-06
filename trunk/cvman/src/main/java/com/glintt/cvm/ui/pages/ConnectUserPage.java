package com.glintt.cvm.ui.pages;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.PageLink;

import com.glintt.cvm.ui.forms.createuser.CVLoginForm;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Panel;

@Page
public class ConnectUserPage extends CustomComponent {
	private static final long serialVersionUID = -820799998883290331L;

	public ConnectUserPage() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSizeFull();

		// 1st column
		LoginForm loginForm = new CVLoginForm();
		Panel loginPanel = new Panel((Lang.getMessage("Login.UI.caption")));
		loginPanel.setStyleName("loginPanel");
		loginPanel.addComponent(loginForm);

		layout.addComponent(loginPanel);
		layout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

		// 2nd column
		PageLink createUserLink = new PageLink(Lang.getMessage("Login.UI.create_new_account"), CreateUserPage.class);
		Panel newUserPanel = new Panel((Lang.getMessage("Login.UI.caption")));
		newUserPanel.setStyleName("loginPanel");
		newUserPanel.addComponent(createUserLink);
		layout.addComponent(newUserPanel);
		layout.setComponentAlignment(newUserPanel, Alignment.MIDDLE_CENTER);

		setCompositionRoot(layout);

	}
}
