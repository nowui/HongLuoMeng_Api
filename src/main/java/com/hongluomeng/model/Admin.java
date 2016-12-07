package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class Admin extends Base<Admin> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_ADMIN = "table_admin";
	public static final String COLUMN_ADMIN_ID = "admin_id";
	public static final String COLUMN_ADMIN_NAME = "admin_name";
	public static final String COLUMN_USER_ID = "user_id";

	private String user_account;

	public String getAdmin_id() {
		return getStr(COLUMN_ADMIN_ID);
	}

	public void setAdmin_id(String admin_id) {
		set(COLUMN_ADMIN_ID, admin_id);
	}

	public void checkAdmin_id() {
		Utility.checkStringLength(getAdmin_id(), 32, "管理员编号");
	}

	public String getAdmin_name() {
		return getStr(COLUMN_ADMIN_NAME);
	}

	public void setAdmin_name(String admin_name) {
		set(COLUMN_ADMIN_NAME, admin_name);
	}

	public void checkAdmin_name() {
		Utility.checkStringLength(getAdmin_name(), 3, 20, "管理员名称");
	}

	public String getUser_id() {
		return getStr(COLUMN_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(COLUMN_USER_ID, user_id);
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

}
