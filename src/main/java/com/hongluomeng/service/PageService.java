package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.PageDao;
import com.hongluomeng.model.Page;

public class PageService extends BaseService {

	private PageDao pageDao = new PageDao();
	private CategoryService categoryService = new CategoryService();
	private MemberService memberService = new MemberService();

	public Map<String, Object> list(JSONObject jsonObject) {
		Integer count = pageDao.count();

		List<Page> pageList = pageDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Page page : pageList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Page.COLUMN_PAGE_ID, page.getPage_id());
			map.put(Page.COLUMN_PAGE_NAME, page.getPage_name());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		Page pageMap = jsonObject.toJavaObject(Page.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Page> pageList = pageDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Page page : pageList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Page.COLUMN_PAGE_ID, page.getPage_id());
			map.put(Page.COLUMN_PAGE_NAME, page.getPage_name());
			map.put(Page.COLUMN_PAGE_KEY, page.getPage_key());

			list.add(map);
		}

		return list;
	}

	public Page find(JSONObject jsonObject) {
		Page pageMap = jsonObject.toJavaObject(Page.class);

		Page page = pageDao.findByPage_id(pageMap.getPage_id());

		return page;
	}

    public Map<String, Object> get(JSONObject jsonObject) {
        Page pageMap = jsonObject.toJavaObject(Page.class);

        Page page = pageDao.findByPage_id(pageMap.getPage_id());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Page.COLUMN_PAGE_ID, page.getPage_id());
        map.put(Page.COLUMN_PAGE_NAME, page.getPage_name());
        map.put(Page.COLUMN_PAGE_CONTENT, page.getPage_content());

        return map;
    }

    public Page ranking(JSONObject jsonObject) {
        Page pageMap = jsonObject.toJavaObject(Page.class);

        Page page = pageDao.findByPage_key("ranking");

        return page;
    }

	public void save(JSONObject jsonObject) {
		Page pageMap = jsonObject.toJavaObject(Page.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		pageDao.save(pageMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		Page pageMap = jsonObject.toJavaObject(Page.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		pageDao.update(pageMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Page pageMap = jsonObject.toJavaObject(Page.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		pageDao.delete(pageMap.getPage_id(), request_user_id);
	}

}
