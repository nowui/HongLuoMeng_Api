package com.hongluomeng.model;

import java.util.Date;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class Member extends Model<Member> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_MEMBER = "table_member";
	public static final String KEY_MEMBER_ID = "member_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_MEMBER_LEVEL_ID = "member_level_id";
	public static final String KEY_MEMBER_LEVEL_NAME = "member_level_name";
	public static final String KEY_MEMBER_LEVEL_VALUE = "member_level_value";
	public static final String KEY_MEMBER_NAME = "member_name";
	public static final String KEY_MEMBER_AVATAR = "member_avatar";
	public static final String KEY_MEMBER_AVATAR_SMALL = "member_avatar_small";
	public static final String KEY_MEMBER_AVATAR_LARGE = "member_avatar_large";
	public static final String KEY_MEMBER_REAL_NAME = "member_real_name";
	public static final String KEY_MEMBER_IDENTITY_CARD = "member_identity_card";
	public static final String KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE = "member_identity_card_front_image";
	public static final String KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE = "member_identity_card_back_image";
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

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public String getMember_level_id() {
		return getStr(KEY_MEMBER_LEVEL_ID);
	}

	public void setMember_level_id(String member_level_id) {
		set(KEY_MEMBER_LEVEL_ID, member_level_id);
	}

	public String getMember_level_name() {
		return getStr(KEY_MEMBER_LEVEL_NAME);
	}

	public Integer getMember_level_value() {
		return Utility.getIntegerValue(get(KEY_MEMBER_LEVEL_VALUE));
	}

	public String getMember_avatar() {
		return getStr(KEY_MEMBER_AVATAR);
	}

	public void setMember_avatar(String member_avatar) {
		set(KEY_MEMBER_AVATAR, member_avatar);
	}

	public String getMember_name() {
		return getStr(KEY_MEMBER_NAME);
	}

	public void setMember_name(String member_name) {
		set(KEY_MEMBER_NAME, member_name);
	}

	public String getMember_real_name() {
		return getStr(KEY_MEMBER_REAL_NAME);
	}

	public void setMember_real_name(String member_real_name) {
		set(KEY_MEMBER_REAL_NAME, member_real_name);
	}

	public String getMember_identity_card() {
		return getStr(KEY_MEMBER_IDENTITY_CARD);
	}

	public void setMember_identity_card(String member_identity_card) {
		set(KEY_MEMBER_IDENTITY_CARD, member_identity_card);
	}

	public String getMember_identity_card_front_image() {
		return getStr(KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE);
	}

	public void setMember_identity_card_front_image(String member_identity_card_front_image) {
		set(KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE, member_identity_card_front_image);
	}

	public String getMember_identity_card_back_image() {
		return getStr(KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE);
	}

	public void setMember_identity_card_back_image(String member_identity_card_back_image) {
		set(KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE, member_identity_card_back_image);
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
