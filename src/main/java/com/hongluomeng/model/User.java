package com.hongluomeng.model;

import java.util.Date;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_USER = "table_user";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_USER_ACCOUNT = "user_account";
	public static final String KEY_USER_PHONE = "user_phone";
	public static final String KEY_USER_EMAIL = "user_email";
	public static final String KEY_USER_PASSWORD = "user_password";
	public static final String KEY_WEIBO_UID = "weibo_uid";
	public static final String KEY_WEIBO_ACCESS_TOKEN = "weibo_access_token";
	public static final String KEY_WECHAT_UID = "wechat_uid";
	public static final String KEY_WECHAT_ACCESS_TOKEN = "wechat_access_token";
	public static final String KEY_OBJECT_ID = "object_id";
	public static final String KEY_USER_TYPE = "user_type";
	public static final String KEY_USER_CREATE_USER_ID = "user_create_user_id";
	public static final String KEY_USER_CREATE_TIME = "user_create_time";
	public static final String KEY_USER_UPDATE_USER_ID = "user_update_user_id";
	public static final String KEY_USER_UPDATE_TIME = "user_update_time";
	public static final String KEY_USER_STATUS = "user_status";

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public void checkUser_id() {
		Utility.checkStringLength(getUser_id(), 32, "用户编号");
	}

	public String getUser_account() {
		return getStr(KEY_USER_ACCOUNT);
	}

	public void setUser_account(String user_account) {
		set(KEY_USER_ACCOUNT, user_account);
	}

	public void checkUser_account() {
		Utility.checkStringLength(getUser_account(), 3, 20, "用户帐号");
	}

	public String getUser_phone() {
		return getStr(KEY_USER_PHONE);
	}

	public void setUser_phone(String user_phone) {
		set(KEY_USER_PHONE, user_phone);
	}

	public void checkUser_phone() {
		Utility.checkStringLength(getUser_phone(), 3, 11, "用户电话");
	}

	public String getUser_email() {
		return getStr(KEY_USER_EMAIL);
	}

	public void setUser_email(String user_email) {
		set(KEY_USER_EMAIL, user_email);
	}

	public void checkUser_email() {
		Utility.checkStringLength(getUser_email(), 3, 20, "用户邮箱");
	}

	public String getUser_password() {
		return getStr(KEY_USER_PASSWORD);
	}

	public void setUser_password(String user_password) {
		set(KEY_USER_PASSWORD, user_password);
	}

	public void checkUser_password() {
		Utility.checkStringLength(getUser_password(), 3, 20, "用户密码");
	}

	public String getWeibo_uid() {
		return getStr(KEY_WEIBO_UID);
	}

	public void setWeibo_uid(String weibo_uid) {
		set(KEY_WEIBO_UID, weibo_uid);
	}

	public void checkWeibo_uid() {
		Utility.checkStringLength(getWeibo_uid(), 0, 50, "微博uid");
	}

	public String getWeibo_access_token() {
		return getStr(KEY_WEIBO_ACCESS_TOKEN);
	}

	public void setWeibo_access_token(String weibo_access_token) {
		set(KEY_WEIBO_ACCESS_TOKEN, weibo_access_token);
	}

	public void checkWeibo_access_token() {
		Utility.checkStringLength(getWeibo_access_token(), 0, 200, "微博token");
	}

	public String getWechat_uid() {
		return getStr(KEY_WECHAT_UID);
	}

	public void setWechat_uid(String wechat_uid) {
		set(KEY_WECHAT_UID, wechat_uid);
	}

	public void checkWechat_uid() {
		Utility.checkStringLength(getWechat_uid(), 0, 50, "微信uid");
	}

	public String getWechat_access_token() {
		return getStr(KEY_WECHAT_ACCESS_TOKEN);
	}

	public void setWechat_access_token(String wechat_access_token) {
		set(KEY_WECHAT_ACCESS_TOKEN, wechat_access_token);
	}

	public void checKWechat_access_token() {
		Utility.checkStringLength(getWechat_access_token(), 0, 200, "微信token");
	}

	public String getObject_id() {
		return getStr(KEY_OBJECT_ID);
	}

	public void setObject_id(String object_id) {
		set(KEY_OBJECT_ID, object_id);
	}

	public void checkObject_id() {
		Utility.checkStringLength(getObject_id(), 32, "外键编号");
	}

	public String getUser_type() {
		return getStr(KEY_USER_TYPE);
	}

	public void setUser_type(String user_type) {
		set(KEY_USER_TYPE, user_type);
	}

	public void checkUser_type() {
		Utility.checkStringLength(getUser_type(), 0, 20, "用户类型");
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

	public Boolean getUser_status() {
		return getBoolean(KEY_USER_STATUS);
	}

	public void setUser_status(Boolean user_status) {
		set(KEY_USER_STATUS, user_status);
	}

}
