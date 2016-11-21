package com.hongluomeng.type;

public enum InputEnum {

	TEXT("TEXT"),
	SELECT("SELECT"),
	NUMBER("NUMBER"),
	DATETIME("DATETIME"),
	IMAGE("IMAGE");

	private String key;

	private InputEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
