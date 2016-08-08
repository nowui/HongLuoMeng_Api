package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;
import com.hongluomeng.common.Utility;

public class Role extends Model<Role> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_ROLE = "role";
	public static final String KEY_ROLE_ID = "role_id";
	public static final String KEY_GROUP_ID = "group_id";
	public static final String KEY_ROLE_NAME = "role_name";
	public static final String KEY_ROLE_KEY = "role_key";
	public static final String KEY_ROLE_SORT = "role_sort";
	public static final String KEY_ROLE_CREATE_USER_ID = "role_create_user_id";
	public static final String KEY_ROLE_CREATE_TIME = "role_create_time";
	public static final String KEY_ROLE_UPDATE_USER_ID = "role_update_user_id";
	public static final String KEY_ROLE_UPDATE_TIME = "role_update_time";

	public String getRole_id() {
		return getStr(KEY_ROLE_ID);
	}

	public void setRole_id(String role_id) {
		set(KEY_ROLE_ID, role_id);
	}

	public String getGroup_id() {
		return getStr(KEY_GROUP_ID);
	}

	public void setGroup_id(String group_id) {
		set(KEY_GROUP_ID, group_id);
	}

	public String getRole_name() {
		return getStr(KEY_ROLE_NAME);
	}

	public void setRole_name(String role_name) {
		set(KEY_ROLE_NAME, role_name);
	}

	public String getRole_key() {
		return getStr(KEY_ROLE_KEY);
	}

	public void setRole_key(String role_key) {
		set(KEY_ROLE_KEY, role_key);
	}

	public Integer getRole_sort() {
		return Utility.getIntegerValue(get(KEY_ROLE_SORT));
	}

	public void setRole_sort(Integer role_sort) {
		set(KEY_ROLE_SORT, role_sort);
	}

	public void setRole_create_user_id(String role_create_user_id) {
		set(KEY_ROLE_CREATE_USER_ID, role_create_user_id);
	}

	public void setRole_create_time(Date role_create_time) {
		set(KEY_ROLE_CREATE_TIME, role_create_time);
	}

	public void setRole_update_user_id(String role_update_user_id) {
		set(KEY_ROLE_UPDATE_USER_ID, role_update_user_id);
	}

	public void setRole_update_time(Date role_update_time) {
		set(KEY_ROLE_UPDATE_TIME, role_update_time);
	}

}
