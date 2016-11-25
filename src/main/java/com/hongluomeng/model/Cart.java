package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class Cart extends Model<Cart> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_CART = "table_cart";
	public static final String KEY_CART_ID = "cart_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_PRODUCT_SKU_ID = "product_sku_id";
	public static final String KEY_PRODUCT_AMOUNT = "product_amount";
	public static final String KEY_CART_CREATE_USER_ID = "cart_create_user_id";
	public static final String KEY_CART_CREATE_TIME = "cart_create_time";
	public static final String KEY_CART_UPDATE_USER_ID = "cart_update_user_id";
	public static final String KEY_CART_UPDATE_TIME = "cart_update_time";
	public static final String KEY_CART_STATUS = "cart_status";

	private List<String> productSkuIdList;

	public String getCart_id() {
		return getStr(KEY_CART_ID);
	}

	public void setCart_id(String cart_id) {
		set(KEY_CART_ID, cart_id);
	}

	public void checkCart_id() {
		Utility.checkStringLength(getCart_id(), 32, "购物车编号");
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

	public String getProduct_sku_id() {
		return getStr(KEY_PRODUCT_SKU_ID);
	}

	public void setProduct_sku_id(String product_sku_id) {
		set(KEY_PRODUCT_SKU_ID, product_sku_id);
	}

	public void checkProduct_sku_id() {
		Utility.checkStringLength(getProduct_sku_id(), 32, "商品SKU编号");
	}

	public String getProduct_id() {
		return getStr(Product.KEY_PRODUCT_ID);
	}

	public void checkProduct_id() {
		Utility.checkStringLength(getProduct_id(), 32, "商品编号");
	}

	public String getProduct_name() {
		return getStr(Product.KEY_PRODUCT_NAME);
	}

	public Integer getProduct_amount() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_AMOUNT));
	}

	public void setProduct_amount(Integer product_amount) {
		set(KEY_PRODUCT_AMOUNT, product_amount);
	}

	public void checkProduct_amount() {
		Utility.checkIntegerLength(getProduct_amount(), 1, 7, "购物车商品数量");
	}

	public JSONArray getProduct_image() {
		return JSONArray.parseArray(getStr(Product.KEY_PRODUCT_IMAGE));
	}

	public JSONArray getProduct_attribute_value() {
		return JSONArray.parseArray(getStr(ProductSku.KEY_PRODUCT_ATTRIBUTE_VALUE));
	}

	public BigDecimal getProduct_price() {
		return getBigDecimal(ProductSku.KEY_PRODUCT_PRICE);
	}

	public void setCart_create_user_id(String cart_create_user_id) {
		set(KEY_CART_CREATE_USER_ID, cart_create_user_id);
	}

	public void setCart_create_time(Date cart_create_time) {
		set(KEY_CART_CREATE_TIME, cart_create_time);
	}

	public void setCart_update_user_id(String cart_update_user_id) {
		set(KEY_CART_UPDATE_USER_ID, cart_update_user_id);
	}

	public void setCart_update_time(Date cart_update_time) {
		set(KEY_CART_UPDATE_TIME, cart_update_time);
	}

	public Boolean getCart_status() {
		return getBoolean(KEY_CART_STATUS);
	}

	public void setCart_status(Boolean cart_status) {
		set(KEY_CART_STATUS, cart_status);
	}

	public List<String> getProductSkuIdList() {
		return productSkuIdList;
	}

	public void setProductSkuIdList(List<String> productSkuIdList) {
		this.productSkuIdList = productSkuIdList;
	}

}
