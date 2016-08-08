package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Role;
import com.hongluomeng.model.RoleOperation;
import com.hongluomeng.type.CodeEnum;

public class RoleValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Role role = jsonObject.toJavaObject(Role.class);

		if(actionKey.equals(Const.URL_ROLE_LIST)) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);

			if(Utility.isNullOrEmpty(role.getGroup_id())) {
				message += "分组编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ROLE_FIND)) {
			isExit = true;

			if(Utility.isNullOrEmpty(role.getRole_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ROLE_SAVE) || actionKey.equals(Const.URL_ROLE_UPDATE)) {
			isExit = true;

			if(actionKey.equals(Const.URL_ROLE_SAVE) && Utility.isNullOrEmpty(role.getGroup_id())) {
				message += "分组编号为空";
				message += Const.LINE_FEED;
			}

			if(actionKey.equals(Const.URL_ROLE_UPDATE) && Utility.isNullOrEmpty(role.getRole_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(role.getRole_name())) {
				message += "名称为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(role.getRole_sort())) {
				message += "密码为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ROLE_DELETE)) {
			isExit = true;

			if(Utility.isNullOrEmpty(role.getRole_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ROLE_OPERATION_LIST)) {
			isExit = true;

			if(Utility.isNullOrEmpty(role.getRole_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_ROLE_OPERATION_UPDATE)) {
			isExit = true;

			if(Utility.isNullOrEmpty(role.getRole_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(jsonObject.get(RoleOperation.KEY_ROLE_OPERATION) == null) {
				message += "角色操作为空";
				message += Const.LINE_FEED;
			}
		}

		if (! isExit) {
	        addError(Const.KEY_MESSAGE, Const.PERMISSION_DENIED);
		}

		if (! Utility.isNullOrEmpty(message)) {
	        addError(Const.KEY_MESSAGE, message);
		}
	}

	protected void handleError(Controller c) {
		c.renderJson(Utility.setResponse(CodeEnum.CODE_400, c.getAttr(Const.KEY_MESSAGE), null));
	}

}
