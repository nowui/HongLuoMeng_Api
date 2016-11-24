package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class BrandApply extends Model<BrandApply> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_BRAND_APPLY = "table_brand_apply";
	public static final String KEY_BRAND_ID = "brand_id";
	public static final String KEY_BRAND_NAME = "brand_name";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_MEMBER_REAL_NAME = "member_real_name";
	public static final String KEY_MEMBER_IDENTITY_CARD = "member_identity_card";
	public static final String KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE = "member_identity_card_front_image";
	public static final String KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE = "member_identity_card_back_image";
	public static final String KEY_BRAND_APPLY_REVIEW_STATUS = "brand_apply_review_status";
	public static final String KEY_BRAND_APPLY_CREATE_USER_ID = "brand_apply_create_user_id";
	public static final String KEY_BRAND_APPLY_CREATE_TIME = "brand_apply_create_time";
	public static final String KEY_BRAND_APPLY_UPDATE_USER_ID = "brand_apply_update_user_id";
	public static final String KEY_BRAND_APPLY_UPDATE_TIME = "brand_apply_update_time";
	public static final String KEY_BRAND_APPLY_STATUS = "brand_apply_status";

	public String getBrand_id() {
		return getStr(KEY_BRAND_ID);
	}

	public void setBrand_id(String brand_id) {
		set(KEY_BRAND_ID, brand_id);
	}

	public String getBrand_name() {
		return getStr(KEY_BRAND_NAME);
	}

	public void setBrand_name(String brand_name) {
		set(KEY_BRAND_NAME, brand_name);
	}

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public String getMember_real_name() {
		return getStr(KEY_MEMBER_REAL_NAME);
	}

	public void setMember_real_name(String member_real_name) {
		set(KEY_MEMBER_REAL_NAME, member_real_name);
	}

	public String getMember_identity_card() {
		return getStr(KEY_MEMBER_IDENTITY_CARD);
	}

	public void setMember_identity_card(String member_identity_card) {
		set(KEY_MEMBER_IDENTITY_CARD, member_identity_card);
	}

	public String getMember_identity_card_front_image() {
		return getStr(KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE);
	}

	public void setMember_identity_card_front_image(String member_identity_card_front_image) {
		set(KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE, member_identity_card_front_image);
	}

	public String getMember_identity_card_back_image() {
		return getStr(KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE);
	}

	public void setMember_identity_card_back_image(String member_identity_card_back_image) {
		set(KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE, member_identity_card_back_image);
	}

	public String getBrand_apply_review_status() {
		return getStr(KEY_BRAND_APPLY_REVIEW_STATUS);
	}

	public void setBrand_apply_review_status(String brand_apply_review_status) {
		set(KEY_BRAND_APPLY_REVIEW_STATUS, brand_apply_review_status);
	}

	public void setBrand_apply_create_user_id(String brand_apply_create_user_id) {
		set(KEY_BRAND_APPLY_CREATE_USER_ID, brand_apply_create_user_id);
	}

	public void setBrand_apply_create_time(Date brand_apply_create_time) {
		set(KEY_BRAND_APPLY_CREATE_TIME, brand_apply_create_time);
	}

	public void setBrand_apply_update_user_id(String brand_apply_update_user_id) {
		set(KEY_BRAND_APPLY_UPDATE_USER_ID, brand_apply_update_user_id);
	}

	public void setBrand_apply_update_time(Date brand_apply_update_time) {
		set(KEY_BRAND_APPLY_UPDATE_TIME, brand_apply_update_time);
	}

	public Boolean getBrand_apply_status() {
		return getBoolean(KEY_BRAND_APPLY_STATUS);
	}

	public void setBrand_apply_status(Boolean brand_apply_status) {
		set(KEY_BRAND_APPLY_STATUS, brand_apply_status);
	}

}
