package com.hongluomeng.model;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class Cart extends Model<Cart> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_CART = "table_cart";
	public static final String KEY_CART_ID = "cart_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_PRODUCT_SKU_ID = "product_sku_id";
	public static final String KEY_PRODUCT_ID = "product_id";
	public static final String KEY_PRODUCT_NAME = "product_name";
	/*public static final String KEY_CATEGORY_ID = "category_id";
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
	public static final String KEY_PRODUCT_ATTRIBUTE_VALUE = "product_attribute_value";
	public static final String KEY_PRODUCT_PRICE = "product_price";
	public static final String KEY_MEMBER_LEVEL_PRICE = "member_level_price";
	public static final String KEY_PRODUCT_STOCK = "product_stock";
	public static final String KEY_PRODUCT_SKU_STATUS = "product_sku_status";*/
	public static final String KEY_CART_PRODUCT_AMOUNT = "cart_product_amount";
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

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public String getProduct_sku_id() {
		return getStr(KEY_PRODUCT_SKU_ID);
	}

	public void setProduct_sku_id(String product_sku_id) {
		set(KEY_PRODUCT_SKU_ID, product_sku_id);
	}

	public String getProduct_id() {
		return getStr(KEY_PRODUCT_ID);
	}

	public String getProduct_name() {
		return getStr(KEY_PRODUCT_NAME);
	}

	public Integer getCart_product_amount() {
		return Utility.getIntegerValue(get(KEY_CART_PRODUCT_AMOUNT));
	}

	public void setCart_product_amount(Integer cart_product_amount) {
		set(KEY_CART_PRODUCT_AMOUNT, cart_product_amount);
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
