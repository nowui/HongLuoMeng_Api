package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Cart;
import com.hongluomeng.model.Product;
import com.hongluomeng.model.ProductSku;

public class CartDao {

	private Integer count(Cart cart) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + Cart.KEY_TABLE_CART + " ");
		dynamicSQL.append(Cart.KEY_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Cart cart = new Cart();

		return count(cart);
	}

	private List<Cart> list(Cart cart, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT " + Cart.KEY_TABLE_CART + ".*, ");
		dynamicSQL.append(Product.KEY_TABLE_PRODUCT + "." + Product.KEY_PRODUCT_NAME + ", ");
		dynamicSQL.append(Product.KEY_TABLE_PRODUCT + "." + Product.KEY_PRODUCT_IMAGE + ", ");
		dynamicSQL.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_ATTRIBUTE_VALUE + ", ");
		dynamicSQL.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_PRICE + " ");
		dynamicSQL.append("FROM " + Cart.KEY_TABLE_CART + " ");
		dynamicSQL.append("LEFT JOIN " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ON " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " = " + Cart.KEY_TABLE_CART + "." + Cart.KEY_PRODUCT_SKU_ID + " ");
		dynamicSQL.append("LEFT JOIN " + Product.KEY_TABLE_PRODUCT + " ON " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_PRODUCT_ID + " = " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_ID + " ");
		dynamicSQL.append("WHERE " + Cart.KEY_TABLE_CART + "." + Cart.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Cart.KEY_TABLE_CART + "." + Cart.KEY_USER_ID + " = ? ", cart.getUser_id());

		if (!Utility.isNullOrEmpty(cart.getProductSkuIdList())) {
			for (int i = 0; i < cart.getProductSkuIdList().size(); i++) {
				String product_sku_id = cart.getProductSkuIdList().get(i);

				if(i == 0) {
					dynamicSQL.append("AND(");
				} else {
					dynamicSQL.append("OR ");
				}

				dynamicSQL.isNullOrEmpty(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " = ? ", product_sku_id);
			}
			dynamicSQL.append(") ");
		}

		dynamicSQL.append("ORDER BY " + Cart.KEY_TABLE_CART + "." + Cart.KEY_SYSTEM_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		return cart.find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
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
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Cart.KEY_TABLE_CART + " ");
		dynamicSQL.append("WHERE " + Cart.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Cart.KEY_CART_ID + " = ? ", cart.getCart_id());
		dynamicSQL.isNullOrEmpty("AND " + Cart.KEY_PRODUCT_SKU_ID + " = ? ", cart.getProduct_sku_id());

		List<Cart> cartList = cart.find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
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
		cart.remove(Cart.KEY_USER_ID);
		cart.remove(Cart.KEY_PRODUCT_SKU_ID);

		cart.initForUpdate(request_user_id);

		cart.update();
	}

	public void updateProduct_amount(List<Cart> cartList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("UPDATE " + Cart.KEY_TABLE_CART + " ");
		sql.append("SET " + Cart.KEY_PRODUCT_AMOUNT + " = ?, ");
		sql.append(Cart.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ");
		sql.append(Cart.KEY_SYSTEM_UPDATE_TIME + " = ? ");
		sql.append("WHERE " + Cart.KEY_CART_ID + " = ? ");

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

		StringBuffer sql = new StringBuffer("UPDATE " + Cart.KEY_TABLE_CART + " ");
		sql.append("SET " + Cart.KEY_PRODUCT_AMOUNT + " = 0, ");
		sql.append(Cart.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ");
		sql.append(Cart.KEY_SYSTEM_UPDATE_TIME + " = ?, ");
		sql.append(Cart.KEY_SYSTEM_STATUS + " = 0 ");
		sql.append("WHERE " + Cart.KEY_CART_ID + " = ? ");

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
