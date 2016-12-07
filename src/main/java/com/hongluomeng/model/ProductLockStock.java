package com.hongluomeng.model;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.Utility;

public class ProductLockStock extends Base<ProductLockStock> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_PRODUCT_LOCK_STOCK = "table_product_lock_stock";
	public static final String COLUMN_ORDER_NO = "order_no";
	public static final String COLUMN_PRODUCT_SKU_ID = "product_sku_id";
	public static final String COLUMN_PRODUCT_LOCK_STOCK = "product_lock_stock";
	public static final String COLUMN_PRODUCT_LOCK_STOCK_EXPIRE_TIME = "product_lock_stock_expire_time";

	private List<String> productSkuIdList;

	public String getOrder_no() {
		return getStr(COLUMN_ORDER_NO);
	}

	public void setOrder_no(String order_no) {
		set(COLUMN_ORDER_NO, order_no);
	}

	public String getProduct_sku_id() {
		return getStr(COLUMN_PRODUCT_SKU_ID);
	}

	public void setProduct_sku_id(String product_sku_id) {
		set(COLUMN_PRODUCT_SKU_ID, product_sku_id);
	}

	public Integer getProduct_lock_stock() {
		return Utility.getIntegerValue(get(COLUMN_PRODUCT_LOCK_STOCK));
	}

	public void setProduct_lock_stock(Integer product_lock_stock) {
		set(COLUMN_PRODUCT_LOCK_STOCK, product_lock_stock);
	}

	public void setProduct_lock_stock_expire_time(Date product_lock_stock_expire_time) {
		set(COLUMN_PRODUCT_LOCK_STOCK_EXPIRE_TIME, product_lock_stock_expire_time);
	}

	public List<String> getProductSkuIdList() {
		return productSkuIdList;
	}

	public void setProductSkuIdList(List<String> productSkuIdList) {
		this.productSkuIdList = productSkuIdList;
	}

}
