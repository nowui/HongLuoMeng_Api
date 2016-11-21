package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Role;

public class RoleValidator extends BaseValidator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Role role = jsonObject.toJavaObject(Role.class);

		switch (actionKey) {
			case Const.URL_ROLE_LIST:
				isExit = true;

				message += Utility.checkPageAndLimit(jsonObject);

				if (Utility.isNullOrEmpty(role.getGroup_id())) {
					message += "分组编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Const.URL_ROLE_FIND:
				isExit = true;

				if (Utility.isNullOrEmpty(role.getRole_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Const.URL_ROLE_SAVE:
			case Const.URL_ROLE_UPDATE:
				isExit = true;

				if (actionKey.equals(Const.URL_ROLE_SAVE) && Utility.isNullOrEmpty(role.getGroup_id())) {
					message += "分组编号为空";
					message += Const.LINE_FEED;
				}

				if (actionKey.equals(Const.URL_ROLE_UPDATE) && Utility.isNullOrEmpty(role.getRole_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(role.getRole_name())) {
					message += "名称为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(role.getRole_sort())) {
					message += "密码为空";
					message += Const.LINE_FEED;
				}
				break;
			case Const.URL_ROLE_DELETE:
				isExit = true;

				if (Utility.isNullOrEmpty(role.getRole_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Const.URL_ROLE_OPERATION_LIST:
				isExit = true;

				if (Utility.isNullOrEmpty(role.getRole_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Const.URL_ROLE_OPERATION_UPDATE:
				isExit = true;

				if (Utility.isNullOrEmpty(role.getRole_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(Const.KEY_LIST)) {
					message += "角色操作为空";
					message += Const.LINE_FEED;
				}
				break;
		}

		if (! isExit) {
	        addError(Const.KEY_MESSAGE, Const.PERMISSION_DENIED);
		}

		if (! Utility.isNullOrEmpty(message)) {
	        addError(Const.KEY_MESSAGE, message);
		}
	}

}
