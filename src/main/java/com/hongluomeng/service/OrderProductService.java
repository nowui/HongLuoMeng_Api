package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.OrderProductDao;
import com.hongluomeng.model.OrderProduct;

public class OrderProductService {

	private OrderProductDao orderProductDao = new OrderProductDao();

	public List<OrderProduct> listByOrder_id(String order_id) {
		List<OrderProduct> orderProductList = orderProductDao.listByOrder_id(order_id);

		for (OrderProduct orderProduct : orderProductList) {
			orderProduct.setOrder_product_status(null);
		}

		return orderProductList;
	}

	public void saveByOrderProductList(List<OrderProduct> orderProductList, String request_user_id) {
		orderProductDao.saveByOrderProductList(orderProductList, request_user_id);
	}

}
