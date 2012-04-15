package com.glintt.cvm.ui.api;

import java.io.Serializable;

public class FormFieldDefinition implements Serializable {
    private static final long serialVersionUID = -8030824488900043262L;
    private final String name;
    private final String caption;
    private final String width;
    private final boolean required;
    private final FieldPosition position;

    public FormFieldDefinition(String name) {
        this(name, null, false, null, null);
    }

    public FormFieldDefinition(String name, String caption) {
        this(name, caption, false, null, null);
    }

    public FormFieldDefinition(String name, String caption, String width) {
        this(name, caption, false, null, null);
    }

    public FormFieldDefinition(String name, boolean required) {
        this(name, null, required, null, null);
    }

    public FormFieldDefinition(String name, boolean required, String width) {
        this(name, null, required, width, null);
    }

    public FormFieldDefinition(String name, String caption, boolean required) {
        this(name, caption, required, null, null);
    }

    public FormFieldDefinition(String name, String caption, boolean required, String width) {
        this(name, caption, required, width, null);
    }

    public FormFieldDefinition(String name, String caption, boolean required, FieldPosition position) {
        this(name, caption, required, null, position);
    }

    public FormFieldDefinition(String name, String caption, boolean required, String width, FieldPosition position) {
        this.name = name;
        this.caption = caption;
        this.required = required;
        this.width = width;
        this.position = position;
    }

    public String getName() {
        return this.name;
    }

    public String getCaption() {
        return this.caption;
    }

    public String getWidth() {
        return this.width;
    }

    public boolean isRequired() {
        return this.required;
    }

    public FieldPosition getPosition() {
        return this.position;
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