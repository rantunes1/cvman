package com.glintt.cvm.ui.forms;

import java.io.Serializable;

public class FormFieldDefinition implements Serializable {
    private static final long serialVersionUID = -8030824488900043262L;
    private final String name;
    private final String caption;
    private final boolean isRequired;
    private final int startCol, startRow, endCol, endRow;

    public FormFieldDefinition(String name) {
        this(name, null, false, 0, 0, 0, 0);
    }

    public FormFieldDefinition(String name, String caption) {
        this(name, caption, false, 0, 0, 0, 0);
    }

    public FormFieldDefinition(String name, boolean isRequired) {
        this(name, null, isRequired, 0, 0, 0, 0);
    }

    public FormFieldDefinition(String name, String caption, boolean isRequired) {
        this(name, caption, isRequired, 0, 0, 0, 0);
    }

    public FormFieldDefinition(String name, String caption, boolean isRequired, int col, int row) {
        this(name, caption, isRequired, col, row, col, row);
    }

    public FormFieldDefinition(String name, String caption, boolean isRequired, int startCol, int startRow, int endCol, int endRow) {
        this.name = name;
        this.caption = caption;
        this.isRequired = isRequired;
        this.startCol = startCol;
        this.startRow = startRow;
        this.endCol = endCol;
        this.endRow = endRow;
    }

    public String getName() {
        return this.name;
    }

    public String getCaption() {
        return this.caption;
    }

    public boolean isRequired() {
        return this.isRequired;
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