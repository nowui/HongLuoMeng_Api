package com.hongluomeng.type;

public enum CatetoryEnum {
	GROUP("group"),
	MENU("menu"),
	PRODUCT("product"),
	BRAND("brand");

	private String key;

	private CatetoryEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
