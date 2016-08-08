package com.hongluomeng.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.type.CodeEnum;

public class ApplicationValidator extends Validator {

	protected void validate(Controller controller) {
		//String actionKey = getActionKey();

		//JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";



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
