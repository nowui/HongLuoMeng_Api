package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class MemberDelivery extends Model<MemberDelivery> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_MEMBER_DELIVERY = "table_member_delivery";
	public static final String KEY_MEMBER_DELIVERY_ID = "member_delivery_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_MEMBER_DELIVERY_NAME = "member_delivery_name";
	public static final String KEY_MEMBER_DELIVERY_PHONE = "member_delivery_phone";
	public static final String KEY_MEMBER_DELIVERY_PROVINCE = "member_delivery_province";
	public static final String KEY_MEMBER_DELIVERY_CITY = "member_delivery_city";
	public static final String KEY_MEMBER_DELIVERY_AREA = "member_delivery_area";
	public static final String KEY_MEMBER_DELIVERY_ADDRESS = "member_delivery_address";
	public static final String KEY_MEMBER_DELIVERY_ZIP = "member_delivery_zip";
	public static final String KEY_MEMBER_DELIVERY_CREATE_USER_ID = "member_delivery_create_user_id";
	public static final String KEY_MEMBER_DELIVERY_CREATE_TIME = "member_delivery_create_time";
	public static final String KEY_MEMBER_DELIVERY_UPDATE_USER_ID = "member_delivery_update_user_id";
	public static final String KEY_MEMBER_DELIVERY_UPDATE_TIME = "member_delivery_update_time";
	public static final String KEY_MEMBER_DELIVERY_STATUS = "member_delivery_status";

	public String getMember_delivery_id() {
		return getStr(KEY_MEMBER_DELIVERY_ID);
	}

	public void setMember_delivery_id(String member_delivery_id) {
		set(KEY_MEMBER_DELIVERY_ID, member_delivery_id);
	}

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public String getMember_delivery_name() {
		return getStr(KEY_MEMBER_DELIVERY_NAME);
	}

	public void setMember_delivery_name(String member_delivery_name) {
		set(KEY_MEMBER_DELIVERY_NAME, member_delivery_name);
	}

	public String getMember_delivery_phone() {
		return getStr(KEY_MEMBER_DELIVERY_PHONE);
	}

	public void setMember_delivery_phone(String member_delivery_phone) {
		set(KEY_MEMBER_DELIVERY_PHONE, member_delivery_phone);
	}

	public String getMember_delivery_province() {
		return getStr(KEY_MEMBER_DELIVERY_PROVINCE);
	}

	public void setMember_delivery_province(String member_delivery_province) {
		set(KEY_MEMBER_DELIVERY_PROVINCE, member_delivery_province);
	}

	public String getMember_delivery_city() {
		return getStr(KEY_MEMBER_DELIVERY_CITY);
	}

	public void setMember_delivery_city(String member_delivery_city) {
		set(KEY_MEMBER_DELIVERY_CITY, member_delivery_city);
	}

	public String getMember_delivery_area() {
		return getStr(KEY_MEMBER_DELIVERY_AREA);
	}

	public void setMember_delivery_area(String member_delivery_area) {
		set(KEY_MEMBER_DELIVERY_AREA, member_delivery_area);
	}

	public String getMember_delivery_address() {
		return getStr(KEY_MEMBER_DELIVERY_ADDRESS);
	}

	public void setMember_delivery_address(String member_delivery_address) {
		set(KEY_MEMBER_DELIVERY_ADDRESS, member_delivery_address);
	}

	public String getMember_delivery_zip() {
		return getStr(KEY_MEMBER_DELIVERY_ZIP);
	}

	public void setMember_delivery_zip(String member_delivery_zip) {
		set(KEY_MEMBER_DELIVERY_ZIP, member_delivery_zip);
	}

	public void setMember_delivery_create_user_id(String member_delivery_create_user_id) {
		set(KEY_MEMBER_DELIVERY_CREATE_USER_ID, member_delivery_create_user_id);
	}

	public void setMember_delivery_create_time(Date member_delivery_create_time) {
		set(KEY_MEMBER_DELIVERY_CREATE_TIME, member_delivery_create_time);
	}

	public void setMember_delivery_update_user_id(String member_delivery_update_user_id) {
		set(KEY_MEMBER_DELIVERY_UPDATE_USER_ID, member_delivery_update_user_id);
	}

	public void setMember_delivery_update_time(Date member_delivery_update_time) {
		set(KEY_MEMBER_DELIVERY_UPDATE_TIME, member_delivery_update_time);
	}

	public Boolean getMember_delivery_status() {
		return getBoolean(KEY_MEMBER_DELIVERY_STATUS);
	}

	public void setMember_delivery_status(Boolean member_delivery_status) {
		set(KEY_MEMBER_DELIVERY_STATUS, member_delivery_status);
	}

}
