package com.glintt.cvm.ui.pages;

import org.vaadin.appfoundation.authorization.Permissions;
import org.vaadin.appfoundation.authorization.Role;
import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.NavigableApplication;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.security.ApplicationResources;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

public class HomePage extends CustomComponent {
    private static final long serialVersionUID = -5058252876771209123L;

    public HomePage() {
        Role userRole = ((CVUser) ((CVApplication) NavigableApplication.getCurrent()).getUser()).getRole();

        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.setSizeFull();

        TabSheet tabs = new TabSheet();
        tabs.setSizeFull();

        VerticalLayout tab1Layout = new VerticalLayout();
        tab1Layout.setMargin(true);
        tab1Layout.addComponent(new Label("this is my cv page"));
        Tab t1 = tabs.addTab(tab1Layout, "My CV", null);

        if (Permissions.hasAccess(userRole, null, ApplicationResources.MANAGEMENT)) {
            VerticalLayout tab2Layout = new VerticalLayout();
            tab2Layout.setMargin(true);
            tab2Layout.addComponent(new Label("this is the explore cv page"));
            Tab t2 = tabs.addTab(tab2Layout, "Explore CVs", null);
        }

        if (Permissions.hasAccess(userRole, null, ApplicationResources.ADMINISTRATION)) {
            VerticalLayout tab3Layout = new VerticalLayout();
            tab3Layout.setMargin(true);
            tab3Layout.addComponent(new Label("this is the administration page"));
            Tab t3 = tabs.addTab(tab3Layout, "Administration", null);
        }

        layout.addComponent(tabs);
        layout.addComponent(new Label("this should be at the end of the page"));
        layout.setCaption(Lang.getMessage("HomePage.UI.caption"));

        setCompositionRoot(layout);
    }
}
