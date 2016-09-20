package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class BrandApply extends Model<BrandApply> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_BRAND_APPLY = "brand_apply";
	public static final String KEY_BRAND_ID = "brand_id";
	public static final String KEY_BRAND_NAME = "brand_name";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_MEMBER_NAME = "member_name";
	public static final String KEY_BRAND_APPLY_CREATE_USER_ID = "brand_apply_create_user_id";
	public static final String KEY_BRAND_APPLY_CREATE_TIME = "brand_apply_create_time";
	public static final String KEY_BRAND_APPLY_UPDATE_USER_ID = "brand_apply_update_user_id";
	public static final String KEY_BRAND_APPLY_UPDATE_TIME = "brand_apply_update_time";
	public static final String KEY_BRAND_APPLY_STATUS = "brand_apply_status";
	public static final String KEY_BRAND_APPLY_REVIEW_STATUS = "brand_apply_review_status";

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

	public String getMember_name() {
		return getStr(KEY_MEMBER_NAME);
	}

	public void setMember_name(String member_name) {
		set(KEY_MEMBER_NAME, member_name);
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

	public Boolean getBrand_apply_review_status() {
		return getBoolean(KEY_BRAND_APPLY_REVIEW_STATUS);
	}

	public void setBrand_apply_review_status(Boolean brand_apply_review_status) {
		set(KEY_BRAND_APPLY_REVIEW_STATUS, brand_apply_review_status);
	}

}
