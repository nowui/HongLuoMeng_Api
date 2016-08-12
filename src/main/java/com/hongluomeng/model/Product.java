package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.hongluomeng.common.Utility;

public class Product extends Model<Product> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_PRODUCT = "product";
	public static final String KEY_PRODUCT_ID = "product_id";
	public static final String KEY_CATEGORY_ID = "category_id";
	public static final String KEY_BRAND_ID = "brand_id";
	public static final String KEY_PRODUCT_NAME = "product_name";
	public static final String KEY_PRODUCT_PRICE = "product_price";
	public static final String KEY_PRODUCT_STOCK = "product_stock";
	public static final String KEY_PRODUCT_IMAGE = "product_image";
	public static final String KEY_PRODUCT_IS_NEWWRRIVAL = "product_is_newarrival";
	public static final String KEY_PRODUCT_IS_RECOMMEND = "product_is_recommend";
	public static final String KEY_PRODUCT_IS_BARGAIN = "product_is_bargain";
	public static final String KEY_PRODUCT_STATUS = "product_status";
	public static final String KEY_PRODUCT_CONTENT = "product_content";
	public static final String KEY_PRODUCT_CREATE_USER_ID = "product_create_user_id";
	public static final String KEY_PRODUCT_CREATE_TIME = "product_create_time";
	public static final String KEY_PRODUCT_UPDATE_USER_ID = "product_update_user_id";
	public static final String KEY_PRODUCT_UPDATE_TIME = "product_update_time";

	private List<Category> categoryList;
	private List<Brand> brandList;
	private List<CategoryAttribute> categoryAttributeList;

	public String getProduct_id() {
		return getStr(KEY_PRODUCT_ID);
	}

	public void setProduct_id(String product_id) {
		set(KEY_PRODUCT_ID, product_id);
	}

	public String getCategory_id() {
		return getStr(KEY_CATEGORY_ID);
	}

	public void setCategory_id(String category_id) {
		set(KEY_CATEGORY_ID, category_id);
	}

	public String getBrand_id() {
		return getStr(KEY_BRAND_ID);
	}

	public void setBrand_id(String brand_id) {
		set(KEY_BRAND_ID, brand_id);
	}

	public String getProduct_name() {
		return getStr(KEY_PRODUCT_NAME);
	}

	public void setProduct_name(String product_name) {
		set(KEY_PRODUCT_NAME, product_name);
	}

	public BigDecimal getProduct_price() {
		return getBigDecimal(KEY_PRODUCT_PRICE);
	}

	public void setProduct_price(BigDecimal product_price) {
		set(KEY_PRODUCT_PRICE, product_price);
	}

	public Integer getProduct_stock() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_STOCK));
	}

	public void setProduct_stock(Integer product_stock) {
		set(KEY_PRODUCT_STOCK, product_stock);
	}

	public String getProduct_image() {
		return getStr(KEY_PRODUCT_IMAGE);
	}

	public void setProduct_image(String product_image) {
		set(KEY_PRODUCT_IMAGE, product_image);
	}

	public Boolean getProduct_is_newarrival() {
		return getBoolean(KEY_PRODUCT_IS_NEWWRRIVAL);
	}

	public void setProduct_is_newarrival(Boolean product_is_newarrival) {
		set(KEY_PRODUCT_IS_NEWWRRIVAL, product_is_newarrival);
	}

	public Boolean getProduct_is_recommend() {
		return getBoolean(KEY_PRODUCT_IS_RECOMMEND);
	}

	public void setProduct_is_recommend(Boolean product_is_recommend) {
		set(KEY_PRODUCT_IS_RECOMMEND, product_is_recommend);
	}

	public Boolean getProduct_is_bargain() {
		return getBoolean(KEY_PRODUCT_IS_BARGAIN);
	}

	public void setProduct_is_bargain(Boolean product_is_bargain) {
		set(KEY_PRODUCT_IS_BARGAIN, product_is_bargain);
	}

	public Boolean getProduct_status() {
		return getBoolean(KEY_PRODUCT_STATUS);
	}

	public void setProduct_status(Boolean product_status) {
		set(KEY_PRODUCT_STATUS, product_status);
	}

	public String getProduct_content() {
		return getStr(KEY_PRODUCT_CONTENT);
	}

	public void setProduct_content(String product_content) {
		set(KEY_PRODUCT_CONTENT, product_content);
	}

	public void setProduct_create_user_id(String product_create_user_id) {
		set(KEY_PRODUCT_CREATE_USER_ID, product_create_user_id);
	}

	public void setProduct_create_time(Date product_create_time) {
		set(KEY_PRODUCT_CREATE_TIME, product_create_time);
	}

	public void setProduct_update_user_id(String product_update_user_id) {
		set(KEY_PRODUCT_UPDATE_USER_ID, product_update_user_id);
	}

	public void setProduct_update_time(Date product_update_time) {
		set(KEY_PRODUCT_UPDATE_TIME, product_update_time);
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Brand> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}

	public List<CategoryAttribute> getCategoryAttributeList() {
		return categoryAttributeList;
	}

	public void setCategoryAttributeList(
			List<CategoryAttribute> categoryAttributeList) {
		this.categoryAttributeList = categoryAttributeList;
	}

}
