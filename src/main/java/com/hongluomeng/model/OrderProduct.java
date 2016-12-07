package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Utility;

public class OrderProduct extends Base<OrderProduct> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_ORDER_PRODUCT = "table_order_product";
	public static final String COLUMN_ORDER_ID = "order_id";
	public static final String COLUMN_PRODUCT_ID = "product_id";
	public static final String COLUMN_CATEGORY_ID = "category_id";
	public static final String COLUMN_CATEGORY_NAME = "category_name";
	public static final String COLUMN_BRAND_ID = "brand_id";
	public static final String COLUMN_BRAND_NAME = "brand_name";
	public static final String COLUMN_PRODUCT_NAME = "product_name";
	public static final String COLUMN_PRODUCT_IMAGE = "product_image";
	public static final String COLUMN_PRODUCT_IS_NEW = "product_is_new";
	public static final String COLUMN_PRODUCT_IS_RECOMMEND = "product_is_recommend";
	public static final String COLUMN_PRODUCT_IS_BARGAIN = "product_is_bargain";
	public static final String COLUMN_PRODUCT_IS_HOT = "product_is_hot";
	public static final String COLUMN_PRODUCT_IS_SELL_OUT = "product_is_sell_out";
	public static final String COLUMN_PRODUCT_IS_SALE = "product_is_sale";
	public static final String COLUMN_PRODUCT_STATUS = "product_status";
	public static final String COLUMN_PRODUCT_CONTENT = "product_content";
	public static final String COLUMN_PRODUCT_SKU_VALUE = "product_sku_value";
	public static final String COLUMN_PRODUCT_SKU_ID = "product_sku_id";
	public static final String COLUMN_PRODUCT_ATTRIBUTE_VALUE = "product_attribute_value";
	public static final String COLUMN_PRODUCT_PRICE = "product_price";
	public static final String COLUMN_MEMBER_LEVEL_PRICE = "member_level_price";
	public static final String KEY_PRODUCT_PAY_PRICE = "product_pay_price";
	public static final String KEY_PRODUCT_PAY_AMOUNT = "product_pay_amount";

	private List<String> orderIdList;

	public String getOrder_id() {
		return getStr(COLUMN_ORDER_ID);
	}

	public void setOrder_id(String order_id) {
		set(COLUMN_ORDER_ID, order_id);
	}

	public void checkOrder_id() {
		Utility.checkStringLength(getOrder_id(), 32, "订单编号");
	}

	public String getProduct_id() {
		return getStr(COLUMN_PRODUCT_ID);
	}

	public void setProduct_id(String product_id) {
		set(COLUMN_PRODUCT_ID, product_id);
	}

	public String getCategory_id() {
		return getStr(COLUMN_CATEGORY_ID);
	}

	public void setCategory_id(String category_id) {
		set(COLUMN_CATEGORY_ID, category_id);
	}

	public String getCategory_name() {
		return getStr(COLUMN_CATEGORY_NAME);
	}

	public void setCategory_name(String category_name) {
		set(COLUMN_CATEGORY_NAME, category_name);
	}

	public String getBrand_id() {
		return getStr(COLUMN_BRAND_ID);
	}

	public void setBrand_id(String brand_id) {
		set(COLUMN_BRAND_ID, brand_id);
	}

	public String getBrand_name() {
		return getStr(COLUMN_BRAND_NAME);
	}

	public void setBrand_name(String brand_name) {
		set(COLUMN_BRAND_NAME, brand_name);
	}

	public String getProduct_name() {
		return getStr(COLUMN_PRODUCT_NAME);
	}

	public void setProduct_name(String product_name) {
		set(COLUMN_PRODUCT_NAME, product_name);
	}

	public JSONArray getProduct_image() {
		return JSONArray.parseArray(getStr(COLUMN_PRODUCT_IMAGE));
	}

	public void setProduct_image(String product_image) {
		set(COLUMN_PRODUCT_IMAGE, product_image);
	}

	public Boolean getProduct_is_new() {
		return getBoolean(COLUMN_PRODUCT_IS_NEW);
	}

	public void setProduct_is_new(Boolean product_is_new) {
		set(COLUMN_PRODUCT_IS_NEW, product_is_new);
	}

	public Boolean getProduct_is_recommend() {
		return getBoolean(COLUMN_PRODUCT_IS_RECOMMEND);
	}

	public void setProduct_is_recommend(Boolean product_is_recommend) {
		set(COLUMN_PRODUCT_IS_RECOMMEND, product_is_recommend);
	}

	public Boolean getProduct_is_bargain() {
		return getBoolean(COLUMN_PRODUCT_IS_BARGAIN);
	}

	public void setProduct_is_bargain(Boolean product_is_bargain) {
		set(COLUMN_PRODUCT_IS_BARGAIN, product_is_bargain);
	}

	public Boolean getProduct_is_hot() {
		return getBoolean(COLUMN_PRODUCT_IS_HOT);
	}

	public void setProduct_is_hot(Boolean product_is_hot) {
		set(COLUMN_PRODUCT_IS_HOT, product_is_hot);
	}

	public Boolean getProduct_is_sell_out() {
		return getBoolean(COLUMN_PRODUCT_IS_SELL_OUT);
	}

	public void setProduct_is_sell_out(Boolean product_is_sell_out) {
		set(COLUMN_PRODUCT_IS_SELL_OUT, product_is_sell_out);
	}

	public Boolean getProduct_is_sale() {
		return getBoolean(COLUMN_PRODUCT_IS_SALE);
	}

	public void setProduct_is_sale(Boolean product_is_sale) {
		set(COLUMN_PRODUCT_IS_SALE, product_is_sale);
	}

	public Boolean getProduct_status() {
		return getBoolean(COLUMN_PRODUCT_STATUS);
	}

	public void setProduct_status(Boolean product_status) {
		set(COLUMN_PRODUCT_STATUS, product_status);
	}

	public String getProduct_content() {
		return getStr(COLUMN_PRODUCT_CONTENT);
	}

	public void setProduct_content(String product_content) {
		set(COLUMN_PRODUCT_CONTENT, product_content);
	}

	public JSONArray getProduct_sku_value() {
		return JSONArray.parseArray(getStr(COLUMN_PRODUCT_SKU_VALUE));
	}

	public void setProduct_sku_value(String product_sku_value) {
		if(Utility.isNullOrEmpty(product_sku_value)) {
			product_sku_value = "[]";
		}
		set(COLUMN_PRODUCT_SKU_VALUE, product_sku_value);
	}

	public String getProduct_sku_id() {
		return getStr(COLUMN_PRODUCT_SKU_ID);
	}

	public void setProduct_sku_id(String product_sku_id) {
		set(COLUMN_PRODUCT_SKU_ID, product_sku_id);
	}

	public JSONArray getProduct_attribute_value() {
		return JSONArray.parseArray(getStr(COLUMN_PRODUCT_ATTRIBUTE_VALUE));
	}

	public void setProduct_attribute_value(String product_attribute_value) {
		set(COLUMN_PRODUCT_ATTRIBUTE_VALUE, product_attribute_value);
	}

	public BigDecimal getProduct_price() {
		return getBigDecimal(COLUMN_PRODUCT_PRICE);
	}

	public void setProduct_price(BigDecimal product_price) {
		set(COLUMN_PRODUCT_PRICE, product_price);
	}

	public JSONArray getMember_level_price() {
		return JSONArray.parseArray(getStr(COLUMN_MEMBER_LEVEL_PRICE));
	}

	public void setMember_level_price(String member_level_price) {
		set(COLUMN_MEMBER_LEVEL_PRICE, member_level_price);
	}

	public BigDecimal getProduct_pay_price() {
		return getBigDecimal(KEY_PRODUCT_PAY_PRICE);
	}

	public void setProduct_pay_price(BigDecimal product_pay_price) {
		set(KEY_PRODUCT_PAY_PRICE, product_pay_price);
	}

	public Integer getProduct_pay_amount() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_PAY_AMOUNT));
	}

	public void setProduct_pay_amount(Integer product_pay_amount) {
		set(KEY_PRODUCT_PAY_AMOUNT, product_pay_amount);
	}

	public List<String> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<String> orderIdList) {
		this.orderIdList = orderIdList;
	}

	public void checkOrderIdList() {
		if (Utility.isNullOrEmpty(getOrderIdList())) {
			throw new RuntimeException("OrderIdList不能为空");
		} else {
			if(getOrderIdList().size() == 0) {
				throw new RuntimeException("OrderIdList不能为空");
			}
		}
	}
}
