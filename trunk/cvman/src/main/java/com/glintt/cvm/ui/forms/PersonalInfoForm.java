package com.glintt.cvm.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.hr_xml._3.CountryCodeEnumType;
import org.hr_xml._3.GenderCodeEnumType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.hr_xml._3.MaritalStatusCodeEnumType;

import com.glintt.cvm.model.Person;
import com.glintt.cvm.model.PersonalInfo;
import com.glintt.cvm.ui.customfields.EnumBasedComboBox;
import com.glintt.cvm.ui.customfields.FileUploadFormField;
import com.vaadin.data.Item;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.GridLayout;

public class PersonalInfoForm extends AbstractBaseForm {

    private GridLayout formLayout;

    public PersonalInfoForm(Person person) {
        super(person.getPersonalInfo());
        PersonalInfo pi = person.getPersonalInfo();
    }

    @Override
    protected FormFieldFactory createFormFactory() {
        return new AbstractFormFieldFactory(this) {

            @Override
            protected Field createPropertyField(Item item, String propertyId) {
                if ("picture".equals(propertyId)) {
                    return new FileUploadFormField(item, propertyId);
                } else if ("maritalStatus".equals(propertyId)) {
                    return new EnumBasedComboBox(item, propertyId, MaritalStatusCodeEnumType.values(),
                            MaritalStatusCodeEnumType.class);
                } else if ("gender".equals(propertyId)) {
                    return new EnumBasedComboBox(item, propertyId, GenderCodeEnumType.values(), GenderCodeEnumType.class);
                } else if ("primaryLanguage".equals(propertyId)) {
                    return new EnumBasedComboBox(item, propertyId, LanguageCodeEnumType.values(), LanguageCodeEnumType.class);
                } else if ("citizenshipCountry".equals(propertyId)) {
                    return new EnumBasedComboBox(item, propertyId, CountryCodeEnumType.values(), CountryCodeEnumType.class);
                }
                return null;
            }

            @Override
            protected RepaintRequestListener getFieldRepaintRequestListener(Field field, FormFieldDefinition fieldDefinition) {
                // TODO Auto-generated method stub
                return null;
            }
        };
    }

    @Override
    protected List<FormFieldDefinition> getEditableFieldsList() {
        return new ArrayList<FormFieldDefinition>() {
            private static final long serialVersionUID = 3830836538116098227L;
            {
                add(new FormFieldDefinition("picture", "PersonalInfoForm.UI.picture.caption", false, 0, 0, 0, 7));
                add(new FormFieldDefinition("name", null, true, 1, 1));
                add(new FormFieldDefinition("fatherName", null, false, 1, 2));
                add(new FormFieldDefinition("motherName", null, false, 1, 3));
                add(new FormFieldDefinition("gender", null, false, 1, 4));
                add(new FormFieldDefinition("maritalStatus", null, false, 1, 5));
                add(new FormFieldDefinition("citizenshipCountry", null, false, 1, 6));
                add(new FormFieldDefinition("primaryLanguage", null, false, 1, 7));

            }
        };
    }

    @Override
    protected void attachField(Object propertyId, Field field) {
        FormFieldDefinition ffd = getFieldDefinition(propertyId.toString());
        if (ffd != null) {
            this.formLayout.addComponent(field, ffd.getStartCol(), ffd.getStartRow(), ffd.getEndCol(), ffd.getEndRow());
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
