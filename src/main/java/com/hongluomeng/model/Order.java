package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class Order extends Model<Order> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_ORDER = "table_order";
	public static final String KEY_ORDER_ID = "order_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_ORDER_NO = "order_no";
	public static final String KEY_ORDER_MESSAGE = "order_message";
	public static final String KEY_ORDER_DELIVERY_NAME = "order_delivery_name";
	public static final String KEY_ORDER_DELIVERY_PHONE = "order_delivery_phone";
	public static final String KEY_ORDER_DELIVERY_PROVINCE = "order_delivery_province";
	public static final String KEY_ORDER_DELIVERY_CITY = "order_delivery_city";
	public static final String KEY_ORDER_DELIVERY_AREA = "order_delivery_area";
	public static final String KEY_ORDER_DELIVERY_ADDRESS = "order_delivery_address";
	public static final String KEY_ORDER_DELIVERY_ZIP = "order_delivery_zip";
	public static final String KEY_ORDER_PAY_TYPE = "order_pay_type";
	public static final String KEY_ORDER_IS_PAY = "order_is_pay";
	public static final String KEY_ORDER_TRADE_NO = "order_trade_no";
	public static final String KEY_ORDER_TRADE_ACCOUNT = "order_trade_account";
	public static final String KEY_ORDER_TRADE_PRICE = "order_trade_price";
	public static final String KEY_ORDER_TRADE_TIME = "order_trade_time";
	public static final String KEY_MEMBER_LEVEL_ID = "member_level_id";
	public static final String KEY_MEMBER_LEVEL_NAME = "member_level_name";
	public static final String KEY_MEMBER_LEVEL_VALUE = "member_level_value";
	public static final String KEY_ORDER_CREATE_USER_ID = "order_create_user_id";
	public static final String KEY_ORDER_CREATE_TIME = "order_create_time";
	public static final String KEY_ORDER_UPDATE_USER_ID = "order_update_user_id";
	public static final String KEY_ORDER_UPDATE_TIME = "order_update_time";
	public static final String KEY_ORDER_FLOW_STATUS = "order_flow_status";
	public static final String KEY_ORDER_STATUS = "order_status";
	public static final String KEY_SIGN = "sign";

	private List<Cart> cartList;

	public String getOrder_id() {
		return getStr(KEY_ORDER_ID);
	}

	public void setOrder_id(String order_id) {
		set(KEY_ORDER_ID, order_id);
	}

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public String getOrder_no() {
		return getStr(KEY_ORDER_NO);
	}

	public void setOrder_no(String order_no) {
		set(KEY_ORDER_NO, order_no);
	}

	public String getOrder_message() {
		return getStr(KEY_ORDER_MESSAGE);
	}

	public void setOrder_message(String order_message) {
		set(KEY_ORDER_MESSAGE, order_message);
	}

	public String getOrder_delivery_name() {
		return getStr(KEY_ORDER_DELIVERY_NAME);
	}

	public void setOrder_delivery_name(String order_delivery_name) {
		set(KEY_ORDER_DELIVERY_NAME, order_delivery_name);
	}

	public String getOrder_delivery_phone() {
		return getStr(KEY_ORDER_DELIVERY_PHONE);
	}

	public void setOrder_delivery_phone(String order_delivery_phone) {
		set(KEY_ORDER_DELIVERY_PHONE, order_delivery_phone);
	}

	public String getOrder_delivery_province() {
		return getStr(KEY_ORDER_DELIVERY_PROVINCE);
	}

	public void setOrder_delivery_province(String order_delivery_province) {
		set(KEY_ORDER_DELIVERY_PROVINCE, order_delivery_province);
	}

	public String getOrder_delivery_city() {
		return getStr(KEY_ORDER_DELIVERY_CITY);
	}

	public void setOrder_delivery_city(String order_delivery_city) {
		set(KEY_ORDER_DELIVERY_CITY, order_delivery_city);
	}

	public String getOrder_delivery_area() {
		return getStr(KEY_ORDER_DELIVERY_AREA);
	}

	public void setOrder_delivery_area(String order_delivery_area) {
		set(KEY_ORDER_DELIVERY_AREA, order_delivery_area);
	}

	public String getOrder_delivery_address() {
		return getStr(KEY_ORDER_DELIVERY_ADDRESS);
	}

	public void setOrder_delivery_address(String order_delivery_address) {
		set(KEY_ORDER_DELIVERY_ADDRESS, order_delivery_address);
	}

	public String getOrder_pay_type() {
		return getStr(KEY_ORDER_PAY_TYPE);
	}

	public void setOrder_pay_type(String order_pay_type) {
		set(KEY_ORDER_PAY_TYPE, order_pay_type);
	}

	public String getOrder_delivery_zip() {
		return getStr(KEY_ORDER_DELIVERY_ZIP);
	}

	public void setOrder_delivery_zip(String order_delivery_zip) {
		set(KEY_ORDER_DELIVERY_ZIP, order_delivery_zip);
	}

	public Boolean getOrder_is_pay() {
		return getBoolean(KEY_ORDER_IS_PAY);
	}

	public void setOrder_is_pay(Boolean order_is_pay) {
		set(KEY_ORDER_IS_PAY, order_is_pay);
	}

	public String getOrder_trade_no() {
		return getStr(KEY_ORDER_TRADE_NO);
	}

	public void setOrder_trade_no(String order_trade_no) {
		set(KEY_ORDER_TRADE_NO, order_trade_no);
	}

	public String getOrder_trade_account() {
		return getStr(KEY_ORDER_TRADE_ACCOUNT);
	}

	public void setOrder_trade_account(String order_trade_account) {
		set(KEY_ORDER_TRADE_ACCOUNT, order_trade_account);
	}

	public BigDecimal getOrder_trade_price() {
		return getBigDecimal(KEY_ORDER_TRADE_PRICE);
	}

	public void setOrder_trade_price(BigDecimal order_trade_price) {
		set(KEY_ORDER_TRADE_PRICE, order_trade_price);
	}

	public String getOrder_trade_time() {
		return getStr(KEY_ORDER_TRADE_TIME);
	}

	public void setOrder_trade_time(String order_trade_time) {
		set(KEY_ORDER_TRADE_TIME, order_trade_time);
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

	public void setMember_level_name(String member_level_name) {
		set(KEY_MEMBER_LEVEL_NAME, member_level_name);
	}

	public Integer getMember_level_value() {
		return Utility.getIntegerValue(get(KEY_MEMBER_LEVEL_VALUE));
	}

	public void setMember_level_value(Integer member_level_value) {
		set(KEY_MEMBER_LEVEL_VALUE, member_level_value);
	}

	public void setOrder_create_user_id(String order_create_user_id) {
		set(KEY_ORDER_CREATE_USER_ID, order_create_user_id);
	}

	public void setOrder_create_time(Date order_create_time) {
		set(KEY_ORDER_CREATE_TIME, order_create_time);
	}

	public void setOrder_update_user_id(String order_update_user_id) {
		set(KEY_ORDER_UPDATE_USER_ID, order_update_user_id);
	}

	public void setOrder_update_time(Date order_update_time) {
		set(KEY_ORDER_UPDATE_TIME, order_update_time);
	}

	public String getOrder_flow_status() {
		return getStr(KEY_ORDER_FLOW_STATUS);
	}

	public void setOrder_flow_status(String order_flow_status) {
		set(KEY_ORDER_FLOW_STATUS, order_flow_status);
	}

	public Boolean getOrder_status() {
		return getBoolean(KEY_ORDER_STATUS);
	}

	public void setOrder_status(Boolean order_status) {
		set(KEY_ORDER_STATUS, order_status);
	}

	public List<Cart> getCartList() {
		return cartList;
	}

	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}

}
