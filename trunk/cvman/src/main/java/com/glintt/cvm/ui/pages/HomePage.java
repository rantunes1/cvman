package com.glintt.cvm.ui.pages;

import org.vaadin.appfoundation.i18n.Lang;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class HomePage extends CustomComponent {
    private static final long serialVersionUID = -5058252876771209123L;

    public HomePage() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSizeFull();
        setCompositionRoot(layout);

        layout.setCaption(Lang.getMessage("HomePage.UI.caption"));
    }
}
