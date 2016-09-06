package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class Shop extends Model<Shop> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_SHOP = "shop";
	public static final String KEY_SHOP_ID = "shop_id";
	public static final String KEY_SHOP_NAME = "shop_name";
	public static final String KEY_SHOP_CREATE_USER_ID = "shop_create_user_id";
	public static final String KEY_SHOP_CREATE_TIME = "shop_create_time";
	public static final String KEY_SHOP_UPDATE_USER_ID = "shop_update_user_id";
	public static final String KEY_SHOP_UPDATE_TIME = "shop_update_time";
	public static final String KEY_SHOP_STATUS = "shop_status";

	public String getShop_id() {
		return getStr(KEY_SHOP_ID);
	}

	public void setShop_id(String shop_id) {
		set(KEY_SHOP_ID, shop_id);
	}

	public String getShop_name() {
		return getStr(KEY_SHOP_NAME);
	}

	public void setShop_name(String shop_name) {
		set(KEY_SHOP_NAME, shop_name);
	}

	public void setShop_create_user_id(String shop_create_user_id) {
		set(KEY_SHOP_CREATE_USER_ID, shop_create_user_id);
	}

	public void setShop_create_time(Date shop_create_time) {
		set(KEY_SHOP_CREATE_TIME, shop_create_time);
	}

	public void setShop_update_user_id(String shop_update_user_id) {
		set(KEY_SHOP_UPDATE_USER_ID, shop_update_user_id);
	}

	public void setShop_update_time(Date shop_update_time) {
		set(KEY_SHOP_UPDATE_TIME, shop_update_time);
	}

	public Boolean getShop_status() {
		return getBoolean(KEY_SHOP_STATUS);
	}

	public void setShop_status(Boolean shop_status) {
		set(KEY_SHOP_STATUS, shop_status);
	}

}
