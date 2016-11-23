package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Activity;

public class ActivityDao {

	private Integer count(Activity activity) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Activity.KEY_TABLE_ACTIVITY + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Activity.KEY_ACTIVITY_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Activity activity = new Activity();

		return count(activity);
	}

	private List<Activity> list(Activity activity, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Activity.KEY_TABLE_ACTIVITY + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Activity.KEY_ACTIVITY_STATUS + " = 1 ");

		sql.append("ORDER BY " + Activity.KEY_ACTIVITY_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Activity> activityList = activity.find(sql.toString(), parameterList.toArray());
		return activityList;
	}

	public List<Activity> list(Integer m, Integer n) {
		Activity activity = new Activity();

		return list(activity, m, n);
	}

	private Activity find(Activity activity) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Activity.KEY_TABLE_ACTIVITY + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(activity.getActivity_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Activity.KEY_ACTIVITY_ID + " = ? ");
			parameterList.add(activity.getActivity_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Activity.KEY_ACTIVITY_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<Activity> activityList = activity.find(sql.toString(), parameterList.toArray());
		if(activityList.size() == 0) {
			return null;
		} else {
			return activityList.get(0);
		}
	}

	public Activity findByActivity_id(String activity_id) {
		Activity activity = new Activity();
		activity.setActivity_id(activity_id);

		return find(activity);
	}

	public void save(Activity activity, String request_user_id) {
		activity.setActivity_id(Utility.getUUID());
		activity.setActivity_create_user_id(request_user_id);
		activity.setActivity_create_time(new Date());
		activity.setActivity_update_user_id(request_user_id);
		activity.setActivity_update_time(new Date());
		activity.setActivity_status(true);

		activity.save();
	}

	public void update(Activity activity, String request_user_id) {
		activity.remove(Activity.KEY_ACTIVITY_CREATE_USER_ID);
		activity.remove(Activity.KEY_ACTIVITY_CREATE_TIME);
		activity.setActivity_update_user_id(request_user_id);
		activity.setActivity_update_time(new Date());

		activity.update();
	}

	public void delete(String activity_id, String request_user_id) {
		Activity activity = new Activity();
		activity.setActivity_id(activity_id);
		activity.setActivity_update_user_id(request_user_id);
		activity.setActivity_update_time(new Date());
		activity.setActivity_status(false);

		activity.update();
	}

}
