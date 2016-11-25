package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Log;
import com.jfinal.validate.Validator;

public class LogValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		Log log = jsonObject.toJavaObject(Log.class);

		switch (actionKey) {
			case Url.URL_LOG_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);

				break;
			case Url.URL_LOG_FIND:
				isExit = true;

				log.checkLog_id();

				break;
		}

		if (!isExit) {
			controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, Const.URL_DENIED, null));
		}
	}

	protected void handleError(Controller controller) {

	}

}
