package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Log;
import com.hongluomeng.service.LogService;

public class LogController extends BaseController {

	private LogService logService = new LogService();

	@ActionKey(Url.URL_LOG_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = logService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_LOG_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Log logValidator = jsonObject.toJavaObject(Log.class);

		logValidator.checkLog_id();

		Log log = logService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(log));
	}

}
