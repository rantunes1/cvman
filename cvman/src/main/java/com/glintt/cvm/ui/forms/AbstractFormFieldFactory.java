package com.glintt.cvm.ui.forms;

import org.vaadin.appfoundation.i18n.Lang;

import com.vaadin.data.Item;
import com.vaadin.terminal.Paintable.RepaintRequestEvent;
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

    protected abstract Field createPropertyField(Item item, String propertyId, Component uiContext);

    @Override
    public Field createField(Item item, Object propertyId, Component uiContext) {
        String propId = (String) propertyId;
        FormFieldDefinition fieldDefinition = this.ownerForm.getFieldDefinition(propId);
        if (fieldDefinition != null) {
            Field field = createPropertyField(item, propId, uiContext);
            if (field == null) {
                field = super.createField(item, propId, uiContext);
            }

            String caption = field.getCaption();
            if (caption == null) {
                caption = createCaptionByPropertyId(propertyId);
            }
            if (caption.indexOf(" :") < 0) {
                caption += " :";
            }
            field.setCaption(caption);

            field.setRequired(fieldDefinition.isRequired());

            if (field.getClass().isAssignableFrom(TextField.class)) {
                ((TextField) field).setNullRepresentation("");
            }

            String fieldWidth = fieldDefinition.getWidth();
            if (fieldWidth != null) {
                field.setWidth(fieldWidth);
            }

            RepaintRequestListener repaintListener = getRepaintRequestListener(field, fieldDefinition);
            if (repaintListener != null) {
                field.addListener(repaintListener);
            }
            return field;

        }

        return null;
    }

    protected RepaintRequestListener getRepaintRequestListener(final Field field, final FormFieldDefinition fieldDefinition) {
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
                    field.setRequiredError(Lang.getMessage("FormsDefaults.Validation.required", field.getCaption()));
                }
                final String caption = fieldDefinition.getCaption();
                if (caption != null && !caption.equals(((Field) event.getSource()).getCaption())) {
                    field.setCaption(Lang.getMessage(caption));
                }
                field.setVisible(true);
            }

        };

    }

}
