package com.hongluomeng.model;

import com.jfinal.plugin.activerecord.Model;

public class RoleOperation extends Model<RoleOperation> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_ROLE_OPERATION = "table_role_operation";
	public static final String KEY_ROLE_ID = "role_id";
	public static final String KEY_OPERATION_ID = "operation_id";

	public String getRole_id() {
		return getStr(KEY_ROLE_ID);
	}

	public void setRole_id(String role_id) {
		set(KEY_ROLE_ID, role_id);
	}

	public String getOperation_id() {
		return getStr(KEY_OPERATION_ID);
	}

	public void setOperation_id(String operation_id) {
		set(KEY_OPERATION_ID, operation_id);
	}

}
