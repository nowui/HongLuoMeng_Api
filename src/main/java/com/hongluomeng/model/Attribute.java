package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;
import com.hongluomeng.common.Utility;

public class Attribute extends Model<Attribute> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_ATTRIBUTE = "attribute";
	public static final String KEY_ATTRIBUTE_ID = "attribute_id";
	public static final String KEY_ATTRIBUTE_NAME = "attribute_name";
	public static final String KEY_ATTRIBUTE_INPUT_TYPE = "attribute_input_type";
	public static final String KEY_ATTRIBUTE_DEFAULT_VALUE = "attribute_default_value";
	public static final String KEY_ATTRIBUTE_TYPE = "attribute_type";
	public static final String KEY_ATTRIBUTE_SORT = "attribute_sort";
	public static final String KEY_ATTRIBUTE_CREATE_USER_ID = "attribute_create_user_id";
	public static final String KEY_ATTRIBUTE_CREATE_TIME = "attribute_create_time";
	public static final String KEY_ATTRIBUTE_UPDATE_USER_ID = "attribute_update_user_id";
	public static final String KEY_ATTRIBUTE_UPDATE_TIME = "attribute_update_time";
	public static final String KEY_ATTRIBUTE_STATUS = "attribute_status";

	private String user_account;

	public String getAttribute_id() {
		return getStr(KEY_ATTRIBUTE_ID);
	}

	public void setAttribute_id(String attribute_id) {
		set(KEY_ATTRIBUTE_ID, attribute_id);
	}

	public String getAttribute_name() {
		return getStr(KEY_ATTRIBUTE_NAME);
	}

	public void setAttribute_name(String attribute_name) {
		set(KEY_ATTRIBUTE_NAME, attribute_name);
	}

	public String getAttribute_input_type() {
		return getStr(KEY_ATTRIBUTE_INPUT_TYPE);
	}

	public void setAttribute_input_type(String attribute_input_type) {
		set(KEY_ATTRIBUTE_INPUT_TYPE, attribute_input_type);
	}

	public String getAttribute_default_value() {
		return getStr(KEY_ATTRIBUTE_DEFAULT_VALUE);
	}

	public void setAttribute_default_value(String attribute_default_value) {
		set(KEY_ATTRIBUTE_DEFAULT_VALUE, attribute_default_value);
	}

	public String getAttribute_type() {
		return getStr(KEY_ATTRIBUTE_TYPE);
	}

	public void setAttribute_type(String attribute_type) {
		set(KEY_ATTRIBUTE_TYPE, attribute_type);
	}


	public Integer getAttribute_sort() {
		return Utility.getIntegerValue(get(KEY_ATTRIBUTE_SORT));
	}

	public void setAttribute_sort(Integer attribute_sort) {
		set(KEY_ATTRIBUTE_SORT, attribute_sort);
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public void setAttribute_create_user_id(String attribute_create_user_id) {
		set(KEY_ATTRIBUTE_CREATE_USER_ID, attribute_create_user_id);
	}

	public void setAttribute_create_time(Date attribute_create_time) {
		set(KEY_ATTRIBUTE_CREATE_TIME, attribute_create_time);
	}

	public void setAttribute_update_user_id(String attribute_update_user_id) {
		set(KEY_ATTRIBUTE_UPDATE_USER_ID, attribute_update_user_id);
	}

	public void setAttribute_update_time(Date attribute_update_time) {
		set(KEY_ATTRIBUTE_UPDATE_TIME, attribute_update_time);
	}

	public Boolean getAttribute_status() {
		return getBoolean(KEY_ATTRIBUTE_STATUS);
	}

	public void setAttribute_status(Boolean attribute_status) {
		set(KEY_ATTRIBUTE_STATUS, attribute_status);
	}

}
