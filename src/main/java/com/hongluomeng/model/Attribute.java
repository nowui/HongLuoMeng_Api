package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class Attribute extends Base<Attribute> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_ATTRIBUTE = "table_attribute";
	public static final String COLUMN_ATTRIBUTE_ID = "attribute_id";
	public static final String KEY_ATTRIBUTE_NAME = "attribute_name";
	public static final String KEY_ATTRIBUTE_INPUT_TYPE = "attribute_input_type";
	public static final String KEY_ATTRIBUTE_DEFAULT_VALUE = "attribute_default_value";
	public static final String KEY_ATTRIBUTE_TYPE = "attribute_type";
	public static final String KEY_ATTRIBUTE_SORT = "attribute_sort";

	public String getAttribute_id() {
		return getStr(COLUMN_ATTRIBUTE_ID);
	}

	public void setAttribute_id(String attribute_id) {
		set(COLUMN_ATTRIBUTE_ID, attribute_id);
	}

	public void checkAttribute_id() {
		Utility.checkStringLength(getAttribute_id(), 32, "属性编号");
	}

	public String getAttribute_name() {
		return getStr(KEY_ATTRIBUTE_NAME);
	}

	public void setAttribute_name(String attribute_name) {
		set(KEY_ATTRIBUTE_NAME, attribute_name);
	}

	public void checkAttribute_name() {
		Utility.checkStringLength(getAttribute_name(), 1, 20, "属性名称");
	}

	public String getAttribute_input_type() {
		return getStr(KEY_ATTRIBUTE_INPUT_TYPE);
	}

	public void setAttribute_input_type(String attribute_input_type) {
		set(KEY_ATTRIBUTE_INPUT_TYPE, attribute_input_type);
	}

	public void checkAttribute_input_type() {
		Utility.checkStringLength(getAttribute_input_type(), 1, 20, "属性输入类型");
	}

	public String getAttribute_default_value() {
		return getStr(KEY_ATTRIBUTE_DEFAULT_VALUE);
	}

	public void setAttribute_default_value(String attribute_default_value) {
		set(KEY_ATTRIBUTE_DEFAULT_VALUE, attribute_default_value);
	}

	public void checkAttribute_default_value() {
		Utility.checkStringLength(getAttribute_default_value(), 0, 1000, "属性初始化值");
	}

	public String getAttribute_type() {
		return getStr(KEY_ATTRIBUTE_TYPE);
	}

	public void setAttribute_type(String attribute_type) {
		set(KEY_ATTRIBUTE_TYPE, attribute_type);
	}

	public void checkAttribute_type() {
		Utility.checkStringLength(getAttribute_type(), 1, 20, "属性类型");
	}

	public Integer getAttribute_sort() {
		return Utility.getIntegerValue(get(KEY_ATTRIBUTE_SORT));
	}

	public void setAttribute_sort(Integer attribute_sort) {
		set(KEY_ATTRIBUTE_SORT, attribute_sort);
	}

	public void checkAttribute_sort() {
		Utility.checkIntegerLength(getAttribute_sort(), 1, 3, "属性排序");
	}

}
