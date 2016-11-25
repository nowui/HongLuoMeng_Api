package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Log;
import com.hongluomeng.service.LogService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.LogValidator;

public class LogController extends BaseController {

	private LogService logService = new LogService();

	@Before(LogValidator.class)
	@ActionKey(Url.URL_LOG_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		System.out.println(jsonObject.toString());

		Map<String, Object> resultMap = logService.list(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@Before(LogValidator.class)
	@ActionKey(Url.URL_LOG_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Log log = logService.find(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", log));
	}

}
