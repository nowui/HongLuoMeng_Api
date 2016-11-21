package com.hongluomeng.type;

public enum AttributeEnum {
	NORMAL("NORMAL"),
	SKU("SKU");

	private String key;

	private AttributeEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
