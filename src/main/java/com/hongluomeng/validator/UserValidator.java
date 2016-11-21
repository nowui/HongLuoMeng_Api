package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.User;

public class UserValidator extends BaseValidator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		User user = jsonObject.toJavaObject(User.class);

		switch (actionKey) {
			case "/user/list":
				isExit = true;

				message += Utility.checkPageAndLimit(jsonObject);
				break;
			case "/user/find":
				isExit = true;

				if (Utility.isNullOrEmpty(user.getUser_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case "/user/save":
			case "/user/update":
				isExit = true;

				if (actionKey.equals("/user/update") && Utility.isNullOrEmpty(user.getUser_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(user.getUser_account())) {
					message += "帐号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(user.getUser_password())) {
					message += "密码为空";
					message += Const.LINE_FEED;
				}
				break;
			case "/user/menu/list":
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

}
