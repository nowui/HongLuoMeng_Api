package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Authorization;
import com.hongluomeng.type.CodeEnum;

public class AuthorizationValidator extends BaseValidator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Authorization authorization = jsonObject.toJavaObject(Authorization.class);

		switch (actionKey) {
			case Const.URL_AUTHORIZATION_LIST:
				isExit = true;

				message += Utility.checkPageAndLimit(jsonObject);
				break;
			case Const.URL_AUTHORIZATION_FIND:
				isExit = true;

				if (Utility.isNullOrEmpty(authorization.getAuthorization_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
		}

		if (! isExit) {
	        addError(Const.KEY_MESSAGE, Const.URL_DENIED);
		}

		if (! Utility.isNullOrEmpty(message)) {
	        addError(Const.KEY_MESSAGE, message);
		}
	}

}
