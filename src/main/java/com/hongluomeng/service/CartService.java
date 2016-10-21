package com.hongluomeng.service;

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
			cart.setCart_product_number(cart.getCart_product_number() + cartMap.getCart_product_number());

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

	public List<Cart> getList(JSONObject jsonObject) {
		//Cart cartMap = jsonObject.toJavaObject(Cart.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Cart> cartList = cartDao.listByUser_id(request_user_id);

		for(Cart cart : cartList) {
			cart.setUser_id(null);
			cart.setProduct_sku_id(null);
			cart.setCart_status(null);
		}

		return cartList;
	}

}
