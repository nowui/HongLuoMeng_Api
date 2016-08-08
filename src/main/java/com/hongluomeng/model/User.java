package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_USER = "user";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_USER_ACCOUNT = "user_account";
	public static final String KEY_USER_PHONE = "user_phone";
	public static final String KEY_USER_EMAIL = "user_email";
	public static final String KEY_USER_PASSWORD = "user_password";
	public static final String KEY_WEIBO_OPEN_ID = "weibo_open_id";
	public static final String KEY_WEIBO_ACCESS_TOKEN = "weibo_access_token";
	public static final String KEY_WECHAT_OPEN_ID = "wechat_open_id";
	public static final String KEY_WECHAT_ACCESS_TOKEN = "wechat_access_token";
	public static final String KEY_OBJECT_ID = "object_id";
	public static final String KEY_USER_TYPE = "user_type";
	public static final String KEY_USER_CREATE_USER_ID = "user_create_user_id";
	public static final String KEY_USER_CREATE_TIME = "user_create_time";
	public static final String KEY_USER_UPDATE_USER_ID = "user_update_user_id";
	public static final String KEY_USER_UPDATE_TIME = "user_update_time";

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public String getUser_account() {
		return getStr(KEY_USER_ACCOUNT);
	}

	public void setUser_account(String user_account) {
		set(KEY_USER_ACCOUNT, user_account);
	}

	public String getUser_phone() {
		return getStr(KEY_USER_PHONE);
	}

	public void setUser_phone(String user_phone) {
		set(KEY_USER_PHONE, user_phone);
	}

	public String getUser_email() {
		return getStr(KEY_USER_EMAIL);
	}

	public void setUser_email(String user_email) {
		set(KEY_USER_EMAIL, user_email);
	}

	public String getUser_password() {
		return getStr(KEY_USER_PASSWORD);
	}

	public void setUser_password(String user_password) {
		set(KEY_USER_PASSWORD, user_password);
	}

	public String getWeibo_open_id() {
		return getStr(KEY_WEIBO_OPEN_ID);
	}

	public void setWeibo_open_id(String weibo_open_id) {
		set(KEY_WEIBO_OPEN_ID, weibo_open_id);
	}

	public String getWeibo_access_token() {
		return getStr(KEY_WEIBO_ACCESS_TOKEN);
	}

	public void setWeibo_access_token(String weibo_access_token) {
		set(KEY_WEIBO_ACCESS_TOKEN, weibo_access_token);
	}

	public String getWechat_open_id() {
		return getStr(KEY_WECHAT_OPEN_ID);
	}

	public void setWechat_open_id(String wechat_open_id) {
		set(KEY_WECHAT_OPEN_ID, wechat_open_id);
	}

	public String getWechat_access_token() {
		return getStr(KEY_WECHAT_ACCESS_TOKEN);
	}

	public void setWechat_access_token(String wechat_access_token) {
		set(KEY_WECHAT_ACCESS_TOKEN, wechat_access_token);
	}

	public String getObject_id() {
		return getStr(KEY_OBJECT_ID);
	}

	public void setObject_id(String object_id) {
		set(KEY_OBJECT_ID, object_id);
	}

	public String getUser_type() {
		return getStr(KEY_USER_TYPE);
	}

	public void setUser_type(String user_type) {
		set(KEY_USER_TYPE, user_type);
	}

	public void setUser_create_user_id(String user_create_user_id) {
		set(KEY_USER_CREATE_USER_ID, user_create_user_id);
	}

	public void setUser_create_time(Date user_create_time) {
		set(KEY_USER_CREATE_TIME, user_create_time);
	}

	public void setUser_update_user_id(String user_update_user_id) {
		set(KEY_USER_UPDATE_USER_ID, user_update_user_id);
	}

	public void setUser_update_time(Date user_update_time) {
		set(KEY_USER_UPDATE_TIME, user_update_time);
	}

}
