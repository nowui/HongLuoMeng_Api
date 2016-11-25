package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.type.CodeEnum;
import com.jfinal.core.Controller;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.Product;
import com.jfinal.validate.Validator;

public class ProductValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Product product = jsonObject.toJavaObject(Product.class);

		Category category = jsonObject.toJavaObject(Category.class);

		CategoryAttribute categoryAttribute = jsonObject.toJavaObject(CategoryAttribute.class);

		Boolean isExit = false;

		switch (actionKey) {
			case Url.URL_PRODUCT_LIST: {
				isExit = true;

				product.checkProduct_name();

				Utility.checkPageAndLimit(jsonObject);
				break;
			}
			case Url.URL_PRODUCT_FIND:
				isExit = true;

				break;
			case Url.URL_PRODUCT_SAVE: {
				isExit = true;

				product.checkCategory_id();

				product.checkBrand_id();

				product.checkProduct_name();

				product.checkProduct_is_new();

				product.checkProduct_is_recommend();

				product.checkProduct_is_bargain();

				product.checkProduct_is_hot();

				product.checkProduct_is_sell_out();

				product.checkProduct_is_sale();

				product.checkProduct_content();

				break;
			}
			case Url.URL_PRODUCT_UPDATE: {
				isExit = true;

				product.checkProduct_id();

				product.checkCategory_id();

				product.checkBrand_id();

				product.checkProduct_name();

				product.checkProduct_is_new();

				product.checkProduct_is_recommend();

				product.checkProduct_is_bargain();

				product.checkProduct_is_hot();

				product.checkProduct_is_sell_out();

				product.checkProduct_is_sale();

				product.checkProduct_content();

				break;
			}
			case Url.URL_PRODUCT_DELETE: {
				isExit = true;

				product.checkProduct_id();

				break;
			}
			case Url.URL_PRODUCT_CATEGORY_LIST:
				isExit = true;

				break;
			case Url.URL_PRODUCT_CATEGORY_FIND: {
				isExit = true;

				category.checkCategory_id();

				break;
			}
			case Url.URL_PRODUCT_CATEGORY_SAVE: {
				isExit = true;

				category.checkParent_id();

				category.checkCategory_name();

				category.checkCategory_sort();

				break;
			}
			case Url.URL_PRODUCT_CATEGORYT_UPDATE: {
				isExit = true;

				category.checkCategory_id();

				category.checkCategory_name();

				category.checkCategory_sort();

				break;
			}
			case Url.URL_PRODUCT_CATEGORYT_DELETE: {
				isExit = true;

				category.checkCategory_id();

				break;
			}
			case Url.URL_PRODUCT_CATEGORY_ATTRIBUTE_LIST: {
				isExit = true;

				categoryAttribute.checkCategory_id();

				break;
			}
			case Url.URL_PRODUCT_CATEGORY_ATTRIBUTE_FIND: {
				isExit = true;

				categoryAttribute.checkCategory_id();

				break;
			}
			case Url.URL_PRODUCT_CATEGORY_ATTRIBUTE_SAVE:
			case Url.URL_PRODUCT_CATEGORYT_ATTRIBUTE_UPDATE: {
				isExit = true;

				categoryAttribute.checkCategory_id();

				categoryAttribute.checkAttribute_id();

				categoryAttribute.checkCategory_attribute_sort();

				break;
			}
			case Url.URL_PRODUCT_CATEGORYT_ATTRIBUTE_DELETE: {
				isExit = true;

				categoryAttribute.checkCategory_id();

				categoryAttribute.checkAttribute_id();

				break;
			}
			case Url.URL_PRODUCT_CATEGORY_LIST_GET:
				isExit = true;

				break;
			case Url.URL_PRODUCT_LIST_GET: {
				isExit = true;

				category.checkCategory_id();

				Utility.checkPageAndLimit(jsonObject);
				break;
			}
			case Url.URL_PRODUCT_BRAND_LIST_GET: {
				isExit = true;

				product.checkBrand_id();

				Utility.checkPageAndLimit(jsonObject);
				break;
			}
			case Url.URL_PRODUCT_MARKET_LIST_GET:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);

				break;
			case Url.URL_PRODUCT_GET: {
				isExit = true;

				product.checkProduct_id();

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
