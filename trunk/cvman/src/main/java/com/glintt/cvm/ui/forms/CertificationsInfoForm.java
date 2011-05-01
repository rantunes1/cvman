package com.glintt.cvm.ui.forms;

import java.util.List;

import com.glintt.cvm.model.Person;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.FormFieldFactory;

public class CertificationsInfoForm extends AbstractBaseForm {

    public CertificationsInfoForm(Person person) {
        super(person.getProfessionalInfo());

    }

    @Override
    protected FormFieldFactory createFormFactory() {
        return new DefaultFieldFactory() {
        };
    }

    @Override
    protected List<FormFieldDefinition> getEditableFieldsList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void executeOnRepaint() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void initFormComponent() {
        // TODO Auto-generated method stub

    }

}
