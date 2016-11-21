package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Operation;

public class OperationValidator extends BaseValidator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Operation operation = jsonObject.toJavaObject(Operation.class);

		switch (actionKey) {
			case Const.URL_OPERATION_LIST:
				isExit = true;

				message += Utility.checkPageAndLimit(jsonObject);

				if (Utility.isNullOrEmpty(operation.getMenu_id())) {
					message += "菜单编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Const.URL_OPERATION_FIND:
				isExit = true;

				if (Utility.isNullOrEmpty(operation.getOperation_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Const.URL_OPERATION_SAVE:
			case Const.URL_OPERATION_UPDATE:
				isExit = true;

				if (actionKey.equals(Const.URL_OPERATION_SAVE) && Utility.isNullOrEmpty(operation.getMenu_id())) {
					message += "菜单编号为空";
					message += Const.LINE_FEED;
				}

				if (actionKey.equals(Const.URL_OPERATION_UPDATE) && Utility.isNullOrEmpty(operation.getOperation_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(operation.getOperation_name())) {
					message += "名称为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(operation.getOperation_sort())) {
					message += "密码为空";
					message += Const.LINE_FEED;
				}
				break;
			case Const.URL_OPERATION_DELETE:
				isExit = true;

				if (Utility.isNullOrEmpty(operation.getOperation_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
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
