package com.hongluomeng.dao;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.hongluomeng.model.Member;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Topic;
import com.jfinal.plugin.activerecord.Record;

public class TopicDao extends BaseDao {

	private Integer count(Topic topic) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT COUNT(*) FROM " + Topic.TABLE_TOPIC + " ");
		myDynamicSQL.append("WHERE " + Topic.TABLE_TOPIC + "." + Topic.COLUMN_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Topic topic = new Topic();

		return count(topic);
	}

	private List<Topic> list(Topic topic, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT " + Topic.TABLE_TOPIC + ".*, " + Member.TABLE_MEMBER + "." + Member.COLUMN_MEMBER_NAME + " FROM " + Topic.TABLE_TOPIC + " ");
        myDynamicSQL.append("LEFT JOIN " + Member.TABLE_MEMBER + " ON " + Member.TABLE_MEMBER + "." + Member.COLUMN_USER_ID + " = " + Topic.TABLE_TOPIC + "." + Topic.COLUMN_USER_ID + " ");
		myDynamicSQL.append("WHERE " + Topic.TABLE_TOPIC + "." + Topic.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + Topic.TABLE_TOPIC + "." + Topic.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		List<Topic> activityList = new Topic().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());

		return activityList;
	}

	public List<Topic> list(Integer m, Integer n) {
		Topic topic = new Topic();

		return list(topic, m, n);
	}

	private Topic find(Topic topic) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Topic.TABLE_TOPIC + " ");
		myDynamicSQL.append("WHERE " + Topic.TABLE_TOPIC + "." + Topic.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Topic.COLUMN_TOPIC_ID + " = ? ", topic.getTopic_id());

		List<Record> recordList = Db.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if (recordList.size() == 0) {
			return null;
		} else {
			Topic activityModel = new Topic().put(recordList.get(0));
			return activityModel;
		}
	}

	public Topic findByTopic_id(String topic_id) {
		Topic topic = new Topic();
		topic.setTopic_id(topic_id);

		topic.checkTopic_id();

		return find(topic);
	}

	public void save(Topic topic, String request_user_id) {
		topic.setTopic_id(Utility.getUUID());
        topic.setUser_id(request_user_id);

		topic.initForSave(request_user_id);

		topic.save();
	}

	public void delete(String topic_id, String request_user_id) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("UPDATE " + Topic.TABLE_TOPIC + " ");
        myDynamicSQL.append("SET " + Topic.COLUMN_SYSTEM_STATUS + " = 0, ");
        myDynamicSQL.append(Topic.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
        myDynamicSQL.append(Topic.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
        myDynamicSQL.append("WHERE " + Topic.COLUMN_TOPIC_ID + " = ? ", topic_id);
        myDynamicSQL.append("AND " + Topic.COLUMN_SYSTEM_CREATE_USER_ID + " = ? ", request_user_id);

        Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
