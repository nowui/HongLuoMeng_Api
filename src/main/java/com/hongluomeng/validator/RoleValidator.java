package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Role;
import com.jfinal.validate.Validator;

public class RoleValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Role role = jsonObject.toJavaObject(Role.class);

		Boolean isExit = false;

		switch (actionKey) {
			case Url.URL_ROLE_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);

				role.checkGroup_id();

				break;
			case Url.URL_ROLE_FIND:
				isExit = true;

				role.checkRole_id();

				break;
			case Url.URL_ROLE_SAVE:
				isExit = true;

				role.checkGroup_id();

				role.checkRole_name();

				role.checkRole_key();

				role.checkRole_sort();

				break;
			case Url.URL_ROLE_UPDATE:
				isExit = true;

				role.checkRole_id();

				role.checkRole_name();

				role.checkRole_key();

				role.checkRole_sort();
				break;
			case Url.URL_ROLE_DELETE:
				isExit = true;

				role.checkRole_id();

				break;
			case Url.URL_ROLE_OPERATION_LIST:
				isExit = true;

				role.checkRole_id();

				break;
			case Url.URL_ROLE_OPERATION_UPDATE:
				isExit = true;

				role.checkRole_id();

				Utility.checkList(jsonObject);

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
