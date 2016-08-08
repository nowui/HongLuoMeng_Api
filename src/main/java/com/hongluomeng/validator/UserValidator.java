package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.User;
import com.hongluomeng.type.CodeEnum;

public class UserValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		User user = jsonObject.toJavaObject(User.class);

		if(actionKey.equals("/user/list")) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);
		} else if(actionKey.equals("/user/find")) {
			isExit = true;

			if(Utility.isNullOrEmpty(user.getUser_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals("/user/save") || actionKey.equals("/user/update")) {
			isExit = true;

			if(actionKey.equals("/user/update") && Utility.isNullOrEmpty(user.getUser_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(user.getUser_account())) {
				message += "帐号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(user.getUser_password())) {
				message += "密码为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals("/user/menu/list")) {
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
