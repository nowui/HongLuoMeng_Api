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
import com.hongluomeng.model.ProductAttribute;

public class CategoryAttributeService {

	private CategoryAttributeDao categoryAttributeDao = new CategoryAttributeDao();

	public List<CategoryAttribute> list(JSONObject jsonObject) {
		CategoryAttribute categoryAttributeMap = jsonObject.toJavaObject(CategoryAttribute.class);

		List<CategoryAttribute> categoryAttributeList;

		String product_id = jsonObject.getString(ProductAttribute.KEY_PRODUCT_ID);

		if (Utility.isNullOrEmpty(product_id)) {
			categoryAttributeList = categoryAttributeDao.listByCategory_id(categoryAttributeMap.getCategory_id());

			for (CategoryAttribute categoryAttribute : categoryAttributeList) {
				categoryAttribute.setAttribute_value("");
			}
		} else {
			categoryAttributeList = categoryAttributeDao.listByProduct_idAndCategory_id(product_id, categoryAttributeMap.getCategory_id());
		}

		return categoryAttributeList;
	}

	public List<CategoryAttribute> listByCategory_id(String category_id) {
		return categoryAttributeDao.listByCategory_id(category_id);
	}

	public List<CategoryAttribute> listByProduct_idAndCategory_id(String product_id, String category_id) {
		List<CategoryAttribute> categoryAttributeList = categoryAttributeDao.listByProduct_idAndCategory_id(product_id, category_id);

		for(CategoryAttribute categoryAttribute : categoryAttributeList) {
			categoryAttribute.setCategory_id(null);
			categoryAttribute.setCategory_attribute_sort(null);
		}

		return categoryAttributeList;
	}

	public Map<String, Object> find(JSONObject jsonObject) {
		CategoryAttribute categoryAttributeMap = jsonObject.toJavaObject(CategoryAttribute.class);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (Utility.isNullOrEmpty(categoryAttributeMap.getAttribute_id())) {
			resultMap.put("category_id", "");
			resultMap.put("attribute_id", "");
			resultMap.put("category_attribute_sort", 0);

			resultMap.put("attributeList", categoryAttributeDao.listNotUsedAttributeByCategory_id(categoryAttributeMap.getCategory_id()));
		} else {
			CategoryAttribute categoryAttribute = categoryAttributeDao.findByCategory_idAndAttribute_id(categoryAttributeMap.getCategory_id(), categoryAttributeMap.getAttribute_id());

			resultMap.put("category_id", categoryAttribute.getCategory_id());
			resultMap.put("attribute_id", categoryAttribute.getAttribute_id());
			resultMap.put("category_attribute_sort", categoryAttribute.getCategory_attribute_sort());

			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Attribute.KEY_ATTRIBUTE_ID, categoryAttribute.getAttribute_id());
			map.put(Attribute.KEY_ATTRIBUTE_NAME, categoryAttribute.getAttribute_name());
			list.add(map);

			resultMap.put("attributeList", list);
		}


		return resultMap;
	}

	public void save(JSONObject jsonObject) {
		CategoryAttribute categoryAttributeMap = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeDao.save(categoryAttributeMap);
	}

	public void update(JSONObject jsonObject) {
		CategoryAttribute categoryAttributeMap = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeDao.update(categoryAttributeMap);
	}

	public void delete(JSONObject jsonObject) {
		CategoryAttribute categoryAttributeMap = jsonObject.toJavaObject(CategoryAttribute.class);

		categoryAttributeDao.delete(categoryAttributeMap);
	}

	public void deleteByCategory_id(String category_id) {
		categoryAttributeDao.deleteByCategory_id(category_id);
	}

	public void deleteByAttribute_id(String attribute_id) {
		categoryAttributeDao.deleteByAttribute_id(attribute_id);
	}

}
