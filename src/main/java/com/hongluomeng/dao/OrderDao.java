package com.hongluomeng.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.Const;
import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Order;
import com.hongluomeng.type.OrderFlowEnum;

public class OrderDao {

	private Integer count(Order order) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + Order.KEY_TABLE_ORDER + " ");
		dynamicSQL.append("WHERE " + Order.KEY_ORDER_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Order.KEY_ORDER_NO + " = ? ", order.getOrder_no());

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Order order = new Order();

		return count(order);
	}

	private List<Order> list(Order order, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		String order_flow_status = order.getOrder_flow_status();
		if(order_flow_status.equals(OrderFlowEnum.WAIT.getKey())) {
			order_flow_status += "," + OrderFlowEnum.CONFIRM.getKey();
		}

		dynamicSQL.append("SELECT * FROM " + Order.KEY_TABLE_ORDER + " ");
		dynamicSQL.append("WHERE " + Order.KEY_ORDER_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Order.KEY_USER_ID + " = ? ", order.getUser_id());
		dynamicSQL.isNullOrEmptyForSplit(Order.KEY_ORDER_FLOW_STATUS + " = ? ", order_flow_status);
		dynamicSQL.append("ORDER BY " + Order.KEY_ORDER_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		return new Order().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Order> list(Integer m, Integer n) {
		Order order = new Order();
		order.setOrder_flow_status("");

		return list(order, m, n);
	}

	public List<Order> listByUser_idAndOrder_flow_status(String user_id, String order_flow_status, Integer m, Integer n) {
		Order order = new Order();
		order.setUser_id(user_id);
		order.setOrder_flow_status(order_flow_status);

		return list(order, m, n);
	}

	private Order find(Order order) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Order.KEY_TABLE_ORDER + " ");
		dynamicSQL.append("WHERE " + Order.KEY_ORDER_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Order.KEY_ORDER_ID + " = ? ", order.getOrder_id());

		List<Order> orderList = new Order().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if (orderList.size() == 0) {
			return null;
		} else {
			return orderList.get(0);
		}
	}

	public Order findByOrder_id(String order_id) {
		Order order = new Order();
		order.setOrder_id(order_id);

		order.checkOrder_id();

		return find(order);
	}

	private Boolean checkOrder_no(String order_no) {
		Order order = new Order();
		order.setOrder_no(order_no);

		order.checkOrder_no();

		Integer count = count(order);

		return count == 0;
	}

	private String getOrder_no() {
		return Const.ORDER_NO_HEADER + Utility.getDateString(new Date()).replaceAll("-", "") + Utility.getFixLenthString(6);
	}

	public void save(Order order, String member_level_id, String member_level_name, Integer member_level_value, String request_user_id) {
		String order_no = getOrder_no();

		Boolean isExit = true;

		while (isExit) {
			if (checkOrder_no(order_no)) {
				isExit = false;
			} else {
				order_no = getOrder_no();
			}
		}

		order.setOrder_id(Utility.getUUID());
		order.setUser_id(request_user_id);
		order.setOrder_no(order_no);
		if (Utility.isNullOrEmpty(order.getOrder_message())) {
			order.setOrder_message("");
		}
		if (Utility.isNullOrEmpty(order.getOrder_delivery_zip())) {
			order.setOrder_delivery_zip("");
		}
		order.setOrder_is_pay(false);
		order.setOrder_trade_no("");
		order.setOrder_trade_account("");
		order.setOrder_trade_price(BigDecimal.ZERO);
		order.setOrder_trade_time("");
		order.setOrder_trade_result("");
		order.setMember_level_id(member_level_id);
		order.setMember_level_name(member_level_name);
		order.setMember_level_value(member_level_value);
		order.setOrder_create_user_id(request_user_id);
		order.setOrder_create_time(new Date());
		order.setOrder_update_user_id(request_user_id);
		order.setOrder_update_time(new Date());
		order.setOrder_flow_status(OrderFlowEnum.WAIT.getKey());
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

	public void payed(String order_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + Order.KEY_TABLE_ORDER + " ");
		dynamicSQL.append("SET " + Order.KEY_ORDER_FLOW_STATUS + " = ? ", OrderFlowEnum.CONFIRM.getKey());
		dynamicSQL.append("WHERE " + Order.KEY_ORDER_ID + " = ? ", order_id);
		dynamicSQL.append("AND " + Order.KEY_ORDER_FLOW_STATUS + " = ? ", OrderFlowEnum.WAIT.getKey());

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public int updateTrade(String order_no, String order_trade_no, String order_trade_account, String order_trade_price, String order_pay_result) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + Order.KEY_TABLE_ORDER + " ");
		dynamicSQL.append("SET " + Order.KEY_ORDER_IS_PAY + " = 1, ");
		dynamicSQL.append(Order.KEY_ORDER_TRADE_NO + " = ?, ", order_trade_no);
		dynamicSQL.append(Order.KEY_ORDER_TRADE_ACCOUNT + " = ?, ", order_trade_account);
		dynamicSQL.append(Order.KEY_ORDER_TRADE_PRICE + " = ?, ", order_trade_price);
		dynamicSQL.append(Order.KEY_ORDER_TRADE_TIME + " = ?, ", Utility.getDateTimeString(new Date()));
		dynamicSQL.append(Order.KEY_ORDER_TRADE_RESULT + " = ?, ", order_pay_result);
		dynamicSQL.append(Order.KEY_ORDER_FLOW_STATUS + " = ? ", OrderFlowEnum.PAYED.getKey());
		dynamicSQL.append("WHERE " + Order.KEY_ORDER_NO + " = ? ", order_no);

		return Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
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
