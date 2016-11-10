package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Cart;
import com.hongluomeng.type.CodeEnum;

public class CartValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Cart cart = jsonObject.toJavaObject(Cart.class);

		if(actionKey.equals(Const.URL_CART_LIST)) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);
		} else if(actionKey.equals(Const.URL_CART_FIND)) {
			isExit = true;

			if(Utility.isNullOrEmpty(cart.getCart_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_CART_SAVE) || actionKey.equals(Const.URL_CART_UPDATE)) {
			isExit = true;

			if(actionKey.equals(Const.URL_CART_UPDATE) && Utility.isNullOrEmpty(cart.getCart_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(actionKey.equals(Const.URL_CART_SAVE) && Utility.isNullOrEmpty(cart.getProduct_sku_id())) {
				message += "商品SKU编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(cart.getCart_product_amount())) {
				message += "商品数量为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_CART_DELETE)) {
			isExit = true;

			if(Utility.isNullOrEmpty(cart.getCart_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_CART_LIST_GET)) {
			isExit = true;
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
