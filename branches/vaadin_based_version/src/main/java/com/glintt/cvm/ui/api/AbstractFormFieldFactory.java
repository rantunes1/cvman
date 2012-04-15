package com.glintt.cvm.ui.api;

import org.vaadin.appfoundation.i18n.Lang;

import com.vaadin.data.Item;
import com.vaadin.terminal.Paintable.RepaintRequestEvent;
import com.vaadin.terminal.Paintable.RepaintRequestListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;

public abstract class AbstractFormFieldFactory extends DefaultFieldFactory {
    private static final long serialVersionUID = 3871354814314737712L;

    private final Form ownerForm;

    protected AbstractFormFieldFactory(Form ownerForm) {
        super();
        this.ownerForm = ownerForm;
    }

    protected abstract Field createPropertyField(Item item, String propertyId, Component uiContext);

    @Override
    public Field createField(Item item, Object propertyId, Component uiContext) {

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
