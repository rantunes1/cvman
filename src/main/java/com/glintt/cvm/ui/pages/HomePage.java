package com.glintt.cvm.ui.pages;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.vaadin.appfoundation.authorization.Permissions;
import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;
import org.vaadin.navigator7.Navigator.NavigationEvent;
import org.vaadin.navigator7.Page;
import org.vaadin.navigator7.ParamChangeListener;
import org.vaadin.navigator7.ParamPageLink;
import org.vaadin.navigator7.uri.Param;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.Person;
import com.glintt.cvm.model.PersonalInfo;
import com.glintt.cvm.model.ProfessionalInfo;
import com.glintt.cvm.model.TestPerson;
import com.glintt.cvm.security.ApplicationResources;
import com.glintt.cvm.security.ApplicationRoles;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

@Page
public class HomePage extends CustomComponent implements ParamChangeListener {
	private static final long serialVersionUID = -5058252876771209123L;

	public enum Action {
		USER_ACCOUNT, USER_CV
	}

	@Param
	private Action action;

	public HomePage() {

		HorizontalLayout baseLayout = new HorizontalLayout();
		baseLayout.setCaption(Lang.getMessage("HomePage.UI.caption"));

		Accordion accordion = new Accordion();
		accordion.setWidth("200px");

		TabSheet tabs = new TabSheet();
		tabs.setSizeFull();

		CVUser user = CVApplication.getCurrent().getUserInfo().getUser();
		Person person = getPerson(user);

		// Role userRole = user.getRole();
		// @todo uncomment previous line to revert to using real role
		Role userRole = ApplicationRoles.ADMINISTRATOR;

		Component userTab = new UserTab(person);
		Tab t1t = tabs.addTab(userTab, "#My CV#", null);

		Link myUserAccount = new ParamPageLink("#My User Account#", this.getClass()).addParam("action", Action.USER_ACCOUNT);
		myUserAccount.setDescription("#Manage your user account#");

		Link myCV = new ParamPageLink("#My CV#", this.getClass()).addParam("action", Action.USER_CV);
		myCV.setDescription("#manage your C.V.#");

		VerticalLayout personalInfoLayout = new VerticalLayout();
		personalInfoLayout.addComponent(myUserAccount);
		personalInfoLayout.addComponent(myCV);

		Tab t1a = accordion.addTab(personalInfoLayout, "#Personal Information", null);

		if (Permissions.hasAccess(userRole, null, ApplicationResources.MANAGEMENT)) {
			Component managerTab = new ManagerTab();
			Tab t2t = tabs.addTab(managerTab, "#Explore CVs#", null);
			Tab t2a = accordion.addTab(new Label("bbbbb"), "#Explore CVs#", null);
		}

		if (Permissions.hasAccess(userRole, null, ApplicationResources.ADMINISTRATION)) {
			Component administrationTab = new AdministratorTab();
			Tab t3t = tabs.addTab(administrationTab, "#Administration#", null);
			Tab t3a = accordion.addTab(new Label("ccccc"), "#Administration#", null);
		}

		baseLayout.addComponent(accordion);
		baseLayout.addComponent(tabs);

		setCompositionRoot(baseLayout);
	}

	private Person getPerson(CVUser user) {
		if (user == null) {
			return null; // @todo throw security exception
		}
		Long userId = user.getId();
		String personQuery = "SELECT p FROM Person p WHERE p.userId=:userId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", userId);
		Person person = FacadeFactory.getFacade().find(personQuery, parameters);
		if (person == null) {
			person = new Person();
			// @todo test code - remove next line
			person = new TestPerson();
			person.setUserId(user.getId());
		}
		if (person.getPersonalInfo() == null) {
			PersonalInfo pi = new PersonalInfo();
			pi.setPicture(getDefaultPicture());
			person.setPersonalInfo(pi);
		}
		if (person.getProfessionalInfo() == null) {
			person.setProfessionalInfo(new ProfessionalInfo());
		}
		return person;
	}

	private byte[] getDefaultPicture() {
		// @todo inject properties for file location
		URL url = getClass().getClassLoader().getResource("images/m_nophoto.jpg");
		if (url == null) {
			// @todo ???
			throw new RuntimeException("NO URL FOUND FOR DEFAULT USER PICTURE!");
		}

		InputStream is = null;
		try {
			is = url.openStream();
			if (is != null) {
				BufferedInputStream bis = new BufferedInputStream(is);

				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] byteChunk = new byte[1024];
				int n;

				while ((n = is.read(byteChunk)) > 0) {
					outStream.write(byteChunk, 0, n);
				}

				return outStream.toByteArray();
			}
		} catch (IOException ioex) {
			// TODO Auto-generated catch block
			ioex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignored) {
					// ignore exception
				}
			}
		}
		return null;
	}

	@Override
	public void paramChanged(NavigationEvent navigationEvent) {
		if (this.action == null) {
			System.out.println("NO ACTION SELECTED");
			return;
		}
		switch (this.action) {
		case USER_ACCOUNT:
			System.out.println("SELECTED USER ACCOUNT ");
		default:
			System.out.println(this.action);
		}
	}

}
