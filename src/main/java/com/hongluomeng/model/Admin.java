package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class Admin extends Model<Admin> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_ADMIN = "admin";
	public static final String KEY_ADMIN_ID = "admin_id";
	public static final String KEY_ADMIN_NAME = "admin_name";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_ADMIN_CREATE_USER_ID = "admin_create_user_id";
	public static final String KEY_ADMIN_CREATE_TIME = "admin_create_time";
	public static final String KEY_ADMIN_UPDATE_USER_ID = "admin_update_user_id";
	public static final String KEY_ADMIN_UPDATE_TIME = "admin_update_time";

	private String user_account;

	public String getAdmin_id() {
		return getStr(KEY_ADMIN_ID);
	}

	public void setAdmin_id(String admin_id) {
		set(KEY_ADMIN_ID, admin_id);
	}

	public String getAdmin_name() {
		return getStr(KEY_ADMIN_NAME);
	}

	public void setAdmin_name(String admin_name) {
		set(KEY_ADMIN_NAME, admin_name);
	}

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	public void setAdmin_create_user_id(String admin_create_user_id) {
		set(KEY_ADMIN_CREATE_USER_ID, admin_create_user_id);
	}

	public void setAdmin_create_time(Date admin_create_time) {
		set(KEY_ADMIN_CREATE_TIME, admin_create_time);
	}

	public void setAdmin_update_user_id(String admin_update_user_id) {
		set(KEY_ADMIN_UPDATE_USER_ID, admin_update_user_id);
	}

	public void setAdmin_update_time(Date admin_update_time) {
		set(KEY_ADMIN_UPDATE_TIME, admin_update_time);
	}

}
