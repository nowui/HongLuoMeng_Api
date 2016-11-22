package com.hongluomeng.type;

public enum OrderFlowEnum {

	WAIT("WAIT"),
	EXPIRE("EXPIRE"),
	CONFIRM("CONFIRM"),
	PAYED("PAYED"),
	SEND("SEND"),
	RECEIVED("RECEIVED"),
	FINISH("FINISH"),
	CANCEL("CANCEL");

	private String key;

	private OrderFlowEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
