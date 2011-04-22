package com.glintt.cvm.ui.pages;

import com.glintt.cvm.ui.forms.CreateUserForm;
import com.vaadin.ui.VerticalLayout;

public class CreateUserPage extends VerticalLayout {
    private static final long serialVersionUID = -820799998883290331L;

    public CreateUserPage() {
        addComponent(new CreateUserForm());
    }
}
