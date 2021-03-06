package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.CategoryAttributeDao;
import com.hongluomeng.model.Attribute;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.CategoryAttributeValue;

public class CategoryAttributeService extends BaseService {

	private CategoryAttributeDao categoryAttributeDao = new CategoryAttributeDao();

	public List<Map<String, Object>> list(JSONObject jsonObject) {
		CategoryAttribute categoryAttributeMap = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeMap.checkCategory_id();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<CategoryAttribute> categoryAttributeList = categoryAttributeDao.listByCategory_id(categoryAttributeMap.getCategory_id());

		for(CategoryAttribute categoryAttribute : categoryAttributeList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(CategoryAttribute.COLUMN_ATTRIBUTE_ID, categoryAttribute.getAttribute_id());
			map.put(Attribute.KEY_ATTRIBUTE_NAME, categoryAttribute.getAttribute_name());
			map.put(Attribute.KEY_ATTRIBUTE_TYPE, categoryAttribute.getAttribute_type());
			map.put(Attribute.KEY_ATTRIBUTE_DEFAULT_VALUE, categoryAttribute.getAttribute_default_value());
			map.put(CategoryAttributeValue.COLUMN_ATTRIBUTE_VALUE, "");
			list.add(map);
		}

		return list;
	}

	public Map<String, Object> find(JSONObject jsonObject) {
		CategoryAttribute categoryAttributeMap = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeMap.checkCategory_id();

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (Utility.isNullOrEmpty(categoryAttributeMap.getAttribute_id())) {
			resultMap.put(CategoryAttribute.COLUMN_CATEGORY_ID, "");
			resultMap.put(CategoryAttribute.COLUMN_ATTRIBUTE_ID, "");
			resultMap.put(CategoryAttribute.COLUMN_CATEGORY_ATTRIBUTE_SORT, 0);

			resultMap.put(CategoryAttribute.COLUMN_ATTRIBUTE_LIST, categoryAttributeDao.listNotUsedAttributeByCategory_id(categoryAttributeMap.getCategory_id()));
		} else {
			CategoryAttribute categoryAttribute = categoryAttributeDao.findByCategory_idAndAttribute_id(categoryAttributeMap.getCategory_id(), categoryAttributeMap.getAttribute_id());

			resultMap.put(CategoryAttribute.COLUMN_CATEGORY_ID, categoryAttribute.getCategory_id());
			resultMap.put(CategoryAttribute.COLUMN_ATTRIBUTE_ID, categoryAttribute.getAttribute_id());
			resultMap.put(CategoryAttribute.COLUMN_CATEGORY_ATTRIBUTE_SORT, categoryAttribute.getCategory_attribute_sort());

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Attribute.COLUMN_ATTRIBUTE_ID, categoryAttribute.getAttribute_id());
			map.put(Attribute.KEY_ATTRIBUTE_NAME, categoryAttribute.getAttribute_name());
			list.add(map);

			resultMap.put(CategoryAttribute.COLUMN_ATTRIBUTE_LIST, list);
		}


		return resultMap;
	}

	public void save(JSONObject jsonObject) {
		CategoryAttribute categoryAttributeMap = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeMap.checkCategory_id();

		categoryAttributeMap.checkAttribute_id();

		categoryAttributeMap.checkCategory_attribute_sort();

		categoryAttributeDao.save(categoryAttributeMap);
	}

	public void update(JSONObject jsonObject) {
		CategoryAttribute categoryAttributeMap = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeMap.checkCategory_id();

		categoryAttributeMap.checkAttribute_id();

		categoryAttributeMap.checkCategory_attribute_sort();

		categoryAttributeDao.update(categoryAttributeMap);
	}

	public void delete(JSONObject jsonObject) {
		CategoryAttribute categoryAttributeMap = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeMap.checkCategory_id();

		categoryAttributeMap.checkAttribute_id();

		categoryAttributeDao.delete(categoryAttributeMap);
	}

	public void deleteByCategory_id(String category_id) {
		categoryAttributeDao.deleteByCategory_id(category_id);
	}

	public void deleteByAttribute_id(String attribute_id) {
		categoryAttributeDao.deleteByAttribute_id(attribute_id);
	}

}
