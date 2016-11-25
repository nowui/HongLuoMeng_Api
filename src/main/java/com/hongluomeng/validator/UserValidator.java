package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.User;
import com.jfinal.validate.Validator;

public class UserValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		User user = jsonObject.toJavaObject(User.class);

		Boolean isExit = false;

		switch (actionKey) {
			case "/user/list":
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case "/user/find":
				isExit = true;

				user.checkUser_id();

				break;
			case "/user/save":
				isExit = true;

				user.checkUser_account();

				user.checkUser_password();

				break;
			case "/user/update":
				isExit = true;

				user.checkUser_id();

				user.checkUser_account();

				user.checkUser_password();

				break;
			case "/user/menu/list":
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
