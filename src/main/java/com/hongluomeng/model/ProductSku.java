package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Utility;

public class ProductSku extends Base<ProductSku> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_PRODUCT_SKU = "table_product_sku";
	public static final String COLUMN_PRODUCT_SKU_ID = "product_sku_id";
	public static final String COLUMN_PRODUCT_ID = "product_id";
	public static final String COLUMN_PRODUCT_ATTRIBUTE_VALUE = "product_attribute_value";
	public static final String COLUMN_PRODUCT_PRICE = "product_price";
	public static final String COLUMN_MEMBER_LEVEL_PRICE = "member_level_price";
	public static final String COLUMN_PRODUCT_STOCK = "product_stock";

	private List<String> productSkuIdList;
	private Integer product_amount;

	public String getProduct_sku_id() {
		return getStr(COLUMN_PRODUCT_SKU_ID);
	}

	public void setProduct_sku_id(String product_sku_id) {
		set(COLUMN_PRODUCT_SKU_ID, product_sku_id);
	}

	public void checkProduct_sku_id() {
		Utility.checkStringLength(getProduct_sku_id(), 32, "商品SKU编号");
	}

	public String getProduct_id() {
		return getStr(COLUMN_PRODUCT_ID);
	}

	public void setProduct_id(String product_id) {
		set(COLUMN_PRODUCT_ID, product_id);
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

	public Integer getProduct_stock() {
		return Utility.getIntegerValue(get(COLUMN_PRODUCT_STOCK));
	}

	public void setProduct_stock(Integer product_stock) {
		set(COLUMN_PRODUCT_STOCK, product_stock);
	}

	public List<String> getProductSkuIdList() {
		return productSkuIdList;
	}

	public void setProductSkuIdList(List<String> productSkuIdList) {
		this.productSkuIdList = productSkuIdList;
	}

	public Integer getProduct_amount() {
		return product_amount;
	}

	public void setProduct_amount(Integer product_amount) {
		this.product_amount = product_amount;
	}

	public Category getCategory() {
		return new Category().put(this);
	}

	public Brand getBrand() {
		return new Brand().put(this);
	}

	public Product getProduct() {
		return new Product().put(this);
	}

	public ProductLockStock getProductLockStock() {
		return new ProductLockStock().put(this);
	}

}
