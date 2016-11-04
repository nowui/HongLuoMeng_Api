package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.OrderProductDao;
import com.hongluomeng.model.OrderProduct;

public class OrderProductService {

	private OrderProductDao orderProductDao = new OrderProductDao();

	public List<OrderProduct> listByOrder_id(String order_id) {
		List<OrderProduct> orderProductList = orderProductDao.listByOrder_id(order_id);

		for(OrderProduct orderProduct : orderProductList) {
			orderProduct.setOrder_product_status(null);
		}

		return orderProductList;
	}

	public void saveByOrderProductList(List<OrderProduct> orderProductList, String request_user_id) {
		orderProductDao.saveByOrderProductList(orderProductList, request_user_id);
	}

	/*public OrderProduct find(JSONObject jsonObject) {
		OrderProduct orderProductMap = jsonObject.toJavaObject(OrderProduct.class);

		OrderProduct orderProduct = orderProductDao.findByOrder_id(orderProductMap.getOrder_id());

		return orderProduct;
	}

	public void save(JSONObject jsonObject) {
		OrderProduct orderProductMap = jsonObject.toJavaObject(OrderProduct.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		orderProductDao.save(orderProductMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		OrderProduct orderProductMap = jsonObject.toJavaObject(OrderProduct.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		orderProductDao.update(orderProductMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		OrderProduct orderProductMap = jsonObject.toJavaObject(OrderProduct.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		orderProductDao.delete(orderProductMap.getOrder_id(), request_user_id);
	}*/

}
