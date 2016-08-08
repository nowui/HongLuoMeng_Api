package com.hongluomeng.type;

public enum CodeEnum {

	CODE_200(200),
	CODE_400(400),
	CODE_401(401),
	CODE_500(500);

	private Integer key;

	private CodeEnum(Integer key) {
		this.key = key;
	}

	public Integer getKey() {
		return key;
	}

}
