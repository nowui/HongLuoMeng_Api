package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.service.UserService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.UserValidator;

@Before(UserValidator.class)
public class UserController extends BaseController {

	private UserService userService = new UserService();

	@ActionKey(Url.URL_USER_MENU_LIST)
	public void listMenu() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> list = userService.menu(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", list));
	}

}
