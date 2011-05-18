package com.glintt.cvm.ui.forms.personalinfo;

import org.hr_xml._3.CountryCodeEnumType;
import org.hr_xml._3.GenderCodeEnumType;
import org.hr_xml._3.LanguageCodeEnumType;
import org.hr_xml._3.MaritalStatusCodeEnumType;

import com.glintt.cvm.model.PersonalInfo;
import com.glintt.cvm.ui.api.XXX_AbstractBeanItemAdapter;
import com.glintt.cvm.ui.api.XXX_BuildableObjectProperty;
import com.glintt.cvm.ui.api.XXX_BuildableObjectProperty.FieldPosition;
import com.glintt.cvm.ui.customfields.BirthdateField;
import com.glintt.cvm.ui.customfields.EnumBasedComboBox;
import com.glintt.cvm.ui.customfields.FileUploadFormField;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

public class XXX_PersonalInfoAdapter extends XXX_AbstractBeanItemAdapter<PersonalInfo> {

    public XXX_PersonalInfoAdapter(PersonalInfo personalInfo) {
        super(personalInfo, new String[] { "picture", "name", "fatherName", "motherName", "gender", "maritalStatus",
                "citizenshipCountry", "primaryLanguage", "birthInfo" });
    }

    @Override
    protected void initializeItemProps() {
        getItemProperty("picture").setCaption("PersonalInfoForm.UI.picture.caption").setRequired(true)
                .setPosition(new FieldPosition(0, 0, 0, 8));
        getItemProperty("name.firstname").setRequired(true).setWidth("400px").setPosition(new FieldPosition(1, 1));
        getItemProperty("fatherName").setWidth("400px").setPosition(new FieldPosition(1, 2));
        getItemProperty("motherName").setWidth("400px").setPosition(new FieldPosition(1, 3));
        getItemProperty("gender").setWidth("100px").setPosition(new FieldPosition(1, 4));
        getItemProperty("maritalStatus").setPosition(new FieldPosition(1, 5));
        getItemProperty("citizenshipCountry").setPosition(new FieldPosition(1, 6));
        getItemProperty("primaryLanguage").setPosition(new FieldPosition(1, 7));
        getItemProperty("birthInfo").setWidth("100%").setPosition(new FieldPosition(1, 8));

    }

    @Override
    protected Field createField(Item item, String propertyId, XXX_BuildableObjectProperty property, Component uiContext) {
        Field field = null;
        if ("picture".equals(propertyId)) {
            field = new FileUploadFormField(item, propertyId, property.getWidth());
        } else if ("maritalStatus".equals(propertyId)) {
            field = new EnumBasedComboBox(item, propertyId, MaritalStatusCodeEnumType.values(), MaritalStatusCodeEnumType.class);
        } else if ("gender".equals(propertyId)) {
            field = new EnumBasedComboBox(item, propertyId, GenderCodeEnumType.values(), GenderCodeEnumType.class);
        } else if ("primaryLanguage".equals(propertyId)) {
            return new EnumBasedComboBox(item, propertyId, LanguageCodeEnumType.values(), LanguageCodeEnumType.class);
        } else if ("citizenshipCountry".equals(propertyId)) {
            field = new EnumBasedComboBox(item, propertyId, CountryCodeEnumType.values(), CountryCodeEnumType.class);
        } else if ("birthInfo".equals(propertyId)) {
            // @todo
            // if (this.birthInfoField == null) {
            // AbstractBaseForm form = (uiContext instanceof
            // AbstractBaseForm) ? (AbstractBaseForm) uiContext : null;
            // this.birthInfoField = new BirthInfoField(item,
            // propertyId, form);
            // }
            // field = this.birthInfoField;
            field = new BirthdateField(item, propertyId);

        }
        return field;
    }

}
