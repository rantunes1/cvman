package com.glintt.cvm.ui.api;

import org.vaadin.addon.customfield.CustomField;

import com.vaadin.data.Item;

public abstract class AbstractItemField extends CustomField {
    private static final long serialVersionUID = -3634817223129332587L;

    private final Item item;
    private final Object propertyId;

    protected AbstractItemField(Item item, Object propertyId) {
        this.item = item;
        this.propertyId = propertyId;
    }

    @Override
    public Class<?> getType() {
        return this.item.getItemProperty(this.propertyId).getType();
    }

    public Item getItem() {
        return this.item;
    }

    public Object getPropertyId() {
        return this.propertyId;
    }

}
