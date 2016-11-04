package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.Cart;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.Product;
import com.hongluomeng.model.ProductSku;

public class CartDao {

	private Integer count(Cart cart) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Cart.KEY_TABLE_CART + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Cart.KEY_CART_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Cart cart = new Cart();

		return count(cart);
	}

	private List<Cart> list(Cart cart, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append(Cart.KEY_TABLE_CART + ".*, ");
		sql.append(Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_ID + ", ");
		sql.append(Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_NAME + ", ");
		sql.append(Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + ", ");
		sql.append(Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_NAME + ", ");
		sql.append(Product.KEY_TABLE_PRODUCT + ".*, ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_ATTRIBUTE_VALUE + ", ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_PRICE + ", ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_MEMBER_LEVEL_PRICE + ", ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_STOCK + ", ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_STATUS + " ");
		sql.append(" ");
		sql.append("FROM " + Cart.KEY_TABLE_CART + " ");
		sql.append("LEFT JOIN " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ON " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " = " + Cart.KEY_TABLE_CART + "." + Cart.KEY_PRODUCT_SKU_ID + " ");
		sql.append("LEFT JOIN " + Product.KEY_TABLE_PRODUCT + " ON " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_PRODUCT_ID + " = " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_ID + " ");
		sql.append("LEFT JOIN " + Category.KEY_TABLE_CATEGORY + " ON " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_CATEGORY_ID + " ");
		sql.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_BRAND_ID + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(cart.getUser_id())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}
			sql.append(Cart.KEY_TABLE_CART + "." + Cart.KEY_USER_ID + " = ? ");
			parameterList.add(cart.getUser_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(cart.getProductSkuIdList())) {
			for(String product_sku_id : cart.getProductSkuIdList()) {
				if(isExit) {
					sql.append("AND ");
				} else {
					sql.append("WHERE ");
				}

				sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " = ? ");
				parameterList.add(product_sku_id);

				isExit = true;
			}
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Cart.KEY_TABLE_CART + "." + Cart.KEY_CART_STATUS + " = 1 ");

		sql.append("ORDER BY " + Cart.KEY_TABLE_CART + "." + Cart.KEY_CART_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Cart> cartList = cart.find(sql.toString(), parameterList.toArray());
		return cartList;
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

	public List<Cart> listByProductSkuIdList(List<String> productSkuIdList) {
		Cart cart = new Cart();
		cart.setProductSkuIdList(productSkuIdList);

		return list(cart, 0, 0);
	}

	private Cart find(Cart cart) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Cart.KEY_TABLE_CART + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(cart.getCart_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Cart.KEY_CART_ID + " = ? ");
			parameterList.add(cart.getCart_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(cart.getProduct_sku_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Cart.KEY_PRODUCT_SKU_ID + " = ? ");
			parameterList.add(cart.getProduct_sku_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Cart.KEY_CART_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<Cart> cartList = cart.find(sql.toString(), parameterList.toArray());
		if(cartList.size() == 0) {
			return null;
		} else {
			return cartList.get(0);
		}
	}

	public Cart findByCart_id(String cart_id) {
		Cart cart = new Cart();
		cart.setCart_id(cart_id);

		return find(cart);
	}

	public Cart findByProduct_sku_id(String product_sku_id) {
		Cart cart = new Cart();
		cart.setProduct_sku_id(product_sku_id);

		return find(cart);
	}

	public void save(Cart cart, String request_user_id) {
		cart.setCart_id(Utility.getUUID());
		cart.setCart_create_user_id(request_user_id);
		cart.setCart_create_time(new Date());
		cart.setCart_update_user_id(request_user_id);
		cart.setCart_update_time(new Date());
		cart.setCart_status(true);

		cart.save();
	}

	public void update(Cart cart, String request_user_id) {
		cart.remove(Cart.KEY_USER_ID);
		cart.remove(Cart.KEY_PRODUCT_SKU_ID);

		cart.remove(Cart.KEY_CART_CREATE_USER_ID);
		cart.remove(Cart.KEY_CART_CREATE_TIME);
		cart.setCart_update_user_id(request_user_id);
		cart.setCart_update_time(new Date());

		cart.update();
	}

	public void delete(String cart_id, String request_user_id) {
		Cart cart = new Cart();
		cart.setCart_id(cart_id);
		cart.setCart_update_user_id(request_user_id);
		cart.setCart_update_time(new Date());
		cart.setCart_status(false);

		cart.update();
	}

}
