package com.glintt.cvm.ui.forms;

import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.terminal.Paintable.RepaintRequestListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextField;

public abstract class AbstractFormFieldFactory extends DefaultFieldFactory {
    private static final long serialVersionUID = 3871354814314737712L;

    private final AbstractBaseForm ownerForm;

    protected AbstractFormFieldFactory(AbstractBaseForm ownerForm) {
        super();
        this.ownerForm = ownerForm;
    }

    protected abstract Field createPropertyField(Item item, String propertyId);

    protected abstract RepaintRequestListener getFieldRepaintRequestListener(Field field, FormFieldDefinition fieldDefinition);

    @Override
    public Field createField(Item item, Object propertyId, Component uiContext) {
        String propId = (String) propertyId;
        List<FormFieldDefinition> editableFieldsList = this.ownerForm.getEditableFieldsList();
        for (FormFieldDefinition fieldDefinition : editableFieldsList) {
            if (fieldDefinition.getName().equals(propId)) {
                Field field = createPropertyField(item, propId);
                if (field == null) {
                    field = super.createField(item, propId, uiContext);
                }

                if (field.getCaption() == null) {
                    field.setCaption(createCaptionByPropertyId(propertyId));
                }

                field.setRequired(fieldDefinition.isRequired());

                if (field.getClass().isAssignableFrom(TextField.class)) {
                    ((TextField) field).setNullRepresentation("");
                }

                RepaintRequestListener repaintListener = getFieldRepaintRequestListener(field, fieldDefinition);
                if (repaintListener != null) {
                    field.addListener(repaintListener);
                }
                return field;
            }
        }
        return null;
    }

}
