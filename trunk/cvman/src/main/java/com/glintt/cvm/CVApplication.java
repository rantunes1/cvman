package com.glintt.cvm;

import java.util.Locale;
import org.apache.lucene.util.Version;
import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.authorization.Permissions;
import org.vaadin.appfoundation.authorization.jpa.JPAPermissionManager;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;
import org.vaadin.lucenecontainer.facade.LuceneFacade;
import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.window.NavigableAppLevelWindow;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.Person;
import com.glintt.cvm.security.ApplicationResources;
import com.glintt.cvm.security.ApplicationRoles;
import com.glintt.cvm.security.Authenticator;
import com.glintt.cvm.util.AppConfig;
import com.glintt.cvm.util.AppProperties;
import com.glintt.cvm.web.SpringWebApplication;
import com.vaadin.terminal.Terminal;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.Notification;

public class CVApplication extends NavigableApplication {
	private static final long serialVersionUID = 5614209556433681287L;

	// @todo inject value?
	private static final Version LUCENE_VERSION = Version.LUCENE_31;
	private static final String[] FIELD_NAMES = { "personalInfo", "professionalInfo" };

	private transient Authenticator authenticator;

	// @todo inject value
	private transient LuceneFacade luceneFacade;

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
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

	public void authenticate(String username, String password) throws ApplicationException {
		if (this.authenticator != null) {
			setUser(this.authenticator.authenticate(username, password));
		}
	}

	public boolean isUserLogged() {
		return getUser() != null;
	}

	public LuceneFacade getLuceneFacade() {
		return this.luceneFacade;
	}

	public void setLuceneFacade(LuceneFacade luceneFacade) {
		this.luceneFacade = luceneFacade;
	}

}
