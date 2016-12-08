package com.hongluomeng.dao;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.hongluomeng.common.Utility;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.model.TopicLike;

public class TopicLikeDao extends BaseDao {

	private List<TopicLike> list(TopicLike topicLike, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + TopicLike.TABLE_TOPIC_LIKE + " ");
		myDynamicSQL.append("WHERE " + TopicLike.TABLE_TOPIC_LIKE + "." + TopicLike.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + TopicLike.COLUMN_SYSTEM_CREATE_TIME + " ASC ");
		myDynamicSQL.appendPagination(m, n);

		return new TopicLike().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<TopicLike> listByTopic_id(String topic_id) {
		TopicLike topicLike = new TopicLike();
		topicLike.setTopic_id(topic_id);

		return list(topicLike, 0, 0);
	}

	public void save(TopicLike topicLike, String request_user_id) {
        topicLike.setTopic_id(Utility.getUUID());
        topicLike.setUser_id(request_user_id);

		topicLike.initForSave(request_user_id);

		topicLike.save();
	}

    public void delete(String topic_comment_id, String request_user_id) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("UPDATE " + TopicLike.TABLE_TOPIC_LIKE + " ");
        myDynamicSQL.append("SET " + TopicLike.COLUMN_SYSTEM_STATUS + " = 0, ");
        myDynamicSQL.append(TopicLike.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
        myDynamicSQL.append(TopicLike.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
        myDynamicSQL.append("WHERE " + TopicLike.COLUMN_TOPIC_LIKE_ID + " = ? ", topic_comment_id);
        myDynamicSQL.append("AND " + TopicLike.COLUMN_SYSTEM_CREATE_USER_ID + " = ? ", request_user_id);

        Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
    }

	public void deleteByTopic_id(String topic_id, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + TopicLike.TABLE_TOPIC_LIKE + " ");
		myDynamicSQL.append("SET " + TopicLike.COLUMN_SYSTEM_STATUS + " = 0, ");
		myDynamicSQL.append(TopicLike.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(TopicLike.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + TopicLike.COLUMN_TOPIC_ID + " = ? ", topic_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
