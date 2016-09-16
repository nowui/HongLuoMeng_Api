package com.hongluomeng.model;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class Brand extends Model<Brand> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_BRAND = "brand";
	public static final String KEY_BRAND_ID = "brand_id";
	public static final String KEY_CATEGORY_ID = "category_id";
	public static final String KEY_BRAND_NAME = "brand_name";
	public static final String KEY_BRAND_LOGO = "brand_logo";
	public static final String KEY_BRAND_INTRODUCE = "brand_introduce";
	public static final String KEY_BRAND_AGREEMENT = "brand_agreement";
	public static final String KEY_BRAND_CREATE_USER_ID = "brand_create_user_id";
	public static final String KEY_BRAND_CREATE_TIME = "brand_create_time";
	public static final String KEY_BRAND_UPDATE_USER_ID = "brand_update_user_id";
	public static final String KEY_BRAND_UPDATE_TIME = "brand_update_time";
	public static final String KEY_BRAND_STATUS = "brand_status";
	public static final String KEY_BRAND_APPLY_COUNT = "brand_apply_count";
	public static final String KEY_BRAND_IS_APPLY = "brand_is_apply";

	private List<Category> categoryList;
	private Boolean brand_is_apply;

	public String getBrand_id() {
		return getStr(KEY_BRAND_ID);
	}

	public void setBrand_id(String brand_id) {
		set(KEY_BRAND_ID, brand_id);
	}

	public String getCategory_id() {
		return getStr(KEY_CATEGORY_ID);
	}

	public void setCategory_id(String category_id) {
		set(KEY_CATEGORY_ID, category_id);
	}

	public String getBrand_name() {
		return getStr(KEY_BRAND_NAME);
	}

	public void setBrand_name(String brand_name) {
		set(KEY_BRAND_NAME, brand_name);
	}

	public String getBrand_logo() {
		return getStr(KEY_BRAND_LOGO);
	}

	public void setBrand_logo(String brand_logo) {
		set(KEY_BRAND_LOGO, brand_logo);
	}

	public String getBrand_introduce() {
		return getStr(KEY_BRAND_INTRODUCE);
	}

	public void setBrand_introduce(String brand_introduce) {
		set(KEY_BRAND_INTRODUCE, brand_introduce);
	}

	public String getBrand_agreement() {
		return getStr(KEY_BRAND_AGREEMENT);
	}

	public void setBrand_agreement(String brand_agreement) {
		set(KEY_BRAND_AGREEMENT, brand_agreement);
	}

	public void setBrand_create_user_id(String brand_create_user_id) {
		set(KEY_BRAND_CREATE_USER_ID, brand_create_user_id);
	}

	public void setBrand_create_time(Date brand_create_time) {
		set(KEY_BRAND_CREATE_TIME, brand_create_time);
	}

	public void setBrand_update_user_id(String brand_update_user_id) {
		set(KEY_BRAND_UPDATE_USER_ID, brand_update_user_id);
	}

	public void setBrand_update_time(Date brand_update_time) {
		set(KEY_BRAND_UPDATE_TIME, brand_update_time);
	}

	public Boolean getBrand_status() {
		return getBoolean(KEY_BRAND_STATUS);
	}

	public void setBrand_status(Boolean brand_status) {
		set(KEY_BRAND_STATUS, brand_status);
	}

	public Integer getBrand_apply_count() {
		return Utility.getIntegerValue(get(KEY_BRAND_APPLY_COUNT));
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Boolean getBrand_is_apply() {
		return brand_is_apply;
	}

	public void setBrand_is_apply(Boolean brand_is_apply) {
		this.brand_is_apply = brand_is_apply;
	}

}
