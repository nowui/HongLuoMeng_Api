package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Operation;
import com.jfinal.validate.Validator;

public class OperationValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Operation operation = jsonObject.toJavaObject(Operation.class);

		switch (actionKey) {
			case Url.URL_OPERATION_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);

				operation.checkMenu_id();

				break;
			case Url.URL_OPERATION_FIND:
				isExit = true;

				operation.checkOperation_id();

				break;
			case Url.URL_OPERATION_SAVE: {
				isExit = true;

				operation.checkMenu_id();

				operation.checkOperation_name();

				operation.checkOperation_key();

				operation.checkOperation_value();

				operation.checkOperation_sort();

				break;
			}
			case Url.URL_OPERATION_UPDATE:
				isExit = true;

				operation.checkOperation_id();

				operation.checkOperation_name();

				operation.checkOperation_key();

				operation.checkOperation_value();

				operation.checkOperation_sort();

				break;
			case Url.URL_OPERATION_DELETE:
				isExit = true;

				operation.checkOperation_id();

				break;
		}

		if (!isExit) {
			controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, Const.URL_DENIED, null));
		}
	}

	protected void handleError(Controller controller) {

	}

}
