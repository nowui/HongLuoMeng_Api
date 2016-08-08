package com.hongluomeng.type;

public enum AccountEnum {

	ACCOUNT("account"),
	PHONE("phone"),
	EMAIL("email"),
	WEIBO("weibo"),
	WECHAT("wechat");

	private String key;

	private AccountEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
