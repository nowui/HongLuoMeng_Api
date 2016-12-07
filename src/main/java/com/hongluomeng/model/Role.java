package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class Role extends Base<Role> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_ROLE = "table_role";
	public static final String COLUMN_ROLE_ID = "role_id";
	public static final String COLUMN_GROUP_ID = "group_id";
	public static final String COLUMN_ROLE_NAME = "role_name";
	public static final String COLUMN_ROLE_KEY = "role_key";
	public static final String COLUMN_ROLE_SORT = "role_sort";

	public String getRole_id() {
		return getStr(COLUMN_ROLE_ID);
	}

	public void setRole_id(String role_id) {
		set(COLUMN_ROLE_ID, role_id);
	}

	public void checkRole_id() {
		Utility.checkStringLength(getRole_id(), 32, "角色编号");
	}

	public String getGroup_id() {
		return getStr(COLUMN_GROUP_ID);
	}

	public void setGroup_id(String group_id) {
		set(COLUMN_GROUP_ID, group_id);
	}

	public void checkGroup_id() {
		Utility.checkStringLength(getGroup_id(), 32, "角色编号");
	}

	public String getRole_name() {
		return getStr(COLUMN_ROLE_NAME);
	}

	public void setRole_name(String role_name) {
		set(COLUMN_ROLE_NAME, role_name);
	}

	public void checkRole_name() {
		Utility.checkStringLength(getRole_name(), 3, 20, "角色名称");
	}

	public String getRole_key() {
		return getStr(COLUMN_ROLE_KEY);
	}

	public void setRole_key(String role_key) {
		set(COLUMN_ROLE_KEY, role_key);
	}

	public void checkRole_key() {
		Utility.checkStringLength(getRole_key(), 3, 20, "角色键值");
	}

	public Integer getRole_sort() {
		return Utility.getIntegerValue(get(COLUMN_ROLE_SORT));
	}

	public void setRole_sort(Integer role_sort) {
		set(COLUMN_ROLE_SORT, role_sort);
	}

	public void checkRole_sort() {
		Utility.checkIntegerLength(getRole_sort(), 1, 3, "角色排序");
	}

}
