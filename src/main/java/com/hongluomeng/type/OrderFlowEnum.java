package com.hongluomeng.type;

public enum OrderFlowEnum {

	WAIT_PAY("wait_pay"),
	WAIT_PAY_EXPIRE("wait_pay_expire"),
	PAYED("payed"),
	SEND("send"),
	RECEIVED("received"),
	FINISH("finish"),
	CANCEL("cancel");

	private String key;

	private OrderFlowEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
