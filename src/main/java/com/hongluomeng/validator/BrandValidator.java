package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.type.CodeEnum;

public class BrandValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		Brand brand = jsonObject.toJavaObject(Brand.class);

		if(actionKey.equals(Const.URL_BRAND_LIST)) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);
		} else if(actionKey.equals(Const.URL_BRAND_FIND)) {
			isExit = true;

			if(Utility.isNullOrEmpty(brand.getBrand_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_BRAND_SAVE) || actionKey.equals(Const.URL_BRAND_UPDATE)) {
			isExit = true;

			if(actionKey.equals(Const.URL_BRAND_UPDATE) && Utility.isNullOrEmpty(brand.getBrand_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(brand.getBrand_name())) {
				message += "名称为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_BRAND_DELETE)) {
			isExit = true;

			if(Utility.isNullOrEmpty(brand.getBrand_id())) {
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
