package com.hongluomeng.type;

public enum BrandApplyReviewEnum {

	NONE("none"),
	WAITING("waiting"),
	PASS("pass"),
	REFUSE("refuse"),
	CANCEL("cancel");

	private String key;

	private BrandApplyReviewEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
