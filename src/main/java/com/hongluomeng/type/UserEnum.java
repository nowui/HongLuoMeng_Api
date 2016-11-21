package com.hongluomeng.type;

public enum UserEnum {

	ADMIN("ADMIN"),
	MEMBER("MEMBER"),
	ENTERPRISE("ENTERPRISE");

	private String key;

	private UserEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
