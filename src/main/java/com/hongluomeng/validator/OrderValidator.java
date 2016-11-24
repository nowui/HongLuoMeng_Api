package com.hongluomeng.validator;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Cart;
import com.hongluomeng.model.Order;
import com.jfinal.validate.Validator;

public class OrderValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		switch (actionKey) {
			case Url.URL_ORDER_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case Url.URL_ORDER_FIND: {
				isExit = true;

				Order order = jsonObject.toJavaObject(Order.class);

				if (Utility.isNullOrEmpty(order.getOrder_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_ORDER_SAVE:
			case Url.URL_ORDER_CART_SAVE:
			case Url.URL_ORDER_UPDATE: {
				isExit = true;

				Order order = jsonObject.toJavaObject(Order.class);

				if (actionKey.equals(Url.URL_ORDER_UPDATE) && Utility.isNullOrEmpty(order.getOrder_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(order.getOrder_pay_type())) {
					message += "支付类型为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(order.getMember_delivery_id())) {
					message += "会员收货编号为空";
					message += Const.LINE_FEED;
				}

				if (actionKey.equals(Url.URL_ORDER_SAVE)) {
					Cart cart = jsonObject.toJavaObject(Cart.class);

					if (Utility.isNullOrEmpty(cart.getProduct_sku_id())) {
						message += "商品SKU编号为空";
						message += Const.LINE_FEED;
					}

					if (Utility.isNullOrEmpty(cart.getProduct_amount())) {
						message += "商品数量为空";
						message += Const.LINE_FEED;
					}
				}

				if (actionKey.equals(Url.URL_ORDER_CART_SAVE)) {
					if (Utility.isNullOrEmpty(order.getCartList())) {
						message += "商品为空";
						message += Const.LINE_FEED;
					} else {
						List<Cart> cartList = order.getCartList();

						if (cartList.size() == 0) {
							message += "商品参数格为空";
							message += Const.LINE_FEED;
						}

						Boolean isError = false;

						for (Cart cart : cartList) {
							if (Utility.isNullOrEmpty(cart.getProduct_sku_id())) {
								isError = true;
							}

							if (Utility.isNullOrEmpty(cart.getProduct_amount())) {
								isError = true;
							}
						}

						if (isError) {
							message += "商品参数格式错误";
							message += Const.LINE_FEED;
						}
					}
				}

				break;
			}
			case Url.URL_ORDER_DELETE: {
				isExit = true;

				Order order = jsonObject.toJavaObject(Order.class);

				if (Utility.isNullOrEmpty(order.getOrder_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_ORDER_LIST_GET: {
				isExit = true;

				Order order = jsonObject.toJavaObject(Order.class);

				if (Utility.isNull(order.getOrder_flow_status())) {
					message += "订单流程状态为空";
					message += Const.LINE_FEED;
				}

				Utility.checkPageAndLimit(jsonObject);
				break;
			}
			case Url.URL_ORDER_PAYED: {
				isExit = true;

				Order order = jsonObject.toJavaObject(Order.class);

				if (Utility.isNull(order.getOrder_id())) {
					message += "订单编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_ORDER_NOTIFY:
				isExit = true;
				break;
		}

		if (! isExit) {
	        addError(Const.KEY_MESSAGE, Const.URL_DENIED);
		}

		if (! Utility.isNullOrEmpty(message)) {
	        addError(Const.KEY_MESSAGE, message);
		}
	}

	protected void handleError(Controller controller) {
		controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, controller.getAttr(Const.KEY_MESSAGE), null));
	}

}
