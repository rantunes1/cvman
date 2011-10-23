package com.glintt.cvm.ui.pages;

import org.vaadin.navigator7.Page;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

@Page
public class ConnectUserPage extends CustomComponent {
	private static final long serialVersionUID = -820799998883290331L;

	public ConnectUserPage() {
		VerticalLayout layout = new VerticalLayout();

		setCompositionRoot(layout);
	}
}
