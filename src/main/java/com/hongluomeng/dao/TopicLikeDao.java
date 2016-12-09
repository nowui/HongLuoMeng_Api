package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.model.TopicLike;

public class TopicLikeDao extends BaseDao {

	private List<TopicLike> list(TopicLike topicLike, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT " + TopicLike.TABLE_TOPIC_LIKE + ".*, ");
        myDynamicSQL.append(Member.TABLE_MEMBER + "." + Member.COLUMN_MEMBER_ID + ", ");
        myDynamicSQL.append(Member.TABLE_MEMBER + "." + Member.COLUMN_MEMBER_NAME + ", ");
        myDynamicSQL.append(Member.TABLE_MEMBER + "." + Member.COLUMN_MEMBER_AVATAR + " ");
        myDynamicSQL.append("FROM " + TopicLike.TABLE_TOPIC_LIKE + " ");
        myDynamicSQL.append("LEFT JOIN " + Member.TABLE_MEMBER + " ON " + Member.TABLE_MEMBER + "." + Member.COLUMN_USER_ID + " = " + TopicLike.TABLE_TOPIC_LIKE + "." + TopicLike.COLUMN_USER_ID + " ");
		myDynamicSQL.append("WHERE " + TopicLike.TABLE_TOPIC_LIKE + "." + TopicLike.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + TopicLike.TABLE_TOPIC_LIKE + "." + TopicLike.COLUMN_TOPIC_ID + " = ? ", topicLike.getTopic_id());
        if (! Utility.isNull(topicLike.getTopicIdList())) {
            for(int i = 0; i < topicLike.getTopicIdList().size(); i++) {
                String topic_id = topicLike.getTopicIdList().get(i);

                if(i == 0) {
                    myDynamicSQL.append("AND (");
                } else {
                    myDynamicSQL.append("OR ");
                }

                myDynamicSQL.append(TopicLike.TABLE_TOPIC_LIKE + "." + TopicLike.COLUMN_TOPIC_ID + " = ? ", topic_id);
            }
            myDynamicSQL.append(") ");
        }
		myDynamicSQL.append("ORDER BY " + TopicLike.COLUMN_SYSTEM_CREATE_TIME + " ASC ");
		myDynamicSQL.appendPagination(m, n);

		return new TopicLike().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<TopicLike> listByTopic_id(String topic_id) {
		TopicLike topicLike = new TopicLike();
		topicLike.setTopic_id(topic_id);

		return list(topicLike, 0, 0);
	}

    public List<TopicLike> listByTopicIdList(List<String> topicIdList) {
        TopicLike topicLike = new TopicLike();
        topicLike.setTopicIdList(topicIdList);

        if(topicIdList.size() == 0) {
            return new ArrayList<TopicLike>();
        }

        return list(topicLike, 0, 0);
    }

	public void save(TopicLike topicLike, String request_user_id) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("INSERT INTO " + TopicLike.TABLE_TOPIC_LIKE + " (");
        myDynamicSQL.append(TopicLike.COLUMN_TOPIC_LIKE_ID + ", ");
        myDynamicSQL.append(TopicLike.COLUMN_TOPIC_ID + ", ");
        myDynamicSQL.append(TopicLike.COLUMN_USER_ID + ", ");
        myDynamicSQL.append(TopicLike.COLUMN_SYSTEM_CREATE_USER_ID + ", ");
        myDynamicSQL.append(TopicLike.COLUMN_SYSTEM_CREATE_TIME + ", ");
        myDynamicSQL.append(TopicLike.COLUMN_SYSTEM_UPDATE_USER_ID + ", ");
        myDynamicSQL.append(TopicLike.COLUMN_SYSTEM_UPDATE_TIME + ", ");
        myDynamicSQL.append(TopicLike.COLUMN_SYSTEM_STATUS);
        myDynamicSQL.append(") SELECT ");
        myDynamicSQL.append("?, ", Utility.getUUID());
        myDynamicSQL.append("?, ", topicLike.getTopic_id());
        myDynamicSQL.append("?, ", request_user_id);
        myDynamicSQL.append("?, ", request_user_id);
        myDynamicSQL.append("?, ", new Date());
        myDynamicSQL.append("?, ", request_user_id);
        myDynamicSQL.append("?, ", new Date());
        myDynamicSQL.append("? ", 1);
        myDynamicSQL.append("FROM DUAL WHERE NOT EXISTS (SELECT * FROM " + TopicLike.TABLE_TOPIC_LIKE + " WHERE " + TopicLike.COLUMN_TOPIC_ID + " = ? AND " + TopicLike.COLUMN_USER_ID + " = ?) ", topicLike.getTopic_id(), request_user_id);

        Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
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
