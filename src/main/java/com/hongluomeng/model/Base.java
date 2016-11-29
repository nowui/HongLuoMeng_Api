package com.hongluomeng.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.Date;

public class Base<M extends Base> extends Model  {

	private static final long serialVersionUID = 1L;

	public static final String KEY_SYSTEM_CREATE_USER_ID = "system_create_user_id";
	public static final String KEY_SYSTEM_CREATE_TIME = "system_create_time";
	public static final String KEY_SYSTEM_UPDATE_USER_ID = "system_update_user_id";
	public static final String KEY_SYSTEM_UPDATE_TIME = "system_update_time";
	public static final String KEY_SYSTEM_STATUS = "system_status";
    

	public void setSystem_create_user_id(String system_create_user_id) {
		set(KEY_SYSTEM_CREATE_USER_ID, system_create_user_id);
	}

	public void setSystem_create_time(Date system_create_time) {
		set(KEY_SYSTEM_CREATE_TIME, system_create_time);
	}

	public void setSystem_update_user_id(String system_update_user_id) {
		set(KEY_SYSTEM_UPDATE_USER_ID, system_update_user_id);
	}

	public void setSystem_update_time(Date system_update_time) {
		set(KEY_SYSTEM_UPDATE_TIME, system_update_time);
	}

	public Boolean getSystem_status() {
		return getBoolean(KEY_SYSTEM_STATUS);
	}

	public void setSystem_status(Boolean system_status) {
		set(KEY_SYSTEM_STATUS, system_status);
	}

}
