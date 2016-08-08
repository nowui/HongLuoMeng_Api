package com.hongluomeng.model;

import com.jfinal.plugin.activerecord.Model;

public class UserRole extends Model<UserRole> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_USER_ROLE = "user_role";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_USER_TYPE = "user_type";
	public static final String KEY_ROLE_ID = "role_id";

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public String getUser_type() {
		return getStr(KEY_USER_TYPE);
	}

	public void setUser_type(String user_type) {
		set(KEY_USER_TYPE, user_type);
	}

	public String getRole_id() {
		return getStr(KEY_ROLE_ID);
	}

	public void setRole_id(String role_id) {
		set(KEY_ROLE_ID, role_id);
	}

}
