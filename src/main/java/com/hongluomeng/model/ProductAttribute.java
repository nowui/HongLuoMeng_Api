package com.hongluomeng.model;

import com.jfinal.plugin.activerecord.Model;

public class ProductAttribute extends Model<ProductAttribute> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_PRODUCT_ATTRIBUTE = "table_product_attribute";
	public static final String KEY_PRODUCT_ID = "product_id";
	public static final String KEY_ATTRIBUTE_ID = "attribute_id";
	public static final String KEY_ATTRIBUTE_VALUE = "attribute_value";

	public String getProduct_id() {
		return getStr(KEY_PRODUCT_ID);
	}

	public void setProduct_id(String product_id) {
		set(KEY_PRODUCT_ID, product_id);
	}

	public String getAttribute_id() {
		return getStr(KEY_ATTRIBUTE_ID);
	}

	public void setAttribute_id(String attribute_id) {
		set(KEY_ATTRIBUTE_ID, attribute_id);
	}

	public String getAttribute_value() {
		return getStr(KEY_ATTRIBUTE_VALUE);
	}

	public void setAttribute_value(String attribute_value) {
		set(KEY_ATTRIBUTE_VALUE, attribute_value);
	}

}
