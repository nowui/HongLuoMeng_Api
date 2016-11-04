package com.hongluomeng.service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
	private CartService cartService = new CartService();
	private OrderProductService orderProductService = new OrderProductService();

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

		List<String> product_sku_id_list = new ArrayList<String>();

		if(orderMap.getCartList().size() == 0) {
			throw new RuntimeException("商品列表为空");
		}

		for(Cart cart : orderMap.getCartList()) {
			product_sku_id_list.add(cart.getProduct_sku_id());
		}

		List<Cart> cartList = cartService.listByProductSkuIdList(product_sku_id_list);

		//判断传过来的商品编号是否存在对应的数据
		for(Cart cart : orderMap.getCartList()) {
			Boolean isExit = false;

			for(Cart cartObject : cartList) {
				if(cart.getProduct_sku_id().equals(cartObject.getProduct_sku_id())) {
					isExit = true;

					break;
				}
			}

			if(! isExit) {
				throw new RuntimeException("没有找到商品编号:" + cart.getProduct_id());
			}
		}

		//计算订单的总价格
		BigDecimal order_pament_price = BigDecimal.valueOf(0);
		for(Cart cart : cartList) {
			if(member.getMember_level_id().equals("")) {
				order_pament_price = order_pament_price.add(cart.getProduct_price());
			} else {
				Boolean isExit = false;

				JSONArray array = cart.getMember_level_price();

				for(int i = 0; i < array.size(); i++) {
					JSONObject object = array.getJSONObject(i);

					String member_level_id = object.getString(MemberLevel.KEY_MEMBER_LEVEL_ID);

					if(member.getMember_level_id().equals(member_level_id)) {
						BigDecimal member_level_price = object.getBigDecimal(ProductSku.KEY_MEMBER_LEVEL_PRICE);

						order_pament_price = order_pament_price.add(member_level_price);

						isExit = true;
					}
				}

				if(! isExit) {
					order_pament_price = order_pament_price.add(cart.getProduct_price());
				}
			}
		}
		orderMap.setOrder_payment_price(order_pament_price);

		orderMap.setOrder_payment_amount(orderMap.getCartList().size());

		orderDao.save(orderMap, member.getMember_level_id(), member.getMember_level_name(), member.getMember_level_value(), request_user_id);

		List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
		for(Cart cart : cartList) {
			BigDecimal product_payment_price = BigDecimal.valueOf(0);

			if(member.getMember_level_id().equals("")) {
				product_payment_price = cart.getProduct_price();
			} else {
				Boolean isExit = false;

				JSONArray array = cart.getMember_level_price();

				for(int i = 0; i < array.size(); i++) {
					JSONObject object = array.getJSONObject(i);

					String member_level_id = object.getString(MemberLevel.KEY_MEMBER_LEVEL_ID);

					if(member.getMember_level_id().equals(member_level_id)) {
						BigDecimal member_level_price = object.getBigDecimal(ProductSku.KEY_MEMBER_LEVEL_PRICE);

						product_payment_price = member_level_price;

						isExit = true;
					}
				}

				if(! isExit) {
					product_payment_price = cart.getProduct_price();
				}
			}

			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setOrder_id(orderMap.getOrder_id());
			orderProduct.setCategory_id(cart.getCategory_id());
			orderProduct.setCategory_name(cart.getCategory_name());
			orderProduct.setBrand_id(cart.getBrand_id());
			orderProduct.setBrand_name(cart.getBrand_name());
			orderProduct.setProduct_id(cart.getProduct_id());
			orderProduct.setProduct_name(cart.getProduct_name());
			orderProduct.setProduct_image(cart.getProduct_image().toJSONString());
			orderProduct.setProduct_is_new(cart.getProduct_is_new());
			orderProduct.setProduct_is_recommend(cart.getProduct_is_recommend());
			orderProduct.setProduct_is_bargain(cart.getProduct_is_bargain());
			orderProduct.setProduct_is_hot(cart.getProduct_is_hot());
			orderProduct.setProduct_is_sell_out(cart.getProduct_is_sell_out());
			orderProduct.setProduct_is_sale(cart.getProduct_is_sale());
			orderProduct.setProduct_content(cart.getProduct_content());
			orderProduct.setProduct_sku_value(cart.getProduct_sku_value().toJSONString());
			orderProduct.setProduct_sku_id(cart.getProduct_sku_id());
			orderProduct.setProduct_attribute_value(cart.getProduct_attribute_value().toJSONString());
			orderProduct.setProduct_price(cart.getProduct_price());
			orderProduct.setMember_level_price(cart.getMember_level_price().toJSONString());
			orderProduct.setProduct_payment_price(product_payment_price);
			orderProduct.setProduct_payment_amount(cart.getProduct_amount());

			orderProductList.add(orderProduct);
		}

		orderProductService.saveByOrderProductList(orderProductList, request_user_id);
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
