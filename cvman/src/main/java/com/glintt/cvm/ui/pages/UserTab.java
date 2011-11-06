package com.glintt.cvm.ui.pages;

import com.glintt.cvm.model.Person;
import com.glintt.cvm.model.PersonalInfo;
import com.glintt.cvm.ui.forms.personalinfo.PersonalInfoForm;
import com.glintt.cvm.ui.forms.professionalinfo.ProfessionalInfoForm;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class UserTab extends CustomComponent {
	private static final long serialVersionUID = 3891511806373976692L;

	public UserTab(Person person) {
		VerticalLayout layout = new VerticalLayout();

		HorizontalLayout personalInfoLayout = new HorizontalLayout();
		personalInfoLayout.setSizeFull();
		Panel personalInfoPanel = new Panel("#personal information#");

		TabSheet personalInfoTabs = new TabSheet();
		personalInfoTabs.addTab(new PersonalProfileTab(person), "#personal profile info#", null);
		personalInfoTabs.addTab(new AdditionalPersonalInfoTab(person), "#additional profile info#", null);
		personalInfoTabs.setSizeFull();
		personalInfoPanel.addComponent(personalInfoTabs);
		personalInfoLayout.addComponent(personalInfoPanel);
		layout.addComponent(personalInfoLayout);

		HorizontalLayout professionalInfoLayout = new HorizontalLayout();
		professionalInfoLayout.setSizeFull();
		Panel professionalInfoPanel = new Panel("#professional information#");

		TabSheet professionalInfoTabs = new TabSheet();
		professionalInfoTabs.setSizeFull();
		professionalInfoTabs.addTab(new GeneralInfoTab(person), "#general info#", null);
		professionalInfoTabs.addTab(new EmploymentInfoTab(person), "#employement history#", null);
		professionalInfoTabs.addTab(new CertificationsTab(person), "#certifications#", null);
		professionalInfoTabs.addTab(new PublicationsTab(person), "#publications#", null);
		professionalInfoPanel.addComponent(professionalInfoTabs);
		professionalInfoLayout.addComponent(professionalInfoPanel);
		layout.addComponent(professionalInfoLayout);

		setCompositionRoot(layout);
	}

	public static class PersonalProfileTab extends CustomComponent {
		private static final long serialVersionUID = -9140239051160684236L;

		public PersonalProfileTab(Person person) {
			VerticalLayout layout = new VerticalLayout();
			PersonalInfo personalInfo = person.getPersonalInfo();
			if (personalInfo == null || personalInfo.getId() == null) {
				// start in editing mode
				Form form = new PersonalInfoForm(person.getPersonalInfo());
				layout.addComponent(form);
			} else {
				// start in view mode
				Panel viewer = new Panel("#PERSONAL INFO VIEWER#");
				Button editButton = new Button("#EDIT#");
				layout.addComponent(viewer);
				layout.addComponent(editButton);
			}
			setCompositionRoot(layout);
		}

	}

	public static class AdditionalPersonalInfoTab extends CustomComponent {
		private static final long serialVersionUID = 5013280823208413546L;

		public AdditionalPersonalInfoTab(Person person) {
			VerticalLayout layout = new VerticalLayout();
			PersonalInfo personalInfo = person.getPersonalInfo();
			if (personalInfo == null || personalInfo.getId() == null) {
				// start in editing mode
				Form form = new PersonalInfoForm(person.getPersonalInfo());
				layout.addComponent(form);
			} else {
				// start in view mode
				Panel viewer = new Panel("#PERSONAL INFO VIEWER#");
				Button editButton = new Button("#EDIT#");
				layout.addComponent(viewer);
				layout.addComponent(editButton);
			}
			setCompositionRoot(layout);
		}

	}

	public static class GeneralInfoTab extends CustomComponent {
		private static final long serialVersionUID = -4293283544318174210L;

		public GeneralInfoTab(Person person) {
			VerticalLayout layout = new VerticalLayout();
			Form form = new ProfessionalInfoForm(person.getProfessionalInfo());
			layout.addComponent(form);
			setCompositionRoot(layout);
		}

	}

	public static class EmploymentInfoTab extends CustomComponent {
		private static final long serialVersionUID = 2993584335733792991L;

		public EmploymentInfoTab(Person person) {
			VerticalLayout layout = new VerticalLayout();
			Form form = new ProfessionalInfoForm(person.getProfessionalInfo());
			layout.addComponent(form);
			setCompositionRoot(layout);
		}

	}

	public static class CertificationsTab extends CustomComponent {
		private static final long serialVersionUID = -5390191191584836627L;

		public CertificationsTab(Person person) {
			VerticalLayout layout = new VerticalLayout();
			Form form = new ProfessionalInfoForm(person.getProfessionalInfo());
			layout.addComponent(form);
			setCompositionRoot(layout);
		}

	}

	public static class PublicationsTab extends CustomComponent {
		private static final long serialVersionUID = 2136681534468956320L;

		public PublicationsTab(Person person) {
			VerticalLayout layout = new VerticalLayout();
			Form form = new ProfessionalInfoForm(person.getProfessionalInfo());
			layout.addComponent(form);
			setCompositionRoot(layout);
		}

	}
}
