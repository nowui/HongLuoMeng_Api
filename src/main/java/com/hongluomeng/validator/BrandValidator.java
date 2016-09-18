package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.Category;
import com.hongluomeng.type.CodeEnum;

public class BrandValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		if(actionKey.equals(Const.URL_BRAND_LIST)) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);
		} else if(actionKey.equals(Const.URL_BRAND_FIND)) {
			isExit = true;

			Brand brand = jsonObject.toJavaObject(Brand.class);

			if(Utility.isNullOrEmpty(brand.getBrand_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_BRAND_SAVE) || actionKey.equals(Const.URL_BRAND_UPDATE)) {
			isExit = true;

			Brand brand = jsonObject.toJavaObject(Brand.class);

			if(actionKey.equals(Const.URL_BRAND_UPDATE) && Utility.isNullOrEmpty(brand.getBrand_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(brand.getCategory_id())) {
				message += "分类为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(brand.getBrand_name())) {
				message += "名称为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNull(brand.getBrand_logo())) {
				message += "logo为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNull(brand.getBrand_introduce())) {
				message += "介绍为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNull(brand.getBrand_agreement())) {
				message += "协议书为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_BRAND_DELETE)) {
			isExit = true;

			Brand brand = jsonObject.toJavaObject(Brand.class);

			if(Utility.isNullOrEmpty(brand.getBrand_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_BRAND_CATEGORY_LIST)) {
			isExit = true;

		} else if(actionKey.equals(Const.URL_BRAND_CATEGORY_FIND)) {
			isExit = true;

			/*Category category = jsonObject.toJavaObject(Category.class);

			if(Utility.isNullOrEmpty(category.getCategory_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}*/
		} else if(actionKey.equals(Const.URL_BRAND_CATEGORY_SAVE) || actionKey.equals(Const.URL_BRAND_CATEGORYT_UPDATE)) {
			isExit = true;

			Category category = jsonObject.toJavaObject(Category.class);

			if(actionKey.equals(Const.URL_BRAND_CATEGORY_SAVE) && Utility.isNullOrEmpty(category.getParent_id())) {
				message += "父编号为空";
				message += Const.LINE_FEED;
			}

			if(actionKey.equals(Const.URL_BRAND_CATEGORYT_UPDATE) && Utility.isNullOrEmpty(category.getCategory_id())) {
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
		} else if(actionKey.equals(Const.URL_BRAND_CATEGORYT_DELETE)) {
			isExit = true;

			Category category = jsonObject.toJavaObject(Category.class);

			if(Utility.isNullOrEmpty(category.getCategory_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_BRAND_CATEGORY_LIST_GET)) {
			isExit = true;

		} else if(actionKey.equals(Const.URL_BRAND_LIST_GET)) {
			isExit = true;

			Category category = jsonObject.toJavaObject(Category.class);

			if(Utility.isNull(category.getCategory_id())) {
				message += "分类编号为空";
				message += Const.LINE_FEED;
			}

			message += Utility.checkPageAndLimit(jsonObject);

		} else if(actionKey.equals(Const.URL_BRAND_MY_LIST_GET)) {
			isExit = true;

			Category category = jsonObject.toJavaObject(Category.class);

			if(Utility.isNull(category.getCategory_id())) {
				message += "分类编号为空";
				message += Const.LINE_FEED;
			}

			message += Utility.checkPageAndLimit(jsonObject);
		} else if(actionKey.equals(Const.URL_BRAND_GET)) {
			isExit = true;

			Brand brand = jsonObject.toJavaObject(Brand.class);

			if(Utility.isNullOrEmpty(brand.getBrand_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_BRAND_APPLY)) {
			isExit = true;

			Brand brand = jsonObject.toJavaObject(Brand.class);

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
