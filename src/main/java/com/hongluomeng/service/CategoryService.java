package com.hongluomeng.service;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.CategoryDao;
import com.hongluomeng.model.Category;

public class CategoryService extends BaseService {

	private CategoryDao categoryDao = new CategoryDao();
	private CategoryAttributeService categoryAttributeService = new CategoryAttributeService();

	public Integer count(JSONObject jsonObject) {
		return categoryDao.count();
	}

	public Map<String, Object> list(JSONObject jsonObject) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Const.KEY_ID, "");
		resultMap.put(Const.KEY_TEXT, "系统分类");
		resultMap.put(Const.KEY_CHILDREN, getChildrenList(categoryDao.listTopCategory(), ""));

		return resultMap;
	}

	public List<Map<String, Object>> list(String category_key) {
		List<Category> categoryList = listByCategory_key(category_key);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for(Category category : categoryList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Category.COLUMN_CATEGORY_ID, category.getCategory_id());
			map.put(Category.COLUMN_CATEGORY_NAME, category.getCategory_name());

			resultList.add(map);
		}

		return resultList;
	}

	public List<Category> listByCategory_path(String category_path) {
		return categoryDao.listByCategory_path(category_path);
	}

	public List<Category> listByCategory_key(String category_key) {
		Category category = findByCategory_key(category_key);

		List<Category> categorieList = listByCategory_path(category.getCategory_id());

		List<Category> resultList = new ArrayList<Category>();

		setChildren(resultList, categorieList, category.getCategory_id());

		return resultList;
	}

	private void setChildren(List<Category> list, List<Category> categoryList, String category_id) {
        for (Category category : categoryList) {
            if (category.getParent_id().equals(category_id)) {
                list.add(category);

                setChildren(list, categoryList, category.getCategory_id());
            }
        }
	}

	public Map<String, Object> treeByCategory_key(String ategory_key) {
		Category category = categoryDao.findByCategory_key(ategory_key);

		if (category == null) {
			throw new RuntimeException("没有该分类");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Const.KEY_ID, category.getCategory_id());
		resultMap.put(Const.KEY_NAME, category.getCategory_name());
		//resultMap.put(Const.KEY_SORT, category.getCategory_sort());
		resultMap.put(Const.KEY_CHILDREN, getChildrenList(categoryDao.listByCategory_path(category.getCategory_id()), category.getCategory_id()));

		return resultMap;
	}

	private List<Map<String, Object>> getChildrenList(List<Category> categoryList, String parent_id) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Category category : categoryList) {
			if (category.getParent_id().equals(parent_id)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Const.KEY_ID, category.getCategory_id());
				map.put(Const.KEY_NAME, category.getCategory_name());
				//map.put(Const.KEY_SORT, category.getCategory_sort());

				List<Map<String, Object>> childrenList = getChildrenList(categoryList, category.getCategory_id());
				if (childrenList.size() > 0) {
					//Map<String, Object> stateMap = new HashMap<String, Object>();
					//stateMap.put(Const.KEY_OPENED, true);

					//map.put(Const.KEY_STATE, stateMap);
					map.put(Const.KEY_CHILDREN, childrenList);
				} else {
					//map.put(Const.KEY_ICON, "none");
					map.put(Const.KEY_CHILDREN, new ArrayList<Map<String, Object>>());
				}
				list.add(map);
			}
		}
		return list;
	}

	public Category find(JSONObject jsonObject) {
		Category categoryMap = jsonObject.toJavaObject(Category.class);

		categoryMap.checkCategory_id();

		Category category = categoryDao.findByCategory_id(categoryMap.getCategory_id());

		return category;
	}

	public Category findByCategory_key(String category_key) {
		return categoryDao.findByCategory_key(category_key);
	}

	public Category save(JSONObject jsonObject) {
		Category categoryMap = jsonObject.toJavaObject(Category.class);

		categoryMap.checkParent_id();

		categoryMap.checkCategory_name();

		categoryMap.checkCategory_key();

		categoryMap.checkCategory_value();

		categoryMap.checkCategory_sort();

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Integer count = 0;

		if (!Utility.isNullOrEmpty(categoryMap.getCategory_key())) {
			count = categoryDao.countByCategory_keyNotEqualCategory_id(categoryMap.getCategory_id(), categoryMap.getCategory_key());
		}

		if (count == 0) {
			Category category = categoryDao.findByCategory_id(categoryMap.getParent_id());

			if (category == null) {
				categoryMap.setCategory_path("");
			} else {
				if (Utility.isNullOrEmpty(category.getCategory_path())) {
					categoryMap.setCategory_path("'" + category.getCategory_id() + "'");
				} else {
					categoryMap.setCategory_path(category.getCategory_path() + ",'" + category.getCategory_id() + "'");
				}
			}

			return categoryDao.save(categoryMap, request_user_id);
		} else {
			throw new RuntimeException("键已经存在");
		}
	}

	public void update(JSONObject jsonObject) {
		Category categoryMap = jsonObject.toJavaObject(Category.class);

		categoryMap.checkCategory_id();

		categoryMap.checkCategory_name();

		categoryMap.checkCategory_key();

		categoryMap.checkCategory_value();

		categoryMap.checkCategory_sort();

		categoryMap.checkCategory_description();

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Integer count = 0;

		if (!Utility.isNullOrEmpty(categoryMap.getCategory_key())) {
			count = categoryDao.countByCategory_keyNotEqualCategory_id(categoryMap.getCategory_id(), categoryMap.getCategory_key());
		}

		if (count == 0) {
			categoryDao.update(categoryMap, request_user_id);
		} else {
			throw new RuntimeException("键已经存在");
		}

	}

	public void delete(JSONObject jsonObject) {
		Category categoryMap = jsonObject.toJavaObject(Category.class);

		categoryMap.checkCategory_id();

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		categoryDao.deleteByCategory_id(categoryMap.getCategory_id(), request_user_id);

		categoryAttributeService.deleteByCategory_id(categoryMap.getCategory_id());
	}

}
