package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.MemberLevel;
import com.jfinal.validate.Validator;

public class MemberLevelValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		MemberLevel memberLevel = jsonObject.toJavaObject(MemberLevel.class);

		switch (actionKey) {
			case Url.URL_MEMBER_LEVEL_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case Url.URL_MEMBER_LEVEL_FIND:
				isExit = true;

				if (Utility.isNullOrEmpty(memberLevel.getMember_level_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Url.URL_MEMBER_LEVEL_SAVE:
			case Url.URL_MEMBER_LEVEL_UPDATE:
				isExit = true;

				if (actionKey.equals(Url.URL_MEMBER_LEVEL_UPDATE) && Utility.isNullOrEmpty(memberLevel.getMember_level_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(memberLevel.getMember_level_name())) {
					message += "名称为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(memberLevel.getMember_level_value())) {
					message += "粉丝数为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(memberLevel.getMember_level_sort())) {
					message += "排序为空";
					message += Const.LINE_FEED;
				}
				break;
			case Url.URL_MEMBER_LEVEL_DELETE:
				isExit = true;

				if (Utility.isNullOrEmpty(memberLevel.getMember_level_id())) {
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

	protected void handleError(Controller controller) {
		controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, controller.getAttrForStr(Const.KEY_MESSAGE), null));
	}

}
