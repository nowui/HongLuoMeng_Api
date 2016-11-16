package com.hongluomeng.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.hongluomeng.model.ProductLockStock;

public class OrderService {

	private OrderDao orderDao = new OrderDao();
	private MemberService memberService = new MemberService();
	private CartService cartService = new CartService();
	private ProductSkuService productSkuService = new ProductSkuService();
	private OrderProductService orderProductService = new OrderProductService();
	private ProductLockStockService productLockStockService = new ProductLockStockService();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Order orderMap = jsonObject.toJavaObject(Order.class);

		Integer count = orderDao.count();

		List<Order> orderList = orderDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for(Order order : orderList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Order.KEY_ORDER_ID, order.getOrder_id());
			map.put(Order.KEY_ORDER_NO, order.getOrder_no());
			map.put(Order.KEY_ORDER_PAYMENT_AMOUNT, order.getOrder_payment_amount());
			map.put(Order.KEY_ORDER_PAYMENT_PRICE, order.getOrder_payment_price());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

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

		if(orderMap.getCartList().size() == 0) {
			throw new RuntimeException("商品列表为空");
		}

		List<String> productSkuIdList = new ArrayList<String>();

		for(Cart cart : orderMap.getCartList()) {
			productSkuIdList.add(cart.getProduct_sku_id());
		}

		List<ProductSku> productSkuList = productSkuService.listByProductSkuIdList(productSkuIdList);

		for(Cart cart : orderMap.getCartList()) {
			//SKU编号是否存在
			Boolean isExit = false;
			//数量是否超出
			Boolean isOver = false;

			for(ProductSku productSku : productSkuList) {
				if(cart.getProduct_sku_id().equals(productSku.getProduct_sku_id())) {
					isExit = true;

					System.out.println(cart.getCart_product_amount() + " - " + productSku.getProduct_stock() + " - " + productSku.getProduct_lock_stock());

					if(cart.getCart_product_amount() < productSku.getProduct_stock() + productSku.getProduct_lock_stock()) {
						isOver = true;
					}

					break;
				}
			}

			if(! isExit) {
				throw new RuntimeException("没有找到的商品SKU编号是:" + cart.getProduct_sku_id());
			}

			if(isOver) {
				throw new RuntimeException("超过库存的商品SKU编号是:" + cart.getProduct_sku_id());
			}
		}



		Boolean aaa = true;

		//是否直接购买
		if(aaa) {
			List<Cart> cartList = cartService.listByUser_idAndproductSkuIdList(request_user_id, productSkuIdList);

			//判断传过来的商品编号在购物车是否存在对应的数据
			for(Cart cart : orderMap.getCartList()) {
				//SKU编号是否存在
				Boolean isExit = false;
				//数量是否超出
				Boolean isOver = false;

				for(Cart cartObject : cartList) {
					if(cart.getProduct_sku_id().equals(cartObject.getProduct_sku_id())) {
						isExit = true;

						if(cart.getCart_product_amount() > cartObject.getCart_product_amount()) {
							isOver = true;
						}

						break;
					}
				}

				if(! isExit) {
					throw new RuntimeException("没有找到的商品SKU编号是:" + cart.getProduct_sku_id());
				}

				if(isOver) {
					throw new RuntimeException("超过购物车中数量的商品SKU编号是:" + cart.getProduct_sku_id());
				}
			}
		}

