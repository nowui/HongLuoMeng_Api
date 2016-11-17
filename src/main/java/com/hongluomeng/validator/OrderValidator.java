package com.hongluomeng.validator;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Cart;
import com.hongluomeng.model.Order;
import com.hongluomeng.type.CodeEnum;

public class OrderValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Order order = jsonObject.toJavaObject(Order.class);

		if(actionKey.equals(Const.URL_ORDER_LIST)) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);
		} else if(actionKey.equals(Const.URL_ORDER_FIND)) {
			isExit = true;

			if(Utility.isNullOrEmpty(order.getOrder_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ORDER_SAVE) || actionKey.equals(Const.URL_ORDER_CART_SAVE) || actionKey.equals(Const.URL_ORDER_UPDATE)) {
			isExit = true;

			if(actionKey.equals(Const.URL_ORDER_UPDATE) && Utility.isNullOrEmpty(order.getOrder_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(order.getOrder_message())) {
				message += "卖家留言为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(order.getOrder_delivery_name())) {
				message += "收货人为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(order.getOrder_delivery_phone())) {
				message += "电话为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(order.getOrder_delivery_province())) {
				message += "省份为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(order.getOrder_delivery_city())) {
				message += "城市为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(order.getOrder_delivery_area())) {
				message += "地区为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(order.getOrder_delivery_address())) {
				message += "详细地址为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(order.getOrder_delivery_zip())) {
				message += "邮政编码为空";
				message += Const.LINE_FEED;
			} else {
				if(order.getOrder_delivery_zip().length() > 6) {
					message += "邮政编码过长";
					message += Const.LINE_FEED;
				}
			}

			if(Utility.isNullOrEmpty(order.getCartList())) {
				message += "商品为空";
				message += Const.LINE_FEED;
			} else {
				List<Cart> cartList = order.getCartList();

				if(cartList.size() == 0) {
					message += "商品参数格为空";
					message += Const.LINE_FEED;
				}

				Boolean isError = false;

				for(Cart cart : cartList) {
					if(Utility.isNullOrEmpty(cart.getProduct_sku_id())) {
						isError = true;
					}

					if(Utility.isNullOrEmpty(cart.getProduct_amount())) {
						isError = true;
					}
				}

				if(isError) {
					message += "商品参数格式错误";
					message += Const.LINE_FEED;
				}
			}
		} else if(actionKey.equals(Const.URL_ORDER_DELETE)) {
			isExit = true;

			if(Utility.isNullOrEmpty(order.getOrder_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ORDER_LIST_GET)) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);
		}

		if (! isExit) {
	        addError(Const.KEY_MESSAGE, Const.URL_DENIED);
		}

		if (! Utility.isNullOrEmpty(message)) {
	        addError(Const.KEY_MESSAGE, message);
		}
	}

	protected void handleError(Controller c) {
		c.renderJson(Utility.setResponse(CodeEnum.CODE_400, c.getAttr(Const.KEY_MESSAGE), null));
	}

}
