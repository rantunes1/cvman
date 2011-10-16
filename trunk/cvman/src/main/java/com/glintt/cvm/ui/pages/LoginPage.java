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
import com.glintt.cvm.ui.forms.AppLoginForm;
import com.glintt.cvm.web.CVLevelWindow;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@Page
public class LoginPage extends CustomComponent implements ParamChangeListener {

	private static final long serialVersionUID = 6289195975689211422L;

	@Param(name = "exit")
	private String exit;

	public LoginPage() {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSizeFull();

		LoginForm loginForm = new AppLoginForm(LoginPage.this);

		PageLink createUserLink = new PageLink(Lang.getMessage("Login.UI.create_new_account"), CreateUserPage.class);

		// @todo move label strings to message resources / use image
		Button signinLinkedInBtn = new Button("linkedin");
		signinLinkedInBtn.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = -2275826009530243265L;

			@Override
			public void buttonClick(ClickEvent event) {
				CVApplication app = CVApplication.getCurrent();
				try {
					app.requestOAuthAuthentication("linkedin", LoginPage.class);
				} catch (ApplicationException ignored) {
					ignored.printStackTrace();
				}
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
		// exit =
		// LinkedinAuth?oauth_token=2a512346-745a-4479-b341-d92554327681&oauth_verifier=75056
		if (this.exit != null) {
			logout();
		} else {
			String params = navigationEvent.getParams();
			if (params != null) {
				String[] parsedParams = params.substring(navigationEvent.getParams().indexOf("?") + 1).split("&");
				for (String parsedParam : parsedParams) {
					if ("oauth_verifier".equals(parsedParam.split("=")[0])) {
						try {
							CVApplication.getCurrent().completeOauthAuthentication(parsedParam.split("=")[1], HomePage.class);
							break;
						} catch (ApplicationException ignored) {
							ignored.printStackTrace();
						}
					}
				}
			}
		}

	}

	private void logout() {
		this.exit = null;
		CVApplication.getCurrent().setUser(null);
		((CVLevelWindow) NavigableApplication.getCurrentNavigableAppLevelWindow()).refresh();
	}
}
