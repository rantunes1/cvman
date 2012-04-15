package com.glintt.cvm.util;

public enum AppProperties {
	LOCALE_COUNTRY("locale.country"),

	LOCALE_LANGUAGE("locale.language"),

	THEME("theme.name"),

	LUCENE_INDEX_DIR("lucene.index.dir"),

	LUCENE_INDEX_ID_PERSON("lucene.index.id.person"),

	LINKEDIN_CONNECT_ICON_URI("linkedin.connect.icon"),

	;

	private String key;

	private AppProperties(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}
}
