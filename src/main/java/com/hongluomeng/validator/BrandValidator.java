package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.Member;
import com.jfinal.validate.Validator;

public class BrandValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Brand brand = jsonObject.toJavaObject(Brand.class);

		Category category = jsonObject.toJavaObject(Category.class);

		Member member = jsonObject.toJavaObject(Member.class);

		BrandApply brandApply = jsonObject.toJavaObject(BrandApply.class);

		Boolean isExit = false;

		switch (actionKey) {
			case Url.URL_BRAND_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case Url.URL_BRAND_FIND:
				isExit = true;

				break;
			case Url.URL_BRAND_SAVE: {
				brand.checkBrand_name();

				brand.checkBrand_logo();

				brand.checkBrand_introduce();

				brand.checkBrand_agreement();

				break;
			}
			case Url.URL_BRAND_UPDATE: {
				isExit = true;

				brand.checkBrand_id();

				brand.checkCategory_id();

				brand.checkBrand_name();

				brand.checkBrand_logo();

				brand.checkBrand_introduce();

				brand.checkBrand_agreement();

				break;
			}
			case Url.URL_BRAND_DELETE: {
				isExit = true;

				brand.checkBrand_id();

				break;
			}
			case Url.URL_BRAND_CATEGORY_LIST:
				isExit = true;

				break;
			case Url.URL_BRAND_CATEGORY_FIND:
				isExit = true;

				break;
			case Url.URL_BRAND_CATEGORY_SAVE: {
				category.checkParent_id();

				category.checkCategory_name();

				category.checkCategory_key();

				category.checkCategory_value();

				category.checkCategory_sort();

				category.checkCategory_description();

				break;
			}
			case Url.URL_BRAND_CATEGORYT_UPDATE: {
				isExit = true;

				category.checkCategory_id();

				category.checkCategory_name();

				category.checkCategory_key();

				category.checkCategory_value();

				category.checkCategory_sort();

				category.checkCategory_description();

				break;
			}
			case Url.URL_BRAND_CATEGORYT_DELETE: {
				isExit = true;

				category.checkCategory_id();

				break;
			}
			case Url.URL_BRAND_CATEGORY_LIST_GET:
				isExit = true;

				break;
			case Url.URL_BRAND_LIST_GET: {
				isExit = true;

				category.checkCategory_id();

				Utility.checkPageAndLimit(jsonObject);

				break;
			}
			case Url.URL_BRAND_MY_LIST_GET: {
				isExit = true;

				category.checkCategory_id();

				Utility.checkPageAndLimit(jsonObject);

				break;
			}
			case Url.URL_BRAND_GET: {
				isExit = true;

				brand.checkBrand_id();

				break;
			}
			case Url.URL_BRAND_APPLY_SAVE: {
				isExit = true;

				brand.checkBrand_id();

				member.checkMember_real_name();

				member.checkMember_identity_card();

				member.checkMember_identity_card_front_image();

				member.checkMember_identity_card_back_image();

				break;
			}
			case Url.URL_BRAND_APPLY_LIST:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case Url.URL_BRAND_APPLYY_FIND:
			case Url.URL_BRAND_APPLYY_PASS:
			case Url.URL_BRAND_APPLYY_REFUSE: {
				isExit = true;

				brandApply.checkBrand_id();

				brandApply.checkUser_id();

				break;
			}
			case Url.URL_BRAND_APPLYY_CANCEL: {
				isExit = true;

				brandApply.checkBrand_id();

				break;
			}
		}

		if (!isExit) {
			controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, Const.URL_DENIED, null));
		}
	}

	protected void handleError(Controller controller) {

	}

}
