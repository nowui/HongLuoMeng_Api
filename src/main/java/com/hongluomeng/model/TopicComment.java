package com.hongluomeng.model;

import java.util.List;

import com.hongluomeng.common.Utility;

public class TopicComment extends Base<TopicComment> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_TOPIC_COMMENT = "table_topic_comment";
	public static final String COLUMN_TOPIC_COMMENT_ID = "topic_comment_id";
	public static final String COLUMN_TOPIC_ID = "topic_id";
    public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_CATEGORY_ID = "topic_comment_reply_to_user_id";
	public static final String COLUMN_CATEGORY_NAME = "topic_comment_text";

	private List<String> orderIdList;

	public String gettTopic_comment_id() {
		return getStr(COLUMN_TOPIC_COMMENT_ID);
	}

	public void setTopic_comment_id(String topic_comment_id) {
		set(COLUMN_TOPIC_COMMENT_ID, topic_comment_id);
	}

	public void checkTopic_comment_id() {
		Utility.checkStringLength(gettTopic_comment_id(), 32, "话题评论编号");
	}

	public String getTopic_id() {
		return getStr(COLUMN_TOPIC_ID);
	}

	public void setTopic_id(String topic_id) {
		set(COLUMN_TOPIC_ID, topic_id);
	}

    public void checkTopic_id() {
        Utility.checkStringLength(getTopic_id(), 32, "话题编号");
    }

    public String getUser_id() {
        return getStr(COLUMN_USER_ID);
    }

    public void setUser_id(String user_id) {
        set(COLUMN_USER_ID, user_id);
    }

    public void checkUser_id() {
        Utility.checkStringLength(getUser_id(), 32, "用户编号");
    }

	public String getTopic_comment_reply_to_user_id() {
		return getStr(COLUMN_CATEGORY_ID);
	}

	public void setTopic_comment_reply_to_user_id(String topic_comment_reply_to_user_id) {
		set(COLUMN_CATEGORY_ID, topic_comment_reply_to_user_id);
	}

    public void checkTopic_comment_reply_to_user_id() {
        Utility.checkStringLength(getTopic_comment_reply_to_user_id(), 0, 32, "话题评论回复用户编号");
    }

	public String getTopic_comment_text() {
		return getStr(COLUMN_CATEGORY_NAME);
	}

	public void setTopic_comment_text(String topic_comment_text) {
		set(COLUMN_CATEGORY_NAME, topic_comment_text);
	}

    public void checkTopic_comment_text() {
        Utility.checkStringLength(getTopic_comment_text(), 0, 150, "话题评论内容");
    }
}
