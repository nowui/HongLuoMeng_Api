package com.hongluomeng.model;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

import java.util.Date;

public class Base<M extends Base> extends Model<M>  {

	private static final long serialVersionUID = 1L;

	public static final String COLUMN_SYSTEM_CREATE_USER_ID = "system_create_user_id";
	public static final String COLUMN_SYSTEM_CREATE_TIME = "system_create_time";
	public static final String COLUMN_SYSTEM_UPDATE_USER_ID = "system_update_user_id";
	public static final String COLUMN_SYSTEM_UPDATE_TIME = "system_update_time";
	public static final String COLUMN_SYSTEM_STATUS = "system_status";

	public void setSystem_create_user_id(String system_create_user_id) {
		set(COLUMN_SYSTEM_CREATE_USER_ID, system_create_user_id);
	}

	public String getSystem_create_time() {
		return Utility.getDateTimeString(getDate(COLUMN_SYSTEM_CREATE_TIME));
	}

	public void setSystem_create_time(Date system_create_time) {
		set(COLUMN_SYSTEM_CREATE_TIME, system_create_time);
	}

	public void setSystem_update_user_id(String system_update_user_id) {
		set(COLUMN_SYSTEM_UPDATE_USER_ID, system_update_user_id);
	}

	public void setSystem_update_time(Date system_update_time) {
		set(COLUMN_SYSTEM_UPDATE_TIME, system_update_time);
	}

//	public Boolean getSystem_status() {
//		return getBoolean(COLUMN_SYSTEM_STATUS);
//	}

	public void setSystem_status(Boolean system_status) {
		set(COLUMN_SYSTEM_STATUS, system_status);
	}

	public void initForSave(String user_id) {
		setSystem_create_user_id(user_id);
		setSystem_create_time(new Date());
		setSystem_update_user_id(user_id);
		setSystem_update_time(new Date());
		setSystem_status(true);
	}

	public void initForUpdate(String user_id) {
		remove(COLUMN_SYSTEM_CREATE_USER_ID);
		remove(COLUMN_SYSTEM_CREATE_TIME);
		setSystem_update_user_id(user_id);
		setSystem_update_time(new Date());
		remove(COLUMN_SYSTEM_STATUS);
	}

	public void initForDelete(String user_id) {
		setSystem_update_user_id(user_id);
		setSystem_update_time(new Date());
		setSystem_status(false);
	}

}
