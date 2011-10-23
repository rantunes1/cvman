package com.glintt.cvm.ui.pages;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.Navigator.NavigationEvent;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.PageLink;
import org.vaadin.navigator7.ParamChangeListener;
import org.vaadin.navigator7.uri.Param;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUserInfo;
import com.glintt.cvm.web.CVLevelWindow;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.Notification;

@Page
public class LoginPage extends CustomComponent implements ParamChangeListener {

	private static final long serialVersionUID = 6289195975689211422L;

	@Param(name = "exit")
	private String exit;

	public LoginPage() {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSizeFull();

		LoginForm loginForm = new CVLoginForm();

		PageLink createUserLink = new PageLink(Lang.getMessage("Login.UI.create_new_account"), CreateUserPage.class);

		// @todo move label strings to message resources / use image
		Button signinLinkedInBtn = new Button("linkedin");
		signinLinkedInBtn.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -2275826009530243265L;

			@Override
			public void buttonClick(ClickEvent event) {
				LoginPage.this.authenticate();
			}
		});

		Panel loginPanel = new Panel(Lang.getMessage("Login.UI.caption"));
		loginPanel.setStyleName("loginPanel");
		loginPanel.addComponent(loginForm);
		loginPanel.addComponent(createUserLink);

		layout.addComponent(loginPanel);
		layout.addComponent(signinLinkedInBtn);
		layout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

		setCompositionRoot(layout);
	}

	@Override
	public void paramChanged(NavigationEvent navigationEvent) {
		if (this.exit != null) {
			logout();
		}
	}

	private void authenticate() {
		CVUserInfo userInfo;
		try {
			CVApplication app = CVApplication.getCurrent();
			app.requestOAuthAuthentication("linkedin", LoginPage.class);
			userInfo = app.getUserInfo();
		} catch (ApplicationException aex) {
			getWindow().showNotification(aex.getMessage(), Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		redirect(userInfo);
	}

	private void authenticate(String username, String password) {
		if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
			return;
		}

		CVUserInfo userInfo;
		try {
			CVApplication app = CVApplication.getCurrent();
			app.authenticateForm(username, password);
			userInfo = app.getUserInfo();
		} catch (ApplicationException aex) {
			getWindow().showNotification(aex.getMessage(), Notification.TYPE_ERROR_MESSAGE);
			return;
		}
		redirect(userInfo);
	}

	public void redirect(CVUserInfo userInfo) {
		if (!userInfo.isUserLogged() && !userInfo.isUserConnected()) {
			Notification notification = new Notification(Lang.getMessage("Login.ErrorMessage.invalid_username_password"),
					Notification.TYPE_WARNING_MESSAGE);
			notification.setPosition(Notification.POSITION_CENTERED_TOP);
			notification.setDelayMsec(1000);
			getWindow().showNotification(notification);
		} else {
			((CVLevelWindow) NavigableApplication.getCurrentNavigableAppLevelWindow()).refresh();
			NavigableApplication.getCurrentNavigableAppLevelWindow().getNavigator().navigateTo(HomePage.class);
		}
	}

	private void logout() {
		this.exit = null;
		CVApplication.getCurrent().logout();
		((CVLevelWindow) NavigableApplication.getCurrentNavigableAppLevelWindow()).refresh();
	}

	private class CVLoginForm extends LoginForm {
		private static final long serialVersionUID = 5700619769345646327L;

		public CVLoginForm() {
			super();
			setStyleName("loginForm");

			addListener(new LoginForm.LoginListener() {
				private static final long serialVersionUID = 754194795438709240L;

				@Override
				public void onLogin(LoginEvent event) {
					String username = event.getLoginParameter("username");
					String password = event.getLoginParameter("password");
					LoginPage.this.authenticate(username, password);
				}
			});
		}
	}
}
