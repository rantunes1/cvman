package com.glintt.cvm.ui.pages.main;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ManagerTab extends CustomComponent {
    private static final long serialVersionUID = 3891511806373976692L;

    public ManagerTab() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("this is the explore cv tab"));
        setCompositionRoot(layout);
    }
}
