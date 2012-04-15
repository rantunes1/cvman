package com.glintt.cvm.ui.forms.createuser;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.navigator7.NavigableApplication;

import com.glintt.cvm.CVApplication;
import com.glintt.cvm.exception.ApplicationException;
import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.ui.api.AbstractBeanItemAdapter;
import com.glintt.cvm.ui.api.BeanItemForm;
import com.glintt.cvm.ui.pages.HomePage;
import com.glintt.cvm.web.CVLevelWindow;
import com.vaadin.data.Buffered;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;

public class CreateUserForm extends BeanItemForm<AbstractBeanItemAdapter<CVUser>> {
	private static final long serialVersionUID = -4348320551950432564L;

	private Layout formLayout;

	public CreateUserForm() {
		super(new CVUserFormAdapter(new CVUser()));
	}

	@Override
	protected Layout getFormLayout() {
		if (this.formLayout == null) {
			this.formLayout = new FormLayout();
		}
		return this.formLayout;
	}

	@Override
	public void commit() throws Buffered.SourceException, InvalidValueException {
		if (isValid()) {
			super.commit();
			try {
				CVApplication.getCurrent().authenticateNewUser(getAdapter().getBean());
			} catch (ApplicationException aex) {
				throw new InvalidValueException(aex.getMessage());
			}
			((CVLevelWindow) NavigableApplication.getCurrentNavigableAppLevelWindow()).refresh();
			NavigableApplication.getCurrentNavigableAppLevelWindow().getNavigator().navigateTo(HomePage.class);
		}
	}

	@Override
	public void discard() throws Buffered.SourceException {
		super.discard();
	}

	@Override
	protected void executeOnRepaint() {
		getSaveBtn().setCaption(Lang.getMessage("FormsDefaults.UI.commit.caption"));
		getResetBtn().setCaption(Lang.getMessage("FormsDefaults.UI.discard.caption"));

		setDescription(Lang.getMessage("CreateUserPage.UI.form.description"));
	}
}
