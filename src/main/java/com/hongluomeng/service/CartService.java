package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.CartDao;
import com.hongluomeng.model.Cart;

public class CartService {

	private CartDao cartDao = new CartDao();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Cart cartMap = jsonObject.toJavaObject(Cart.class);

		Integer count = cartDao.count();

		List<Cart> cartList = cartDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, cartList);

		return resultMap;
	}

	public List<Cart> listByUser_idAndproductSkuIdList(String user_id, List<String> productSkuIdList) {
		return cartDao.listByUser_idAndproductSkuIdList(user_id, productSkuIdList);
	}

	public Cart find(JSONObject jsonObject) {
		Cart cartMap = jsonObject.toJavaObject(Cart.class);

		Cart cart = cartDao.findByCart_id(cartMap.getCart_id());

		return cart;
	}

	public void save(JSONObject jsonObject) {
		Cart cartMap = jsonObject.toJavaObject(Cart.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		cartMap.setUser_id(request_user_id);

		Cart cart = cartDao.findByProduct_sku_id(cartMap.getProduct_sku_id());

		if(cart == null) {
			cartDao.save(cartMap, request_user_id);
		} else {
			cart.setCart_product_amount(cart.getCart_product_amount() + cartMap.getCart_product_amount());

			cartDao.update(cart, request_user_id);
		}
	}

	public void update(JSONObject jsonObject) {
		Cart cartMap = jsonObject.toJavaObject(Cart.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		cartDao.update(cartMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Cart cartMap = jsonObject.toJavaObject(Cart.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		cartDao.delete(cartMap.getCart_id(), request_user_id);
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		//Cart cartMap = jsonObject.toJavaObject(Cart.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Cart> cartList = cartDao.listByUser_id(request_user_id);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for(Cart cart : cartList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Cart.KEY_CART_ID, cart.getCart_id());
			map.put(Cart.KEY_PRODUCT_NAME, cart.getProduct_name());
			map.put(Cart.KEY_PRODUCT_SKU_ID, cart.getProduct_sku_id());
			map.put(Cart.KEY_CART_PRODUCT_AMOUNT, cart.getCart_product_amount());

			resultList.add(map);
		}

		return resultList;
	}

}
