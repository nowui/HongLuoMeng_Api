package com.hongluomeng.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.OrderDao;
import com.hongluomeng.model.Cart;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.MemberLevel;
import com.hongluomeng.model.Order;
import com.hongluomeng.model.OrderProduct;
import com.hongluomeng.model.ProductSku;

public class OrderService {

	private OrderDao orderDao = new OrderDao();
	private MemberService memberService = new MemberService();

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

		Member member = memberService.findByUser_id(request_user_id);

		BigDecimal order_pament_price = BigDecimal.valueOf(0);

		for(Cart cart : orderMap.getCartList()) {
			if(member.getMember_level_id().equals("")) {
				order_pament_price = order_pament_price.add(cart.getProduct_price());
			} else {
				JSONArray array = cart.getMember_level_price();

				for(int i = 0; i < array.size(); i++) {
					JSONObject object = array.getJSONObject(i);

					String member_level_id = object.getString(MemberLevel.KEY_MEMBER_LEVEL_ID);

					if(member.getMember_level_id().equals(member_level_id)) {
						BigDecimal member_level_price = object.getBigDecimal(ProductSku.KEY_MEMBER_LEVEL_PRICE);

						order_pament_price = order_pament_price.add(member_level_price);
					}
				}
			}
		}
		orderMap.setOrder_payment_price(order_pament_price);

		orderMap.setOrder_payment_amount(orderMap.getCartList().size());

		orderDao.save(orderMap, member.getMember_level_id(), member.getMember_level_name(), member.getMember_level_value(), request_user_id);

		for(Cart cart : orderMap.getCartList()) {
			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setProduct_id(cart.getProduct_id());
		}
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
