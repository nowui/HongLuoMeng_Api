package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Attribute;
import com.hongluomeng.service.AttributeService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.AttributeValidator;

public class AttributeController extends BaseController {

	private AttributeService attributeService = new AttributeService();

	@Before(AttributeValidator.class)
	@ActionKey(Const.URL_ATTRIBUTE_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = attributeService.list(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
    }

	@Before(AttributeValidator.class)
	@ActionKey(Const.URL_ATTRIBUTE_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Attribute attribute = attributeService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", attribute));
    }

	@Before(AttributeValidator.class)
	@ActionKey(Const.URL_ATTRIBUTE_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		attributeService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(AttributeValidator.class)
	@ActionKey(Const.URL_ATTRIBUTE_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		attributeService.update(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(AttributeValidator.class)
	@ActionKey(Const.URL_ATTRIBUTE_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		attributeService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}
