package com.hongluomeng.type;

public enum OrderEnum {

	WAIT("WAIT"),
	EXPIRE("EXPIRE"),
	CONFIRM("CONFIRM"),
	PAYED("PAYED"),
	SEND("SEND"),
	RECEIVED("RECEIVED"),
	FINISH("FINISH"),
	CANCEL("CANCEL");

	private String key;

	private OrderEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
