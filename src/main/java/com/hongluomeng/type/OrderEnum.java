package com.hongluomeng.type;

public enum OrderEnum {

	WAIT("WAIT", "代付款"),
	EXPIRE("EXPIRE", "超时未付款"),
	CONFIRM("CONFIRM", "已付款，带确认"),
	PAYED("PAYED", "已付款"),
	SEND("SEND", "已发货"),
	RECEIVED("RECEIVED", "货已签收"),
	FINISH("FINISH", "订单完成"),
	CANCEL("CANCEL", "订单取消");

	private String key;
	private String value;

	private OrderEnum(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
