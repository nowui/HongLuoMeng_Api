package com.hongluomeng.model;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;

public class TopicComment extends Base<TopicComment> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_TOPIC_COMMENT = "table_topic_comment";
	public static final String COLUMN_TOPIC_COMMENT_ID = "topic_comment_id";
	public static final String COLUMN_TOPIC_ID = "topic_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_ID = "topic_comment_reply_to_member_id";
    public static final String COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_NAME = "topic_comment_reply_to_member_name";
    public static final String COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_AVATAR = "topic_comment_reply_to_member_avatar";
	public static final String COLUMN_TOPIC_COMMENT_TEXT = "topic_comment_text";

    private List<String> topicIdList;

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

	public String getTopic_comment_reply_to_member_id() {
		return getStr(COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_ID);
	}

	public void setTopic_comment_reply_to_member_id(String topic_comment_reply_to_member_id) {
		set(COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_ID, topic_comment_reply_to_member_id);
	}

    public void checkTopic_comment_reply_to_member_id() {
        Utility.checkStringLength(getTopic_comment_reply_to_member_id(), 0, 32, "话题评论回复用户编号");
    }

	public String getTopic_comment_text() {
		return getStr(COLUMN_TOPIC_COMMENT_TEXT);
	}

	public void setTopic_comment_text(String topic_comment_text) {
		set(COLUMN_TOPIC_COMMENT_TEXT, topic_comment_text);
	}

    public void checkTopic_comment_text() {
        Utility.checkStringLength(getTopic_comment_text(), 0, 150, "话题评论内容");
    }

    public List<String> getTopicIdList() {
        return topicIdList;
    }

    public void setTopicIdList(List<String> topicIdList) {
        this.topicIdList = topicIdList;
    }

    public Member getMember() {
        return new Member().put(this);
    }

    public String getTopic_comment_reply_to_member_name() {
        return getStr(COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_NAME);
    }

    public String getTopic_comment_reply_to_member_avatar() {
        JSONObject topic_comment_reply_to_member_avatar = JSONObject.parseObject(getStr(COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_AVATAR));
        if(Utility.isNull(topic_comment_reply_to_member_avatar)) {
            return "";
        } else {
            return topic_comment_reply_to_member_avatar.getString(Const.UPLOAD_SMALL);
        }
    }
}
