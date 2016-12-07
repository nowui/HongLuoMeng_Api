package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class Sms extends Base<Sms> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_SMS = "table_sms";
	public static final String COLUMN_SMS_ID = "sms_id";
	public static final String COLUMN_SMS_TYPE = "sms_type";
	public static final String COLUMN_SMS_PHONE = "sms_phone";
	public static final String COLUMN_SMS_CODE = "sms_code";
	public static final String COLUMN_SMS_IP_ADDRESS = "sms_ip_address";

	public String getSms_id() {
		return getStr(COLUMN_SMS_ID);
	}

	public void setSms_id(String sms_id) {
		set(COLUMN_SMS_ID, sms_id);
	}

	public void checkSms_id() {
		Utility.checkStringLength(getSms_id(), 32, "短信编号");
	}

	public String getSms_type() {
		return getStr(COLUMN_SMS_TYPE);
	}

	public void setSms_type(String sms_type) {
		set(COLUMN_SMS_TYPE, sms_type);
	}

	public void checkMember_real_name() {
		Utility.checkStringLength(getSms_type(), 1, 20, "短信类型");
	}

	public String getSms_phone() {
		return getStr(COLUMN_SMS_PHONE);
	}

	public void setSms_phone(String sms_phone) {
		set(COLUMN_SMS_PHONE, sms_phone);
	}

	public void checkSms_phone() {
		Utility.checkStringLength(getSms_phone(), 11, "短信电话");
	}

	public String getSms_code() {
		return getStr(COLUMN_SMS_CODE);
	}

	public void setSms_code(String sms_code) {
		set(COLUMN_SMS_CODE, sms_code);
	}

	public void checkSms_code() {
		Utility.checkStringLength(getSms_code(), 6, "短信验证码");
	}

	public String getSms_ip_address() {
		return getStr(COLUMN_SMS_IP_ADDRESS);
	}

	public void setSms_ip_address(String sms_ip_address) {
		set(COLUMN_SMS_IP_ADDRESS, sms_ip_address);
	}

	public void checkSms_ip_address() {
		Utility.checkStringLength(getSms_ip_address(), 0, 100, "短信ip地址");
	}

}
