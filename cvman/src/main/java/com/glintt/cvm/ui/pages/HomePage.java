package com.glintt.cvm.ui.pages;

import org.vaadin.appfoundation.authorization.Permissions;
import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.Page;

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
        // Role userRole = ((CVUser) ((CVApplication)
        // NavigableApplication.getCurrent()).getUser()).getRole();
        // @todo uncomment previous line to rever to real role
        Role userRole = ApplicationRoles.ADMINISTRATOR;

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSizeFull();

        TabSheet tabs = new TabSheet();
        tabs.setSizeFull();

        Tab t1 = tabs.addTab(new UserTab(), "My CV", null);

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
