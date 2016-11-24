package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Activity;
import com.jfinal.validate.Validator;

public class ActivityValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Activity activity = jsonObject.toJavaObject(Activity.class);

		Boolean isExit = false;

		switch (actionKey) {
			case Url.URL_ACTIVITY_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case Url.URL_ACTIVITY_FIND:
				isExit = true;

				break;
			case Url.URL_ACTIVITY_SAVE: {
				isExit = true;


				activity.checkActivity_name();

				break;
			}
			case Url.URL_ACTIVITY_UPDATE: {
				isExit = true;

				activity.checkActivity_id();

				activity.checkActivity_name();

				break;
			}
			case Url.URL_ACTIVITY_DELETE: {
				isExit = true;

				activity.checkActivity_id();

				break;
			}
			case Url.URL_ACTIVITY_LIST_GET: {
				Utility.checkPageAndLimit(jsonObject);

				break;
			}
			case Url.URL_ACTIVITY_GET: {
				isExit = true;

				activity.checkActivity_id();

				break;
			}
		}

		if (!isExit) {
			controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, Const.URL_DENIED, null));
		}
	}

	protected void handleError(Controller controller) {

	}

}
