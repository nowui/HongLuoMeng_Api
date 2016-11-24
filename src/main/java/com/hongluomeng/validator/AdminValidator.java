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

		Boolean isExit = false;

		String message = "";

		switch (actionKey) {
			case Url.URL_ADMIN_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);

				break;
			case Url.URL_ADMIN_FIND: {
				isExit = true;

				Admin admin = jsonObject.toJavaObject(Admin.class);

				admin.checkAdmin_id();

				break;
			}
			case Url.URL_ADMIN_SAVE: {
				isExit = true;

				Admin admin = jsonObject.toJavaObject(Admin.class);

				admin.checkAdmin_name();

				break;
			}
			case Url.URL_ADMIN_UPDATE: {
				isExit = true;

				Admin admin = jsonObject.toJavaObject(Admin.class);

				User user = jsonObject.toJavaObject(User.class);

				admin.checkAdmin_id();

				admin.checkAdmin_name();

				if (Utility.isNullOrEmpty(user.getUser_account())) {
					message += "帐号为空";
					message += Const.LINE_FEED;
				}

				if (actionKey.equals(Url.URL_ADMIN_SAVE) && Utility.isNullOrEmpty(user.getUser_password())) {
					message += "密码为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_ADMIN_DELETE: {
				isExit = true;

				Admin admin = jsonObject.toJavaObject(Admin.class);

				if (Utility.isNullOrEmpty(admin.getAdmin_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_ADMIN_OPERATION_LIST: {
				isExit = true;

				Admin admin = jsonObject.toJavaObject(Admin.class);

				if (Utility.isNullOrEmpty(admin.getUser_id())) {
					message += "用户编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_ADMIN_OPERATION_UPDATE: {
				isExit = true;

				Admin admin = jsonObject.toJavaObject(Admin.class);

				if (Utility.isNullOrEmpty(admin.getUser_id())) {
					message += "用户编号为空";
					message += Const.LINE_FEED;
				}

				if (jsonObject.get(Const.KEY_LIST) == null) {
					message += "用户角色为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_ADMIN_LOGIN: {
				isExit = true;

				User user = jsonObject.toJavaObject(User.class);

				if (Utility.isNullOrEmpty(user.getUser_account())) {
					message += "帐号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(user.getUser_password())) {
					message += "密码为空";
					message += Const.LINE_FEED;
				}
				break;
			}
		}

		if (! isExit) {
	        addError(Const.KEY_MESSAGE, Const.URL_DENIED);
		}

		if (! Utility.isNullOrEmpty(message)) {
	        addError(Const.KEY_MESSAGE, message);
		}
	}

	protected void handleError(Controller controller) {
		controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, controller.getAttr(Const.KEY_MESSAGE), null));
	}

}
