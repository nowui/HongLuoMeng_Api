package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.service.ActivityService;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Activity;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.ActivityValidator;

public class ActivityController extends BaseController {

	private ActivityService activityService = new ActivityService();

	@Before(ActivityValidator.class)
	@ActionKey(Url.URL_ACTIVITY_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = activityService.list(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

	@Before(ActivityValidator.class)
	@ActionKey(Url.URL_ACTIVITY_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Activity activity = activityService.find(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", activity));
	}

	@Before(ActivityValidator.class)
	@ActionKey(Url.URL_ACTIVITY_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		activityService.save(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(ActivityValidator.class)
	@ActionKey(Url.URL_ACTIVITY_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		activityService.update(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(ActivityValidator.class)
	@ActionKey(Url.URL_ACTIVITY_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		activityService.delete(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(ActivityValidator.class)
	@ActionKey(Url.URL_ACTIVITY_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> resultList = activityService.getList(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultList));
	}

	@Before(ActivityValidator.class)
	@ActionKey(Url.URL_ACTIVITY_GET)
	public void get() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = activityService.get(jsonObject);

		renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
	}

}
