package com.hongluomeng.model;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class RoleOperation extends Model<RoleOperation> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_ROLE_OPERATION = "table_role_operation";
	public static final String COLUMN_ROLE_ID = "role_id";
	public static final String COLUMN_OPERATION_ID = "operation_id";

	public String getRole_id() {
		return getStr(COLUMN_ROLE_ID);
	}

	public void setRole_id(String role_id) {
		set(COLUMN_ROLE_ID, role_id);
	}

	public void checkRole_id() {
		Utility.checkStringLength(getRole_id(), 32, "角色编号");
	}

	public String getOperation_id() {
		return getStr(COLUMN_OPERATION_ID);
	}

	public void setOperation_id(String operation_id) {
		set(COLUMN_OPERATION_ID, operation_id);
	}

}
