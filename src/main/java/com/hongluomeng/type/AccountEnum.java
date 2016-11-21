package com.hongluomeng.type;

public enum AccountEnum {

	ACCOUNT("ACCOUNT"),
	PHONE("PHONE"),
	EMAIL("EMAIL"),
	WEIBO("WEIBO"),
	WECHAT("WECHAT");

	private String key;

	private AccountEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
