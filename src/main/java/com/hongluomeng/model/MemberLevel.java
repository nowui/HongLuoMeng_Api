package com.hongluomeng.model;

import java.util.Date;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class MemberLevel extends Model<MemberLevel> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_MEMBER_LEVEL = "member_level";
	public static final String KEY_MEMBER_LEVEL_ID = "member_level_id";
	public static final String KEY_MEMBER_LEVEL_NAME = "member_level_name";
	public static final String KEY_MEMBER_LEVEL_VALUE = "member_level_value";
	public static final String  KEY_MEMBER_LEVEL_SORT = "member_level_sort";
	public static final String KEY_MEMBER_LEVEL_CREATE_USER_ID = "member_level_create_user_id";
	public static final String KEY_MEMBER_LEVEL_CREATE_TIME = "member_level_create_time";
	public static final String KEY_MEMBER_LEVEL_UPDATE_USER_ID = "member_level_update_user_id";
	public static final String KEY_MEMBER_LEVEL_UPDATE_TIME = "member_level_update_time";
	public static final String KEY_MEMBER_LEVEL_STATUS = "member_level_status";

	public String getMember_level_id() {
		return getStr(KEY_MEMBER_LEVEL_ID);
	}

	public void setMember_level_id(String member_level_id) {
		set(KEY_MEMBER_LEVEL_ID, member_level_id);
	}

	public String getMember_level_name() {
		return getStr(KEY_MEMBER_LEVEL_NAME);
	}

	public void setMember_level_name(String member_level_name) {
		set(KEY_MEMBER_LEVEL_NAME, member_level_name);
	}

	public Integer getMember_level_value() {
		return Utility.getIntegerValue(get(KEY_MEMBER_LEVEL_VALUE));
	}

	public void setMember_level_value(String member_level_value) {
		set(KEY_MEMBER_LEVEL_VALUE, member_level_value);
	}

	public Integer getMember_level_sort() {
		return Utility.getIntegerValue(get(KEY_MEMBER_LEVEL_SORT));
	}

	public void setMember_level_sort(Integer member_level_sort) {
		set(KEY_MEMBER_LEVEL_SORT, member_level_sort);
	}

	public void setMember_level_create_user_id(String member_level_create_user_id) {
		set(KEY_MEMBER_LEVEL_CREATE_USER_ID, member_level_create_user_id);
	}

	public void setMember_level_create_time(Date member_level_create_time) {
		set(KEY_MEMBER_LEVEL_CREATE_TIME, member_level_create_time);
	}

	public void setMember_level_update_user_id(String member_level_update_user_id) {
		set(KEY_MEMBER_LEVEL_UPDATE_USER_ID, member_level_update_user_id);
	}

	public void setMember_level_update_time(Date member_level_update_time) {
		set(KEY_MEMBER_LEVEL_UPDATE_TIME, member_level_update_time);
	}

	public Boolean getMember_level_status() {
		return getBoolean(KEY_MEMBER_LEVEL_STATUS);
	}

	public void setMember_level_status(Boolean member_level_status) {
		set(KEY_MEMBER_LEVEL_STATUS, member_level_status);
	}

}
