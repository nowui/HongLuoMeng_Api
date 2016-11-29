package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Admin;
import com.hongluomeng.model.User;
import com.jfinal.validate.Validator;

public class AdminValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Admin admin = jsonObject.toJavaObject(Admin.class);

		User user = jsonObject.toJavaObject(User.class);

		Boolean isExit = false;

		switch (actionKey) {
			case Url.URL_ADMIN_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);

				break;
			case Url.URL_ADMIN_FIND: {
				isExit = true;

				admin.checkAdmin_id();

				break;
			}
			case Url.URL_ADMIN_SAVE: {
				isExit = true;

				admin.checkAdmin_name();

				user.checkUser_account();

				user.checkUser_password();

				break;
			}
			case Url.URL_ADMIN_UPDATE: {
				isExit = true;

				admin.checkAdmin_id();

				admin.checkAdmin_name();

				user.checkUser_account();

				break;
			}
			case Url.URL_ADMIN_DELETE: {
				isExit = true;

				admin.checkAdmin_id();

				break;
			}
			case Url.URL_ADMIN_OPERATION_LIST: {
				isExit = true;

				user.checkUser_id();

				break;
			}
			case Url.URL_ADMIN_OPERATION_UPDATE: {
				isExit = true;

				user.checkUser_id();

				Utility.checkList(jsonObject);

				break;
			}
			case Url.URL_ADMIN_LOGIN: {
				isExit = true;

				user.checkUser_account();

				user.checkUser_password();

				break;
			}
		}

		if (!isExit) {
			addError(Const.KEY_ERROR, Const.URL_DENIED);
		}
	}

	protected void handleError(Controller controller) {
		controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, controller.getAttr(Const.KEY_ERROR), null));
	}

}
