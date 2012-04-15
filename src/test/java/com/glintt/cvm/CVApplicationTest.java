package com.glintt.cvm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import org.vaadin.navigator7.NavigatorConfig;
import org.vaadin.navigator7.WebApplication;
import org.vaadin.navigator7.window.AppLevelWindow;
import org.vaadin.navigator7.window.NavigableAppLevelWindow;

import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.CVUserInfo;
import com.glintt.cvm.security.SecurityContext;
import com.glintt.cvm.web.SpringWebApplication;
import com.vaadin.service.ApplicationContext;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class CVApplicationTest {

	private Mockery testContext;
	private CVApplication testApp;

	private SecurityContext<CVUser, CVUserInfo> mockCVSecurityContext;
	private ServletContext mockServletContext;
	private SpringWebApplication mockWebApplication;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		this.testContext = new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		this.mockCVSecurityContext = this.testContext.mock(SecurityContext.class);

		final WebApplicationContext mockSpringContext = this.testContext.mock(WebApplicationContext.class);
		final ApplicationContext mockApplicationContext = this.testContext.mock(ApplicationContext.class);
		this.mockServletContext = this.testContext.mock(ServletContext.class);
		this.mockWebApplication = this.testContext.mock(SpringWebApplication.class);
		final NavigatorConfig mockNavigatorConfig = this.testContext.mock(NavigatorConfig.class);
		final AppLevelWindow mockLevelWindow = this.testContext.mock(NavigableAppLevelWindow.class);
		final Collection<Class<? extends Component>> pages = new ArrayList<Class<? extends Component>>();
		pages.add(CustomComponent.class);

		this.testContext.checking(new Expectations() {
			{
				one(CVApplicationTest.this.mockServletContext).getAttribute(
						with(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE));
				will(returnValue(mockSpringContext));

				one(mockSpringContext).getBean("webapp", SpringWebApplication.class);
				will(returnValue(CVApplicationTest.this.mockWebApplication));

				one(CVApplicationTest.this.mockServletContext).setAttribute(WebApplication.WEBAPPLICATION_CONTEXT_ATTRIBUTE_NAME,
						CVApplicationTest.this.mockWebApplication);

				one(CVApplicationTest.this.mockCVSecurityContext).getUserServices();
				will(returnValue(null));

				one(CVApplicationTest.this.mockCVSecurityContext).getRequestAuthenticator();
				will(returnValue(null));

				one(CVApplicationTest.this.mockCVSecurityContext).getLDAPAuthenticator();
				will(returnValue(null));

				one(CVApplicationTest.this.mockCVSecurityContext).getPasswordEncryptor();
				will(returnValue(null));

				//
				// one(mockSpringContext).getBean("appLevelWindow",
				// NavigableAppLevelWindow.class);
				// will(returnValue(mockLevelWindow));
				//
				// ignoring(mockLevelWindow);
				//
				//
				// one(mockApplicationContext).addTransactionListener(with(any(Lang.class)));
				//
				// allowing(mockServletContext).getAttribute(WebApplication.WEBAPPLICATION_CONTEXT_ATTRIBUTE_NAME);
				// will(returnValue(mockWebApplication));
				//
				// one(mockWebApplication).getNavigatorConfig();
				// will(returnValue(mockNavigatorConfig));
				//
				// one(mockNavigatorConfig).getPagesClass();
				// will(returnValue(pages));
				//
				// one(mockApplicationContext).addTransactionListener(with(any(CVApplication.class)));

			}
		});

		this.testApp = new CVApplication();

		SpringWebApplication.init(null, this.mockServletContext, this.getClass().getClassLoader());
		this.testApp.setSecurityContext(this.mockCVSecurityContext);
	}

	@Test
	public void testGetCurrent() {
		CVApplication actual = CVApplication.getCurrent();
		Assert.assertNotNull(actual);

	}

	@Test
	public void testGetUserInfo() {
		this.testContext.checking(new Expectations() {
			{
				one(CVApplicationTest.this.mockServletContext).setAttribute(WebApplication.WEBAPPLICATION_CONTEXT_ATTRIBUTE_NAME,
						CVApplicationTest.this.mockWebApplication);
			}
		});

		this.testApp.getUserInfo();
	}

	@Test
	public void testLogout() {
		this.testApp.logout();
	}

	@Test
	public void testGetUser() {
		this.testApp.getUser();
	}

	@Test(expected = RuntimeException.class)
	public void testCreateNewNavigableAppLevelWindowNotInitialized() {
		this.testApp.createNewNavigableAppLevelWindow();
	}

	@Test
	public void testCreateNewNavigableAppLevelWindowInitialized() {
		this.testApp.init();
		this.testApp.createNewNavigableAppLevelWindow();
	}

	@Test
	public void testTerminalErrorErrorEvent() {
		this.testApp.terminalError(null);
	}

	@Test
	public void testTransationStart() {
		this.testApp.transactionStart(null, null);
	}

	@Test
	public void testTransactionEnd() {
		this.testApp.transactionEnd(null, null);
	}

	@Test
	public void testAuthenticateForm() {
		try {
			this.testApp.authenticateForm(null, null);
		} catch (ApplicationException aex) {
			fail("unexpected exception : " + aex.getMessage());
			aex.printStackTrace();
		}
	}

	@Test
	public void testRequestOAuthAuthentication() {
		try {
			this.testApp.authenticateOAuth(null);
		} catch (ApplicationException aex) {
			fail("unexpected exception : " + aex.getMessage());
			aex.printStackTrace();
		}
	}

	@Test
	public void testCompleteOAuthAuthentication() {
		try {
			this.testApp.completeOAuthAuthentication(null, null);
		} catch (ApplicationException aex) {
			fail("unexpected exception : " + aex.getMessage());
			aex.printStackTrace();
		}
	}

	@Test
	public void testGetLuceneFacade() {
		this.testApp.getLuceneFacade();
	}
}
