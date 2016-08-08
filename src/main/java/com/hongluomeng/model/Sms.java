package com.hongluomeng.model;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class Sms extends Model<Sms> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_SMS = "sms";
	public static final String KEY_SMS_ID = "sms_id";
	public static final String KEY_SMS_PHONE = "sms_phone";
	public static final String KEY_SMS_CODE = "sms_code";
	public static final String KEY_SMS_IP_ADDRESS = "sms_ip_address";
	public static final String KEY_SMS_STATUS = "sms_status";
	public static final String KEY_SMS_CREATE_TIME = "sms_create_time";

	public String getSms_id() {
		return getStr(KEY_SMS_ID);
	}

	public void setSms_id(String sms_id) {
		set(KEY_SMS_ID, sms_id);
	}

	public String getSms_phone() {
		return getStr(KEY_SMS_PHONE);
	}

	public void setSms_phone(String sms_phone) {
		set(KEY_SMS_PHONE, sms_phone);
	}

	public String getSms_code() {
		return getStr(KEY_SMS_CODE);
	}

	public void setSms_code(String sms_code) {
		set(KEY_SMS_CODE, sms_code);
	}

	public String getSms_ip_address() {
		return getStr(KEY_SMS_IP_ADDRESS);
	}

	public void setSms_ip_address(String sms_ip_address) {
		set(KEY_SMS_IP_ADDRESS, sms_ip_address);
	}

	public Boolean getSms_status() {
		return getBoolean(KEY_SMS_STATUS);
	}

	public void setSms_status(Boolean sms_status) {
		set(KEY_SMS_STATUS, sms_status);
	}

	public Date getSms_create_time() {
		return getDate(KEY_SMS_CREATE_TIME);
	}

	public void setSms_create_time(Date sms_create_time) {
		set(KEY_SMS_CREATE_TIME, sms_create_time);
	}

}
