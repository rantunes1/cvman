package com.glintt.cvm.ui.pages;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class UserTab extends CustomComponent {
    private static final long serialVersionUID = 3891511806373976692L;

    public UserTab() {
        VerticalLayout layout = new VerticalLayout();

        CssLayout personalInfoLayout = new CssLayout();
        personalInfoLayout.setSizeFull();
        personalInfoLayout.setCaption("personal profile");
        personalInfoLayout.setHeight(10, UNITS_EM);

        HorizontalLayout professionalInfoLayout = new HorizontalLayout();
        professionalInfoLayout.setSizeFull();
        professionalInfoLayout.setCaption("professional profile");
        TabSheet professionalInfoTabs = new TabSheet();
        professionalInfoTabs.setSizeFull();
        professionalInfoTabs.addTab(new GeneralInfoTab(), "general info", null);
        professionalInfoTabs.addTab(new EmploymentInfoTab(), "employement history", null);
        professionalInfoTabs.addTab(new CertificationsTab(), "certifications", null);
        professionalInfoTabs.addTab(new PublicationsTab(), "publications", null);
        professionalInfoLayout.addComponent(professionalInfoTabs);

        layout.addComponent(personalInfoLayout);
        layout.addComponent(professionalInfoLayout);
        setCompositionRoot(layout);
    }

    public static class GeneralInfoTab extends CustomComponent {
        private static final long serialVersionUID = -9140239051160684236L;

        public GeneralInfoTab() {
            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(new Label("general info content"));
            setCompositionRoot(layout);
        }

    }

    public static class EmploymentInfoTab extends CustomComponent {
        private static final long serialVersionUID = 2993584335733792991L;

        public EmploymentInfoTab() {
            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(new Label("employment info content"));
            setCompositionRoot(layout);
        }

    }

    public static class CertificationsTab extends CustomComponent {
        private static final long serialVersionUID = -5390191191584836627L;

        public CertificationsTab() {
            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(new Label("certifications content"));
            setCompositionRoot(layout);
        }

    }

    public static class PublicationsTab extends CustomComponent {
        private static final long serialVersionUID = 2136681534468956320L;

        public PublicationsTab() {
            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(new Label("publications content"));
            setCompositionRoot(layout);
        }

    }
}
