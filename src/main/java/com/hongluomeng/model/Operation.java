package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class Operation extends Base<Operation> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_OPERATION = "table_operation";
	public static final String COLUMN_OPERATION_ID = "operation_id";
	public static final String COLUMN_MENU_ID = "menu_id";
	public static final String COLUMN_OPERATION_NAME = "operation_name";
	public static final String COLUMN_OPERATION_KEY = "operation_key";
	public static final String COLUMN_OPERATION_VALUE = "operation_value";
	public static final String COLUMN_OPERATION_SORT = "operation_sort";

	public String getOperation_id() {
		return getStr(COLUMN_OPERATION_ID);
	}

	public void setOperation_id(String operation_id) {
		set(COLUMN_OPERATION_ID, operation_id);
	}

	public void checkOperation_id() {
		Utility.checkStringLength(getOperation_id(), 32, "操作编号");
	}

	public String getMenu_id() {
		return getStr(COLUMN_MENU_ID);
	}

	public void setMenu_id(String menu_id) {
		set(COLUMN_MENU_ID, menu_id);
	}

	public void checkMenu_id() {
		Utility.checkStringLength(getMenu_id(), 32, "菜单编号");
	}

	public String getOperation_name() {
		return getStr(COLUMN_OPERATION_NAME);
	}

	public void setOperation_name(String operation_name) {
		set(COLUMN_OPERATION_NAME, operation_name);
	}

	public void checkOperation_name() {
		Utility.checkStringLength(getOperation_name(), 1, 20, "操作名称");
	}

	public String getOperation_key() {
		return getStr(COLUMN_OPERATION_KEY);
	}

	public void setOperation_key(String operation_key) {
		set(COLUMN_OPERATION_KEY, operation_key);
	}

	public void checkOperation_key() {
		Utility.checkStringLength(getOperation_key(), 0, 200, "操作键值");
	}

	public String getOperation_value() {
		return getStr(COLUMN_OPERATION_VALUE);
	}

	public void setOperation_value(String operation_value) {
		set(COLUMN_OPERATION_VALUE, operation_value);
	}

	public void checkOperation_value() {
		Utility.checkStringLength(getOperation_value(), 3, 200, "操作数值");
	}

	public Integer getOperation_sort() {
		return Utility.getIntegerValue(get(COLUMN_OPERATION_SORT));
	}

	public void setOperation_sort(Integer operation_sort) {
		set(COLUMN_OPERATION_SORT, operation_sort);
	}

	public void checkOperation_sort() {
		Utility.checkIntegerLength(getOperation_sort(), 1, 3, "操作排序");
	}

}
