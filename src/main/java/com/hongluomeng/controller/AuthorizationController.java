package com.hongluomeng.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
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
	@ActionKey(Const.URL_AUTHORIZATION_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Authorization> authorizationList = authorizationService.list(jsonObject);

		Integer count = authorizationService.count(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", count, authorizationList));
    }

	@Before(AuthorizationValidator.class)
	@ActionKey(Const.URL_AUTHORIZATION_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Authorization authorization = authorizationService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", authorization));
    }

}
