package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.type.CodeEnum;

public class CategoryValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Category category = jsonObject.toJavaObject(Category.class);

		if(actionKey.equals(Const.URL_CATEGORY_LIST)) {
			isExit = true;

		} else if(actionKey.equals(Const.URL_CATEGORY_FIND)) {
			isExit = true;

			if(Utility.isNullOrEmpty(category.getCategory_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_CATEGORY_SAVE) || actionKey.equals(Const.URL_CATEGORY_UPDATE)) {
			isExit = true;

			if(actionKey.equals(Const.URL_CATEGORY_UPDATE) && Utility.isNullOrEmpty(category.getCategory_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(category.getCategory_name())) {
				message += "用户名为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(category.getCategory_sort())) {
				message += "排序为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_CATEGORY_DELETE)) {
			isExit = true;

			if(Utility.isNullOrEmpty(category.getCategory_id())) {
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
