package com.glintt.cvm;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Required;
import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.authorization.Permissions;
import org.vaadin.appfoundation.authorization.jpa.JPAPermissionManager;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;
import org.vaadin.lucenecontainer.facade.LuceneFacade;
import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.PageResource;
import org.vaadin.navigator7.window.NavigableAppLevelWindow;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.CVUserInfo;
import com.glintt.cvm.model.Person;
import com.glintt.cvm.security.ApplicationResources;
import com.glintt.cvm.security.ApplicationRoles;
import com.glintt.cvm.security.CVSecurityContext;
import com.glintt.cvm.ui.pages.HomePage;
import com.glintt.cvm.ui.pages.LoginPage;
import com.glintt.cvm.util.AppConfig;
import com.glintt.cvm.util.AppProperties;
import com.glintt.cvm.web.SpringWebApplication;
import com.vaadin.terminal.Terminal;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.Notification;

public class CVApplication extends NavigableApplication implements HttpServletRequestListener {
	private static final long serialVersionUID = 5614209556433681287L;

	// @todo inject value?
	private static final Version LUCENE_VERSION = Version.LUCENE_31;
	private static final String[] FIELD_NAMES = { "personalInfo", "professionalInfo" };

	private transient CVSecurityContext secContext;
	private transient HttpServletRequest request;
	private transient CVUserInfo userInfo;

	// @todo inject value
	private transient LuceneFacade luceneFacade;

	@Required
	public void setSecurityContext(CVSecurityContext authContext) {
		CVApplication app = CVApplication.getCurrent();
		if (app == null) {
			app = this;
		}
		app.secContext = authContext;
	}

	private CVSecurityContext getSecurityContext() {
		CVApplication app = CVApplication.getCurrent();
		if (app == null) {
			app = this;
		}
		return app.secContext;
	}

	public static CVApplication getCurrent() {
		return (CVApplication) NavigableApplication.getCurrent();
	}

	private void setRequest(HttpServletRequest request) {
		CVApplication app = CVApplication.getCurrent();
		if (app == null) {
			app = this;
		}
		app.request = request;
	}

	private HttpServletRequest getRequest() {
		CVApplication app = CVApplication.getCurrent();
		if (app == null) {
			app = this;
		}
		return app.request;
	}

	private void setUserInfo(CVUserInfo userInfo) {
		CVApplication app = CVApplication.getCurrent();
		if (app == null) {
			app = this;
		}
		app.userInfo = userInfo;
	}

	public CVUserInfo getUserInfo() {
		CVApplication app = CVApplication.getCurrent();
		if (app == null) {
			app = this;
		}
		CVUserInfo userInfo = app.userInfo;
		if (userInfo == null) {
			userInfo = new CVUserInfo();
		}
		setUserInfo(userInfo);
		return userInfo;
	}

	public void logout() {
		setUserInfo(null);
	}

	@Override
	public Object getUser() {
		return getUserInfo().getUser();
	}

	@Override
	public void setUser(Object user) {
		if (user != null && CVUser.class.isAssignableFrom(user.getClass())) {
			getUserInfo().setUser((CVUser) user);
		}
	}

