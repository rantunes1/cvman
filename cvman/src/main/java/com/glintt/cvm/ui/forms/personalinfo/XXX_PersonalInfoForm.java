package com.glintt.cvm.ui.forms.personalinfo;

import com.glintt.cvm.model.PersonalInfo;
import com.glintt.cvm.ui.api.XXX_AbstractBeanItemAdapter;
import com.glintt.cvm.ui.api.XXX_BeanItemForm;
import com.glintt.cvm.ui.api.XXX_BuildableObjectProperty;
import com.glintt.cvm.ui.api.XXX_BuildableObjectProperty.FieldPosition;
import com.vaadin.ui.Field;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;

public class XXX_PersonalInfoForm extends XXX_BeanItemForm<XXX_AbstractBeanItemAdapter<PersonalInfo>> {

    private GridLayout formLayout;

    public XXX_PersonalInfoForm(PersonalInfo personalInfo) {
        super(new XXX_PersonalInfoAdapter(personalInfo));
    }

    @Override
    protected void executeOnRepaint() {
        // TODO Auto-generated method stub

    }

    @Override
    protected Layout getFormLayout() {
        if (this.formLayout == null) {
            int columns = getAdapter().getItemPropertyIds().size();
            this.formLayout = new GridLayout(2, columns);
            this.formLayout.setMargin(true, false, false, true);
            this.formLayout.setSpacing(true);
        }
        return this.formLayout;
    }

    @Override
    protected void attachField(Object propertyId, Field field) {
        XXX_BuildableObjectProperty property = getAdapter().getItemProperty(propertyId);
        if (property != null) {
            FieldPosition position = property.getPosition();
            if (position != null) {
                this.formLayout.addComponent(field, position.getStartCol(), position.getStartRow(), position.getEndCol(),
                        position.getEndRow());
            } else {
                this.formLayout.addComponent(field);
            }
        }
        // don't call super.attachField.
    }
}
