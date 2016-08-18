package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;
import com.hongluomeng.common.Utility;

public class Operation extends Model<Operation> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_OPERATION = "operation";
	public static final String KEY_OPERATION_ID = "operation_id";
	public static final String KEY_MENU_ID = "menu_id";
	public static final String KEY_OPERATION_NAME = "operation_name";
	public static final String KEY_OPERATION_KEY = "operation_key";
	public static final String KEY_OPERATION_VALUE = "operation_value";
	public static final String KEY_OPERATION_SORT = "operation_sort";
	public static final String KEY_OPERATION_CREATE_USER_ID = "operation_create_user_id";
	public static final String KEY_OPERATION_CREATE_TIME = "operation_create_time";
	public static final String KEY_OPERATION_UPDATE_USER_ID = "operation_update_user_id";
	public static final String KEY_OPERATION_UPDATE_TIME = "operation_update_time";
	public static final String KEY_OPERATION_STATUS = "operation_status";

	public String getOperation_id() {
		return getStr(KEY_OPERATION_ID);
	}

	public void setOperation_id(String operation_id) {
		set(KEY_OPERATION_ID, operation_id);
	}

	public String getMenu_id() {
		return getStr(KEY_MENU_ID);
	}

	public void setMenu_id(String menu_id) {
		set(KEY_MENU_ID, menu_id);
	}

	public String getOperation_name() {
		return getStr(KEY_OPERATION_NAME);
	}

	public void setOperation_name(String operation_name) {
		set(KEY_OPERATION_NAME, operation_name);
	}

	public String getOperation_key() {
		return getStr(KEY_OPERATION_KEY);
	}

	public void setOperation_key(String operation_key) {
		set(KEY_OPERATION_KEY, operation_key);
	}

	public String getOperation_value() {
		return getStr(KEY_OPERATION_VALUE);
	}

	public void setOperation_value(String operation_value) {
		set(KEY_OPERATION_VALUE, operation_value);
	}

	public Integer getOperation_sort() {
		return Utility.getIntegerValue(get(KEY_OPERATION_SORT));
	}

	public void setOperation_sort(Integer operation_sort) {
		set(KEY_OPERATION_SORT, operation_sort);
	}

	public void setOperation_create_user_id(String operation_create_user_id) {
		set(KEY_OPERATION_CREATE_USER_ID, operation_create_user_id);
	}

	public void setOperation_create_time(Date operation_create_time) {
		set(KEY_OPERATION_CREATE_TIME, operation_create_time);
	}

	public void setOperation_update_user_id(String operation_update_user_id) {
		set(KEY_OPERATION_UPDATE_USER_ID, operation_update_user_id);
	}

	public void setOperation_update_time(Date operation_update_time) {
		set(KEY_OPERATION_UPDATE_TIME, operation_update_time);
	}

	public Boolean getOperation_status() {
		return getBoolean(KEY_OPERATION_STATUS);
	}

	public void setOperation_status(Boolean operation_status) {
		set(KEY_OPERATION_STATUS, operation_status);
	}

}
