package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.jfinal.validate.Validator;

public class CategoryValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		Category category = jsonObject.toJavaObject(Category.class);

		switch (actionKey) {
			case Url.URL_CATEGORY_LIST:
				isExit = true;

				break;
			case Url.URL_CATEGORY_FIND:
				isExit = true;

				category.checkCategory_id();

				break;
			case Url.URL_CATEGORY_SAVE: {
				isExit = true;

				category.checkCategory_name();

				category.checkCategory_sort();

				break;
			}
			case Url.URL_CATEGORY_UPDATE:
				isExit = true;

				category.checkCategory_id();

				category.checkCategory_name();

				category.checkCategory_sort();

				break;
			case Url.URL_CATEGORY_DELETE:
				isExit = true;

				category.checkCategory_id();

				break;
			case Url.URL_CATEGORY_CHINA:
				isExit = true;
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
