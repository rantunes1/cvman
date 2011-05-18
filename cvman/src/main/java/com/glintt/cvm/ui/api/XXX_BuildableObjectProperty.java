package com.glintt.cvm.ui.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.ui.Field;

public class XXX_BuildableObjectProperty implements Property {

    private static final String DEFAULT_WIDTH = "200px";

    private final Property property;
    private String caption;
    private String width = DEFAULT_WIDTH;
    private boolean radio = false;
    private boolean richText = false;
    private boolean required = false;
    private Field field;
    private FieldPosition position;
    private List<Validator> validators;

    public XXX_BuildableObjectProperty(Property property) {
        this.property = property;
    }

    @Override
    public String toString() {
        assert (this.property != null);
        return this.property.toString();
    }

    @Override
    public Class<?> getType() {
        assert (this.property != null);
        return this.property.getType();
    }

    @Override
    public Object getValue() {
        assert (this.property != null);
        return this.property.getValue();
    }

    @Override
    public boolean isReadOnly() {
        assert (this.property != null);
        return this.property.isReadOnly();
    }

    @Override
    public void setReadOnly(boolean newStatus) {
        assert (this.property != null);
        this.property.setReadOnly(newStatus);
    }

    @Override
    public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
        assert (this.property != null);
        this.property.setValue(newValue);
    }

    public XXX_BuildableObjectProperty setWidth(String width) {
        this.width = width;
        return this;
    }

    public String getWidth() {
        return this.width;
    }

    public String getCaption() {
        return this.caption;
    }

    public XXX_BuildableObjectProperty setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public boolean isRadio() {
        return this.radio;
    }

    public XXX_BuildableObjectProperty setRadio(boolean radio) {
        this.radio = radio;
        return this;
    }

    public boolean isRichText() {
        return this.richText;
    }

    public XXX_BuildableObjectProperty setRichText(boolean richText) {
        this.richText = richText;
        return this;
    }

    public XXX_BuildableObjectProperty setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public boolean isRequired() {
        return this.required;
    }

    public XXX_BuildableObjectProperty addValidator(Validator validator) {
        if (this.validators == null)
            this.validators = new ArrayList<Validator>();
        this.validators.add(validator);

        return this;
    }

    public List<Validator> getValidators() {
        if (this.validators == null)
            return Collections.<Validator> emptyList();

        return this.validators;
    }

    public Field getField() {
        return this.field;
    }

    public FieldPosition getPosition() {
        return this.position;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setPosition(FieldPosition position) {
        this.position = position;
    }

    public static class FieldPosition implements Serializable {
        private static final long serialVersionUID = 3394803367392383071L;

        private final int startCol, startRow, endCol, endRow;

        public FieldPosition(int column, int row) {
            this(column, row, column, row);
        }

        public FieldPosition(int startCol, int startRow, int endCol, int endRow) {
            this.startCol = startCol;
            this.startRow = startRow;
            this.endCol = endCol;
            this.endRow = endRow;
        }

        public int getStartCol() {
            return this.startCol;
        }

        public int getStartRow() {
            return this.startRow;
        }

        public int getEndCol() {
            return this.endCol;
        }

        public int getEndRow() {
            return this.endRow;
        }

    }

}
