package com.hongluomeng.type;

public enum PlatformEnum {

	WEB("web"),
	IOS("ios"),
	ANDROID("android");

	private String key;

	private PlatformEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
