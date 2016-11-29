package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Url;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class MenuValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Category category = jsonObject.toJavaObject(Category.class);

		Boolean isExit = false;

		switch (actionKey) {
			case Url.URL_MENU_LIST:
				isExit = true;

				break;
			case Url.URL_MENU_FIND:
				isExit = true;

				break;
			case Url.URL_MENU_SAVE: {
				isExit = true;

				category.checkParent_id();

				category.checkCategory_name();

				category.checkCategory_sort();

				break;
			}
			case Url.URL_MENU_UPDATE:
				isExit = true;

				category.checkCategory_id();

				category.checkCategory_name();

				category.checkCategory_sort();

				break;
			case Url.URL_MENU_DELETE:
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
