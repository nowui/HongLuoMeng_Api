package com.hongluomeng.type;

public enum CatetoryEnum {
	GROUP("group"),
	MENU("menu"),
	PRODUCT("product");

	private String key;

	private CatetoryEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
