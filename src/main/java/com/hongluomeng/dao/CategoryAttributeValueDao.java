package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.hongluomeng.model.Attribute;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.CategoryAttributeValue;

public class CategoryAttributeValueDao {

	public List<CategoryAttributeValue> listByObject_idAndObject_typeAndCategory_id(String object_id, String object_type, String category_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT ");
		dynamicSQL.append(CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + ", ");
		dynamicSQL.append(CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + ", ");
		dynamicSQL.append(Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_NAME + ", ");
		dynamicSQL.append(Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_DEFAULT_VALUE + ", ");
		dynamicSQL.append(Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_TYPE + ", ");
		dynamicSQL.append("IFNULL(" + CategoryAttributeValue.KEY_TABLE_CATEGORY_ATTRIBUTE_VALUE + "." + CategoryAttributeValue.KEY_ATTRIBUTE_VALUE + ", '') AS " + CategoryAttributeValue.KEY_ATTRIBUTE_VALUE + " ");
		dynamicSQL.append("FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " ");
		dynamicSQL.append("LEFT JOIN (SELECT * FROM " + CategoryAttributeValue.KEY_TABLE_CATEGORY_ATTRIBUTE_VALUE + " WHERE " + CategoryAttributeValue.KEY_OBJECT_ID + " = ? AND " + CategoryAttributeValue.KEY_OBJECT_TYPE + " = ?) AS " + CategoryAttributeValue.KEY_TABLE_CATEGORY_ATTRIBUTE_VALUE + " ON " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " = " + CategoryAttributeValue.KEY_TABLE_CATEGORY_ATTRIBUTE_VALUE + "." + CategoryAttributeValue.KEY_ATTRIBUTE_ID + " ", object_id, object_type);
		dynamicSQL.append("LEFT JOIN table_attribute ON table_attribute.attribute_id = " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " ");
		dynamicSQL.append("WHERE " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ? ", category_id);
		dynamicSQL.append("ORDER BY " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_TYPE + ", " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " ");

		return new CategoryAttributeValue().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void saveByObject_idAndObject_typeAndCategoryAttributeList(String object_id, String object_type, List<CategoryAttributeValue> categoryAttributeValueList) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + CategoryAttributeValue.KEY_TABLE_CATEGORY_ATTRIBUTE_VALUE + " (" + CategoryAttributeValue.KEY_OBJECT_ID + ", " + CategoryAttributeValue.KEY_OBJECT_TYPE + ", " + CategoryAttributeValue.KEY_ATTRIBUTE_ID + ", " + CategoryAttributeValue.KEY_ATTRIBUTE_VALUE + ") VALUES (?, ?, ?, ?) ");

		for(CategoryAttributeValue categoryAttributeValue : categoryAttributeValueList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(object_id);
			objectList.add(object_type);
			objectList.add(categoryAttributeValue.getAttribute_id());
			objectList.add(categoryAttributeValue.getAttribute_value());

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

	public void deleteByObject_idAndObject_type(String object_id, String object_type) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("DELETE FROM " + CategoryAttributeValue.KEY_TABLE_CATEGORY_ATTRIBUTE_VALUE + " WHERE " + CategoryAttributeValue.KEY_OBJECT_ID + " = ? AND " + CategoryAttributeValue.KEY_OBJECT_TYPE + " = ? ", object_id, object_type);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void deleteByAttribute_id(String attribute_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("DELETE FROM " + CategoryAttributeValue.KEY_TABLE_CATEGORY_ATTRIBUTE_VALUE + " WHERE " + CategoryAttributeValue.KEY_ATTRIBUTE_ID + " = ? ", attribute_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

}
