package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Activity;
import com.jfinal.plugin.activerecord.Record;

public class ActivityDao {

	private Integer count(Activity activity) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT COUNT(*) FROM " + Activity.TABLE_ACTIVITY + " ");
		myDynamicSQL.append("WHERE " + Activity.TABLE_ACTIVITY + "." + Activity.COLUMN_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Activity activity = new Activity();

		return count(activity);
	}

	private List<Activity> list(Activity activity, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Activity.TABLE_ACTIVITY + " ");
		myDynamicSQL.append("WHERE " + Activity.TABLE_ACTIVITY + "." + Activity.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + Activity.TABLE_ACTIVITY + "." + Activity.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		List<Activity> activityList = new Activity().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());

		return activityList;
	}

	public List<Activity> list(Integer m, Integer n) {
		Activity activity = new Activity();

		return list(activity, m, n);
	}

	private Activity find(Activity activity) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Activity.TABLE_ACTIVITY + " ");
		myDynamicSQL.append("WHERE " + Activity.TABLE_ACTIVITY + "." + Activity.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Activity.COLUMN_ACTIVITY_ID + " = ? ", activity.getActivity_id());

		List<Record> recordList = Db.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if (recordList.size() == 0) {
			return null;
		} else {
			Activity activityModel = new Activity().put(recordList.get(0));
			return activityModel;
		}
	}

	public Activity findByActivity_id(String activity_id) {
		Activity activity = new Activity();
		activity.setActivity_id(activity_id);

		activity.checkActivity_id();

		return find(activity);
	}

	public void save(Activity activity, String request_user_id) {
		activity.setActivity_id(Utility.getUUID());

		activity.initForSave(request_user_id);

		activity.save();
	}

	public void update(Activity activity, String request_user_id) {
		activity.initForUpdate(request_user_id);

		activity.update();
	}

	public void delete(String activity_id, String request_user_id) {
		Activity activity = new Activity();
		activity.setActivity_id(activity_id);
		activity.setSystem_update_user_id(request_user_id);
		activity.setSystem_update_time(new Date());
		activity.setSystem_status(false);

		activity.update();
	}

}
