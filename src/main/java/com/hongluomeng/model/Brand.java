package com.hongluomeng.model;

import java.util.Calendar;
import java.util.List;

import com.hongluomeng.common.Utility;

public class Brand extends Base<Brand> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_BRAND = "table_brand";
	public static final String KEY_BRAND_ID = "brand_id";
	public static final String KEY_CATEGORY_ID = "category_id";
	public static final String KEY_BRAND_NAME = "brand_name";
	public static final String KEY_BRAND_LOGO = "brand_logo";
	public static final String KEY_BRAND_BACKGROUND = "brand_background";
	public static final String KEY_BRAND_INTRODUCE = "brand_introduce";
	public static final String KEY_BRAND_AGREEMENT = "brand_agreement";
	public static final String KEY_BRAND_APPLY_CREATE_TIME = "brand_apply_create_time";
	public static final String KEY_BRAND_APPLY_EXPIRE_TIME = "brand_apply_expire_time";
	public static final String KEY_BRAND_APPLY_REVIEW_STATUS = "brand_apply_review_status";

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

	public String getBrand_background() {
		return getStr(KEY_BRAND_BACKGROUND);
	}

	public void setBrand_background(String brand_background) {
		set(KEY_BRAND_BACKGROUND, brand_background);
	}

	public void checkBrand_background() {
		Utility.checkStringLength(getBrand_background(), 0, 100, "品牌背景");
	}

	public String getBrand_introduce() {
		return getStr(KEY_BRAND_INTRODUCE);
	}

	public void setBrand_introduce(String brand_introduce) {
		set(KEY_BRAND_INTRODUCE, brand_introduce);
	}

	public void checkBrand_introduce() {
		Utility.checkStringLength(getBrand_introduce(), 0, 1000, "品牌介绍");
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

	public String getBrand_apply_create_time() {
		return Utility.getDateTimeString(getDate(KEY_BRAND_APPLY_CREATE_TIME));
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

	public Category getCategory() {
		return new Category().put(this);
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

}
