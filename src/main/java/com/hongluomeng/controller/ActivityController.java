package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.service.ActivityService;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Activity;

public class ActivityController extends BaseController {

	private ActivityService activityService = new ActivityService();

	@ActionKey(Url.URL_ACTIVITY_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = activityService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_ACTIVITY_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Activity activityValidator = jsonObject.toJavaObject(Activity.class);

		activityValidator.checkActivity_id();

		Activity activity = activityService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(activity));
	}

	@ActionKey(Url.URL_ACTIVITY_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Activity activityValidator = jsonObject.toJavaObject(Activity.class);

		activityValidator.checkActivity_name();

		activityValidator.checkActivity_url();

		activityValidator.checkActivity_logo();

		activityValidator.checkActivity_sort();

		activityValidator.checkActivity_content();

		activityService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ACTIVITY_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Activity activityValidator = jsonObject.toJavaObject(Activity.class);

		activityValidator.checkActivity_id();

		activityValidator.checkActivity_name();

		activityValidator.checkActivity_url();

		activityValidator.checkActivity_logo();

		activityValidator.checkActivity_sort();

		activityValidator.checkActivity_content();

		activityService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ACTIVITY_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Activity activityValidator = jsonObject.toJavaObject(Activity.class);

		activityValidator.checkActivity_id();

		activityService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ACTIVITY_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		List<Map<String, Object>> resultList = activityService.getList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_ACTIVITY_GET)
	public void get() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Activity activityValidator = jsonObject.toJavaObject(Activity.class);

		activityValidator.checkActivity_id();

		Map<String, Object> resultMap = activityService.get(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

}
