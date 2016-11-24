package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Cart;
import com.jfinal.validate.Validator;

public class CartValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Cart cart = jsonObject.toJavaObject(Cart.class);

		switch (actionKey) {
			case Url.URL_CART_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case Url.URL_CART_FIND:
				isExit = true;

				if (Utility.isNullOrEmpty(cart.getCart_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Url.URL_CART_SAVE:
			case Url.URL_CART_UPDATE:
				isExit = true;

				if (actionKey.equals(Url.URL_CART_UPDATE) && Utility.isNullOrEmpty(cart.getCart_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (actionKey.equals(Url.URL_CART_SAVE) && Utility.isNullOrEmpty(cart.getProduct_sku_id())) {
					message += "商品SKU编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(cart.getProduct_amount())) {
					message += "商品数量为空";
					message += Const.LINE_FEED;
				}
				break;
			case Url.URL_CART_DELETE:
				isExit = true;

				if (Utility.isNullOrEmpty(cart.getCart_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Url.URL_CART_LIST_GET:
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
		controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, controller.getAttrForStr(Const.KEY_MESSAGE), null));
	}

}
