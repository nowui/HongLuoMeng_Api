package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Admin;
import com.hongluomeng.model.User;
import com.hongluomeng.model.UserRole;
import com.hongluomeng.type.CodeEnum;

public class AdminValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		if(actionKey.equals(Const.URL_ADMIN_LIST)) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);
		} else if(actionKey.equals(Const.URL_ADMIN_FIND)) {
			isExit = true;

			Admin admin = jsonObject.toJavaObject(Admin.class);

			if(Utility.isNullOrEmpty(admin.getAdmin_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ADMIN_SAVE) || actionKey.equals(Const.URL_ADMIN_UPDATE)) {
			isExit = true;

			Admin admin = jsonObject.toJavaObject(Admin.class);

			User user = jsonObject.toJavaObject(User.class);

			if(actionKey.equals(Const.URL_ADMIN_UPDATE) && Utility.isNullOrEmpty(admin.getAdmin_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(admin.getAdmin_name())) {
				message += "名称为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(user.getUser_account())) {
				message += "帐号为空";
				message += Const.LINE_FEED;
			}

			if(actionKey.equals(Const.URL_ADMIN_SAVE) && Utility.isNullOrEmpty(user.getUser_password())) {
				message += "密码为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ADMIN_DELETE)) {
			isExit = true;

			Admin admin = jsonObject.toJavaObject(Admin.class);

			if(Utility.isNullOrEmpty(admin.getAdmin_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ADMIN_OPERATION_LIST)) {
			isExit = true;

			Admin admin = jsonObject.toJavaObject(Admin.class);

			if(Utility.isNullOrEmpty(admin.getUser_id())) {
				message += "用户编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ADMIN_OPERATION_UPDATE)) {
			isExit = true;

			Admin admin = jsonObject.toJavaObject(Admin.class);

			if(Utility.isNullOrEmpty(admin.getUser_id())) {
				message += "用户编号为空";
				message += Const.LINE_FEED;
			}

			if(jsonObject.get(UserRole.KEY_TABLE_USER_ROLE) == null) {
				message += "用户角色为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ADMIN_LOGIN)) {
			isExit = true;

			User user = jsonObject.toJavaObject(User.class);

			if(Utility.isNullOrEmpty(user.getUser_account())) {
				message += "帐号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(user.getUser_password())) {
				message += "密码为空";
				message += Const.LINE_FEED;
			}
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
