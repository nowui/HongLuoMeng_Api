package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Order;

public class OrderDao {

	private Integer count(Order order) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Order.KEY_ORDER + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Order.KEY_ORDER_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Order order = new Order();

		return count(order);
	}

	private List<Order> list(Order order, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Order.KEY_ORDER + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Order.KEY_ORDER_STATUS + " = 1 ");

		sql.append("ORDER BY " + Order.KEY_ORDER_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Order> orderList = order.find(sql.toString(), parameterList.toArray());
		return orderList;
	}

	public List<Order> list(Integer m, Integer n) {
		Order order = new Order();

		return list(order, m, n);
	}

	private Order find(Order order) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Order.KEY_ORDER + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(order.getOrder_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Order.KEY_ORDER_ID + " = ? ");
			parameterList.add(order.getOrder_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Order.KEY_ORDER_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<Order> orderList = order.find(sql.toString(), parameterList.toArray());
		if(orderList.size() == 0) {
			return null;
		} else {
			return orderList.get(0);
		}
	}

	public Order findByOrder_id(String order_id) {
		Order order = new Order();
		order.setOrder_id(order_id);

		return find(order);
	}

	public void save(Order order, String request_user_id) {
		order.setOrder_id(Utility.getUUID());
		order.setOrder_create_user_id(request_user_id);
		order.setOrder_create_time(new Date());
		order.setOrder_update_user_id(request_user_id);
		order.setOrder_update_time(new Date());
		order.setOrder_status(true);

		order.save();
	}

	public void update(Order order, String request_user_id) {
		order.remove(Order.KEY_ORDER_CREATE_USER_ID);
		order.remove(Order.KEY_ORDER_CREATE_TIME);
		order.setOrder_update_user_id(request_user_id);
		order.setOrder_update_time(new Date());

		order.update();
	}

	public void delete(String order_id, String request_user_id) {
		Order order = new Order();
		order.setOrder_id(order_id);
		order.setOrder_update_user_id(request_user_id);
		order.setOrder_update_time(new Date());
		order.setOrder_status(false);

		order.update();
	}

}
