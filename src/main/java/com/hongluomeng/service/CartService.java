package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.CartDao;
import com.hongluomeng.model.Attribute;
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
			int count = cart.getProduct_amount() + cartMap.getProduct_amount();

			if(count > 0) {
				cart.setProduct_amount(cart.getProduct_amount() + cartMap.getProduct_amount());

				cartDao.update(cart, request_user_id);
			} else {
				cartDao.delete(cart.getCart_id(), request_user_id);
			}
		}
	}

	public void update(JSONObject jsonObject) {
		Cart cartMap = jsonObject.toJavaObject(Cart.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		cartDao.update(cartMap, request_user_id);
	}

	public void updateProduct_amount(List<Cart> cartList, String request_user_id) {
		cartDao.updateProduct_amount(cartList, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Cart cartMap = jsonObject.toJavaObject(Cart.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		cartDao.delete(cartMap.getCart_id(), request_user_id);
	}

	public void delete(List<Cart> cartList, String request_user_id) {
		cartDao.delete(cartList, request_user_id);
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		//Cart cartMap = jsonObject.toJavaObject(Cart.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Cart> cartList = cartDao.listByUser_id(request_user_id);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for(Cart cart : cartList) {
			String product_attribute_value = "";
			for (int i = 0; i < cart.getProduct_attribute_value().size(); i++) {
				JSONObject object = cart.getProduct_attribute_value().getJSONObject(i);

				if (i > 0) {
					product_attribute_value += " ";
				}

				product_attribute_value += object.getString(Attribute.KEY_ATTRIBUTE_VALUE);
			}


			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Cart.KEY_CART_ID, cart.getCart_id());
			map.put(Cart.KEY_PRODUCT_NAME, cart.getProduct_name());
			map.put(Cart.KEY_PRODUCT_SKU_ID, cart.getProduct_sku_id());
			map.put(Cart.KEY_PRODUCT_AMOUNT, cart.getProduct_amount());
			map.put(Cart.KEY_PRODUCT_IMAGE, cart.getProduct_image().get(0));
			map.put(Cart.KEY_PRODUCT_ATTRIBUTE_VALUE, product_attribute_value);
			map.put(Cart.KEY_PRODUCT_PRICE, cart.getProduct_price());

			resultList.add(map);
		}

		return resultList;
	}

}
