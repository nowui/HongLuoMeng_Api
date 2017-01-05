package com.hongluomeng.model;

import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;

import java.util.List;

public class Topic extends Base<Topic> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_TOPIC = "table_topic";
	public static final String COLUMN_TOPIC_ID = "topic_id";
    public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_TOPIC_TEXT = "topic_text";
    public static final String COLUMN_TOPIC_IMAGE = "topic_image";
    public static final String COLUMN_TOPIC_LIKE_LIST = "topic_like_list";
    public static final String COLUMN_TOPIC_COMMENT_LIST = "topic_comment_list";
    public static final String KEY_TOPIC_IS_HAVE_LIKE = "topic_is_have_like";
    public static final String KEY_TOPIC_LARGE_IMAGE = "topic_large_image";
    public static final String KEY_TOPIC_URL = "topic_url";

    private List<TopicLike> topicLikeList;
    private List<TopicComment> topicCommentList;

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

	public String getTopic_text() {
		return getStr(COLUMN_TOPIC_TEXT);
	}

	public void setTopic_text(String topic_text) {
		set(COLUMN_TOPIC_TEXT, topic_text);
	}

	public void checkTopic_text() {
		Utility.checkStringLength(getTopic_text(), 0, 255, "话题文字");
	}

    public JSONArray getTopic_image() {
        if(Utility.isNullOrEmpty(getStr(COLUMN_TOPIC_IMAGE))) {
            return JSONArray.parseArray(Const.ARRAY_EMPTY);
        } else {
            return JSONArray.parseArray(getStr(COLUMN_TOPIC_IMAGE));
        }
    }

    public JSONArray getTopic_large_image() {
        if(Utility.isNullOrEmpty(getStr(COLUMN_TOPIC_IMAGE))) {
            return JSONArray.parseArray(Const.ARRAY_EMPTY);
        } else {
            JSONArray array = new JSONArray();

            for (int i = 0; i < getTopic_image().size(); i++) {
                array.add(Utility.packageImagePath(getTopic_image().get(i).toString(), Const.UPLOAD_LARGE));
            }

            return array;
        }
    }

	public void setTopic_image(String brand_image) {
		set(COLUMN_TOPIC_IMAGE, brand_image);
	}

	public void checkTopic_image() {
		Utility.checkStringLength(getTopic_image().toJSONString(), 0, 1000, "话题图片");
	}

	public Member getMember() {
        return new Member().put(this);
    }

    public List<TopicLike> getTopicLikeList() {
        return topicLikeList;
    }

    public void setTopicLikeList(List<TopicLike> topicLikeList) {
        this.topicLikeList = topicLikeList;
    }

    public List<TopicComment> getTopicCommentList() {
        return topicCommentList;
    }

    public void setTopicCommentList(List<TopicComment> topicCommentList) {
        this.topicCommentList = topicCommentList;
    }

}
