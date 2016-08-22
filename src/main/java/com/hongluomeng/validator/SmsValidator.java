package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Sms;
import com.hongluomeng.type.CodeEnum;

public class SmsValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		if(actionKey.equals(Const.URL_SMS_REGISTER) || actionKey.equals(Const.URL_SMS_RESET_PASSWORD)) {
			isExit = true;

			Sms sms = jsonObject.toJavaObject(Sms.class);

			if(Utility.isNullOrEmpty(sms.getSms_phone())) {
				message += "手机号码为空";
				message += Const.LINE_FEED;
			} else {
				if (! Utility.isPhone(sms.getSms_phone())) {
					message += "手机号码格式不对";
					message += Const.LINE_FEED;
				}
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
