package com.glintt.cvm.ui.customfields;

import java.lang.reflect.Method;

import com.glintt.cvm.ui.api.AbstractItemField;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Select;

public class EnumBasedComboBox extends AbstractItemField {
    private static final long serialVersionUID = 309906974793801811L;

    public <E extends Enum<?>> EnumBasedComboBox(Item item, Object propertyId, E[] enumValues, Class<E> enumClass) {
        super(item, propertyId);

        Method valueMethod = null;
        try {
            valueMethod = enumClass.getDeclaredMethod("value");
        } catch (Exception ignored) {
            // ignore exception
        }

        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("value", String.class, null);
        for (E enumProperty : enumValues) {
            Item option = container.addItem(enumProperty.name());
            option.getItemProperty("name").setValue(enumProperty.name());
            if (valueMethod != null) {
                Object value = null;
                try {
                    value = valueMethod.invoke(enumProperty);
                } catch (Exception ignored) {
                    // ignore exception
                }
                if (value != null) {
                    option.getItemProperty("value").setValue(value);
                }
            }
        }
        container.sort(new Object[] { "name" }, new boolean[] { true });
        ComboBox combo = new ComboBox(null, container);
        combo.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
        combo.setItemCaptionPropertyId("value");
        combo.setFilteringMode(Filtering.FILTERINGMODE_CONTAINS);
        combo.setImmediate(true);
        combo.setNullSelectionAllowed(false);
        combo.setSizeFull();

        setCompositionRoot(combo);
    }
}
