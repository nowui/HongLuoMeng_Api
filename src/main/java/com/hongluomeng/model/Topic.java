package com.hongluomeng.model;

import com.alibaba.fastjson.JSONArray;
import com.hongluomeng.common.Utility;

public class Topic extends Base<Topic> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_TOPIC = "table_topic";
	public static final String COLUMN_TOPIC_ID = "topic_id";
    public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_TOPIC_TEXT = "topic_text";
	public static final String COLUMN_TOPIC_IMAGE = "topic_image";

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
		Utility.checkStringLength(getTopic_text(), 1, 255, "话题文字");
	}

	public JSONArray getTopic_image() {
		return JSONArray.parseArray(getStr(COLUMN_TOPIC_IMAGE));
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

}
