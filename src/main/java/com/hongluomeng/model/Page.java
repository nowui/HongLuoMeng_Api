package com.hongluomeng.model;

import com.hongluomeng.common.Utility;

public class Page extends Base<Page> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_PAGE = "table_page";
	public static final String COLUMN_PAGE_ID = "page_id";
	public static final String COLUMN_PAGE_NAME = "page_name";
	public static final String COLUMN_PAGE_KEY = "page_key";
	public static final String COLUMN_PAGE_CONTENT = "page_content";
	public static final String COLUMN_PAGE_SORT = "page_sort";

	public String getPage_id() {
		return getStr(COLUMN_PAGE_ID);
	}

	public void setPage_id(String page_id) {
		set(COLUMN_PAGE_ID, page_id);
	}

	public void checkPage_id() {
		Utility.checkStringLength(getPage_id(), 32, "页面编号");
	}

	public String getPage_name() {
		return getStr(COLUMN_PAGE_NAME);
	}

	public void setPage_name(String page_name) {
		set(COLUMN_PAGE_NAME, page_name);
	}

	public void checkPage_name() {
		Utility.checkStringLength(getPage_name(), 3, 20, "页面名称");
	}

	public String getPage_key() {
		return getStr(COLUMN_PAGE_KEY);
	}

	public void setPage_key(String page_key) {
		set(COLUMN_PAGE_KEY, page_key);
	}

	public void checkPage_key() {
		Utility.checkStringLength(getPage_key(), 0, 100, "页面键值");
	}

	public String getPage_content() {
		return getStr(COLUMN_PAGE_CONTENT);
	}

	public void setPage_content(String page_content) {
		set(COLUMN_PAGE_CONTENT, page_content);
	}

	public void checkPage_content() {
		Utility.checkStringLength(getPage_content(), 0, 0, "页面内容");
	}

	public Integer getPage_sort() {
		return Utility.getIntegerValue(get(COLUMN_PAGE_SORT));
	}

	public void setPage_sort(Integer page_sort) {
		set(COLUMN_PAGE_SORT, page_sort);
	}

	public void checkPage_sort() {
		Utility.checkIntegerLength(getPage_sort(), 1, 3, "页面排序");
	}

}
