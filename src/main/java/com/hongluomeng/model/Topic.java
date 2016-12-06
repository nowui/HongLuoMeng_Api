package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class Topic extends Base<Topic> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_TOPIC = "table_topic";
	public static final String KEY_TOPIC_ID = "topic_id";
	public static final String KEY_TOPIC_NAME = "topic_name";
	public static final String KEY_TOPIC_URL = "topic_url";
	public static final String KEY_TOPIC_IMAGE = "topic_image";
	public static final String KEY_TOPIC_CONTENT = "topic_content";
	public static final String KEY_TOPIC_SORT = "topic_sort";

	public String getTopic_id() {
		return getStr(KEY_TOPIC_ID);
	}

	public void setTopic_id(String topic_id) {
		set(KEY_TOPIC_ID, topic_id);
	}

	public void checkTopic_id() {
		Utility.checkStringLength(getTopic_id(), 32, "活动编号");
	}

	public String getTopic_name() {
		return getStr(KEY_TOPIC_NAME);
	}

	public void setTopic_name(String topic_name) {
		set(KEY_TOPIC_NAME, topic_name);
	}

	public void checkTopic_name() {
		Utility.checkStringLength(getTopic_name(), 3, 20, "活动名称");
	}

	public String getTopic_url() {
		return getStr(KEY_TOPIC_URL);
	}

	public void setTopic_url(String topic_url) {
		set(KEY_TOPIC_URL, topic_url);
	}

	public void checkTopic_url() {
		Utility.checkStringLength(getTopic_url(), 0, 100, "活动URL");
	}

	public String getTopic_image() {
		return getStr(KEY_TOPIC_IMAGE);
	}

	public void setTopic_image(String brand_image) {
		set(KEY_TOPIC_IMAGE, brand_image);
	}

	public void checkTopic_logo() {
		Utility.checkStringLength(getTopic_image(), 0, 100, "品牌图片");
	}

	public String getTopic_content() {
		return getStr(KEY_TOPIC_CONTENT);
	}

	public void setTopic_content(String topic_content) {
		set(KEY_TOPIC_CONTENT, topic_content);
	}

	public void checkTopic_content() {
		Utility.checkStringLength(getTopic_content(), 0, 0, "活动内容");
	}

	public Integer getTopic_sort() {
		return Utility.getIntegerValue(get(KEY_TOPIC_SORT));
	}

	public void setTopic_sort(Integer topic_sort) {
		set(KEY_TOPIC_SORT, topic_sort);
	}

	public void checkTopic_sort() {
		Utility.checkIntegerLength(getTopic_sort(), 1, 3, "活动排序");
	}

}
