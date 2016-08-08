package com.hongluomeng.type;

public enum UserEnum {

	ADMIN("admin"),
	MEMBER("member"),
	ENTERPRISE("enterprise");

	private String key;

	private UserEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
