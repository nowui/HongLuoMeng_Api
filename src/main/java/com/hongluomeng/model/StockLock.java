package com.hongluomeng.model;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Model;

public class StockLock extends Model<StockLock> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_STOCK_LOCK = "table_stock_lock";
	public static final String KEY_ORDER_ID = "order_id";
	public static final String KEY_PRODUCT_SKU_ID = "product_sku_id";
	public static final String KEY_PRODUCT_STOCK = "product_stock";
	public static final String KEY_STOCK_LOCK_CREATE_USER_ID = "stock_lock_create_user_id";
	public static final String KEY_STOCK_LOCK_CREATE_TIME = "stock_lock_create_time";
	public static final String KEY_STOCK_LOCK_UPDATE_USER_ID = "stock_lock_update_user_id";
	public static final String KEY_STOCK_LOCK_UPDATE_TIME = "stock_lock_update_time";
	public static final String KEY_STOCK_LOCK_STATUS = "stock_lock_status";

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

	public Integer getProduct_stock() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_STOCK));
	}

	public void setProduct_stock(Integer product_stock) {
		set(KEY_PRODUCT_STOCK, product_stock);
	}

	public void setStock_lock_create_user_id(String stock_lock_create_user_id) {
		set(KEY_STOCK_LOCK_CREATE_USER_ID, stock_lock_create_user_id);
	}

	public void setStock_lock_create_time(Date stock_lock_create_time) {
		set(KEY_STOCK_LOCK_CREATE_TIME, stock_lock_create_time);
	}

	public void setStock_lock_update_user_id(String stock_lock_update_user_id) {
		set(KEY_STOCK_LOCK_UPDATE_USER_ID, stock_lock_update_user_id);
	}

	public void setStock_lock_update_time(Date stock_lock_update_time) {
		set(KEY_STOCK_LOCK_UPDATE_TIME, stock_lock_update_time);
	}

	public Boolean getStock_lock_status() {
		return getBoolean(KEY_STOCK_LOCK_STATUS);
	}

	public void setStock_lock_status(Boolean stock_lock_status) {
		set(KEY_STOCK_LOCK_STATUS, stock_lock_status);
	}

	public List<String> getProductSkuIdList() {
		return productSkuIdList;
	}

	public void setProductSkuIdList(List<String> productSkuIdList) {
		this.productSkuIdList = productSkuIdList;
	}

}
