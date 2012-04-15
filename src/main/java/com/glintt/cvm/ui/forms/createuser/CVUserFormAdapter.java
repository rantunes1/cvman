package com.glintt.cvm.ui.forms.createuser;

import org.vaadin.appfoundation.i18n.Lang;

import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.ui.api.AbstractBeanItemAdapter;
import com.glintt.cvm.ui.api.BuildableObjectProperty;
import com.glintt.cvm.ui.customfields.PasswordFormField;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

public class CVUserFormAdapter extends AbstractBeanItemAdapter<CVUser> {
	private static final long serialVersionUID = -6736101137728972607L;

	public CVUserFormAdapter(CVUser user) {
		super(user, new String[] { "name", "username", "email", "password" });
	}

	@Override
	protected void initializeItemProps() {
		getItemProperty("username").setCaption(Lang.getMessage("CreateUserPage.UI.name.caption")).setRequired(true);
		getItemProperty("email").setCaption(Lang.getMessage("CreateUserPage.UI.email.caption")).setRequired(true);
		getItemProperty("password").setCaption(Lang.getMessage("CreateUserPage.UI.password.caption")).setRequired(true);
	}

	@Override
	protected Field createField(Item item, String propertyId, BuildableObjectProperty property, Component uiContext) {
		Field field = null;
		if ("password".equals(propertyId)) {
			field = new PasswordFormField(item, propertyId);
		}
		return field;
	}

}
