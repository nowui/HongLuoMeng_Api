package com.hongluomeng.type;

public enum SmsEnum {

	REGISTER("REGISTER"),
	PASSWORD("PASSWORD"),
	WEIXIN("WEIXIN");

	private String key;

	private SmsEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
