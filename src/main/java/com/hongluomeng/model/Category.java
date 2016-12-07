package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class Category extends Base<Category> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_CATEGORY = "table_category";
	public static final String COLUMN_CATEGORY_ID = "category_id";
	public static final String COLUMN_PARENT_ID = "parent_id";
	public static final String COLUMN_CATEGORY_NAME = "category_name";
	public static final String COLUMN_CATEGORY_KEY = "category_key";
	public static final String COLUMN_CATEGORY_VALUE = "category_value";
	public static final String COLUMN_CATEGORY_SORT = "category_sort";
	public static final String COLUMN_CATEGORY_PATH = "category_path";
	public static final String COLUMN_CATEGORY_DESCRIPTION = "category_description";

	public String getCategory_id() {
		return getStr(COLUMN_CATEGORY_ID);
	}

	public void setCategory_id(String category_id) {
		set(COLUMN_CATEGORY_ID, category_id);
	}

	public void checkCategory_id() {
		Utility.checkStringLength(getCategory_id(), 32, "分类编号");
	}

	public String getParent_id() {
		return getStr(COLUMN_PARENT_ID);
	}

	public void setParent_id(String parent_id) {
		set(COLUMN_PARENT_ID, parent_id);
	}

	public void checkParent_id() {
		Utility.checkStringLength(getParent_id(), 32, "分类父编号编号");
	}

	public String getCategory_name() {
		return getStr(COLUMN_CATEGORY_NAME);
	}

	public void setCategory_name(String category_name) {
		set(COLUMN_CATEGORY_NAME, category_name);
	}

	public void checkCategory_name() {
		Utility.checkStringLength(getCategory_name(), 1, 20, "分类名称");
	}

	public String getCategory_key() {
		return getStr(COLUMN_CATEGORY_KEY);
	}

	public void setCategory_key(String parent_key) {
		set(COLUMN_CATEGORY_KEY, parent_key);
	}

	public void checkCategory_key() {
		Utility.checkStringLength(getCategory_key(), 0, 200, "分类键值");
	}

	public String getCategory_value() {
		return getStr(COLUMN_CATEGORY_VALUE);
	}

	public void setCategory_value(String category_value) {
		set(COLUMN_CATEGORY_VALUE, category_value);
	}

	public void checkCategory_value() {
		Utility.checkStringLength(getCategory_value(), 0, 200, "分类数值");
	}

	public Integer getCategory_sort() {
		return Utility.getIntegerValue(get(COLUMN_CATEGORY_SORT));
	}

	public void setCategory_sort(Integer category_sort) {
		set(COLUMN_CATEGORY_SORT, category_sort);
	}

	public void checkCategory_sort() {
		Utility.checkIntegerLength(getCategory_sort(), 1, 3, "分类排序");
	}

	public String getCategory_path() {
		return getStr(COLUMN_CATEGORY_PATH);
	}

	public void setCategory_path(String category_path) {
		set(COLUMN_CATEGORY_PATH, category_path);
	}

	public void checkCategory_path() {
		Utility.checkStringLength(getCategory_path(), 3, 1000, "分类路径");
	}

	public String getCategory_description() {
		return getStr(COLUMN_CATEGORY_DESCRIPTION);
	}

	public void setCategory_description(String category_description) {
		set(COLUMN_CATEGORY_DESCRIPTION, category_description);
	}

	public void checkCategory_description() {
		Utility.checkStringLength(getCategory_description(), 0, 255, "分类描述");
	}

}
