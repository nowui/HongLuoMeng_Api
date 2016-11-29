package com.hongluomeng.model;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.plugin.activerecord.Model;

public class CategoryAttributeValue extends Model<CategoryAttributeValue> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_CATEGORY_ATTRIBUTE_VALUE = "table_category_attribute_value";
	public static final String KEY_OBJECT_ID = "object_id";
	public static final String KEY_OBJECT_TYPE = "object_type";
	public static final String KEY_ATTRIBUTE_ID = "attribute_id";
	public static final String KEY_ATTRIBUTE_VALUE = "attribute_value";

	public String getObject_id() {
		return getStr(KEY_OBJECT_ID);
	}

	public void setObject_id(String object_id) {
		set(KEY_OBJECT_ID, object_id);
	}

	public String getObject_type() {
		return getStr(KEY_OBJECT_TYPE);
	}

	public void setObject_type(String object_type) {
		set(KEY_OBJECT_TYPE, object_type);
	}

	public String getAttribute_id() {
		return getStr(KEY_ATTRIBUTE_ID);
	}

	public void setAttribute_id(String attribute_id) {
		set(KEY_ATTRIBUTE_ID, attribute_id);
	}

	public String getAttribute_name() {
		return getStr(Attribute.KEY_ATTRIBUTE_NAME);
	}

	public JSONArray getAttribute_default_value() {
		return JSONArray.parseArray(getStr(Attribute.KEY_ATTRIBUTE_DEFAULT_VALUE));
	}

	public String getAttribute_type() {
		return getStr(Attribute.KEY_ATTRIBUTE_TYPE);
	}

	public String getAttribute_value() {
		return getStr(KEY_ATTRIBUTE_VALUE);
	}

	public void setAttribute_value(String attribute_value) {
		set(KEY_ATTRIBUTE_VALUE, attribute_value);
	}

}
