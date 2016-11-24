package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Activity;

public class ActivityDao {

	private Integer count(Activity activity) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT COUNT(*) FROM " + Activity.KEY_TABLE_ACTIVITY + " ");
		dynamicSQL.append("WHERE " + Activity.KEY_ACTIVITY_STATUS + " = 1 ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Activity activity = new Activity();

		return count(activity);
	}

	private List<Activity> list(Activity activity, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Activity.KEY_TABLE_ACTIVITY + " ");
		dynamicSQL.append("WHERE " + Activity.KEY_ACTIVITY_STATUS + " = 1 ");
		dynamicSQL.append("ORDER BY " + Activity.KEY_ACTIVITY_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		return new Activity().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Activity> list(Integer m, Integer n) {
		Activity activity = new Activity();

		return list(activity, m, n);
	}

	private Activity find(Activity activity) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Activity.KEY_TABLE_ACTIVITY + " ");
		dynamicSQL.append("WHERE " + Activity.KEY_ACTIVITY_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Activity.KEY_ACTIVITY_ID + " = ? ", activity.getActivity_id());

		List<Activity> activityList = activity.find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if(activityList == null) {
			return null;
		} else {
			return activityList.get(0);
		}
	}

	public Activity findByActivity_id(String activity_id) {
		Activity activity = new Activity();
		activity.setActivity_id(activity_id);

		Utility.checkIsNullOrEmpty(activity_id);

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
