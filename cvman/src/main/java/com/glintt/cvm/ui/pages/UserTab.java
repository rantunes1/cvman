package com.glintt.cvm.ui.pages;

import com.glintt.cvm.model.Person;
import com.glintt.cvm.model.PersonalInfo;
import com.glintt.cvm.model.ProfessionalInfo;
import com.glintt.cvm.ui.forms.CertificationsInfoForm;
import com.glintt.cvm.ui.forms.EmploymentInfoForm;
import com.glintt.cvm.ui.forms.PersonalInfoForm;
import com.glintt.cvm.ui.forms.ProfessionalInfoForm;
import com.glintt.cvm.ui.forms.PublicationsInfoForm;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class UserTab extends CustomComponent {
    private static final long serialVersionUID = 3891511806373976692L;

    public UserTab(Person person) {
        VerticalLayout layout = new VerticalLayout();

        CssLayout personalInfoLayout = new CssLayout();
        personalInfoLayout.setSizeFull();
        Panel personalInfoPanel = new Panel("#personal profile#");
        PersonalInfo personalInfo = person.getPersonalInfo();
        if (personalInfo == null) {
            personalInfo = new PersonalInfo();
            person.setPersonalInfo(personalInfo);
            // start in editing mode
            personalInfoPanel.addComponent(new PersonalInfoForm(person));
        } else {
            // start in view mode
            Panel viewer = new Panel("#PERSONAL INFO VIEWER#");
            Button editButton = new Button("#EDIT#");
            personalInfoPanel.addComponent(viewer);
            personalInfoPanel.addComponent(editButton);
        }
        personalInfoLayout.addComponent(personalInfoPanel);

        HorizontalLayout professionalInfoLayout = new HorizontalLayout();
        professionalInfoLayout.setSizeFull();
        Panel professionalInfoPanel = new Panel("#professional profile#");
        ProfessionalInfo professionalInfo = person.getProfessionalInfo();
        if (professionalInfo == null) {
            professionalInfo = new ProfessionalInfo();
            person.setProfessionalInfo(professionalInfo);
        }
        TabSheet professionalInfoTabs = new TabSheet();
        professionalInfoTabs.setSizeFull();
        professionalInfoTabs.addTab(new GeneralInfoTab(person), "general info", null);
        professionalInfoTabs.addTab(new EmploymentInfoTab(person), "employement history", null);
        professionalInfoTabs.addTab(new CertificationsTab(person), "certifications", null);
        professionalInfoTabs.addTab(new PublicationsTab(person), "publications", null);
        professionalInfoPanel.addComponent(professionalInfoTabs);
        professionalInfoLayout.addComponent(professionalInfoPanel);

        layout.addComponent(personalInfoLayout);
        layout.addComponent(professionalInfoLayout);
        setCompositionRoot(layout);
    }

    public static class GeneralInfoTab extends CustomComponent {
        private static final long serialVersionUID = -9140239051160684236L;

        public GeneralInfoTab(Person person) {
            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(new ProfessionalInfoForm(person));
            setCompositionRoot(layout);
        }

    }

    public static class EmploymentInfoTab extends CustomComponent {
        private static final long serialVersionUID = 2993584335733792991L;

        public EmploymentInfoTab(Person person) {
            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(new EmploymentInfoForm(person));
            setCompositionRoot(layout);
        }

    }

    public static class CertificationsTab extends CustomComponent {
        private static final long serialVersionUID = -5390191191584836627L;

        public CertificationsTab(Person person) {
            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(new CertificationsInfoForm(person));
            setCompositionRoot(layout);
        }

    }

    public static class PublicationsTab extends CustomComponent {
        private static final long serialVersionUID = 2136681534468956320L;

        public PublicationsTab(Person person) {
            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(new PublicationsInfoForm(person));
            setCompositionRoot(layout);
        }

    }
}