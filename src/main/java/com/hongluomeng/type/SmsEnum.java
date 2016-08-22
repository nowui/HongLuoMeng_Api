package com.hongluomeng.type;

public enum SmsEnum {

	REGISTER("register"),
	RESET_PASSWORD("reset_password");

	private String key;

	private SmsEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
