package com.hongluomeng.model;

import java.util.Date;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class Cart extends Model<Cart> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_CART = "cart";
	public static final String KEY_CART_ID = "cart_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_PRODUCT_SKU_ID = "product_sku_id";
	public static final String KEY_CART_PRODUCT_NUMBER = "cart_product_number";
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

	public Integer getCart_product_number() {
		return Utility.getIntegerValue(get(KEY_CART_PRODUCT_NUMBER));
	}

	public void setCart_product_number(Integer cart_product_number) {
		set(KEY_CART_PRODUCT_NUMBER, cart_product_number);
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
