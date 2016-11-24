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

		Map<String, Object> resultMap = Utility.setResultMap(count, activityList);

		return resultMap;
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		Activity activityMap = jsonObject.toJavaObject(Activity.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Activity> activityList = activityDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for(Activity activity : activityList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Activity.KEY_ACTIVITY_ID, activity.getActivity_id());
			map.put(Activity.KEY_ACTIVITY_NAME, activity.getActivity_name());
			map.put(Activity.KEY_ACTIVITY_URL, activity.getActivity_url());

			list.add(map);
		}

		return list;
	}

	public List<Activity> listByUser_id(String user_id) {
		List<Activity> activityList = activityDao.list(0, 0);

		List<Activity> activityResultList = new ArrayList<Activity>();
		for(Activity activity : activityList) {
			Activity b = new Activity();
			b.setActivity_id(activity.getActivity_id());
			b.setActivity_name(activity.getActivity_name());
			activityResultList.add(b);
		}
		return activityResultList;
	}

	public Activity find(JSONObject jsonObject) {
		Activity activityMap = jsonObject.toJavaObject(Activity.class);

		Activity activity = activityDao.findByActivity_id(activityMap.getActivity_id());

		return activity;
	}

	public Map<String, Object> get(JSONObject jsonObject) {
		Activity activityMap = jsonObject.toJavaObject(Activity.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Activity activity = activityDao.findByActivity_id(activityMap.getActivity_id());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Activity.KEY_ACTIVITY_ID, activity.getActivity_id());
		map.put(Activity.KEY_ACTIVITY_NAME, activity.getActivity_name());
		map.put(Activity.KEY_ACTIVITY_URL, activity.getActivity_url());

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
