package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.OrderProductDao;
import com.hongluomeng.model.OrderProduct;

public class OrderProductService extends BaseService {

	private OrderProductDao orderProductDao = new OrderProductDao();

	public List<OrderProduct> listByOrder_id(String order_id) {
		List<OrderProduct> orderProductList = orderProductDao.listByOrder_id(order_id);

		return orderProductList;
	}

	public List<OrderProduct> listByOrderIdList(List<String> orderIdList) {
		List<OrderProduct> orderProductList = orderProductDao.listByOrderIdList(orderIdList);

		return orderProductList;
	}

	public void saveByOrderProductList(List<OrderProduct> orderProductList, String request_user_id) {
		orderProductDao.saveByOrderProductList(orderProductList, request_user_id);
	}

}
