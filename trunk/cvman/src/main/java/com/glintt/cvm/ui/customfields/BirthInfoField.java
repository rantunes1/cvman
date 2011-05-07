package com.glintt.cvm.ui.customfields;

import org.vaadin.addon.customfield.demo.field.EmbeddedForm;

import com.vaadin.data.Item;
import com.vaadin.ui.Form;

public class BirthInfoField extends AbstractItemField {
    private static final long serialVersionUID = -68160211596410488L;

    Form parentForm;

    public BirthInfoField(Item item, Object propertyId, Form parentForm) {
        super(item, propertyId);
        this.parentForm = (parentForm != null) ? new EmbeddedForm(parentForm) : new Form();
        setCompositionRoot(this.parentForm);
    }

}
