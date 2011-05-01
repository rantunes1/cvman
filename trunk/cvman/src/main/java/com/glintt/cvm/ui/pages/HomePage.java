package com.glintt.cvm.ui.pages;

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
        CVUser user = (CVUser) ((CVApplication) NavigableApplication.getCurrent()).getUser();

        Long userId = user.getId();
        String personQuery = "SELECT p FROM Person p WHERE p.userId=:userId";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userId", userId);
        Person person = FacadeFactory.getFacade().find(personQuery, parameters);
        if (person == null) {
            person = new Person();
            person.setUserId(user.getId());
        }

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSizeFull();

        TabSheet tabs = new TabSheet();
        tabs.setSizeFull();

        Tab t1 = tabs.addTab(new UserTab(person), "My CV", null);

        // Role userRole = user.getRole();
        // @todo uncomment previous line to revert to using real role
        Role userRole = ApplicationRoles.ADMINISTRATOR;

        if (Permissions.hasAccess(userRole, null, ApplicationResources.MANAGEMENT)) {
            Tab t2 = tabs.addTab(new ManagerTab(), "Explore CVs", null);
        }

        if (Permissions.hasAccess(userRole, null, ApplicationResources.ADMINISTRATION)) {
            Tab t3 = tabs.addTab(new AdministratorTab(), "Administration", null);
        }

        layout.addComponent(tabs);
        layout.setCaption(Lang.getMessage("HomePage.UI.caption"));

        setCompositionRoot(layout);
    }
}
