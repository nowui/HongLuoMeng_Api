package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Attribute;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.validate.Validator;

public class AttributeValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		Attribute attribute = jsonObject.toJavaObject(Attribute.class);

		switch (actionKey) {
			case Url.URL_ATTRIBUTE_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case Url.URL_ATTRIBUTE_FIND:
				isExit = true;

				attribute.checkAttribute_id();

				break;
			case Url.URL_ATTRIBUTE_SAVE: {
				attribute.checkAttribute_name();

				attribute.checkAttribute_input_type();

				attribute.checkAttribute_type();

				attribute.checkAttribute_sort();
			}
			case Url.URL_ATTRIBUTE_UPDATE:
				isExit = true;

				attribute.checkAttribute_id();

				attribute.checkAttribute_name();

				attribute.checkAttribute_input_type();

				attribute.checkAttribute_type();

				attribute.checkAttribute_sort();

				break;
			case Url.URL_ATTRIBUTE_DELETE:
				isExit = true;

				attribute.checkAttribute_id();

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
