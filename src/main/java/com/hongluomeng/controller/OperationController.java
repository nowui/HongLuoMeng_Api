package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Operation;
import com.hongluomeng.service.OperationService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.OperationValidator;

public class OperationController extends BaseController {

	private OperationService operationService = new OperationService();

	@Before(OperationValidator.class)
	@ActionKey(Const.URL_OPERATION_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = operationService.list(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
    }

	@Before(OperationValidator.class)
	@ActionKey(Const.URL_OPERATION_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Operation operation = operationService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", operation));
    }

	@Before(OperationValidator.class)
	@ActionKey(Const.URL_OPERATION_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		operationService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(OperationValidator.class)
	@ActionKey(Const.URL_OPERATION_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		operationService.update(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(OperationValidator.class)
	@ActionKey(Const.URL_OPERATION_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		operationService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}
