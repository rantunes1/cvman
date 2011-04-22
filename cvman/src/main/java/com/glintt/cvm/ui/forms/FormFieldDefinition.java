package com.glintt.cvm.ui.forms;

import java.io.Serializable;

public class FormFieldDefinition implements Serializable {
    private static final long serialVersionUID = -8030824488900043262L;
    private final String name;
    private final String caption;
    private final boolean isRequired;

    public FormFieldDefinition(String name) {
        this(name, null, false);
    }

    public FormFieldDefinition(String name, String caption) {
        this(name, caption, false);
    }

    public FormFieldDefinition(String name, boolean isRequired) {
        this(name, null, isRequired);
    }

    public FormFieldDefinition(String name, String caption, boolean isRequired) {
        this.name = name;
        this.caption = caption;
        this.isRequired = isRequired;
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
}