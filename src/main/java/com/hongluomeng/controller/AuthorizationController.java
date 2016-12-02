package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Authorization;
import com.hongluomeng.service.AuthorizationService;

public class AuthorizationController extends BaseController {

	private AuthorizationService authorizationService = new AuthorizationService();

	@ActionKey(Url.URL_AUTHORIZATION_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = authorizationService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_AUTHORIZATION_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Authorization authorizationValidator = jsonObject.toJavaObject(Authorization.class);

		authorizationValidator.checkAuthorization_id();

		Authorization authorization = authorizationService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(authorization));
	}

}
