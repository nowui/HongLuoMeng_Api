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

		MemberLevel memberLevel = jsonObject.toJavaObject(MemberLevel.class);

		switch (actionKey) {
			case Url.URL_MEMBER_LEVEL_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);

				break;
			case Url.URL_MEMBER_LEVEL_FIND:
				isExit = true;

				memberLevel.checkMember_level_id();

				break;
			case Url.URL_MEMBER_LEVEL_SAVE: {
				isExit = true;

				memberLevel.checkMember_level_name();

				memberLevel.checkMember_level_value();

				memberLevel.checkMember_level_sort();

				break;
			}
			case Url.URL_MEMBER_LEVEL_UPDATE:
				isExit = true;

				memberLevel.checkMember_level_id();

				memberLevel.checkMember_level_name();

				memberLevel.checkMember_level_value();

				memberLevel.checkMember_level_sort();

				break;
			case Url.URL_MEMBER_LEVEL_DELETE:
				isExit = true;

				memberLevel.checkMember_level_id();

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
