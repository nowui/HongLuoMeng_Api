package com.hongluomeng.model;

import com.jfinal.plugin.activerecord.Model;

public class UserRole extends Model<UserRole> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_USER_ROLE = "table_user_role";
	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_USER_TYPE = "user_type";
	public static final String COLUMN_ROLE_ID = "role_id";

	public String getUser_id() {
		return getStr(COLUMN_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(COLUMN_USER_ID, user_id);
	}

	public String getUser_type() {
		return getStr(COLUMN_USER_TYPE);
	}

	public void setUser_type(String user_type) {
		set(COLUMN_USER_TYPE, user_type);
	}

	public String getRole_id() {
		return getStr(COLUMN_ROLE_ID);
	}

	public void setRole_id(String role_id) {
		set(COLUMN_ROLE_ID, role_id);
	}

}
