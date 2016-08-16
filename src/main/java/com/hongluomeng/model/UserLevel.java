package com.hongluomeng.model;

import com.jfinal.plugin.activerecord.Model;

public class UserLevel extends Model<UserLevel> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_USER_LEVEL = "user_level";
	public static final String KEY_USER_LEVEL_ID = "user_level_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_USER_LEVEL_VALUE = "user_level_value";
	public static final String KEY_USER_LEVEL_DESCRIPTION = "user_level_description";

	public String getUser_level_id() {
		return getStr(KEY_USER_LEVEL_ID);
	}

	public void setUser_level_id(String user_level_id) {
		set(KEY_USER_LEVEL_ID, user_level_id);
	}

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public String getUser_level_value() {
		return getStr(KEY_USER_LEVEL_VALUE);
	}

	public void setUser_level_value(String user_level_value) {
		set(KEY_USER_LEVEL_VALUE, user_level_value);
	}

	public String getRole_id() {
		return getStr(KEY_USER_LEVEL_DESCRIPTION);
	}

	public void setRole_level_description(String user_level_description) {
		set(KEY_USER_LEVEL_DESCRIPTION, user_level_description);
	}

}
