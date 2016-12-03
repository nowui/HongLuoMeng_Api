package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Utility;

public class ProductSku extends Base<ProductSku> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_PRODUCT_SKU = "table_product_sku";
	public static final String KEY_PRODUCT_SKU_ID = "product_sku_id";
	public static final String KEY_PRODUCT_ID = "product_id";
	public static final String KEY_PRODUCT_ATTRIBUTE_VALUE = "product_attribute_value";
	public static final String KEY_PRODUCT_PRICE = "product_price";
	public static final String KEY_MEMBER_LEVEL_PRICE = "member_level_price";
	public static final String KEY_PRODUCT_STOCK = "product_stock";

	private List<String> productSkuIdList;
	private Integer product_amount;

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
		return getStr(KEY_PRODUCT_ID);
	}

	public void setProduct_id(String product_id) {
		set(KEY_PRODUCT_ID, product_id);
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

	public Integer getProduct_stock() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_STOCK));
	}

	public void setProduct_stock(Integer product_stock) {
		set(KEY_PRODUCT_STOCK, product_stock);
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

	private Category getCategory() {
		return new Category().put(this);
	}

	public String getCategory_id() {
		return getCategory().getCategory_id();
	}

	public String getCategory_name() {
		return getCategory().getCategory_name();
	}

	private Brand getBrand() {
		return new Brand().put(this);
	}

	public String getBrand_id() {
		return getBrand().getBrand_id();
	}

	public String getBrand_name() {
		return getBrand().getBrand_name();
	}

	private Product getProduct() {
		return new Product().put(this);
	}

	public String getProduct_name() {
		return getProduct().getProduct_name();
	}

	public JSONArray getProduct_image() {
		return getProduct().getProduct_image();
	}

	public Boolean getProduct_is_new() {
		return getProduct().getProduct_is_new();
	}

	public Boolean getProduct_is_recommend() {
		return getProduct().getProduct_is_recommend();
	}

	public Boolean getProduct_is_bargain() {
		return getProduct().getProduct_is_bargain();
	}

	public Boolean getProduct_is_hot() {
		return getProduct().getProduct_is_hot();
	}

	public Boolean getProduct_is_sell_out() {
		return getProduct().getProduct_is_sell_out();
	}

	public Boolean getProduct_is_sale() {
		return getProduct().getProduct_is_sale();
	}

	public String getProduct_content() {
		return getProduct().getProduct_content();
	}

	public JSONArray getProduct_sku_value() {
		return getProduct().getProduct_sku_value();
	}

	private ProductLockStock getProductLockStock() {
		return new ProductLockStock().put(this);
	}

	public Integer getProduct_lock_stock() {
		return getProductLockStock().getProduct_lock_stock();
	}

}
