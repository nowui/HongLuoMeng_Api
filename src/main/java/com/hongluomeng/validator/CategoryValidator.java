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

		String message = "";

		Category category = jsonObject.toJavaObject(Category.class);

		switch (actionKey) {
			case Url.URL_CATEGORY_LIST:
				isExit = true;

				break;
			case Url.URL_CATEGORY_FIND:
				isExit = true;

				if (Utility.isNullOrEmpty(category.getCategory_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Url.URL_CATEGORY_SAVE:
			case Url.URL_CATEGORY_UPDATE:
				isExit = true;

				if (actionKey.equals(Url.URL_CATEGORY_UPDATE) && Utility.isNullOrEmpty(category.getCategory_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(category.getCategory_name())) {
					message += "用户名为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(category.getCategory_sort())) {
					message += "排序为空";
					message += Const.LINE_FEED;
				}
				break;
			case Url.URL_CATEGORY_DELETE:
				isExit = true;

				if (Utility.isNullOrEmpty(category.getCategory_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			case Url.URL_CATEGORY_CHINA:
				isExit = true;
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
