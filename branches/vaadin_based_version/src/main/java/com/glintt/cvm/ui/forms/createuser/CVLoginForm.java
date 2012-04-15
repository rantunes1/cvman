package com.glintt.cvm.ui.forms.createuser;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.exception.ApplicationException;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.Window.Notification;

public class CVLoginForm extends LoginForm {
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
				authenticate(username, password);
			}
		});
	}

	private void authenticate(String username, String password) {
		if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
			return;
		}

		try {
			CVApplication.getCurrent().authenticateForm(username, password);
		} catch (ApplicationException aex) {
			getWindow().showNotification(aex.getMessage(), Notification.TYPE_ERROR_MESSAGE);
			return;
		}
	}

}
