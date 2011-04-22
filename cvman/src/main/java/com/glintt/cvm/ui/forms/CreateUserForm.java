package com.glintt.cvm.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.UserType;
import com.glintt.cvm.ui.FileUploadFormField;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ErrorMessage;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class CreateUserForm extends Form {
    private static final long serialVersionUID = -4348320551950432564L;

    private boolean validatedAtLeastOnce = false;

    private final List<FormFieldDefinition> EDITABLE_FIELDS = new ArrayList<FormFieldDefinition>() {
        private static final long serialVersionUID = 3830836538116098227L;
        {
            add(new FormFieldDefinition("username", true));
            add(new FormFieldDefinition("password", true));
            add(new FormFieldDefinition("email", "CreateUserPage.UI.email.caption", true));
            add(new FormFieldDefinition("name", "CreateUserPage.UI.name.caption"));
            add(new FormFieldDefinition("firstName", "CreateUserPage.UI.firstName.caption"));
            add(new FormFieldDefinition("surname", "CreateUserPage.UI.surname.caption"));
            add(new FormFieldDefinition("picture", "CreateUserPage.UI.picture.caption"));
        }
    };

    public CreateUserForm() {
        super();

        this.setValidationVisible(false);
        this.setValidationVisibleOnCommit(true);
        this.setImmediate(true);

        final Button commit = new Button();
        final Button discard = new Button();

        addListener(new RepaintRequestListener() {
            private static final long serialVersionUID = -7446116193659548789L;

            @Override
            public void repaintRequested(RepaintRequestEvent event) {
                boolean visible = CreateUserForm.this.isVisible();
                // prevent repaint from being called recursively
                CreateUserForm.this.setVisible(false);
                commit.setCaption(Lang.getMessage("CreateUserPage.UI.commit.caption"));
                discard.setCaption(Lang.getMessage("CreateUserPage.UI.discard.caption"));
                CreateUserForm.this.setCaption(Lang.getMessage("CreateUserPage.UI.form.caption"));
                CreateUserForm.this.setDescription(Lang.getMessage("CreateUserPage.UI.form.description"));
                CreateUserForm.this.setVisible(visible);

            }

        });

        this.setItemDataSource(new BeanItem<CVUser>(new CVUser()));
        this.setWriteThrough(false);

        this.setFormFieldFactory(createUserFormFactory());

        List<String> fieldsOrder = new ArrayList<String>();
        for (FormFieldDefinition fieldDefinition : this.EDITABLE_FIELDS) {
            fieldsOrder.add(fieldDefinition.getName());
        }
        this.setVisibleItemProperties(fieldsOrder);

        commit.addListener(new ClickListener() {
            private static final long serialVersionUID = 4321448266793397942L;

            @Override
            public void buttonClick(ClickEvent event) {
                System.out.println("Updating form bean contents");

                CreateUserForm.this.validatedAtLeastOnce = true;
                CreateUserForm.this.requestRepaint();
                if (!CreateUserForm.this.isValid()) {
                    System.out.println("form is not valid");
                    return;
                }

                CreateUserForm.this.commit();

                System.out.println("Persisting changes");
                try {
                    @SuppressWarnings("unchecked")
                    CVUser user = ((BeanItem<CVUser>) CreateUserForm.this.getItemDataSource()).getBean();
                    if (user.getUserType() == null) {
                        user.setUserType(UserType.EXTERNAL);
                    }
                    FacadeFactory.getFacade().store(user);
                } catch (Exception ex) {
                    // @todo deal with validation exceptions
                    System.out.println("Exception saving form bean : " + ex);
                }
            }

        });

        discard.addListener(new ClickListener() {
            private static final long serialVersionUID = -3459450343828705504L;

            @Override
            public void buttonClick(ClickEvent event) {
                System.out.println("Discarding form changes");
                CreateUserForm.this.discard();
            }

        });

        HorizontalLayout footer = new HorizontalLayout();
        footer.addComponent(commit);
        footer.addComponent(discard);
        this.setFooter(footer);
    }

    @Override
    public ErrorMessage getErrorMessage() {
        ErrorMessage message = super.getErrorMessage();
        if (message != null) {
            return new UserError(Lang.getMessage("CreateUserPage.Validation.generic"));
        }
        return null;
    }

    private FormFieldFactory createUserFormFactory() {
        return new DefaultFieldFactory() {
            private static final long serialVersionUID = -5993283538602645997L;

            @Override
            public Field createField(Item item, Object propertyId, Component uiContext) {
                String propId = (String) propertyId;
                for (FormFieldDefinition fieldDefinition : CreateUserForm.this.EDITABLE_FIELDS) {
                    if (fieldDefinition.getName().equals(propId)) {
                        final Field field = ("picture".equals(propertyId)) ? new FileUploadFormField(item, propertyId) : super
                                .createField(item, propertyId, uiContext);

                        field.setRequired(fieldDefinition.isRequired());

                        if (field.getClass().isAssignableFrom(TextField.class)) {
                            ((TextField) field).setNullRepresentation("");
                        }

                        final String caption = fieldDefinition.getCaption();
                        // @todo for field username, on change, check database
                        // availability. this must also be done when validation
                        // the form
                        field.addListener(new RepaintRequestListener() {
                            private static final long serialVersionUID = -428629319667385841L;

                            @Override
                            public void repaintRequested(RepaintRequestEvent event) {
                                if (!field.isVisible()) {
                                    return;
                                }
                                // prevent repaint from being called recursively
                                field.setVisible(false);
                                if (CreateUserForm.this.validatedAtLeastOnce && field.isRequired()) {
                                    System.out.println("field is required. valid : " + field.isValid());
                                    field.setRequiredError(Lang.getMessage("CreateUserPage.Validation.required", field.getCaption()));
                                }
                                if (caption != null && !caption.equals(((Field) event.getSource()).getCaption())) {
                                    field.setCaption(Lang.getMessage(caption));
                                }
                                field.setVisible(true);

                            }

                        });
                        return field;
                    }
                }
                return null;
            }
        };
    }

}
