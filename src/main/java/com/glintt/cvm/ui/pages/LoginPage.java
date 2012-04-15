package com.glintt.cvm.ui.pages;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.PageLink;
import org.vaadin.navigator7.uri.Param;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.ui.forms.createuser.CVLoginForm;
import com.glintt.cvm.util.AppConfig;
import com.glintt.cvm.util.AppProperties;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

@Page
public class LoginPage extends CustomComponent {

	private static final long serialVersionUID = 6289195975689211422L;

	@SuppressWarnings("unused")
	@Param
	private transient String exit;

	public LoginPage() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setMargin(true);
		layout.setSizeFull();

		// 1st column
		LoginForm loginForm = new CVLoginForm();
		Panel loginPanel = new Panel((Lang.getMessage("Login.UI.caption")));
		loginPanel.setStyleName("loginPanel");
		loginPanel.setDescription("Login with your existing account");
		loginPanel.addComponent(loginForm);

		layout.addComponent(loginPanel);
		layout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

		// 2nd column
		// @todo move label strings to message resources / use image
		Button signinLinkedInBtn = new Button();
		signinLinkedInBtn.setDisableOnClick(true);
		signinLinkedInBtn.setIcon(new ThemeResource(AppConfig.getString(AppProperties.LINKEDIN_CONNECT_ICON_URI.getKey())));
		signinLinkedInBtn.setStyleName("linkedin_btn");
		signinLinkedInBtn.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -2275826009530243265L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					CVApplication.getCurrent().authenticateOAuth("linkedin");
				} catch (ApplicationException aex) {
					getWindow().showNotification(aex.getMessage(), Notification.TYPE_ERROR_MESSAGE);
					return;
				}

			}
		});
		Panel socialPanel = new Panel((Lang.getMessage("Login.UI.caption")));
		socialPanel.setStyleName("loginPanel");
		socialPanel.setDescription("Connect with your LinkedIn account");
		socialPanel.addComponent(signinLinkedInBtn);
		layout.addComponent(socialPanel);
		layout.setComponentAlignment(socialPanel, Alignment.MIDDLE_CENTER);

		// 3rd column
		PageLink createUserLink = new PageLink(Lang.getMessage("Login.UI.create_new_account"), CreateUserPage.class);
		Panel newUserPanel = new Panel((Lang.getMessage("Login.UI.caption")));
		newUserPanel.setStyleName("loginPanel");
		loginPanel.setDescription("Create a new account");
		newUserPanel.addComponent(createUserLink);
		layout.addComponent(newUserPanel);
		layout.setComponentAlignment(newUserPanel, Alignment.MIDDLE_CENTER);

		setCompositionRoot(layout);
	}
}
