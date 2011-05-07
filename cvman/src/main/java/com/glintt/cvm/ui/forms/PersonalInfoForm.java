package com.glintt.cvm.ui.forms;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.hr_xml._3.CountryCodeEnumType;
import org.hr_xml._3.GenderCodeEnumType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.hr_xml._3.MaritalStatusCodeEnumType;

import com.glintt.cvm.model.Person;
import com.glintt.cvm.ui.customfields.BirthInfoField;
import com.glintt.cvm.ui.customfields.EnumBasedComboBox;
import com.glintt.cvm.ui.customfields.FileUploadFormField;
import com.glintt.cvm.ui.forms.FormFieldDefinition.FieldPosition;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.GridLayout;

public class PersonalInfoForm extends AbstractBaseForm {

    private GridLayout formLayout;

    public PersonalInfoForm(Person person) {
        super(person.getPersonalInfo());
    }

    protected void setInternalItemDataSource(Person person) {
        if (person.getPersonalInfo().getPicture() == null) {
            person.getPersonalInfo().setPicture(getDefaultPicture());
        }
        super.setInternalItemDataSource(person);
    }

    @Override
    protected List<FormFieldDefinition> getEditableFieldsList() {
        return new ArrayList<FormFieldDefinition>() {
            private static final long serialVersionUID = 3830836538116098227L;
            {
                add(new FormFieldDefinition("picture", "PersonalInfoForm.UI.picture.caption", true, "200px", new FieldPosition(0,
                        0, 0, 8)));
                add(new FormFieldDefinition("name", null, true, "400px", new FieldPosition(1, 1)));
                add(new FormFieldDefinition("fatherName", null, false, "400px", new FieldPosition(1, 2)));
                add(new FormFieldDefinition("motherName", null, false, "400px", new FieldPosition(1, 3)));
                add(new FormFieldDefinition("gender", null, false, "100px", new FieldPosition(1, 4)));
                add(new FormFieldDefinition("maritalStatus", null, false, "200px", new FieldPosition(1, 5)));
                add(new FormFieldDefinition("citizenshipCountry", null, false, "200px", new FieldPosition(1, 6)));
                add(new FormFieldDefinition("primaryLanguage", null, false, "200px", new FieldPosition(1, 7)));
                add(new FormFieldDefinition("birthInfo", null, false, "100%", new FieldPosition(1, 8)));
            }
        };
    }

    @Override
    protected FormFieldFactory createFormFactory() {
        return new AbstractFormFieldFactory(this) {
            private Field birthInfoField;

            @Override
            protected Field createPropertyField(Item item, String propertyId, Component uiContext) {
                Field field = null;
                if ("picture".equals(propertyId)) {
                    FormFieldDefinition ffd = getFieldDefinition(propertyId);
                    String width = (ffd != null) ? ffd.getWidth() : null;
                    field = new FileUploadFormField(item, propertyId, width);
                } else if ("maritalStatus".equals(propertyId)) {
                    field = new EnumBasedComboBox(item, propertyId, MaritalStatusCodeEnumType.values(),
                            MaritalStatusCodeEnumType.class);
                } else if ("gender".equals(propertyId)) {
                    field = new EnumBasedComboBox(item, propertyId, GenderCodeEnumType.values(), GenderCodeEnumType.class);
                } else if ("primaryLanguage".equals(propertyId)) {
                    return new EnumBasedComboBox(item, propertyId, LanguageCodeEnumType.values(), LanguageCodeEnumType.class);
                } else if ("citizenshipCountry".equals(propertyId)) {
                    field = new EnumBasedComboBox(item, propertyId, CountryCodeEnumType.values(), CountryCodeEnumType.class);
                } else if ("birthInfo".equals(propertyId)) {
                    if (this.birthInfoField == null) {
                        Form form = (uiContext instanceof Form) ? (Form) uiContext : null;
                        this.birthInfoField = new BirthInfoField(item, propertyId, form);
                    }
                    field = this.birthInfoField;
                }
                return field;
            }
        };
    }

    private byte[] getDefaultPicture() {
        // @todo inject properties for file location
        URL url = getClass().getClassLoader().getResource("images/m_nophoto.jpg");
        if (url == null) {
            // @todo ???
            throw new RuntimeException("NO URL FOUND FOR DEFAULT USER PICTURE!");
        }

        InputStream is = null;
        try {
            is = url.openStream();
            if (is != null) {
                BufferedInputStream bis = new BufferedInputStream(is);

                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] byteChunk = new byte[1024];
                int n;

                while ((n = is.read(byteChunk)) > 0) {
                    outStream.write(byteChunk, 0, n);
                }

                return outStream.toByteArray();
            }
        } catch (IOException ioex) {
            // TODO Auto-generated catch block
            ioex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                    // ignore exception
                }
            }
        }
        return null;
    }

    @Override
    protected void attachField(Object propertyId, Field field) {
        FormFieldDefinition ffd = getFieldDefinition(propertyId.toString());
        if (ffd != null) {
            FieldPosition position = ffd.getPosition();
            if (position != null) {
                this.formLayout.addComponent(field, position.getStartCol(), position.getStartRow(), position.getEndCol(),
                        position.getEndRow());
            } else {
                this.formLayout.addComponent(field);
            }
        }
    }

    @Override
    protected void executeOnRepaint() {
        // TODO Auto-generated method stub
    }

    @Override
    protected void initFormComponent() {
        int columns = getEditableFieldsList().size();
        this.formLayout = new GridLayout(2, columns);
        this.formLayout.setMargin(true, false, false, true);
        this.formLayout.setSpacing(true);
        setLayout(this.formLayout);
    }
}
