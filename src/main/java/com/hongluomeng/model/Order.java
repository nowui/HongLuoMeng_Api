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
	public static final String KEY_ORDER_PRICE = "order_price";
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

	private String member_delivery_id;
	private List<Cart> cartList;

	public String getOrder_id() {
		return getStr(KEY_ORDER_ID);
	}

	public void setOrder_id(String order_id) {
		set(KEY_ORDER_ID, order_id);
	}

	public void checkOrder_id() {
		Utility.checkStringLength(getOrder_id(), 32, "订单编号");
	}

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public void checkUser_id() {
		Utility.checkStringLength(getUser_id(), 32, "用户编号");
	}

	public String getOrder_no() {
		return getStr(KEY_ORDER_NO);
	}

	public void setOrder_no(String order_no) {
		set(KEY_ORDER_NO, order_no);
	}

	public void checkOrder_no() {
		Utility.checkStringLength(getOrder_no(), 15, "订单号");
	}

	public BigDecimal getOrder_price() {
		return getBigDecimal(KEY_ORDER_PRICE);
	}

	public void setOrder_price(BigDecimal order_price) {
		set(KEY_ORDER_PRICE, order_price);
	}

	public void checkOrder_price() {
		Utility.checkDecimalLength(getOrder_price(), 11, 2, "订单价格");
	}

	public String getOrder_message() {
		return getStr(KEY_ORDER_MESSAGE);
	}

	public void setOrder_message(String order_message) {
		set(KEY_ORDER_MESSAGE, order_message);
	}

	public void checkOrder_message() {
		Utility.checkStringLength(getOrder_message(), 0, 150, "买家留言");
	}

	public String getOrder_delivery_name() {
		return getStr(KEY_ORDER_DELIVERY_NAME);
	}

	public void setOrder_delivery_name(String order_delivery_name) {
		set(KEY_ORDER_DELIVERY_NAME, order_delivery_name);
	}

	public void checkOrder_delivery_name() {
		Utility.checkStringLength(getOrder_delivery_name(), 0, 20, "收货人姓名");
	}

	public String getOrder_delivery_phone() {
		return getStr(KEY_ORDER_DELIVERY_PHONE);
	}

	public void setOrder_delivery_phone(String order_delivery_phone) {
		set(KEY_ORDER_DELIVERY_PHONE, order_delivery_phone);
	}

	public void checkOrder_delivery_phone() {
		Utility.checkStringLength(getOrder_delivery_phone(), 0, 20, "收货人电话");
	}

	public String getOrder_delivery_province() {
		return getStr(KEY_ORDER_DELIVERY_PROVINCE);
	}

	public void setOrder_delivery_province(String order_delivery_province) {
		set(KEY_ORDER_DELIVERY_PROVINCE, order_delivery_province);
	}

	public void checkOrder_delivery_province() {
		Utility.checkStringLength(getOrder_delivery_province(), 0, 32, "收货省份");
	}

	public String getOrder_delivery_city() {
		return getStr(KEY_ORDER_DELIVERY_CITY);
	}

	public void setOrder_delivery_city(String order_delivery_city) {
		set(KEY_ORDER_DELIVERY_CITY, order_delivery_city);
	}

	public void checkOrder_delivery_city() {
		Utility.checkStringLength(getOrder_delivery_city(), 0, 32, "收货城市");
	}

	public String getOrder_delivery_area() {
		return getStr(KEY_ORDER_DELIVERY_AREA);
	}

	public void setOrder_delivery_area(String order_delivery_area) {
		set(KEY_ORDER_DELIVERY_AREA, order_delivery_area);
	}

	public void checkOrder_delivery_area() {
		Utility.checkStringLength(getOrder_delivery_area(), 0, 32, "收货地区");
	}

	public String getOrder_delivery_address() {
		return getStr(KEY_ORDER_DELIVERY_ADDRESS);
	}

	public void setOrder_delivery_address(String order_delivery_address) {
		set(KEY_ORDER_DELIVERY_ADDRESS, order_delivery_address);
	}

	public void checkOrder_delivery_address() {
		Utility.checkStringLength(getOrder_delivery_address(), 0, 150, "收货详细地址");
	}

	public String getOrder_delivery_zip() {
		return getStr(KEY_ORDER_DELIVERY_ZIP);
	}

	public void setOrder_delivery_zip(String order_delivery_zip) {
		set(KEY_ORDER_DELIVERY_ZIP, order_delivery_zip);
	}

	public void checkOrder_delivery_zip() {
		Utility.checkStringLength(getOrder_delivery_zip(), 0, 6, "收货邮编");
	}

	public String getOrder_pay_type() {
		return getStr(KEY_ORDER_PAY_TYPE);
	}

	public void setOrder_pay_type(String order_pay_type) {
		set(KEY_ORDER_PAY_TYPE, order_pay_type);
	}

	public void checkOrder_pay_type() {
		Utility.checkStringLength(getOrder_pay_type(), 0, 10, "支付类型");
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

	public void checkOrder_trade_no() {
		Utility.checkStringLength(getOrder_trade_no(), 0, 20, "支付交易号");
	}

	public String getOrder_trade_account() {
		return getStr(KEY_ORDER_TRADE_ACCOUNT);
	}

	public void setOrder_trade_account(String order_trade_account) {
		set(KEY_ORDER_TRADE_ACCOUNT, order_trade_account);
	}

	public void checkOrder_trade_account() {
		Utility.checkStringLength(getOrder_trade_account(), 0, 20, "支付帐号");
	}

	public BigDecimal getOrder_trade_price() {
		return getBigDecimal(KEY_ORDER_TRADE_PRICE);
	}

	public void setOrder_trade_price(BigDecimal order_trade_price) {
		set(KEY_ORDER_TRADE_PRICE, order_trade_price);
	}

	public void checkOrder_trade_price() {
		Utility.checkDecimalLength(getOrder_trade_price(), 11, 2, "支付价格");
	}

	public String getOrder_trade_time() {
		return getStr(KEY_ORDER_TRADE_TIME);
	}

	public void setOrder_trade_time(String order_trade_time) {
		set(KEY_ORDER_TRADE_TIME, order_trade_time);
	}

	public void checkOrder_trade_time() {
		Utility.checkStringLength(getOrder_trade_time(), 0, 19, "支付时间");
	}

	public String getMember_level_id() {
		return getStr(KEY_MEMBER_LEVEL_ID);
	}

	public void setMember_level_id(String member_level_id) {
		set(KEY_MEMBER_LEVEL_ID, member_level_id);
	}

	public void checkMember_level_id() {
		Utility.checkStringLength(getMember_level_id(), 32, "会员等级编号");
	}

	public String getMember_level_name() {
		return getStr(KEY_MEMBER_LEVEL_NAME);
	}

	public void setMember_level_name(String member_level_name) {
		set(KEY_MEMBER_LEVEL_NAME, member_level_name);
	}

	public void checkMember_level_name() {
		Utility.checkStringLength(getMember_level_name(), 0, 20, "会员等级名称");
	}

	public Integer getMember_level_value() {
		return Utility.getIntegerValue(get(KEY_MEMBER_LEVEL_VALUE));
	}

	public void setMember_level_value(Integer member_level_value) {
		set(KEY_MEMBER_LEVEL_VALUE, member_level_value);
	}

	public void checkMember_level_value() {
		Utility.checkIntegerLength(getMember_level_value(), 1, 11, "会员等级粉丝数");
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

	public void checkOrder_flow_status() {
		Utility.checkStringLength(getOrder_flow_status(), 1, 10, "订单流程状态");
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

	public void checkCartList() {
		if (Utility.isNullOrEmpty(getCartList())) {
			throw new RuntimeException("购物车不能为空");
		} else {
			List<Cart> cartList = getCartList();

			if (cartList.size() == 0) {
				throw new RuntimeException("购物车不能为空");
			}

			Boolean isError = false;

			for (Cart cart : cartList) {
				if (Utility.isNullOrEmpty(cart.getProduct_sku_id())) {
					isError = true;
				}

				if (Utility.isNullOrEmpty(cart.getProduct_amount())) {
					isError = true;
				}
			}

			if (isError) {
				throw new RuntimeException("商品参数格式错误");
			}
		}
	}

	public String getMember_delivery_id() {
		return member_delivery_id;
	}

	public void setMember_delivery_id(String member_delivery_id) {
		this.member_delivery_id = member_delivery_id;
	}

	public void checkMember_delivery_id() {
		Utility.checkStringLength(getMember_delivery_id(), 32, "会员快递信息编号");
	}
}
