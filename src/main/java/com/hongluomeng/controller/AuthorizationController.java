package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Authorization;
import com.hongluomeng.service.AuthorizationService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.AuthorizationValidator;

public class AuthorizationController extends BaseController {

	private AuthorizationService authorizationService = new AuthorizationService();

	@Before(AuthorizationValidator.class)
	@ActionKey(Url.URL_AUTHORIZATION_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = authorizationService.list(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@Before(AuthorizationValidator.class)
	@ActionKey(Url.URL_AUTHORIZATION_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Authorization authorization = authorizationService.find(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", authorization));
	}

}
