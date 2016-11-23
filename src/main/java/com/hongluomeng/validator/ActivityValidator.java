package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Activity;
import com.hongluomeng.model.Category;

public class ActivityValidator extends BaseValidator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		switch (actionKey) {
			case Const.URL_ACTIVITY_LIST:
				isExit = true;

				message += Utility.checkPageAndLimit(jsonObject);
				break;
			case Const.URL_ACTIVITY_FIND:
				isExit = true;

				break;
			case Const.URL_ACTIVITY_SAVE:
			case Const.URL_ACTIVITY_UPDATE: {
				isExit = true;

				Activity brand = jsonObject.toJavaObject(Activity.class);

				if (actionKey.equals(Const.URL_ACTIVITY_UPDATE) && Utility.isNullOrEmpty(brand.getActivity_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(brand.getActivity_name())) {
					message += "名称为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNull(brand.getActivity_logo())) {
					message += "logo为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Const.URL_ACTIVITY_DELETE: {
				isExit = true;

				Activity brand = jsonObject.toJavaObject(Activity.class);

				if (Utility.isNullOrEmpty(brand.getActivity_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Const.URL_ACTIVITY_LIST_GET: {
				isExit = true;

				Category category = jsonObject.toJavaObject(Category.class);

				if (Utility.isNull(category.getCategory_id())) {
					message += "分类编号为空";
					message += Const.LINE_FEED;
				}

				message += Utility.checkPageAndLimit(jsonObject);
				break;
			}
			case Const.URL_ACTIVITY_GET: {
				isExit = true;

				Activity brand = jsonObject.toJavaObject(Activity.class);

				if (Utility.isNullOrEmpty(brand.getActivity_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
		}

		if (! isExit) {
	        addError(Const.KEY_MESSAGE, Const.URL_DENIED);
		}

		if (! Utility.isNullOrEmpty(message)) {
	        addError(Const.KEY_MESSAGE, message);
		}
	}

}
