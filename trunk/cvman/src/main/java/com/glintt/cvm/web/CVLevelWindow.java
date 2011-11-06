package com.glintt.cvm.web;

import java.util.Locale;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.NavigableApplication;
import org.vaadin.navigator7.Navigator.NavigationEvent;
import org.vaadin.navigator7.ParamPageLink;
import org.vaadin.navigator7.WebApplication;
import org.vaadin.navigator7.interceptor.PageChangeListenersInterceptor.PageChangeListener;
import org.vaadin.navigator7.window.HeaderFooterFluidAppLevelWindow;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUserInfo;
import com.glintt.cvm.ui.pages.LoginPage;
import com.glintt.cvm.util.AppConfig;
import com.glintt.cvm.util.AppProperties;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

public class CVLevelWindow extends HeaderFooterFluidAppLevelWindow {
	private static final long serialVersionUID = -862665532709430539L;

	private Component header;
	private Layout headerBand;
	private Component footer;
	private Layout footerBand;

	@Override
	protected Component createHeader() {
		VerticalLayout header = new VerticalLayout();
		header.addStyleName("header");
		header.setWidth("100%");
		header.setHeight(6, Component.UNITS_EM);

		CVUserInfo userInfo = CVApplication.getCurrent().getUserInfo();

		if (userInfo.isUserLogged()) {
			header.addComponent(new Label(Lang.getMessage("Header.authenticated.caption", userInfo.getUser().getName())));
			header.addComponent(new ParamPageLink(Lang.getMessage("Header.logout"), LoginPage.class).addParam("exit", ""));
			if (!userInfo.isUserConnected()) {
				Button signinLinkedInBtn = new Button("linkedin");
				signinLinkedInBtn.setDisableOnClick(true);
				signinLinkedInBtn.addListener(new Button.ClickListener() {
					private static final long serialVersionUID = -2275826009530243265L;

					@Override
					public void buttonClick(ClickEvent event) {
						try {
							CVApplication.getCurrent().authenticateOAuth("linkedin");
						} catch (ApplicationException aex) {
							getWindow().showNotification(aex.getMessage(), Notification.TYPE_ERROR_MESSAGE);
							return;
						}

					}
				});

				header.addComponent(signinLinkedInBtn);
			}
		} else {
			header.addComponent(new Label(Lang.getMessage("Header.guest.caption")));
		}

		ComboBox i18Combo = new ComboBox(Lang.getMessage("Header.lang"));
		i18Combo.setNullSelectionAllowed(false);
		i18Combo.setNewItemsAllowed(false);
		// @todo grab values to populate list. remove empty option and set
		// selected value to be the current Locale
		i18Combo.addItem("");
		i18Combo.addItem("en");
		i18Combo.addItem("pt");
		i18Combo.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 528275277817821339L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = (String) event.getProperty().getValue();

				Lang.setLocale(new Locale(value, value));

				((CVLevelWindow) NavigableApplication.getCurrentNavigableAppLevelWindow()).refresh();
			}
		});
		i18Combo.setImmediate(true);
		header.addComponent(i18Combo);
		header.setComponentAlignment(i18Combo, Alignment.TOP_RIGHT);

		// @todo remove (debug only) NavigationListener label
		final Label navLabel = new Label();
		navLabel.setWidth(null);
		header.addComponent(navLabel);
		header.setComponentAlignment(navLabel, Alignment.BOTTOM_LEFT);

		CVWebApplication webapp = (CVWebApplication) WebApplication.getCurrent();
		webapp.getPageChangeListenerInterceptor().addPageChangeListener(new PageChangeListener() {
			private static final long serialVersionUID = 3844442620108457514L;

			@Override
			public void pageChanged(NavigationEvent event) {
				navLabel.setValue("PageChangeListener: pageClass = " + event.getPageClass() + " -- params = " + event.getParams());
			}
		});

		return header;
	}

	@Override
	protected Component createFooter() {
		VerticalLayout footer = new VerticalLayout();
		footer.addStyleName("footer");
		footer.setWidth("100%");

		Label footerLbl = null;
		if (CVApplication.getCurrent().getUserInfo().isUserLogged()) {
			footerLbl = new Label(Lang.getMessage("Footer.authenticated.caption"));
		} else {
			footerLbl = new Label(Lang.getMessage("Footer.guest.caption"));
		}
		footer.addComponent(footerLbl);
		footer.setComponentAlignment(footerLbl, Alignment.TOP_LEFT);

		Label copyrightLbl = new Label(Lang.getMessage("Footer.copyright"));
		copyrightLbl.setWidth(null);
		footer.addComponent(copyrightLbl);
		footer.setComponentAlignment(copyrightLbl, Alignment.BOTTOM_RIGHT);

		return footer;

	}

	@Override
	protected ComponentContainer createComponents() {
		setTheme(AppConfig.getString(AppProperties.THEME.getKey()));

		VerticalLayout windowContentLayout = (VerticalLayout) getContent();

		this.header = createHeader();
		this.headerBand = new HorizontalLayout();
		this.headerBand.setWidth("100%");
		this.headerBand.addComponent(this.header);
		windowContentLayout.addComponent(this.headerBand);

		CssLayout content = new CssLayout(); // Will be super.pageContainer.
		windowContentLayout.addComponent(content);
		content.setWidth("100%");

		this.footer = createFooter();
		this.footerBand = new HorizontalLayout();
		this.footerBand.setWidth("100%");
		this.footerBand.addComponent(this.footer);
		windowContentLayout.addComponent(this.footerBand);

		return content;
	}

	public void refresh() {
		this.headerBand.removeAllComponents();
		this.headerBand.addComponent(createHeader());

		this.footerBand.removeAllComponents();
		this.footerBand.addComponent(createFooter());

		getPageContainer().requestRepaintAll();
	}

}
