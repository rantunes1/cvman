package com.glintt.cvm.ui.customfields;

import com.glintt.cvm.ui.api.AbstractItemField;
import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;

public class PasswordFormField extends AbstractItemField {
	private static final long serialVersionUID = 4667502319618362372L;

	private Field password;
	private Field repeatPassword;

	public PasswordFormField(Item item, Object propertyId) {
		super(item, propertyId);
		this.password = new PasswordField();
		this.password.setRequired(true);
		this.repeatPassword = new PasswordField();
		this.repeatPassword.setRequired(true);

		FormLayout layout = new FormLayout();

		layout.addComponent(this.password);
		layout.addComponent(this.repeatPassword);

		setCompositionRoot(layout);
	}

	@Override
	public void validate() throws Validator.InvalidValueException {
		this.password.validate();
		this.repeatPassword.validate();
		if (!this.password.getValue().equals(this.repeatPassword.getValue())) {
			throw new Validator.InvalidValueException("passwords don't match");
		}
	}

	@Override
	public Object getValue() {
		return this.password.getValue();
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		this.password.setValue(newValue);
		this.repeatPassword.setValue(newValue);
	}

}
