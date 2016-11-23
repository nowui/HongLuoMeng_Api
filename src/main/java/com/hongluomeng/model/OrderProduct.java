package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class OrderProduct extends Model<OrderProduct> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_ORDER_PRODUCT = "table_order_product";
	public static final String KEY_ORDER_ID = "order_id";
	public static final String KEY_PRODUCT_ID = "product_id";
	public static final String KEY_CATEGORY_ID = "category_id";
	public static final String KEY_CATEGORY_NAME = "category_name";
	public static final String KEY_BRAND_ID = "brand_id";
	public static final String KEY_BRAND_NAME = "brand_name";
	public static final String KEY_PRODUCT_NAME = "product_name";
	public static final String KEY_PRODUCT_IMAGE = "product_image";
	public static final String KEY_PRODUCT_IS_NEW = "product_is_new";
	public static final String KEY_PRODUCT_IS_RECOMMEND = "product_is_recommend";
	public static final String KEY_PRODUCT_IS_BARGAIN = "product_is_bargain";
	public static final String KEY_PRODUCT_IS_HOT = "product_is_hot";
	public static final String KEY_PRODUCT_IS_SELL_OUT = "product_is_sell_out";
	public static final String KEY_PRODUCT_IS_SALE = "product_is_sale";
	public static final String KEY_PRODUCT_STATUS = "product_status";
	public static final String KEY_PRODUCT_CONTENT = "product_content";
	public static final String KEY_PRODUCT_SKU_VALUE = "product_sku_value";
	public static final String KEY_PRODUCT_SKU_ID = "product_sku_id";
	public static final String KEY_PRODUCT_ATTRIBUTE_VALUE = "product_attribute_value";
	public static final String KEY_PRODUCT_PRICE = "product_price";
	public static final String KEY_MEMBER_LEVEL_PRICE = "member_level_price";
	public static final String KEY_PRODUCT_TRADE_PRICE = "product_trade_price";
	public static final String KEY_PRODUCT_TRADE_AMOUNT = "product_trade_amount";
	public static final String KEY_ORDER_PRODUCT_CREATE_USER_ID = "order_product_create_user_id";
	public static final String KEY_ORDER_PRODUCT_CREATE_TIME = "order_product_create_time";
	public static final String KEY_ORDER_PRODUCT_UPDATE_USER_ID = "order_product_update_user_id";
	public static final String KEY_ORDER_PRODUCT_UPDATE_TIME = "order_product_update_time";
	public static final String KEY_ORDER_PRODUCT_STATUS = "order_product_status";

	public String getOrder_id() {
		return getStr(KEY_ORDER_ID);
	}

	public void setOrder_id(String order_id) {
		set(KEY_ORDER_ID, order_id);
	}

	public String getProduct_id() {
		return getStr(KEY_PRODUCT_ID);
	}

	public void setProduct_id(String product_id) {
		set(KEY_PRODUCT_ID, product_id);
	}

	public String getCategory_id() {
		return getStr(KEY_CATEGORY_ID);
	}

	public void setCategory_id(String category_id) {
		set(KEY_CATEGORY_ID, category_id);
	}

	public String getCategory_name() {
		return getStr(KEY_CATEGORY_NAME);
	}

	public void setCategory_name(String category_name) {
		set(KEY_CATEGORY_NAME, category_name);
	}

	public String getBrand_id() {
		return getStr(KEY_BRAND_ID);
	}

	public void setBrand_id(String brand_id) {
		set(KEY_BRAND_ID, brand_id);
	}

	public String getBrand_name() {
		return getStr(KEY_BRAND_NAME);
	}

	public void setBrand_name(String brand_name) {
		set(KEY_BRAND_NAME, brand_name);
	}

	public String getProduct_name() {
		return getStr(KEY_PRODUCT_NAME);
	}

	public void setProduct_name(String product_name) {
		set(KEY_PRODUCT_NAME, product_name);
	}

	public JSONArray getProduct_image() {
		return JSONArray.parseArray(getStr(KEY_PRODUCT_IMAGE));
	}

	public void setProduct_image(String product_image) {
		set(KEY_PRODUCT_IMAGE, product_image);
	}

	public Boolean getProduct_is_new() {
		return getBoolean(KEY_PRODUCT_IS_NEW);
	}

	public void setProduct_is_new(Boolean product_is_new) {
		set(KEY_PRODUCT_IS_NEW, product_is_new);
	}

	public Boolean getProduct_is_recommend() {
		return getBoolean(KEY_PRODUCT_IS_RECOMMEND);
	}

	public void setProduct_is_recommend(Boolean product_is_recommend) {
		set(KEY_PRODUCT_IS_RECOMMEND, product_is_recommend);
	}

	public Boolean getProduct_is_bargain() {
		return getBoolean(KEY_PRODUCT_IS_BARGAIN);
	}

	public void setProduct_is_bargain(Boolean product_is_bargain) {
		set(KEY_PRODUCT_IS_BARGAIN, product_is_bargain);
	}

	public Boolean getProduct_is_hot() {
		return getBoolean(KEY_PRODUCT_IS_HOT);
	}

	public void setProduct_is_hot(Boolean product_is_hot) {
		set(KEY_PRODUCT_IS_HOT, product_is_hot);
	}

	public Boolean getProduct_is_sell_out() {
		return getBoolean(KEY_PRODUCT_IS_SELL_OUT);
	}

	public void setProduct_is_sell_out(Boolean product_is_sell_out) {
		set(KEY_PRODUCT_IS_SELL_OUT, product_is_sell_out);
	}

	public Boolean getProduct_is_sale() {
		return getBoolean(KEY_PRODUCT_IS_SALE);
	}

	public void setProduct_is_sale(Boolean product_is_sale) {
		set(KEY_PRODUCT_IS_SALE, product_is_sale);
	}

	public Boolean getProduct_status() {
		return getBoolean(KEY_PRODUCT_STATUS);
	}

	public void setProduct_status(Boolean product_status) {
		set(KEY_PRODUCT_STATUS, product_status);
	}

	public String getProduct_content() {
		return getStr(KEY_PRODUCT_CONTENT);
	}

	public void setProduct_content(String product_content) {
		set(KEY_PRODUCT_CONTENT, product_content);
	}

	public JSONArray getProduct_sku_value() {
		return JSONArray.parseArray(getStr(KEY_PRODUCT_SKU_VALUE));
	}

	public void setProduct_sku_value(String product_sku_value) {
		if(Utility.isNullOrEmpty(product_sku_value)) {
			product_sku_value = "[]";
		}
		set(KEY_PRODUCT_SKU_VALUE, product_sku_value);
	}

	public String getProduct_sku_id() {
		return getStr(KEY_PRODUCT_SKU_ID);
	}

	public void setProduct_sku_id(String product_sku_id) {
		set(KEY_PRODUCT_SKU_ID, product_sku_id);
	}

	public JSONArray getProduct_attribute_value() {
		return JSONArray.parseArray(getStr(KEY_PRODUCT_ATTRIBUTE_VALUE));
	}

	public void setProduct_attribute_value(String product_attribute_value) {
		set(KEY_PRODUCT_ATTRIBUTE_VALUE, product_attribute_value);
	}

	public BigDecimal getProduct_price() {
		return getBigDecimal(KEY_PRODUCT_PRICE);
	}

	public void setProduct_price(BigDecimal product_price) {
		set(KEY_PRODUCT_PRICE, product_price);
	}

	public JSONArray getMember_level_price() {
		return JSONArray.parseArray(getStr(KEY_MEMBER_LEVEL_PRICE));
	}

	public void setMember_level_price(String member_level_price) {
		set(KEY_MEMBER_LEVEL_PRICE, member_level_price);
	}

	public BigDecimal getProduct_trade_price() {
		return getBigDecimal(KEY_PRODUCT_TRADE_PRICE);
	}

	public void setProduct_trade_price(BigDecimal product_trade_price) {
		set(KEY_PRODUCT_TRADE_PRICE, product_trade_price);
	}

	public Integer getProduct_trade_amount() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_TRADE_AMOUNT));
	}

	public void setProduct_trade_amount(Integer product_trade_amount) {
		set(KEY_PRODUCT_TRADE_AMOUNT, product_trade_amount);
	}

	public void setOrder_product_create_user_id(String order_product_create_user_id) {
		set(KEY_ORDER_PRODUCT_CREATE_USER_ID, order_product_create_user_id);
	}

	public void setOrder_product_create_time(Date order_product_create_time) {
		set(KEY_ORDER_PRODUCT_CREATE_TIME, order_product_create_time);
	}

	public void setOrder_product_update_user_id(String order_product_update_user_id) {
		set(KEY_ORDER_PRODUCT_UPDATE_USER_ID, order_product_update_user_id);
	}

	public void setOrder_product_update_time(Date order_product_update_time) {
		set(KEY_ORDER_PRODUCT_UPDATE_TIME, order_product_update_time);
	}

	public Boolean getOrder_product_status() {
		return getBoolean(KEY_ORDER_PRODUCT_STATUS);
	}

	public void setOrder_product_status(Boolean order_product_status) {
		set(KEY_ORDER_PRODUCT_STATUS, order_product_status);
	}

}
