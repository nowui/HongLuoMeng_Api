package com.hongluomeng.type;

public enum BrandApplyReviewEnum {

	NONE("NONE", "未申请"),
	WAIT("WAIT", "待审核"),
	PASS("PASS", "通过"),
	REFUSE("REFUSE", "拒绝"),
	CANCEL("CANCEL", "取消");

	private String key;
	private String value;

	private BrandApplyReviewEnum(String key, String value) {
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
