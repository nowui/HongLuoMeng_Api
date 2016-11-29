package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Authorization;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.validate.Validator;

public class AuthorizationValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		Authorization authorization = jsonObject.toJavaObject(Authorization.class);

		switch (actionKey) {
			case Url.URL_AUTHORIZATION_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);

				break;
			case Url.URL_AUTHORIZATION_FIND:
				isExit = true;

				authorization.checkAuthorization_id();

				break;
		}

		if (!isExit) {
			addError(Const.KEY_ERROR, Const.URL_DENIED);
		}
	}

	protected void handleError(Controller controller) {
		controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, controller.getAttr(Const.KEY_ERROR), null));
	}

}
