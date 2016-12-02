package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.ActivityDao;
import com.hongluomeng.model.Activity;

public class ActivityService {

	private ActivityDao activityDao = new ActivityDao();
	private CategoryService categoryService = new CategoryService();
	private MemberService memberService = new MemberService();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Activity activityMap = jsonObject.toJavaObject(Activity.class);

		Integer count = activityDao.count();

		List<Activity> activityList = activityDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Activity activity : activityList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Activity.KEY_ACTIVITY_ID, activity.getActivity_id());
			map.put(Activity.KEY_ACTIVITY_NAME, activity.getActivity_name());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		Activity activityMap = jsonObject.toJavaObject(Activity.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Activity> activityList = activityDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Activity activity : activityList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Activity.KEY_ACTIVITY_ID, activity.getActivity_id());
			map.put(Activity.KEY_ACTIVITY_NAME, activity.getActivity_name());
			map.put(Activity.KEY_ACTIVITY_URL, activity.getActivity_url());
			map.put(Activity.KEY_ACTIVITY_IMAGE, activity.getActivity_image());

			list.add(map);
		}

		return list;
	}

	public Activity find(JSONObject jsonObject) {
		Activity activityMap = jsonObject.toJavaObject(Activity.class);

		Activity activity = activityDao.findByActivity_id(activityMap.getActivity_id());

		return activity;
	}

	public Map<String, Object> get(JSONObject jsonObject) {
		Activity activityMap = jsonObject.toJavaObject(Activity.class);

		Activity activity = activityDao.findByActivity_id(activityMap.getActivity_id());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Activity.KEY_ACTIVITY_ID, activity.getActivity_id());
		map.put(Activity.KEY_ACTIVITY_NAME, activity.getActivity_name());
		map.put(Activity.KEY_ACTIVITY_CONTENT, activity.getActivity_content());

		return map;
	}

	public void save(JSONObject jsonObject) {
		Activity activityMap = jsonObject.toJavaObject(Activity.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		activityDao.save(activityMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		Activity activityMap = jsonObject.toJavaObject(Activity.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		activityDao.update(activityMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Activity activityMap = jsonObject.toJavaObject(Activity.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		activityDao.delete(activityMap.getActivity_id(), request_user_id);
	}

}