	@Override
	public void init() {
		System.out.println("INITIALIZING I18N");

		Lang.initialize(this);
		Locale locale = new Locale(AppConfig.getString(AppProperties.LOCALE_LANGUAGE.getKey()),
				AppConfig.getString(AppProperties.LOCALE_COUNTRY.getKey()));
		Lang.setLocale(locale);

		super.init();
		setTheme(AppConfig.getString(AppProperties.THEME.getKey()));

		System.out.println("INITIALIZING SECURITY");
		SessionHandler.initialize(this);
		Permissions.initialize(this, new JPAPermissionManager());

		Permissions.allowAll(ApplicationRoles.ADMINISTRATOR, ApplicationResources.ADMINISTRATION);
		Permissions.denyAll(ApplicationRoles.MANAGER, ApplicationResources.ADMINISTRATION);
		Permissions.denyAll(ApplicationRoles.USER, ApplicationResources.ADMINISTRATION);
		Permissions.denyAll(ApplicationRoles.USER, ApplicationResources.MANAGEMENT);

		System.out.println("INITIALIZING LUCENE INDEX");
		String luceneIndexDir = AppConfig.getString(AppProperties.LUCENE_INDEX_DIR.getKey());
		setLuceneFacade(new LuceneFacade(LUCENE_VERSION, luceneIndexDir, AppProperties.LUCENE_INDEX_ID_PERSON.getKey()));
		getLuceneFacade().createIndex(luceneIndexDir, FacadeFactory.getFacade(), Person.class, FIELD_NAMES);
		// ViewHandler.initialize(this);

		// @todo TEST CODE (to be removed)
		// Person testPerson = new TestPerson();
		// FacadeFactory.getFacade().store(testPerson);
		//
		// try {
		// Person testPersonBD = FacadeFactory.getFacade().find(Person.class,
		// testPerson.getId());
		// System.out.println("person retrieved : " + testPersonBD.getId());
		// new HRXMLConverter().convertAndSave(testPersonBD, new
		// FileOutputStream("candidateBDOut.xml"));
		// System.out.println("person converted");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	@Override
	public NavigableAppLevelWindow createNewNavigableAppLevelWindow() {
		NavigableAppLevelWindow window = SpringWebApplication.getBean("appLevelWindow", NavigableAppLevelWindow.class);
		if (window == null) {
			throw new RuntimeException(
					"Error initialising Navigator7 Level Window. Please check that a  bean definition for id 'appLevelWindow' is present on  the application context file.");
		}
		window.setSizeFull();

		window.addListener(new Window.CloseListener() {
			private static final long serialVersionUID = -1268430795721422774L;

			@Override
			public void windowClose(CloseEvent e) {
				System.out.println("Closing the application");
				System.out.println(e.getSource());
				System.out.println(e.getWindow().getName());
				// getMainWindow().getApplication().close();
			}
		});
		return window;
	}

	@Override
	public void terminalError(Terminal.ErrorEvent event) {
		super.terminalError(event);

		Window mainWindow = getMainWindow();
		if (mainWindow != null) {
			mainWindow.showNotification("An unexpected exception occured!", event.getThrowable().toString(),
					Notification.TYPE_ERROR_MESSAGE);
		}
	}

	@Override
	public void onRequestStart(HttpServletRequest request, HttpServletResponse response) {
		if (!isVaadinAjaxRequest(request)) {
			setRequest(request);
			System.out.println("------> REQUEST START! " + request.getPathInfo() + " - " + request.getQueryString());
		}
	}

	@Override
	public void onRequestEnd(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("------> REQUEST END! " + getRequest());
	}

	public void authenticateForm(String username, String password) throws ApplicationException {
		CVSecurityContext secContext = getSecurityContext();
		CVUserInfo userInfo = getUserInfo();
		setUserInfo(secContext.authenticateForm(userInfo, username, password));
	}

	public void requestOAuthAuthentication(String providerId, Class<? extends Component> callbackPage) throws ApplicationException {
		CVSecurityContext authContext = getSecurityContext();
		CVUserInfo userInfo = getUserInfo();
		userInfo = authContext.authenticateOAuthProvider(userInfo, providerId, getRequest(), callbackPage);
		String authURL = (userInfo.getAuthenticationURL());
		PageResource redirect;
		if (authURL == null) {
			redirect = new PageResource(HomePage.class);
		} else {
			redirect = new PageResource(authURL);
		}
		CVApplication.getCurrentNavigableAppLevelWindow().open(redirect);
	}

	public void completeOAuthAuthentication(String verifier, Class<? extends Component> callbackPage) throws ApplicationException {
		CVSecurityContext authContext = getSecurityContext();
		CVUserInfo userInfo = getUserInfo();
		userInfo = authContext.signin(userInfo, verifier);
		// @todo review
		new LoginPage().redirect(userInfo);
	}

	public LuceneFacade getLuceneFacade() {
		return this.luceneFacade;
	}

	public void setLuceneFacade(LuceneFacade luceneFacade) {
		this.luceneFacade = luceneFacade;
	}

	private boolean isVaadinAjaxRequest(HttpServletRequest request) {
		return request != null && "/UIDL".equals(request.getPathInfo());
	}

}
