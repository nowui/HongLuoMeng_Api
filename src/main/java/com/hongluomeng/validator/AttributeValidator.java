package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Attribute;
import com.hongluomeng.type.CodeEnum;

public class AttributeValidator extends BaseValidator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Attribute attribute = jsonObject.toJavaObject(Attribute.class);

		switch (actionKey) {
			case Const.URL_ATTRIBUTE_LIST:
				isExit = true;

				message += Utility.checkPageAndLimit(jsonObject);
				break;
			case Const.URL_ATTRIBUTE_FIND:
				isExit = true;

				if (Utility.isNullOrEmpty(attribute.getAttribute_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Const.URL_ATTRIBUTE_SAVE:
			case Const.URL_ATTRIBUTE_UPDATE:
				isExit = true;

				if (actionKey.equals(Const.URL_ATTRIBUTE_UPDATE) && Utility.isNullOrEmpty(attribute.getAttribute_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(attribute.getAttribute_name())) {
					message += "名称为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(attribute.getAttribute_input_type())) {
					message += "输入框为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(attribute.getAttribute_type())) {
					message += "类型为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(attribute.getAttribute_sort())) {
					message += "排序为空";
					message += Const.LINE_FEED;
				}
				break;
			case Const.URL_ATTRIBUTE_DELETE:
				isExit = true;

				if (Utility.isNullOrEmpty(attribute.getAttribute_id())) {
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
