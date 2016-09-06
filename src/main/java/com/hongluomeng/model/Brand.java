package com.hongluomeng.model;

import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class Brand extends Model<Brand> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_BRAND = "brand";
	public static final String KEY_BRAND_ID = "brand_id";
	public static final String KEY_CATEGORY_ID = "category_id";
	public static final String KEY_BRAND_NAME = "brand_name";
	public static final String KEY_BRAND_CREATE_USER_ID = "brand_create_user_id";
	public static final String KEY_BRAND_CREATE_TIME = "brand_create_time";
	public static final String KEY_BRAND_UPDATE_USER_ID = "brand_update_user_id";
	public static final String KEY_BRAND_UPDATE_TIME = "brand_update_time";
	public static final String KEY_BRAND_STATUS = "brand_status";

	private List<Category> categoryList;

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

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

}
