package com.hongluomeng.type;

public enum OperationEnum {

	VIEW("view");

	private String key;

	private OperationEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
