package com.glintt.cvm.util;

public enum AppProperties {
    LOCALE_COUNTRY("locale.country"),

    LOCALE_LANGUAGE("locale.language"),

    THEME("theme.name"),

    ;

    private String key;

    private AppProperties(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
