package com.hongluomeng.model;

import com.jfinal.plugin.activerecord.Model;
import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Utility;

public class CategoryAttribute extends Model<CategoryAttribute> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_CATEGORY_ATTRIBUTE = "table_category_attribute";
	public static final String KEY_CATEGORY_ID = "category_id";
	public static final String KEY_ATTRIBUTE_ID = "attribute_id";
	public static final String KEY_CATEGORY_ATTRIBUTE_SORT = "category_attribute_sort";
	public static final String KEY_ATTRIBUTE_LIST = "attributeList";

	public String getCategory_id() {
		return getStr(KEY_CATEGORY_ID);
	}

	public void setCategory_id(String category_id) {
		set(KEY_CATEGORY_ID, category_id);
	}

	public void checkCategory_id() {
		Utility.checkStringLength(getCategory_id(), 32, "分类编号");
	}

	public String getAttribute_id() {
		return getStr(KEY_ATTRIBUTE_ID);
	}

	public void setAttribute_id(String attribute_id) {
		set(KEY_ATTRIBUTE_ID, attribute_id);
	}

	public void checkAttribute_id() {
		Utility.checkStringLength(getAttribute_id(), 32, "属性编号");
	}

	public Integer getCategory_attribute_sort() {
		return Utility.getIntegerValue(get(KEY_CATEGORY_ATTRIBUTE_SORT));
	}

	public void setCategory_attribute_sort(Integer category_attribute_sort) {
		set(KEY_CATEGORY_ATTRIBUTE_SORT, category_attribute_sort);
	}

	public void checkCategory_attribute_sort() {
		Utility.checkIntegerLength(getCategory_attribute_sort(), 1, 3, "分类属性排序");
	}

	public String getAttribute_name() {
		return getStr(Attribute.KEY_ATTRIBUTE_NAME);
	}

	public String getAttribute_type() {
		return getStr(Attribute.KEY_ATTRIBUTE_TYPE);
	}

	public JSONArray getAttribute_default_value() {
		return JSONArray.parseArray(getStr(Attribute.KEY_ATTRIBUTE_DEFAULT_VALUE));
	}

}
