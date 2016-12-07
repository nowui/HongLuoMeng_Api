package com.hongluomeng.model;

import java.util.List;

import com.hongluomeng.common.Utility;

public class Cart extends Base<Cart> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_CART = "table_cart";
	public static final String COLUMN_CART_ID = "cart_id";
	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_PRODUCT_SKU_ID = "product_sku_id";
	public static final String COLUMN_PRODUCT_AMOUNT = "product_amount";

	private List<String> productSkuIdList;

	public String getCart_id() {
		return getStr(COLUMN_CART_ID);
	}

	public void setCart_id(String cart_id) {
		set(COLUMN_CART_ID, cart_id);
	}

	public void checkCart_id() {
		Utility.checkStringLength(getCart_id(), 32, "购物车编号");
	}

	public String getUser_id() {
		return getStr(COLUMN_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(COLUMN_USER_ID, user_id);
	}

	public void checkUser_id() {
		Utility.checkStringLength(getUser_id(), 32, "用户编号");
	}

	public String getProduct_sku_id() {
		return getStr(COLUMN_PRODUCT_SKU_ID);
	}

	public void setProduct_sku_id(String product_sku_id) {
		set(COLUMN_PRODUCT_SKU_ID, product_sku_id);
	}

	public void checkProduct_sku_id() {
		Utility.checkStringLength(getProduct_sku_id(), 32, "商品SKU编号");
	}

	public Integer getProduct_amount() {
		return Utility.getIntegerValue(get(COLUMN_PRODUCT_AMOUNT));
	}

	public void setProduct_amount(Integer product_amount) {
		set(COLUMN_PRODUCT_AMOUNT, product_amount);
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
