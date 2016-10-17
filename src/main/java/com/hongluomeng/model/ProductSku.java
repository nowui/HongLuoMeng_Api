package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class ProductSku extends Model<ProductSku> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_PRODUCT_SKU = "product_sku";
	public static final String KEY_PRODUCT_SKU_ID = "product_sku_id";
	public static final String KEY_PRODUCT_ID = "product_id";
	public static final String KEY_PRODUCT_ATTRIBUTE_VALUE = "product_attribute_value";
	public static final String KEY_PRODUCT_PRICE = "product_price";
	public static final String KEY_MEMBER_LEVEL_ID = "member_level_id";
	public static final String KEY_MEMBER_LEVEL_PRICE = "member_level_price";
	public static final String KEY_PRODUCT_STOCK = "product_stock";
	public static final String KEY_PRODUCT_SKU_CREATE_USER_ID = "product_sku_create_user_id";
	public static final String KEY_PRODUCT_SKU_CREATE_TIME = "product_sku_create_time";
	public static final String KEY_PRODUCT_SKU_UPDATE_USER_ID = "product_sku_update_user_id";
	public static final String KEY_PRODUCT_SKU_UPDATE_TIME = "product_sku_update_time";
	public static final String KEY_PRODUCT_SKU_STATUS = "product_sku_status";

	public String getProduct_sku_id() {
		return getStr(KEY_PRODUCT_SKU_ID);
	}

	public void setProduct_sku_id(String product_sku_id) {
		set(KEY_PRODUCT_SKU_ID, product_sku_id);
	}

	public String getProduct_id() {
		return getStr(KEY_PRODUCT_ID);
	}

	public void setProduct_id(String product_id) {
		set(KEY_PRODUCT_ID, product_id);
	}

	public JSONArray getProduct_attribute_value() {
		return JSONArray.parseArray(getStr(KEY_PRODUCT_ATTRIBUTE_VALUE));
	}

	public void setProduct_attribute_value(String product_attribute_value) {
		set(KEY_PRODUCT_ATTRIBUTE_VALUE, product_attribute_value);
	}

	public BigDecimal getProduct_price() {
		return getBigDecimal(KEY_PRODUCT_PRICE);
	}

	public void setProduct_price(BigDecimal product_price) {
		set(KEY_PRODUCT_PRICE, product_price);
	}

	public JSONArray getMember_level_price() {
		return JSONArray.parseArray(getStr(KEY_MEMBER_LEVEL_PRICE));
	}

	public void setMember_level_price(String member_level_price) {
		set(KEY_MEMBER_LEVEL_PRICE, member_level_price);
	}

	public Integer getProduct_stock() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_STOCK));
	}

	public void setProduct_stock(Integer product_stock) {
		set(KEY_PRODUCT_STOCK, product_stock);
	}

	public void setProduct_sku_create_user_id(String product_sku_create_user_id) {
		set(KEY_PRODUCT_SKU_CREATE_USER_ID, product_sku_create_user_id);
	}

	public void setProduct_sku_create_time(Date product_sku_create_time) {
		set(KEY_PRODUCT_SKU_CREATE_TIME, product_sku_create_time);
	}

	public void setProduct_sku_update_user_id(String product_sku_update_user_id) {
		set(KEY_PRODUCT_SKU_UPDATE_USER_ID, product_sku_update_user_id);
	}

	public void setProduct_sku_update_time(Date product_sku_update_time) {
		set(KEY_PRODUCT_SKU_UPDATE_TIME, product_sku_update_time);
	}

	public Boolean getProduct_sku_status() {
		return getBoolean(KEY_PRODUCT_SKU_STATUS);
	}

	public void setProduct_sku_status(Boolean product_sku_status) {
		set(KEY_PRODUCT_SKU_STATUS, product_sku_status);
	}

}
