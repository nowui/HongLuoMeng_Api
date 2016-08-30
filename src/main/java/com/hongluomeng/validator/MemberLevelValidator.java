package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.MemberLevel;
import com.hongluomeng.type.CodeEnum;

public class MemberLevelValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		MemberLevel member_level = jsonObject.toJavaObject(MemberLevel.class);

		if(actionKey.equals(Const.URL_MEMBER_LEVEL_LIST)) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);
		} else if(actionKey.equals(Const.URL_MEMBER_LEVEL_FIND)) {
			isExit = true;

			if(Utility.isNullOrEmpty(member_level.getMember_level_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_MEMBER_LEVEL_SAVE) || actionKey.equals(Const.URL_MEMBER_LEVEL_UPDATE)) {
			isExit = true;

			if(actionKey.equals(Const.URL_MEMBER_LEVEL_UPDATE) && Utility.isNullOrEmpty(member_level.getMember_level_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(member_level.getMember_level_name())) {
				message += "名称为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_MEMBER_LEVEL_DELETE)) {
			isExit = true;

			if(Utility.isNullOrEmpty(member_level.getMember_level_id())) {
				message += "编号为空";
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
