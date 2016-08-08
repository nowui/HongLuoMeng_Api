package com.hongluomeng.type;

public enum InputEnum {

	TEXT("text"),
	SELECT("select"),
	NUMBER("number"),
	DATETIME("datetime"),
	IMAGE("image");

	private String key;

	private InputEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
