package com.glintt.cvm.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.appfoundation.i18n.Lang;
import org.vaadin.appfoundation.persistence.facade.FacadeFactory;

import com.glintt.cvm.model.CVUser;
import com.glintt.cvm.model.UserType;
import com.glintt.cvm.security.ApplicationRoles;
import com.glintt.cvm.ui.customfields.FileUploadFormField;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.ErrorMessage;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.HorizontalLayout;

public class CreateUserForm extends AbstractBaseForm {
    private static final long serialVersionUID = -4348320551950432564L;

    private boolean validatedAtLeastOnce = false;
    private Button saveBtn;
    private Button resetBtn;

    public CreateUserForm() {
        super(new CVUser());

        this.saveBtn.addListener(new ClickListener() {
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
                        user.setRole(ApplicationRoles.USER);
                    }
                    FacadeFactory.getFacade().store(user);
                } catch (Exception ex) {
                    // @todo deal with validation exceptions
                    System.out.println("Exception saving form bean : " + ex);
                }
            }

        });

        this.resetBtn.addListener(new ClickListener() {
            private static final long serialVersionUID = -3459450343828705504L;

            @Override
            public void buttonClick(ClickEvent event) {
                System.out.println("Discarding form changes");
                CreateUserForm.this.discard();
            }

        });

        HorizontalLayout footer = new HorizontalLayout();
        footer.addComponent(this.saveBtn);
        footer.addComponent(this.resetBtn);
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
    protected void initFormComponent() {
        this.saveBtn = new Button();
        this.resetBtn = new Button();
    }

    @Override
    protected FormFieldFactory createFormFactory() {
        return new AbstractFormFieldFactory(this) {
            private static final long serialVersionUID = -2205076078288722621L;

            @Override
            protected Field createPropertyField(Item item, String propertyId) {
                return ("picture".equals(propertyId)) ? new FileUploadFormField(item, propertyId) : null;

                // @todo for field username, on change, check database
                // availability. this must also be done when validation
                // the form
            }

            @Override
            protected RepaintRequestListener getFieldRepaintRequestListener(final Field field,
                    final FormFieldDefinition fieldDefinition) {
                return new RepaintRequestListener() {
                    private static final long serialVersionUID = -428629319667385841L;

                    @Override
                    public void repaintRequested(RepaintRequestEvent event) {
                        if (!field.isVisible()) {
                            return;
                        }
                        // prevent repaint from being called recursively
                        field.setVisible(false);
                        if (field.isRequired()) {
                            System.out.println("field is required. valid : " + field.isValid());
                            field.setRequiredError(Lang.getMessage("CreateUserPage.Validation.required", field.getCaption()));
                        }
                        final String caption = fieldDefinition.getCaption();
                        if (caption != null && !caption.equals(((Field) event.getSource()).getCaption())) {
                            field.setCaption(Lang.getMessage(caption));
                        }
                        field.setVisible(true);

                    }

                };

            }
        };
    }

    @Override
    protected void executeOnRepaint() {
        this.saveBtn.setCaption(Lang.getMessage("CreateUserPage.UI.commit.caption"));
        this.resetBtn.setCaption(Lang.getMessage("CreateUserPage.UI.discard.caption"));
        setCaption(Lang.getMessage("CreateUserPage.UI.form.caption"));
        setDescription(Lang.getMessage("CreateUserPage.UI.form.description"));
    }

}
