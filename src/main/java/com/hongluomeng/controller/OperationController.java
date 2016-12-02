package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.model.Operation;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.service.OperationService;

public class OperationController extends BaseController {

	private OperationService operationService = new OperationService();

	@ActionKey(Url.URL_OPERATION_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Operation operationValidator = jsonObject.toJavaObject(Operation.class);

		Utility.checkPageAndLimit(jsonObject);

		operationValidator.checkMenu_id();

		Map<String, Object> resultMap = operationService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_OPERATION_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Operation operationValidator = jsonObject.toJavaObject(Operation.class);

		operationValidator.checkOperation_id();

		Operation operation = operationService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(operation));
	}

	@ActionKey(Url.URL_OPERATION_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Operation operationValidator = jsonObject.toJavaObject(Operation.class);

		operationValidator.checkMenu_id();

		operationValidator.checkOperation_name();

		operationValidator.checkOperation_key();

		operationValidator.checkOperation_value();

		operationValidator.checkOperation_sort();

		operationService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_OPERATION_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Operation operationValidator = jsonObject.toJavaObject(Operation.class);

		operationValidator.checkOperation_id();

		operationValidator.checkOperation_name();

		operationValidator.checkOperation_key();

		operationValidator.checkOperation_value();

		operationValidator.checkOperation_sort();

		operationService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_OPERATION_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Operation operationValidator = jsonObject.toJavaObject(Operation.class);

		operationValidator.checkOperation_id();

		operationService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

}
