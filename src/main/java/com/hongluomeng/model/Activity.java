package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class Activity extends Base<Activity> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_ACTIVITY = "table_activity";
	public static final String KEY_ACTIVITY_ID = "activity_id";
	public static final String KEY_ACTIVITY_NAME = "activity_name";
	public static final String KEY_ACTIVITY_URL = "activity_url";
	public static final String KEY_ACTIVITY_IMAGE = "activity_image";
	public static final String KEY_ACTIVITY_CONTENT = "activity_content";
	public static final String KEY_ACTIVITY_SORT = "activity_sort";

	public String getActivity_id() {
		return getStr(KEY_ACTIVITY_ID);
	}

	public void setActivity_id(String activity_id) {
		set(KEY_ACTIVITY_ID, activity_id);
	}

	public void checkActivity_id() {
		Utility.checkStringLength(getActivity_id(), 32, "活动编号");
	}

	public String getActivity_name() {
		return getStr(KEY_ACTIVITY_NAME);
	}

	public void setActivity_name(String activity_name) {
		set(KEY_ACTIVITY_NAME, activity_name);
	}

	public void checkActivity_name() {
		Utility.checkStringLength(getActivity_name(), 3, 20, "活动名称");
	}

	public String getActivity_url() {
		return getStr(KEY_ACTIVITY_URL);
	}

	public void setActivity_url(String activity_url) {
		set(KEY_ACTIVITY_URL, activity_url);
	}

	public void checkActivity_url() {
		Utility.checkStringLength(getActivity_url(), 0, 100, "活动URL");
	}

	public String getActivity_image() {
		return getStr(KEY_ACTIVITY_IMAGE);
	}

	public void setActivity_image(String brand_image) {
		set(KEY_ACTIVITY_IMAGE, brand_image);
	}

	public void checkActivity_logo() {
		Utility.checkStringLength(getActivity_image(), 0, 100, "品牌图片");
	}

	public String getActivity_content() {
		return getStr(KEY_ACTIVITY_CONTENT);
	}

	public void setActivity_content(String activity_content) {
		set(KEY_ACTIVITY_CONTENT, activity_content);
	}

	public void checkActivity_content() {
		Utility.checkStringLength(getActivity_content(), 0, 0, "活动内容");
	}

	public Integer getActivity_sort() {
		return Utility.getIntegerValue(get(KEY_ACTIVITY_SORT));
	}

	public void setActivity_sort(Integer activity_sort) {
		set(KEY_ACTIVITY_SORT, activity_sort);
	}

	public void checkActivity_sort() {
		Utility.checkIntegerLength(getActivity_sort(), 1, 3, "活动排序");
	}

}
