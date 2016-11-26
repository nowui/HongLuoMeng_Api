package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;
import com.hongluomeng.common.Utility;

public class Category extends Model<Category> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_CATEGORY = "table_category";
	public static final String KEY_CATEGORY_ID = "category_id";
	public static final String KEY_PARENT_ID = "parent_id";
	public static final String KEY_CATEGORY_NAME = "category_name";
	public static final String KEY_CATEGORY_KEY = "category_key";
	public static final String KEY_CATEGORY_VALUE = "category_value";
	public static final String KEY_CATEGORY_SORT = "category_sort";
	public static final String KEY_CATEGORY_PATH = "category_path";
	public static final String KEY_CATEGORY_DESCRIPTION = "category_description";
	public static final String KEY_CATEGORY_CREATE_USER_ID = "category_create_user_id";
	public static final String KEY_CATEGORY_CREATE_TIME = "category_create_time";
	public static final String KEY_CATEGORY_UPDATE_USER_ID = "category_update_user_id";
	public static final String KEY_CATEGORY_UPDATE_TIME = "category_update_time";
	public static final String KEY_CATEGORY_STATUS = "category_status";

	public String getCategory_id() {
		return getStr(KEY_CATEGORY_ID);
	}

	public void setCategory_id(String category_id) {
		set(KEY_CATEGORY_ID, category_id);
	}

	public void checkCategory_id() {
		Utility.checkStringLength(getCategory_id(), 32, "分类编号");
	}

	public String getParent_id() {
		return getStr(KEY_PARENT_ID);
	}

	public void setParent_id(String parent_id) {
		set(KEY_PARENT_ID, parent_id);
	}

	public void checkParent_id() {
		Utility.checkStringLength(getParent_id(), 32, "分类父编号编号");
	}

	public String getCategory_name() {
		return getStr(KEY_CATEGORY_NAME);
	}

	public void setCategory_name(String category_name) {
		set(KEY_CATEGORY_NAME, category_name);
	}

	public void checkCategory_name() {
		Utility.checkStringLength(getCategory_name(), 3, 20, "分类名称");
	}

	public String getCategory_key() {
		return getStr(KEY_CATEGORY_KEY);
	}

	public void setCategory_key(String parent_key) {
		set(KEY_CATEGORY_KEY, parent_key);
	}

	public void checkCategory_key() {
		Utility.checkStringLength(getCategory_key(), 0, 200, "分类键值");
	}

	public String getCategory_value() {
		return getStr(KEY_CATEGORY_VALUE);
	}

	public void setCategory_value(String category_value) {
		set(KEY_CATEGORY_VALUE, category_value);
	}

	public void checkCategory_value() {
		Utility.checkStringLength(getCategory_value(), 0, 200, "分类数值");
	}

	public Integer getCategory_sort() {
		return Utility.getIntegerValue(get(KEY_CATEGORY_SORT));
	}

	public void setCategory_sort(Integer category_sort) {
		set(KEY_CATEGORY_SORT, category_sort);
	}

	public void checkCategory_sort() {
		Utility.checkIntegerLength(getCategory_sort(), 1, 3, "分类排序");
	}

	public String getCategory_path() {
		return getStr(KEY_CATEGORY_PATH);
	}

	public void setCategory_path(String category_path) {
		set(KEY_CATEGORY_PATH, category_path);
	}

	public void checkCategory_path() {
		Utility.checkStringLength(getCategory_path(), 3, 1000, "分类路径");
	}

	public String getCategory_description() {
		return getStr(KEY_CATEGORY_DESCRIPTION);
	}

	public void setCategory_description(String category_description) {
		set(KEY_CATEGORY_DESCRIPTION, category_description);
	}

	public void checkCategory_description() {
		Utility.checkStringLength(getCategory_description(), 0, 255, "分类描述");
	}

	public void setCategory_create_user_id(String category_create_user_id) {
		set(KEY_CATEGORY_CREATE_USER_ID, category_create_user_id);
	}

	public void setCategory_create_time(Date category_create_time) {
		set(KEY_CATEGORY_CREATE_TIME, category_create_time);
	}

	public void setCategory_update_user_id(String category_update_user_id) {
		set(KEY_CATEGORY_UPDATE_USER_ID, category_update_user_id);
	}

	public void setCategory_update_time(Date category_update_time) {
		set(KEY_CATEGORY_UPDATE_TIME, category_update_time);
	}

	public Boolean getCategory_status() {
		return getBoolean(KEY_CATEGORY_STATUS);
	}

	public void setCategory_status(Boolean category_status) {
		set(KEY_CATEGORY_STATUS, category_status);
	}

}
