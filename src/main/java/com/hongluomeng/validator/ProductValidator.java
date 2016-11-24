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

		Boolean isExit = false;

		String message = "";

		switch (actionKey) {
			case Url.URL_PRODUCT_LIST: {
				isExit = true;

				Product product = jsonObject.toJavaObject(Product.class);

				if (Utility.isNull(product.getProduct_name())) {
					message += "名称为空";
					message += Const.LINE_FEED;
				}

				Utility.checkPageAndLimit(jsonObject);
				break;
			}
			case Url.URL_PRODUCT_FIND:
				isExit = true;

			/*if(Utility.isNullOrEmpty(product.getProduct_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}*/
				break;
			case Url.URL_PRODUCT_SAVE:
			case Url.URL_PRODUCT_UPDATE: {
				isExit = true;

				Product product = jsonObject.toJavaObject(Product.class);

				if (actionKey.equals(Url.URL_PRODUCT_UPDATE) && Utility.isNullOrEmpty(product.getProduct_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(product.getCategory_id())) {
					message += "分类为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(product.getBrand_id())) {
					message += "品牌为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(product.getProduct_name())) {
					message += "名称为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(product.getProduct_is_new())) {
					message += "是否新品参数为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(product.getProduct_is_recommend())) {
					message += "是否推荐参数为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(product.getProduct_is_bargain())) {
					message += "是否特价参数为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(product.getProduct_is_hot())) {
					message += "是否热卖参数为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(product.getProduct_is_sell_out())) {
					message += "是否买完参数为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(product.getProduct_is_sale())) {
					message += "是否上下架为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNull(product.getProduct_content())) {
					message += "介绍为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_PRODUCT_DELETE: {
				isExit = true;

				Product product = jsonObject.toJavaObject(Product.class);

				if (Utility.isNullOrEmpty(product.getProduct_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_PRODUCT_CATEGORY_LIST:
				isExit = true;

				break;
			case Url.URL_PRODUCT_CATEGORY_FIND: {
				isExit = true;

				Category category = jsonObject.toJavaObject(Category.class);

				if (Utility.isNullOrEmpty(category.getCategory_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_PRODUCT_CATEGORY_SAVE:
			case Url.URL_PRODUCT_CATEGORYT_UPDATE: {
				isExit = true;

				Category category = jsonObject.toJavaObject(Category.class);

				if (actionKey.equals(Url.URL_PRODUCT_CATEGORY_SAVE) && Utility.isNullOrEmpty(category.getParent_id())) {
					message += "父编号为空";
					message += Const.LINE_FEED;
				}

				if (actionKey.equals(Url.URL_PRODUCT_CATEGORYT_UPDATE) && Utility.isNullOrEmpty(category.getCategory_id())) {
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
			}
			case Url.URL_PRODUCT_CATEGORYT_DELETE: {
				isExit = true;

				Category category = jsonObject.toJavaObject(Category.class);

				if (Utility.isNullOrEmpty(category.getCategory_id())) {
					message += "编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_PRODUCT_CATEGORY_ATTRIBUTE_LIST: {
				isExit = true;

				CategoryAttribute categoryAttribute = jsonObject.toJavaObject(CategoryAttribute.class);

				if (Utility.isNullOrEmpty(categoryAttribute.getCategory_id())) {
					message += "分类编号为空";
					message += Const.LINE_FEED;
				}

				break;
			}
			case Url.URL_PRODUCT_CATEGORY_ATTRIBUTE_FIND: {
				isExit = true;

				CategoryAttribute categoryAttribute = jsonObject.toJavaObject(CategoryAttribute.class);

				if (Utility.isNullOrEmpty(categoryAttribute.getCategory_id())) {
					message += "分类编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_PRODUCT_CATEGORY_ATTRIBUTE_SAVE:
			case Url.URL_PRODUCT_CATEGORYT_ATTRIBUTE_UPDATE: {
				isExit = true;

				CategoryAttribute categoryAttribute = jsonObject.toJavaObject(CategoryAttribute.class);

				if (Utility.isNullOrEmpty(categoryAttribute.getCategory_id())) {
					message += "分类编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(categoryAttribute.getAttribute_id())) {
					message += "属性编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(categoryAttribute.getCategory_attribute_sort())) {
					message += "排序为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_PRODUCT_CATEGORYT_ATTRIBUTE_DELETE: {
				isExit = true;

				CategoryAttribute categoryAttribute = jsonObject.toJavaObject(CategoryAttribute.class);

				if (Utility.isNullOrEmpty(categoryAttribute.getCategory_id())) {
					message += "分类编号为空";
					message += Const.LINE_FEED;
				}

				if (Utility.isNullOrEmpty(categoryAttribute.getAttribute_id())) {
					message += "属性编号为空";
					message += Const.LINE_FEED;
				}
				break;
			}
			case Url.URL_PRODUCT_CATEGORY_LIST_GET:
				isExit = true;

				break;
			case Url.URL_PRODUCT_LIST_GET: {
				isExit = true;

				Category category = jsonObject.toJavaObject(Category.class);

				if (Utility.isNull(category.getCategory_id())) {
					message += "分类编号为空";
					message += Const.LINE_FEED;
				}

				Utility.checkPageAndLimit(jsonObject);
				break;
			}
			case Url.URL_PRODUCT_BRAND_LIST_GET: {
				isExit = true;

				Product product = jsonObject.toJavaObject(Product.class);

				if (Utility.isNullOrEmpty(product.getBrand_id())) {
					message += "品牌为空";
					message += Const.LINE_FEED;
				}

				Utility.checkPageAndLimit(jsonObject);
				break;
			}
			case Url.URL_PRODUCT_MARKET_LIST_GET:
				isExit = true;

				Utility.checkPageAndLimit(jsonObject);
				break;
			case Url.URL_PRODUCT_GET: {
				isExit = true;

				Product product = jsonObject.toJavaObject(Product.class);

				if (Utility.isNullOrEmpty(product.getProduct_id())) {
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

	protected void handleError(Controller controller) {
		controller.renderJson(Utility.setResponse(CodeEnum.CODE_400, controller.getAttrForStr(Const.KEY_MESSAGE), null));
	}

}
