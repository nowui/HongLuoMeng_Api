package com.hongluomeng.model;

import java.util.Date;

import com.hongluomeng.common.Utility;

public class MemberDelivery extends Base<MemberDelivery> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_MEMBER_DELIVERY = "table_member_delivery";
	public static final String COLUMN_MEMBER_DELIVERY_ID = "member_delivery_id";
	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_MEMBER_DELIVERY_NAME = "member_delivery_name";
	public static final String COLUMN_MEMBER_DELIVERY_PHONE = "member_delivery_phone";
	public static final String COLUMN_MEMBER_DELIVERY_PROVINCE = "member_delivery_province";
	public static final String COLUMN_MEMBER_DELIVERY_CITY = "member_delivery_city";
	public static final String COLUMN_MEMBER_DELIVERY_AREA = "member_delivery_area";
	public static final String COLUMN_MEMBER_DELIVERY_ADDRESS = "member_delivery_address";
	public static final String COLUMN_MEMBER_DELIVERY_ZIP = "member_delivery_zip";

	public String getMember_delivery_id() {
		return getStr(COLUMN_MEMBER_DELIVERY_ID);
	}

	public void setMember_delivery_id(String member_delivery_id) {
		set(COLUMN_MEMBER_DELIVERY_ID, member_delivery_id);
	}

	public void checkMember_delivery_id() {
		Utility.checkStringLength(getMember_delivery_id(), 32, "会员快递信息编号编号");
	}

	public String getUser_id() {
		return getStr(COLUMN_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(COLUMN_USER_ID, user_id);
	}

	public void checkUser_id() {
		Utility.checkStringLength(getUser_id(), 32, "用户编号");
	}

	public String getMember_delivery_name() {
		return getStr(COLUMN_MEMBER_DELIVERY_NAME);
	}

	public void setMember_delivery_name(String member_delivery_name) {
		set(COLUMN_MEMBER_DELIVERY_NAME, member_delivery_name);
	}

	public void checkMember_delivery_name() {
		Utility.checkStringLength(getMember_delivery_name(), 1, 20, "收货人");
	}

	public String getMember_delivery_phone() {
		return getStr(COLUMN_MEMBER_DELIVERY_PHONE);
	}

	public void setMember_delivery_phone(String member_delivery_phone) {
		set(COLUMN_MEMBER_DELIVERY_PHONE, member_delivery_phone);
	}

	public void checkMember_delivery_phone() {
		Utility.checkStringLength(getMember_delivery_phone(), 1, 20, "电话");
	}

	public String getMember_delivery_province() {
		return getStr(COLUMN_MEMBER_DELIVERY_PROVINCE);
	}

	public void setMember_delivery_province(String member_delivery_province) {
		set(COLUMN_MEMBER_DELIVERY_PROVINCE, member_delivery_province);
	}

	public void checkMember_delivery_province() {
		Utility.checkStringLength(getMember_delivery_province(), 32, "省份");
	}

	public String getMember_delivery_city() {
		return getStr(COLUMN_MEMBER_DELIVERY_CITY);
	}

	public void setMember_delivery_city(String member_delivery_city) {
		set(COLUMN_MEMBER_DELIVERY_CITY, member_delivery_city);
	}

	public void checkMember_delivery_city() {
		Utility.checkStringLength(getMember_delivery_city(), 32, "城市");
	}

	public String getMember_delivery_area() {
		return getStr(COLUMN_MEMBER_DELIVERY_AREA);
	}

	public void setMember_delivery_area(String member_delivery_area) {
		set(COLUMN_MEMBER_DELIVERY_AREA, member_delivery_area);
	}

	public void checkMember_delivery_area() {
		Utility.checkStringLength(getMember_delivery_area(), 32, "地区");
	}

	public String getMember_delivery_address() {
		return getStr(COLUMN_MEMBER_DELIVERY_ADDRESS);
	}

	public void setMember_delivery_address(String member_delivery_address) {
		set(COLUMN_MEMBER_DELIVERY_ADDRESS, member_delivery_address);
	}

	public void checkMember_delivery_address() {
		Utility.checkStringLength(getMember_delivery_address(), 1, 250, "详细地址");
	}

	public String getMember_delivery_zip() {
		return getStr(COLUMN_MEMBER_DELIVERY_ZIP);
	}

	public void setMember_delivery_zip(String member_delivery_zip) {
		set(COLUMN_MEMBER_DELIVERY_ZIP, member_delivery_zip);
	}

	public void checkMember_delivery_zip() {
		Utility.checkStringLength(getMember_delivery_zip(), 0, 6, "邮政编码");
	}

}
