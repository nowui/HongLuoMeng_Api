package com.hongluomeng.validator;

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

		Order order = jsonObject.toJavaObject(Order.class);

		Boolean isExit = false;

		switch (actionKey) {
			case Url.URL_ORDER_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case Url.URL_ORDER_FIND: {
				isExit = true;

				order.checkOrder_id();

				break;
			}
			case Url.URL_ORDER_SAVE: {
				isExit = true;

				order.checkOrder_pay_type();

				order.checkMember_delivery_id();

				Cart cart = jsonObject.toJavaObject(Cart.class);

				cart.checkProduct_sku_id();

				cart.checkProduct_amount();

				break;
			}
			case Url.URL_ORDER_CART_SAVE: {
				isExit = true;

				order.checkOrder_pay_type();

				order.checkMember_delivery_id();

				order.checkCartList();

				break;
			}
			case Url.URL_ORDER_UPDATE: {
				isExit = true;

				order.checkOrder_id();

				order.checkOrder_pay_type();

				order.checkMember_delivery_id();

				break;
			}
			case Url.URL_ORDER_DELETE: {
				isExit = true;

				order.checkOrder_id();

				break;
			}
			case Url.URL_ORDER_LIST_GET: {
				isExit = true;

				order.checkOrder_flow_status();

				Utility.checkPageAndLimit(jsonObject);
				break;
			}
			case Url.URL_ORDER_PAYED: {
				isExit = true;

				order.checkOrder_id();

				break;
			}
			case Url.URL_ORDER_NOTIFY:
				isExit = true;
				break;
		}

		if (!isExit) {
			controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, Const.URL_DENIED, null));
		}
	}

	protected void handleError(Controller controller) {

	}

}