		//计算订单的总价格
		BigDecimal order_pament_price = BigDecimal.valueOf(0);
		for(ProductSku productSku : productSkuList) {
			if(member.getMember_level_id().equals("")) {
				order_pament_price = order_pament_price.add(productSku.getProduct_price());
			} else {
				Boolean isExit = false;

				JSONArray array = productSku.getMember_level_price();

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
					order_pament_price = order_pament_price.add(productSku.getProduct_price());
				}
			}
		}
		orderMap.setOrder_payment_price(order_pament_price);

		orderMap.setOrder_payment_amount(orderMap.getCartList().size());

		orderDao.save(orderMap, member.getMember_level_id(), member.getMember_level_name(), member.getMember_level_value(), request_user_id);

		//保存购物车里的商品
		List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
		List<ProductLockStock> productLockStockList = new ArrayList<ProductLockStock>();
		for(ProductSku productSku : productSkuList) {
			BigDecimal product_payment_price = BigDecimal.valueOf(0);

			if(member.getMember_level_id().equals("")) {
				product_payment_price = productSku.getProduct_price();
			} else {
				Boolean isExit = false;

				JSONArray array = productSku.getMember_level_price();

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
					product_payment_price = productSku.getProduct_price();
				}
			}

			OrderProduct orderProduct = new OrderProduct();
			orderProduct.setOrder_id(orderMap.getOrder_id());
			orderProduct.setCategory_id(productSku.getCategory_id());
			orderProduct.setCategory_name(productSku.getCategory_name());
			orderProduct.setBrand_id(productSku.getBrand_id());
			orderProduct.setBrand_name(productSku.getBrand_name());
			orderProduct.setProduct_id(productSku.getProduct_id());
			orderProduct.setProduct_name(productSku.getProduct_name());
			orderProduct.setProduct_image(productSku.getProduct_image().toJSONString());
			orderProduct.setProduct_is_new(productSku.getProduct_is_new());
			orderProduct.setProduct_is_recommend(productSku.getProduct_is_recommend());
			orderProduct.setProduct_is_bargain(productSku.getProduct_is_bargain());
			orderProduct.setProduct_is_hot(productSku.getProduct_is_hot());
			orderProduct.setProduct_is_sell_out(productSku.getProduct_is_sell_out());
			orderProduct.setProduct_is_sale(productSku.getProduct_is_sale());
			orderProduct.setProduct_content(productSku.getProduct_content());
			orderProduct.setProduct_sku_value(productSku.getProduct_sku_value().toJSONString());
			orderProduct.setProduct_sku_id(productSku.getProduct_sku_id());
			orderProduct.setProduct_attribute_value(productSku.getProduct_attribute_value().toJSONString());
			orderProduct.setProduct_price(productSku.getProduct_price());
			orderProduct.setMember_level_price(productSku.getMember_level_price().toJSONString());
			orderProduct.setProduct_payment_price(product_payment_price);
			orderProduct.setProduct_payment_amount(0);
			for(Cart cart : orderMap.getCartList()) {
				if(cart.getProduct_sku_id().equals(productSku.getProduct_sku_id())) {
					orderProduct.setProduct_payment_amount(cart.getCart_product_amount());
				}
			}
			orderProductList.add(orderProduct);

			ProductLockStock productLockStock = new ProductLockStock();
			productLockStock.setOrder_id(orderMap.getOrder_id());
			productLockStock.setProduct_sku_id(productSku.getProduct_sku_id());
			productLockStock.setProduct_lock_stock(0);
			for(Cart cart : orderMap.getCartList()) {
				if(cart.getProduct_sku_id().equals(productSku.getProduct_sku_id())) {
					productLockStock.setProduct_lock_stock(cart.getCart_product_amount());
				}
			}
			productLockStockList.add(productLockStock);
		}

		orderProductService.saveByOrderProductList(orderProductList, request_user_id);

		productLockStockService.save(productLockStockList, request_user_id);
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

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		//Cart cartMap = jsonObject.toJavaObject(Cart.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Order> orderList = orderDao.listByUser_id(request_user_id, Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for(Order order : orderList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Order.KEY_ORDER_ID, order.getOrder_id());
			map.put(Order.KEY_ORDER_NO, order.getOrder_no());
			map.put(Order.KEY_ORDER_PAYMENT_AMOUNT, order.getOrder_payment_amount());
			map.put(Order.KEY_ORDER_PAYMENT_PRICE, order.getOrder_payment_price());

			resultList.add(map);
		}

		return resultList;
	}

}
