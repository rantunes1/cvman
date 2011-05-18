package com.glintt.cvm.ui.forms.createuser;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.UserType;
import com.glintt.cvm.security.ApplicationRoles;
import com.glintt.cvm.ui.api.AbstractBaseForm;
import com.glintt.cvm.ui.api.AbstractFormFieldFactory;
import com.glintt.cvm.ui.api.FormFieldDefinition;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;

public class CreateUserForm extends AbstractBaseForm {
    private static final long serialVersionUID = -4348320551950432564L;

    @Override
    protected CVUser getDatasource() {
        return new CVUser();
    }

    @Override
    protected List<FormFieldDefinition> getEditableFieldsList() {
        return new ArrayList<FormFieldDefinition>() {
            private static final long serialVersionUID = 3830836538116098227L;
            {
                add(new FormFieldDefinition("username", true));
                add(new FormFieldDefinition("password", true));
                add(new FormFieldDefinition("email", "CreateUserPage.UI.email.caption", true));
                add(new FormFieldDefinition("name", "CreateUserPage.UI.name.caption"));
                add(new FormFieldDefinition("firstName", "CreateUserPage.UI.firstName.caption"));
                add(new FormFieldDefinition("surname", "CreateUserPage.UI.surname.caption"));
            }
        };
    }

    @Override
    protected FormFieldFactory createFormFactory() {
        return new AbstractFormFieldFactory(this) {
            private static final long serialVersionUID = -2205076078288722621L;

            @Override
            protected Field createPropertyField(Item item, String propertyId, Component uiContext) {
                return null; // let superclass create the field

                // @todo for field username, on change, check database
                // availability. this must also be done when validating
                // the form
            }

        };
    }

    @Override
    protected void executeOnRepaint() {
        setCaption(Lang.getMessage("CreateUserPage.UI.form.caption"));
        setDescription(Lang.getMessage("CreateUserPage.UI.form.description"));
    }

    @Override
    protected ClickListener getOnSaveListener() {
        final CreateUserForm form = this;
        return new ClickListener() {
            private static final long serialVersionUID = 4321448266793397942L;

            @Override
            public void buttonClick(ClickEvent event) {
                System.out.println("Updating form bean contents");

                form.requestRepaint();
                if (!form.isValid()) {
                    System.out.println("form is not valid");
                    return;
                }

                form.commit();

                System.out.println("Persisting changes");
                try {
                    @SuppressWarnings("unchecked")
                    CVUser user = ((BeanItem<CVUser>) form.getItemDataSource()).getBean();
                    if (user.getUserType() == null) {
                        user.setUserType(UserType.EXTERNAL);
                        user.setRole(ApplicationRoles.USER);
                    }
                    FacadeFactory.getFacade().store(user);
                } catch (Exception ex) {
                    // @todo deal with validation exceptions
                    System.out.println("Exception saving form bean : " + ex);
                }
            }

        };
    }
}
