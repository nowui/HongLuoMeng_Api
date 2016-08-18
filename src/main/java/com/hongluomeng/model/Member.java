package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class Member extends Model<Member> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_MEMBER = "member";
	public static final String KEY_MEMBER_ID = "member_id";
	public static final String KEY_MEMBER_NAME = "member_name";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_MEMBER_CREATE_USER_ID = "member_create_user_id";
	public static final String KEY_MEMBER_CREATE_TIME = "member_create_time";
	public static final String KEY_MEMBER_UPDATE_USER_ID = "member_update_user_id";
	public static final String KEY_MEMBER_UPDATE_TIME = "member_update_time";
	public static final String KEY_MEMBER_STATUS = "member_status";

	public String getMember_id() {
		return getStr(KEY_MEMBER_ID);
	}

	public void setMember_id(String member_id) {
		set(KEY_MEMBER_ID, member_id);
	}

	public String getMember_name() {
		return getStr(KEY_MEMBER_NAME);
	}

	public void setMember_name(String member_name) {
		set(KEY_MEMBER_NAME, member_name);
	}

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public void setMember_create_user_id(String member_create_user_id) {
		set(KEY_MEMBER_CREATE_USER_ID, member_create_user_id);
	}

	public void setMember_create_time(Date member_create_time) {
		set(KEY_MEMBER_CREATE_TIME, member_create_time);
	}

	public void setMember_update_user_id(String member_update_user_id) {
		set(KEY_MEMBER_UPDATE_USER_ID, member_update_user_id);
	}

	public void setMember_update_time(Date member_update_time) {
		set(KEY_MEMBER_UPDATE_TIME, member_update_time);
	}

	public Boolean getMember_status() {
		return getBoolean(KEY_MEMBER_STATUS);
	}

	public void setMember_status(Boolean member_status) {
		set(KEY_MEMBER_STATUS, member_status);
	}

}
