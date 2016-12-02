package com.hongluomeng.model;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Utility;

public class Cart extends Base<Cart> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_CART = "table_cart";
	public static final String KEY_CART_ID = "cart_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_PRODUCT_SKU_ID = "product_sku_id";
	public static final String KEY_PRODUCT_AMOUNT = "product_amount";

	private Product product;
	private ProductSku productSku;
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

	public Integer getProduct_amount() {
		return Utility.getIntegerValue(get(KEY_PRODUCT_AMOUNT));
	}

	public void setProduct_amount(Integer product_amount) {
		set(KEY_PRODUCT_AMOUNT, product_amount);
	}

	public void checkProduct_amount() {
		Utility.checkIntegerLength(getProduct_amount(), 1, 7, "购物车商品数量");
	}

	public Product getProduct() {
		return new Product().put(this);
	}

	public ProductSku getProductSku() {
		return new ProductSku().put(this);
	}

	public List<String> getProductSkuIdList() {
		return productSkuIdList;
	}

	public void setProductSkuIdList(List<String> productSkuIdList) {
		this.productSkuIdList = productSkuIdList;
	}

}
