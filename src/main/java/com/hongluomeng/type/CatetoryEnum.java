package com.hongluomeng.type;

public enum CatetoryEnum {
	GROUP("GROUP"),
	MENU("MENU"),
	PRODUCT("PRODUCT"),
	BRAND("BRAND"),
	CHINA("CHINA");

	private String key;

	private CatetoryEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
