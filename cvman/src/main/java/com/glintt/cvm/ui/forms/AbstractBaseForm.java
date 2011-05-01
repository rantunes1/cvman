package com.glintt.cvm.ui.forms;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.appfoundation.persistence.data.AbstractPojo;

import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.Paintable.RepaintRequestListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;

public abstract class AbstractBaseForm extends Form implements RepaintRequestListener {
    private static final long serialVersionUID = 852508767733733785L;

    protected <AP extends AbstractPojo> AbstractBaseForm(AP datasource) {
        super();
        initFormComponent();
        if (datasource != null) {
            this.setItemDataSource(new BeanItem<AP>(datasource));
        }
        setValidationVisible(false);
        setValidationVisibleOnCommit(true);
        setImmediate(true);
        setWriteThrough(false);
        addListener((RepaintRequestListener) this);
        setFormFieldFactory(createFormFactory());
        List<FormFieldDefinition> editableFields = getEditableFieldsList();
        if (editableFields != null) {
            List<String> fieldsOrder = new ArrayList<String>();
            for (FormFieldDefinition fieldDefinition : editableFields) {
                fieldsOrder.add(fieldDefinition.getName());
            }
            this.setVisibleItemProperties(fieldsOrder);
        }

    }

    protected boolean isEditableproperty(String propertyId) {
        return getFieldDefinition(propertyId) != null;
    }

    protected FormFieldDefinition getFieldDefinition(String propertyId) {
        List<FormFieldDefinition> editableFields = getEditableFieldsList();
        if (editableFields != null) {
            for (FormFieldDefinition fieldDefinition : editableFields) {
                if (fieldDefinition.getName().equals(propertyId)) {
                    return fieldDefinition;
                }
            }
        }
        return null;
    }

    @Override
    public void repaintRequested(RepaintRequestEvent event) {
        boolean visible = isVisible();
        // prevent repaint from being called recursively
        setVisible(false);
        executeOnRepaint();
        setVisible(visible);
    }

    protected abstract void initFormComponent();

    protected abstract FormFieldFactory createFormFactory();

    protected abstract List<FormFieldDefinition> getEditableFieldsList();

    protected abstract void executeOnRepaint();
}
