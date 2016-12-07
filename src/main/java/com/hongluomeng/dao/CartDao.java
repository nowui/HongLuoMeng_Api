package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Cart;
import com.hongluomeng.model.Product;
import com.hongluomeng.model.ProductSku;

public class CartDao {

	private Integer count(Cart cart) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + Cart.TABLE_CART + " ");
		myDynamicSQL.append("" + Cart.TABLE_CART + "." + Cart.COLUMN_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Cart cart = new Cart();

		return count(cart);
	}

	private List<Cart> list(Cart cart, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT " + Cart.TABLE_CART + ".*, ");
		myDynamicSQL.append(Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_PRODUCT_NAME + ", ");
		myDynamicSQL.append(Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_PRODUCT_IMAGE + ", ");
		myDynamicSQL.append(ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_ATTRIBUTE_VALUE + ", ");
		myDynamicSQL.append(ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_PRICE + " ");
		myDynamicSQL.append("FROM " + Cart.TABLE_CART + " ");
		myDynamicSQL.append("LEFT JOIN " + ProductSku.TABLE_PRODUCT_SKU + " ON " + ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_SKU_ID + " = " + Cart.TABLE_CART + "." + Cart.COLUMN_PRODUCT_SKU_ID + " ");
		myDynamicSQL.append("LEFT JOIN " + Product.KEY_TABLE_PRODUCT + " ON " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_PRODUCT_ID + " = " + ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_ID + " ");
		myDynamicSQL.append("WHERE " + Cart.TABLE_CART + "." + Cart.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Cart.TABLE_CART + "." + Cart.COLUMN_USER_ID + " = ? ", cart.getUser_id());

		if (!Utility.isNullOrEmpty(cart.getProductSkuIdList())) {
			for (int i = 0; i < cart.getProductSkuIdList().size(); i++) {
				String product_sku_id = cart.getProductSkuIdList().get(i);

				if(i == 0) {
					myDynamicSQL.append("AND(");
				} else {
					myDynamicSQL.append("OR ");
				}

				myDynamicSQL.isNullOrEmpty(ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_SKU_ID + " = ? ", product_sku_id);
			}
			myDynamicSQL.append(") ");
		}

		myDynamicSQL.append("ORDER BY " + Cart.TABLE_CART + "." + Cart.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		return cart.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<Cart> list(Integer m, Integer n) {
		Cart cart = new Cart();

		return list(cart, m, n);
	}

	public List<Cart> listByUser_id(String user_id) {
		Cart cart = new Cart();
		cart.setUser_id(user_id);

		return list(cart, 0, 0);
	}

	public List<Cart> listByUser_idAndproductSkuIdList(String user_id, List<String> productSkuIdList) {
		Cart cart = new Cart();
		cart.setUser_id(user_id);
		cart.setProductSkuIdList(productSkuIdList);

		return list(cart, 0, 0);
	}

	private Cart find(Cart cart) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Cart.TABLE_CART + " ");
		myDynamicSQL.append("WHERE " + Cart.TABLE_CART + "." + Cart.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Cart.COLUMN_CART_ID + " = ? ", cart.getCart_id());
		myDynamicSQL.isNullOrEmpty("AND " + Cart.COLUMN_PRODUCT_SKU_ID + " = ? ", cart.getProduct_sku_id());

		List<Cart> cartList = cart.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if (cartList.size() == 0) {
			return null;
		} else {
			return cartList.get(0);
		}
	}

	public Cart findByCart_id(String cart_id) {
		Cart cart = new Cart();
		cart.setCart_id(cart_id);

		cart.checkCart_id();

		return find(cart);
	}

	public Cart findByProduct_sku_id(String product_sku_id) {
		Cart cart = new Cart();
		cart.setProduct_sku_id(product_sku_id);

		cart.checkProduct_sku_id();

		return find(cart);
	}

	public void save(Cart cart, String request_user_id) {
		cart.setCart_id(Utility.getUUID());

		cart.initForSave(request_user_id);

		cart.save();
	}

	public void update(Cart cart, String request_user_id) {
		cart.remove(Cart.COLUMN_USER_ID);
		cart.remove(Cart.COLUMN_PRODUCT_SKU_ID);

		cart.initForUpdate(request_user_id);

		cart.update();
	}

	public void updateProduct_amount(List<Cart> cartList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("UPDATE " + Cart.TABLE_CART + " ");
		sql.append("SET " + Cart.COLUMN_PRODUCT_AMOUNT + " = ?, ");
		sql.append(Cart.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ");
		sql.append(Cart.COLUMN_SYSTEM_UPDATE_TIME + " = ? ");
		sql.append("WHERE " + Cart.COLUMN_CART_ID + " = ? ");

		for (Cart cart : cartList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(cart.getProduct_amount());
			objectList.add(request_user_id);
			objectList.add(new Date());
			objectList.add(cart.getCart_id());

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

	public void delete(String cart_id, String request_user_id) {
		Cart cart = new Cart();
		cart.setCart_id(cart_id);
		cart.setProduct_amount(0);

		cart.initForDelete(request_user_id);

		cart.update();
	}

	public void delete(List<Cart> cartList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("UPDATE " + Cart.TABLE_CART + " ");
		sql.append("SET " + Cart.COLUMN_PRODUCT_AMOUNT + " = 0, ");
		sql.append(Cart.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ");
		sql.append(Cart.COLUMN_SYSTEM_UPDATE_TIME + " = ?, ");
		sql.append(Cart.COLUMN_SYSTEM_STATUS + " = 0 ");
		sql.append("WHERE " + Cart.COLUMN_CART_ID + " = ? ");

		for (Cart cart : cartList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(request_user_id);
			objectList.add(new Date());
			objectList.add(cart.getCart_id());

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

}
