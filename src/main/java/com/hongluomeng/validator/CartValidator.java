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

		Cart cart = jsonObject.toJavaObject(Cart.class);

		switch (actionKey) {
			case Url.URL_CART_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case Url.URL_CART_FIND:
				isExit = true;

				cart.checkCart_id();

				break;
			case Url.URL_CART_SAVE: {
				isExit = true;

				cart.checkCart_id();

				cart.checkProduct_sku_id();

				cart.checkProduct_amount();

				break;
			}
			case Url.URL_CART_UPDATE:
				isExit = true;

				cart.checkCart_id();

				cart.checkProduct_amount();

				break;
			case Url.URL_CART_DELETE:
				isExit = true;

				cart.checkCart_id();

				break;
			case Url.URL_CART_LIST_GET:
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
