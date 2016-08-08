package com.hongluomeng.validator;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.Product;
import com.hongluomeng.type.CodeEnum;

public class ProductValidator extends Validator {

	protected void validate(Controller controller) {
		String actionKey = getActionKey();

		JSONObject jsonObject = controller.getAttr(Const.KEY_REQUEST);

		Boolean isExit = false;

		String message = "";

		if(actionKey.equals(Const.URL_PRODUCT_LIST)) {
			isExit = true;

			message += Utility.checkPageAndLimit(jsonObject);
		} else if(actionKey.equals(Const.URL_PRODUCT_FIND)) {
			isExit = true;

			/*if(Utility.isNullOrEmpty(product.getProduct_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}*/
		} else if(actionKey.equals(Const.URL_PRODUCT_SAVE) || actionKey.equals(Const.URL_PRODUCT_UPDATE)) {
			isExit = true;

			Product product = jsonObject.toJavaObject(Product.class);

			if(actionKey.equals(Const.URL_PRODUCT_UPDATE) && Utility.isNullOrEmpty(product.getProduct_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(product.getCategory_id())) {
				message += "分类为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(product.getBrand_id())) {
				message += "品牌为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(product.getProduct_name())) {
				message += "名称为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(product.getProduct_price())) {
				message += "价格为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(product.getProduct_stock())) {
				message += "库存为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(product.getProduct_is_newarrival())) {
				message += "新品参数为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(product.getProduct_is_recommend())) {
				message += "推荐参数为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(product.getProduct_is_bargain())) {
				message += "特价参数为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(product.getProduct_status())) {
				message += "状态为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_PRODUCT_DELETE)) {
			isExit = true;

			Product product = jsonObject.toJavaObject(Product.class);

			if(Utility.isNullOrEmpty(product.getProduct_id())) {
				message += "编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_PRODUCT_CATEGORY_LIST)) {
			isExit = true;

		} else if(actionKey.equals(Const.URL_PRODUCT_CATEGORY_FIND)) {
			isExit = true;

		} else if(actionKey.equals(Const.URL_PRODUCT_CATEGORY_SAVE) || actionKey.equals(Const.URL_PRODUC_CATEGORYT_UPDATE)) {
			isExit = true;

			Category category = jsonObject.toJavaObject(Category.class);

			if(actionKey.equals(Const.URL_PRODUCT_CATEGORY_SAVE) && Utility.isNullOrEmpty(category.getParent_id())) {
				message += "父编号为空";
				message += Const.LINE_FEED;
			}

			if(actionKey.equals(Const.URL_PRODUC_CATEGORYT_UPDATE) && Utility.isNullOrEmpty(category.getCategory_id())) {
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
		} else if(actionKey.equals(Const.URL_PRODUC_CATEGORYT_DELETE)) {
			isExit = true;

		} else if(actionKey.equals(Const.URL_PRODUCT_CATEGORY_ATTRIBUTE_LIST)) {
			isExit = true;

			CategoryAttribute categoryAttribute = jsonObject.toJavaObject(CategoryAttribute.class);

			if(Utility.isNullOrEmpty(categoryAttribute.getCategory_id())) {
				message += "分类编号为空";
				message += Const.LINE_FEED;
			}

		} else if(actionKey.equals(Const.URL_PRODUCT_CATEGORY_ATTRIBUTE_FIND)) {
			isExit = true;

			CategoryAttribute categoryAttribute = jsonObject.toJavaObject(CategoryAttribute.class);

			if(Utility.isNullOrEmpty(categoryAttribute.getCategory_id())) {
				message += "分类编号为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_PRODUCT_CATEGORY_ATTRIBUTE_SAVE) || actionKey.equals(Const.URL_PRODUC_CATEGORYT_ATTRIBUTE_UPDATE)) {
			isExit = true;

			CategoryAttribute categoryAttribute = jsonObject.toJavaObject(CategoryAttribute.class);

			if(Utility.isNullOrEmpty(categoryAttribute.getCategory_id())) {
				message += "分类编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(categoryAttribute.getAttribute_id())) {
				message += "属性编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(categoryAttribute.getCategory_attribute_sort())) {
				message += "排序为空";
				message += Const.LINE_FEED;
			}
		} else if(actionKey.equals(Const.URL_PRODUC_CATEGORYT_ATTRIBUTE_DELETE)) {
			isExit = true;

			CategoryAttribute categoryAttribute = jsonObject.toJavaObject(CategoryAttribute.class);

			if(Utility.isNullOrEmpty(categoryAttribute.getCategory_id())) {
				message += "分类编号为空";
				message += Const.LINE_FEED;
			}

			if(Utility.isNullOrEmpty(categoryAttribute.getAttribute_id())) {
				message += "属性编号为空";
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
