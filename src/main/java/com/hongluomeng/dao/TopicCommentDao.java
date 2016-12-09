package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.model.TopicComment;

public class TopicCommentDao extends BaseDao {

	private List<TopicComment> list(TopicComment topicComment, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT " + TopicComment.TABLE_TOPIC_COMMENT + ".*, ");
        myDynamicSQL.append(Member.TABLE_MEMBER + "." + Member.COLUMN_MEMBER_ID + ", ");
        myDynamicSQL.append(Member.TABLE_MEMBER + "." + Member.COLUMN_MEMBER_NAME + ", ");
        myDynamicSQL.append(Member.TABLE_MEMBER + "." + Member.COLUMN_MEMBER_AVATAR + ", ");
        myDynamicSQL.append("IFNULL(reply." + Member.COLUMN_MEMBER_ID + ", '') AS " + TopicComment.COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_ID + ", ");
        myDynamicSQL.append("IFNULL(reply." + Member.COLUMN_MEMBER_NAME + ", '') AS " + TopicComment.COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_NAME + ", ");
        myDynamicSQL.append("IFNULL(reply." + Member.COLUMN_MEMBER_AVATAR + ", '') AS " + TopicComment.COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_AVATAR + " ");
        myDynamicSQL.append("FROM " + TopicComment.TABLE_TOPIC_COMMENT + " ");
        myDynamicSQL.append("LEFT JOIN " + Member.TABLE_MEMBER + " ON " + Member.TABLE_MEMBER + "." + Member.COLUMN_USER_ID + " = " + TopicComment.TABLE_TOPIC_COMMENT + "." + TopicComment.COLUMN_USER_ID + " ");
        myDynamicSQL.append("LEFT JOIN " + Member.TABLE_MEMBER + " AS reply ON reply." + Member.COLUMN_MEMBER_ID + " = " + TopicComment.TABLE_TOPIC_COMMENT + "." + TopicComment.COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_ID + " ");
        myDynamicSQL.append("WHERE " + TopicComment.TABLE_TOPIC_COMMENT + "." + TopicComment.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + TopicComment.TABLE_TOPIC_COMMENT + "." + TopicComment.COLUMN_TOPIC_ID + " = ? ", topicComment.getTopic_id());
        if (! Utility.isNull(topicComment.getTopicIdList())) {
            for(int i = 0; i < topicComment.getTopicIdList().size(); i++) {
                String topic_id = topicComment.getTopicIdList().get(i);

                if(i == 0) {
                    myDynamicSQL.append("AND (");
                } else {
                    myDynamicSQL.append("OR ");
                }

                myDynamicSQL.append(TopicComment.TABLE_TOPIC_COMMENT + "." + TopicComment.COLUMN_TOPIC_ID + " = ? ", topic_id);
            }
            myDynamicSQL.append(") ");
        }
		myDynamicSQL.append("ORDER BY " + TopicComment.COLUMN_SYSTEM_CREATE_TIME + " ASC ");
		myDynamicSQL.appendPagination(m, n);

		return new TopicComment().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

    public List<TopicComment> listByTopic_id(String topic_id) {
        TopicComment topicComment = new TopicComment();
        topicComment.setTopic_id(topic_id);

        return list(topicComment, 0, 0);
    }

    public List<TopicComment> listByTopicIdList(List<String> topicIdList) {
        TopicComment topicComment = new TopicComment();
        topicComment.setTopicIdList(topicIdList);

        if(topicIdList.size() == 0) {
            return new ArrayList<TopicComment>();
        }

        return list(topicComment, 0, 0);
    }

	public void save(TopicComment topicComment, String request_user_id) {
        topicComment.setTopic_comment_id(Utility.getUUID());
        topicComment.setUser_id(request_user_id);

		topicComment.initForSave(request_user_id);

		topicComment.save();
	}

    public void delete(String topic_comment_id, String request_user_id) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("UPDATE " + TopicComment.TABLE_TOPIC_COMMENT + " ");
        myDynamicSQL.append("SET " + TopicComment.COLUMN_SYSTEM_STATUS + " = 0, ");
        myDynamicSQL.append(TopicComment.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
        myDynamicSQL.append(TopicComment.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
        myDynamicSQL.append("WHERE " + TopicComment.COLUMN_TOPIC_COMMENT_ID + " = ? ", topic_comment_id);
        myDynamicSQL.append("AND " + TopicComment.COLUMN_SYSTEM_CREATE_USER_ID + " = ? ", request_user_id);

        Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
    }

	public void deleteByTopic_id(String topic_id, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + TopicComment.TABLE_TOPIC_COMMENT + " ");
		myDynamicSQL.append("SET " + TopicComment.COLUMN_SYSTEM_STATUS + " = 0, ");
		myDynamicSQL.append(TopicComment.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(TopicComment.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + TopicComment.COLUMN_TOPIC_ID + " = ? ", topic_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
