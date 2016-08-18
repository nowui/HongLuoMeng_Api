package com.hongluomeng.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.ShopDao;
import com.hongluomeng.model.Shop;

public class ShopService {

	private ShopDao shopDao = new ShopDao();

	public Integer count(JSONObject jsonObject) {
		//Shop shopMap = jsonObject.toJavaObject(Shop.class);

		return shopDao.count();
	}

	public List<Shop> list(JSONObject jsonObject) {
		//Shop shopMap = jsonObject.toJavaObject(Shop.class);

		return shopDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
	}

	public Shop find(JSONObject jsonObject) {
		Shop shopMap = jsonObject.toJavaObject(Shop.class);

		Shop shop = shopDao.findByShop_id(shopMap.getShop_id());

		return shop;
	}

	public void save(JSONObject jsonObject) {
		Shop shopMap = jsonObject.toJavaObject(Shop.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		shopDao.save(shopMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		Shop shopMap = jsonObject.toJavaObject(Shop.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		shopDao.update(shopMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Shop shopMap = jsonObject.toJavaObject(Shop.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		shopDao.delete(shopMap.getShop_id(), request_user_id);
	}

}