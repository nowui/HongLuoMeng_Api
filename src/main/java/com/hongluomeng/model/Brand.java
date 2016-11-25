package com.hongluomeng.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class Brand extends Model<Brand> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_BRAND = "table_brand";
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
	public static final String KEY_BRAND_APPLY_CREATE_TIME = "brand_apply_create_time";
	public static final String KEY_BRAND_APPLY_EXPIRE_TIME = "brand_apply_expire_time";
	public static final String KEY_BRAND_APPLY_REVIEW_STATUS = "brand_apply_review_status";
	public static final String KEY_BRAND_STATUS = "brand_status";

	private List<Category> categoryList;

	public String getBrand_id() {
		return getStr(KEY_BRAND_ID);
	}

	public void setBrand_id(String brand_id) {
		set(KEY_BRAND_ID, brand_id);
	}

	public void checkBrand_id() {
		Utility.checkStringLength(getBrand_id(), 32, "品牌编号");
	}

	public String getCategory_id() {
		return getStr(KEY_CATEGORY_ID);
	}

	public void setCategory_id(String category_id) {
		set(KEY_CATEGORY_ID, category_id);
	}

	public void checkCategory_id() {
		Utility.checkStringLength(getCategory_id(), 32, "分类编号");
	}

	public String getBrand_name() {
		return getStr(KEY_BRAND_NAME);
	}

	public void setBrand_name(String brand_name) {
		set(KEY_BRAND_NAME, brand_name);
	}

	public void checkBrand_name() {
		Utility.checkStringLength(getBrand_name(), 3, 20, "品牌名称");
	}

	public String getBrand_logo() {
		return getStr(KEY_BRAND_LOGO);
	}

	public void setBrand_logo(String brand_logo) {
		set(KEY_BRAND_LOGO, brand_logo);
	}

	public void checkBrand_logo() {
		Utility.checkStringLength(getBrand_logo(), 1, 100, "品牌logo");
	}

	public String getBrand_introduce() {
		return getStr(KEY_BRAND_INTRODUCE);
	}

	public void setBrand_introduce(String brand_introduce) {
		set(KEY_BRAND_INTRODUCE, brand_introduce);
	}

	public void checkBrand_introduce() {
		Utility.checkStringLength(getBrand_introduce(), 0, 500, "品牌介绍");
	}

	public String getBrand_agreement() {
		return getStr(KEY_BRAND_AGREEMENT);
	}

	public void setBrand_agreement(String brand_agreement) {
		set(KEY_BRAND_AGREEMENT, brand_agreement);
	}

	public void checkBrand_agreement() {
		Utility.checkStringLength(getBrand_agreement(), 0, 0, "品牌协议书");
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

	public String getBrand_apply_create_time() {
		if(Utility.isNullOrEmpty(getDate(KEY_BRAND_APPLY_CREATE_TIME))) {
			return "";
		} else {
			return Utility.getDateTimeString(getDate(KEY_BRAND_APPLY_CREATE_TIME));
		}
	}

	public String getBrand_apply_expire_time() {
		if(Utility.isNullOrEmpty(getDate(KEY_BRAND_APPLY_CREATE_TIME))) {
			return "";
		} else {
			Calendar  calendar = Calendar.getInstance();
			calendar.setTime(getDate(KEY_BRAND_APPLY_CREATE_TIME));
			calendar.add(Calendar.MONTH, 3);

			return Utility.getDateTimeString(calendar.getTime());
		}
	}

	public String getBrand_apply_review_status() {
		return getStr(KEY_BRAND_APPLY_REVIEW_STATUS);
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
