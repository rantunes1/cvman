package com.glintt.cvm.ui.pages;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.Page;

import com.glintt.cvm.ui.forms.createuser.CreateUserForm;
import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Form;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@Page
public class CreateUserPage extends CustomComponent {
	private static final long serialVersionUID = -820799998883290331L;

	private Panel container;

	public CreateUserPage() {
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSizeFull();

		Form form = new CreateUserForm();
		this.container = new Panel();
		this.container.addComponent(form);
		layout.addComponent(this.container);

		setCompositionRoot(layout);
	}

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		this.container.setCaption(Lang.getMessage("CreateUserPage.UI.form.caption"));
		super.paintContent(target);
	}

}
