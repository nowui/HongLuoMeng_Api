package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.CategoryAttributeValueDao;
import com.hongluomeng.model.CategoryAttributeValue;

public class CategoryAttributeValueService {

	private CategoryAttributeValueDao categoryAttributeValueDao = new CategoryAttributeValueDao();

	public List<CategoryAttributeValue> listByObject_idAndObject_typeAndCategory_id(String object_id, String object_type, String category_id) {
		return categoryAttributeValueDao.listByObject_idAndObject_typeAndCategory_id(object_id, object_type, category_id);
	}

	public void saveByObject_idAndObject_typeAndCategoryAttributeList(String object_id, String object_type, List<CategoryAttributeValue> categoryAttributeValueList) {
		categoryAttributeValueDao.deleteByObject_idAndObject_type(object_id, object_type);

		categoryAttributeValueDao.saveByObject_idAndObject_typeAndCategoryAttributeList(object_id, object_type, categoryAttributeValueList);
	}

	public void deleteByAttribute_id(String attribute_id) {
		categoryAttributeValueDao.deleteByAttribute_id(attribute_id);
	}

}
