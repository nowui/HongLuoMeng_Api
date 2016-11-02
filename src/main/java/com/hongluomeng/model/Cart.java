package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class Cart extends Model<Cart> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_CART = "table_cart";
	public static final String KEY_CART_ID = "cart_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_PRODUCT_SKU_ID = "product_sku_id";
	public static final String KEY_PRODUCT_ID = "product_id";
	public static final String KEY_PRODUCT_NAME = "product_name";
	public static final String KEY_PRODUCT_PRICE = "product_price";
	public static final String KEY_MEMBER_LEVEL_PRICE = "member_level_price";
	public static final String KEY_PRODUCT_STOCK = "product_stock";
	public static final String KEY_PRODUCT_SKU_STATUS = "product_sku_status";
	public static final String KEY_PRODUCT_AMOUNT = "product_amount";
	public static final String KEY_CART_CREATE_USER_ID = "cart_create_user_id";
	public static final String KEY_CART_CREATE_TIME = "cart_create_time";
	public static final String KEY_CART_UPDATE_USER_ID = "cart_update_user_id";
	public static final String KEY_CART_UPDATE_TIME = "cart_update_time";
	public static final String KEY_CART_STATUS = "cart_status";

	public String getCart_id() {
		return getStr(KEY_CART_ID);
	}

	public void setCart_id(String cart_id) {
		set(KEY_CART_ID, cart_id);
	}

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public String getProduct_sku_id() {
		return getStr(KEY_PRODUCT_SKU_ID);
	}

	public void setProduct_sku_id(String product_sku_id) {
		set(KEY_PRODUCT_SKU_ID, product_sku_id);
	}

	public String getProduct_id() {
		return getStr(KEY_PRODUCT_ID);
	}

	public String getProduct_name() {
		return getStr(KEY_PRODUCT_NAME);
	}

	public BigDecimal getProduct_price() {
		return getBigDecimal(KEY_PRODUCT_PRICE);
	}

	public JSONArray getMember_level_price() {
		return JSONArray.parseArray(getStr(KEY_MEMBER_LEVEL_PRICE));
	}

	public Integer getProduct_stock() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_STOCK));
	}

	public Boolean getProduct_sku_status() {
		return getBoolean(KEY_PRODUCT_SKU_STATUS);
	}

	public Integer getProduct_amount() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_AMOUNT));
	}

	public void setProduct_amount(Integer product_amount) {
		set(KEY_PRODUCT_AMOUNT, product_amount);
	}

	public void setCart_create_user_id(String cart_create_user_id) {
		set(KEY_CART_CREATE_USER_ID, cart_create_user_id);
	}

	public void setCart_create_time(Date cart_create_time) {
		set(KEY_CART_CREATE_TIME, cart_create_time);
	}

	public void setCart_update_user_id(String cart_update_user_id) {
		set(KEY_CART_UPDATE_USER_ID, cart_update_user_id);
	}

	public void setCart_update_time(Date cart_update_time) {
		set(KEY_CART_UPDATE_TIME, cart_update_time);
	}

	public Boolean getCart_status() {
		return getBoolean(KEY_CART_STATUS);
	}

	public void setCart_status(Boolean cart_status) {
		set(KEY_CART_STATUS, cart_status);
	}

}
