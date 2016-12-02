package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Attribute;
import com.hongluomeng.service.AttributeService;

public class AttributeController extends BaseController {

	private AttributeService attributeService = new AttributeService();

	@ActionKey(Url.URL_ATTRIBUTE_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = attributeService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_ATTRIBUTE_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Attribute attributeValidator = jsonObject.toJavaObject(Attribute.class);

		attributeValidator.checkAttribute_id();

		Attribute attribute = attributeService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(attribute));
	}

	@ActionKey(Url.URL_ATTRIBUTE_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Attribute attributeValidator = jsonObject.toJavaObject(Attribute.class);

		attributeValidator.checkAttribute_name();

		attributeValidator.checkAttribute_input_type();

		attributeValidator.checkAttribute_type();

		attributeValidator.checkAttribute_sort();

		attributeService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ATTRIBUTE_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Attribute attributeValidator = jsonObject.toJavaObject(Attribute.class);

		attributeValidator.checkAttribute_id();

		attributeValidator.checkAttribute_name();

		attributeValidator.checkAttribute_input_type();

		attributeValidator.checkAttribute_type();

		attributeValidator.checkAttribute_sort();

		attributeService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ATTRIBUTE_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Attribute attributeValidator = jsonObject.toJavaObject(Attribute.class);

		attributeValidator.checkAttribute_id();

		attributeService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

}
