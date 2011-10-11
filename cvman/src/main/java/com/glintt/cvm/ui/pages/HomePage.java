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
import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.Page;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.Person;
import com.glintt.cvm.model.PersonalInfo;
import com.glintt.cvm.model.ProfessionalInfo;
import com.glintt.cvm.model.TestPerson;
import com.glintt.cvm.security.ApplicationResources;
import com.glintt.cvm.security.ApplicationRoles;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

@Page
public class HomePage extends CustomComponent {
	private static final long serialVersionUID = -5058252876771209123L;

	public HomePage() {
		VerticalLayout contentLayout = new VerticalLayout();
		contentLayout.setStyleName("content");
		contentLayout.setMargin(true);
		contentLayout.setSizeFull();

		TabSheet tabs = new TabSheet();
		tabs.setSizeFull();

		CVUser user = (CVUser) ((CVApplication) NavigableApplication.getCurrent()).getUser();
		Person person = getPerson(user);

		Tab t1 = tabs.addTab(new UserTab(person), "#My CV#", null);

		// Role userRole = user.getRole();
		// @todo uncomment previous line to revert to using real role
		Role userRole = ApplicationRoles.ADMINISTRATOR;

		if (Permissions.hasAccess(userRole, null, ApplicationResources.MANAGEMENT)) {
			Tab t2 = tabs.addTab(new ManagerTab(), "#Explore CVs#", null);
		}

		if (Permissions.hasAccess(userRole, null, ApplicationResources.ADMINISTRATION)) {
			Tab t3 = tabs.addTab(new AdministratorTab(), "#Administration#", null);
		}

		contentLayout.addComponent(tabs);
		contentLayout.setCaption(Lang.getMessage("HomePage.UI.caption"));

		setCompositionRoot(contentLayout);
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

}
