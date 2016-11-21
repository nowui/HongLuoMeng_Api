package com.hongluomeng.type;

public enum BrandApplyReviewEnum {

	NONE("NONE"),
	WAIT("WAIT"),
	PASS("PASS"),
	REFUSE("REFUSE"),
	CANCEL("CANCEL");

	private String key;

	private BrandApplyReviewEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
