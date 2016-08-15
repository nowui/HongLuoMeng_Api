package com.hongluomeng.type;

public enum AttributeEnum {
	NORMAL("normal"),
	SKU("sku");

	private String key;

	private AttributeEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
