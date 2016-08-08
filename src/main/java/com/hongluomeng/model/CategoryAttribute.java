package com.hongluomeng.model;

import com.jfinal.plugin.activerecord.Model;
import com.hongluomeng.common.Utility;

public class CategoryAttribute extends Model<CategoryAttribute> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_CATEGORY_ATTRIBUTE = "category_attribute";
	public static final String KEY_CATEGORY_ID = "category_id";
	public static final String KEY_ATTRIBUTE_ID = "attribute_id";
	public static final String KEY_ATTRIBUTE_NAME = "attribute_name";
	public static final String KEY_CATEGORY_ATTRIBUTE_SORT = "category_attribute_sort";
	public static final String KEY_ATTRIBUTE_VALUE = "attribute_value";

	private String attribute_name;
	private String attribute_value;

	public String getCategory_id() {
		return getStr(KEY_CATEGORY_ID);
	}

	public void setCategory_id(String category_id) {
		set(KEY_CATEGORY_ID, category_id);
	}

	public String getAttribute_id() {
		return getStr(KEY_ATTRIBUTE_ID);
	}

	public void setAttribute_id(String attribute_id) {
		set(KEY_ATTRIBUTE_ID, attribute_id);
	}

	public Integer getCategory_attribute_sort() {
		return Utility.getIntegerValue(get(KEY_CATEGORY_ATTRIBUTE_SORT));
	}

	public void setCategory_attribute_sort(Integer category_attribute_sort) {
		set(KEY_CATEGORY_ATTRIBUTE_SORT, category_attribute_sort);
	}

	public String getAttribute_name() {
		return attribute_name;
	}

	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}

	public String getAttribute_value() {
		return attribute_value;
	}

	public void setAttribute_value(String attribute_value) {
		this.attribute_value = attribute_value;
	}

}
