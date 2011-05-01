package com.glintt.cvm.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.hr_xml._3.GenderCodeEnumType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.hr_xml._3.MaritalStatusCodeEnumType;

import com.glintt.cvm.model.Person;
import com.glintt.cvm.ui.FileUploadFormField;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Select;

public class PersonalInfoForm extends AbstractBaseForm {

    private GridLayout formLayout;

    public PersonalInfoForm(Person person) {
        super(person.getPersonalInfo());
    }

    @Override
    protected FormFieldFactory createFormFactory() {
        return new AbstractFormFieldFactory(this) {

            @Override
            protected Field createPropertyField(Item item, String propertyId) {
                if ("picture".equals(propertyId)) {
                    return new FileUploadFormField(item, propertyId);
                } else if ("maritalStatus".equals(propertyId)) {
                    IndexedContainer container = new IndexedContainer();
                    container.addContainerProperty("name", String.class, null);
                    container.addContainerProperty("value", String.class, null);
                    for (MaritalStatusCodeEnumType mscet : MaritalStatusCodeEnumType.values()) {
                        Item option = container.addItem(mscet.name());
                        option.getItemProperty("name").setValue(mscet.name());
                        option.getItemProperty("value").setValue(mscet.value());
                    }
                    container.sort(new Object[] { "name" }, new boolean[] { true });
                    ComboBox martitalStatusCombo = new ComboBox(null, container);
                    martitalStatusCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
                    martitalStatusCombo.setItemCaptionPropertyId("value");
                    martitalStatusCombo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
                    martitalStatusCombo.setImmediate(true);
                    martitalStatusCombo.setNullSelectionAllowed(false);
                    return martitalStatusCombo;
                } else if ("gender".equals(propertyId)) {
                    IndexedContainer container = new IndexedContainer();
                    container.addContainerProperty("name", String.class, null);
                    container.addContainerProperty("value", String.class, null);
                    for (GenderCodeEnumType gcet : GenderCodeEnumType.values()) {
                        Item option = container.addItem(gcet.name());
                        option.getItemProperty("name").setValue(gcet.name());
                        option.getItemProperty("value").setValue(gcet.value());
                    }
                    container.sort(new Object[] { "name" }, new boolean[] { true });
                    ComboBox genderCombo = new ComboBox(null, container);
                    genderCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
                    genderCombo.setItemCaptionPropertyId("value");
                    genderCombo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
                    genderCombo.setImmediate(true);
                    genderCombo.setNullSelectionAllowed(false);
                    return genderCombo;
                } else if ("primaryLanguage".equals(propertyId)) {
                    IndexedContainer container = new IndexedContainer();
                    container.addContainerProperty("name", String.class, null);
                    container.addContainerProperty("value", String.class, null);
                    for (LanguageCodeEnumType lcet : LanguageCodeEnumType.values()) {
                        Item option = container.addItem(lcet.name());
                        option.getItemProperty("name").setValue(lcet.name());
                        option.getItemProperty("value").setValue(lcet.value());
                    }
                    container.sort(new Object[] { "name" }, new boolean[] { true });
                    ComboBox languageCombo = new ComboBox(null, container);
                    languageCombo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
                    languageCombo.setItemCaptionPropertyId("value");
                    languageCombo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
                    languageCombo.setImmediate(true);
                    languageCombo.setNullSelectionAllowed(false);
                    return languageCombo;
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
