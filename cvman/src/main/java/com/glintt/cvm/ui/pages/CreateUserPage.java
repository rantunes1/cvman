package com.glintt.cvm.ui.pages;

import org.vaadin.navigator7.Page;

import com.glintt.cvm.ui.forms.CreateUserForm;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

@Page
public class CreateUserPage extends CustomComponent {
    private static final long serialVersionUID = -820799998883290331L;

    public CreateUserPage() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new CreateUserForm());
        setCompositionRoot(layout);
    }
}
