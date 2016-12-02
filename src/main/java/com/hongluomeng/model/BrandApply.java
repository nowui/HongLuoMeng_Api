package com.hongluomeng.model;

import com.hongluomeng.common.Utility;
import com.hongluomeng.type.BrandApplyReviewEnum;

public class BrandApply extends Base<BrandApply> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_BRAND_APPLY = "table_brand_apply";
	public static final String KEY_BRAND_ID = "brand_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_MEMBER_REAL_NAME = "member_real_name";
	public static final String KEY_MEMBER_IDENTITY_CARD = "member_identity_card";
	public static final String KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE = "member_identity_card_front_image";
	public static final String KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE = "member_identity_card_back_image";
	public static final String KEY_BRAND_APPLY_REVIEW_STATUS = "brand_apply_review_status";

	private Brand brand;

	public String getBrand_id() {
		return getStr(KEY_BRAND_ID);
	}

	public void setBrand_id(String brand_id) {
		set(KEY_BRAND_ID, brand_id);
	}

	public void checkBrand_id() {
		Utility.checkStringLength(getBrand_id(), 32, "品牌编号");
	}

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public void checkUser_id() {
		Utility.checkStringLength(getUser_id(), 32, "用户编号");
	}

	public String getMember_real_name() {
		return getStr(KEY_MEMBER_REAL_NAME);
	}

	public void setMember_real_name(String member_real_name) {
		set(KEY_MEMBER_REAL_NAME, member_real_name);
	}

	public void checkMember_real_name() {
		Utility.checkStringLength(getMember_real_name(), 3, 300, "真实姓名");
	}

	public String getMember_identity_card() {
		return getStr(KEY_MEMBER_IDENTITY_CARD);
	}

	public void setMember_identity_card(String member_identity_card) {
		set(KEY_MEMBER_IDENTITY_CARD, member_identity_card);
	}

	public void checkMember_identity_card() {
		Utility.checkStringLength(getMember_identity_card(), 0, 18, "身份证号码");
	}

	public String getMember_identity_card_front_image() {
		return getStr(KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE);
	}

	public void setMember_identity_card_front_image(String member_identity_card_front_image) {
		set(KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE, member_identity_card_front_image);
	}

	public void checkMember_identity_card_front_image() {
		Utility.checkStringLength(getMember_identity_card_front_image(), 0, 100, "身份证照片正面");
	}

	public String getMember_identity_card_back_image() {
		return getStr(KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE);
	}

	public void setMember_identity_card_back_image(String member_identity_card_back_image) {
		set(KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE, member_identity_card_back_image);
	}

	public void checkMember_identity_card_back_image() {
		Utility.checkStringLength(getMember_identity_card_back_image(), 0, 100, "身份证照片反面");
	}

	public String getBrand_apply_review_status() {
		return getStr(KEY_BRAND_APPLY_REVIEW_STATUS);
	}

	public void setBrand_apply_review_status(String brand_apply_review_status) {
		set(KEY_BRAND_APPLY_REVIEW_STATUS, brand_apply_review_status);
	}

	public String getBrand_apply_review_status_value() {
		String apply_review_status = getBrand_apply_review_status();

		for(BrandApplyReviewEnum brandApplyReviewEnum : BrandApplyReviewEnum.values()) {
			if(apply_review_status.equals(brandApplyReviewEnum.getKey())) {
				return brandApplyReviewEnum.getValue();
			}
		}

		return "";
	}

	public Brand getBrand() {
		return new Brand().put(this);
	}

}
