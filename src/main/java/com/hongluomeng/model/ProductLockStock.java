package com.hongluomeng.model;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class ProductLockStock extends Model<ProductLockStock> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_PRODUCT_LOCK_STOCK = "table_product_lock_stock";
	public static final String KEY_ORDER_ID = "order_id";
	public static final String KEY_PRODUCT_SKU_ID = "product_sku_id";
	public static final String KEY_PRODUCT_LOCK_STOCK = "product_lock_stock";
	public static final String KEY_PRODUCT_LOCK_STOCK_CREATE_USER_ID = "product_lock_stock_create_user_id";
	public static final String KEY_PRODUCT_LOCK_STOCK_CREATE_TIME = "product_lock_stock_create_time";
	public static final String KEY_PRODUCT_LOCK_STOCK_UPDATE_USER_ID = "product_lock_stock_update_user_id";
	public static final String KEY_PRODUCT_LOCK_STOCK_UPDATE_TIME = "product_lock_stock_update_time";
	public static final String KEY_PRODUCT_LOCK_STOCK_EXPIRE_TIME = "product_lock_stock_expire_time";
	public static final String KEY_PRODUCT_LOCK_STOCK_STATUS = "product_lock_stock_status";

	private List<String> productSkuIdList;

	public String getOrder_id() {
		return getStr(KEY_ORDER_ID);
	}

	public void setOrder_id(String order_id) {
		set(KEY_ORDER_ID, order_id);
	}

	public String getProduct_sku_id() {
		return getStr(KEY_PRODUCT_SKU_ID);
	}

	public void setProduct_sku_id(String product_sku_id) {
		set(KEY_PRODUCT_SKU_ID, product_sku_id);
	}

	public Integer getProduct_lock_stock() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_LOCK_STOCK));
	}

	public void setProduct_lock_stock(Integer product_lock_stock) {
		set(KEY_PRODUCT_LOCK_STOCK, product_lock_stock);
	}

	public void setProduct_lock_stock_create_user_id(String product_lock_stock_create_user_id) {
		set(KEY_PRODUCT_LOCK_STOCK_CREATE_USER_ID, product_lock_stock_create_user_id);
	}

	public void setProduct_lock_stock_create_time(Date product_lock_stock_create_time) {
		set(KEY_PRODUCT_LOCK_STOCK_CREATE_TIME, product_lock_stock_create_time);
	}

	public void setProduct_lock_stock_update_user_id(String product_lock_stock_update_user_id) {
		set(KEY_PRODUCT_LOCK_STOCK_UPDATE_USER_ID, product_lock_stock_update_user_id);
	}

	public void setProduct_lock_stock_update_time(Date product_lock_stock_update_time) {
		set(KEY_PRODUCT_LOCK_STOCK_UPDATE_TIME, product_lock_stock_update_time);
	}

	public void setProduct_lock_stock_expire_time(Date product_lock_stock_expire_time) {
		set(KEY_PRODUCT_LOCK_STOCK_EXPIRE_TIME, product_lock_stock_expire_time);
	}

	public Boolean getProduct_lock_stock_status() {
		return getBoolean(KEY_PRODUCT_LOCK_STOCK_STATUS);
	}

	public void setProduct_lock_stock_status(Boolean product_lock_stock_status) {
		set(KEY_PRODUCT_LOCK_STOCK_STATUS, product_lock_stock_status);
	}

	public List<String> getProductSkuIdList() {
		return productSkuIdList;
	}

	public void setProductSkuIdList(List<String> productSkuIdList) {
		this.productSkuIdList = productSkuIdList;
	}

}
