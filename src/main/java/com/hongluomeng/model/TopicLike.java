package com.hongluomeng.model;

import java.util.List;

import com.hongluomeng.common.Utility;

public class TopicLike extends Base<TopicLike> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_TOPIC_LIKE = "table_topic_like";
	public static final String COLUMN_TOPIC_LIKE_ID = "topic_like_id";
	public static final String COLUMN_TOPIC_ID = "topic_id";
    public static final String COLUMN_USER_ID = "user_id";

	private List<String> orderIdList;

	public String gettTopic_like_id() {
		return getStr(COLUMN_TOPIC_LIKE_ID);
	}

	public void setTopic_like_id(String topic_like_id) {
		set(COLUMN_TOPIC_LIKE_ID, topic_like_id);
	}

	public void checkTopic_like_id() {
		Utility.checkStringLength(gettTopic_like_id(), 32, "话题评论编号");
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
}
