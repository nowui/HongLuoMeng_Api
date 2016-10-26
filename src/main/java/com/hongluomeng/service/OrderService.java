package com.hongluomeng.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.OrderDao;
import com.hongluomeng.model.Order;

public class OrderService {

	private OrderDao orderDao = new OrderDao();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Order orderMap = jsonObject.toJavaObject(Order.class);

		Integer count = orderDao.count();

		List<Order> orderList = orderDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, orderList);

		return resultMap;
	}

	public Order find(JSONObject jsonObject) {
		Order orderMap = jsonObject.toJavaObject(Order.class);

		Order order = orderDao.findByOrder_id(orderMap.getOrder_id());

		return order;
	}

	public void save(JSONObject jsonObject) {
		Order orderMap = jsonObject.toJavaObject(Order.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		orderDao.save(orderMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		Order orderMap = jsonObject.toJavaObject(Order.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		orderDao.update(orderMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Order orderMap = jsonObject.toJavaObject(Order.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		orderDao.delete(orderMap.getOrder_id(), request_user_id);
	}

}
