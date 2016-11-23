package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class Activity extends Model<Activity> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_ACTIVITY = "table_activity";
	public static final String KEY_ACTIVITY_ID = "activity_id";
	public static final String KEY_ACTIVITY_NAME = "activity_name";
	public static final String KEY_ACTIVITY_LOGO = "activity_logo";
	public static final String KEY_ACTIVITY_CREATE_USER_ID = "activity_create_user_id";
	public static final String KEY_ACTIVITY_CREATE_TIME = "activity_create_time";
	public static final String KEY_ACTIVITY_UPDATE_USER_ID = "activity_update_user_id";
	public static final String KEY_ACTIVITY_UPDATE_TIME = "activity_update_time";
	public static final String KEY_ACTIVITY_STATUS = "activity_status";

	public String getActivity_id() {
		return getStr(KEY_ACTIVITY_ID);
	}

	public void setActivity_id(String activity_id) {
		set(KEY_ACTIVITY_ID, activity_id);
	}

	public String getActivity_name() {
		return getStr(KEY_ACTIVITY_NAME);
	}

	public void setActivity_name(String activity_name) {
		set(KEY_ACTIVITY_NAME, activity_name);
	}

	public String getActivity_logo() {
		return getStr(KEY_ACTIVITY_LOGO);
	}

	public void setActivity_logo(String activity_logo) {
		set(KEY_ACTIVITY_LOGO, activity_logo);
	}

	public void setActivity_create_user_id(String activity_create_user_id) {
		set(KEY_ACTIVITY_CREATE_USER_ID, activity_create_user_id);
	}

	public void setActivity_create_time(Date activity_create_time) {
		set(KEY_ACTIVITY_CREATE_TIME, activity_create_time);
	}

	public void setActivity_update_user_id(String activity_update_user_id) {
		set(KEY_ACTIVITY_UPDATE_USER_ID, activity_update_user_id);
	}

	public void setActivity_update_time(Date activity_update_time) {
		set(KEY_ACTIVITY_UPDATE_TIME, activity_update_time);
	}

	public Boolean getActivity_status() {
		return getBoolean(KEY_ACTIVITY_STATUS);
	}

	public void setActivity_status(Boolean activity_status) {
		set(KEY_ACTIVITY_STATUS, activity_status);
	}

}
