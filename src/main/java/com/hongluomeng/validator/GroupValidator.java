package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.jfinal.core.Controller;

public class GroupValidator extends BaseValidator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		switch (actionKey) {
			case Const.URL_GROUP_LIST:
				isExit = true;

				break;
			case Const.URL_GROUP_FIND:
				isExit = true;

				break;
			case Const.URL_GROUP_SAVE:
			case Const.URL_GROUP_UPDATE:
				isExit = true;

				Category category = jsonObject.toJavaObject(Category.class);

				if (actionKey.equals(Const.URL_GROUP_SAVE) && Utility.isNullOrEmpty(category.getParent_id())) {
					message += "父编号为空";
					message += Const.LINE_FEED;
				}

				if (actionKey.equals(Const.URL_GROUP_UPDATE) && Utility.isNullOrEmpty(category.getCategory_id())) {
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
			case Const.URL_GROUP_DELETE:
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

}
