package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.hongluomeng.model.Attribute;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.CategoryAttributeValue;

public class CategoryAttributeValueDao extends BaseDao {

	public List<CategoryAttributeValue> listByObject_idAndObject_typeAndCategory_id(String object_id, String object_type, String category_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT ");
		myDynamicSQL.append(CategoryAttribute.TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.COLUMN_CATEGORY_ID + ", ");
		myDynamicSQL.append(CategoryAttribute.TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.COLUMN_ATTRIBUTE_ID + ", ");
		myDynamicSQL.append(Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_NAME + ", ");
		myDynamicSQL.append(Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_DEFAULT_VALUE + ", ");
		myDynamicSQL.append(Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_TYPE + ", ");
		myDynamicSQL.append("IFNULL(" + CategoryAttributeValue.TABLE_CATEGORY_ATTRIBUTE_VALUE + "." + CategoryAttributeValue.COLUMN_ATTRIBUTE_VALUE + ", '') AS " + CategoryAttributeValue.COLUMN_ATTRIBUTE_VALUE + " ");
		myDynamicSQL.append("FROM " + CategoryAttribute.TABLE_CATEGORY_ATTRIBUTE + " ");
		myDynamicSQL.append("LEFT JOIN (SELECT * FROM " + CategoryAttributeValue.TABLE_CATEGORY_ATTRIBUTE_VALUE + " WHERE " + CategoryAttributeValue.COLUMN_OBJECT_ID + " = ? AND " + CategoryAttributeValue.COLUMN_OBJECT_TYPE + " = ?) AS " + CategoryAttributeValue.TABLE_CATEGORY_ATTRIBUTE_VALUE + " ON " + CategoryAttribute.TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.COLUMN_ATTRIBUTE_ID + " = " + CategoryAttributeValue.TABLE_CATEGORY_ATTRIBUTE_VALUE + "." + CategoryAttributeValue.COLUMN_ATTRIBUTE_ID + " ", object_id, object_type);
		myDynamicSQL.append("LEFT JOIN table_attribute ON table_attribute.attribute_id = " + CategoryAttribute.TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.COLUMN_ATTRIBUTE_ID + " ");
		myDynamicSQL.append("WHERE " + CategoryAttribute.TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.COLUMN_CATEGORY_ID + " = ? ", category_id);
		myDynamicSQL.append("ORDER BY " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_TYPE + ", " + CategoryAttribute.TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.COLUMN_CATEGORY_ATTRIBUTE_SORT + " ");

		return new CategoryAttributeValue().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void saveByObject_idAndObject_typeAndCategoryAttributeList(String object_id, String object_type, List<CategoryAttributeValue> categoryAttributeValueList) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + CategoryAttributeValue.TABLE_CATEGORY_ATTRIBUTE_VALUE + " (" + CategoryAttributeValue.COLUMN_OBJECT_ID + ", " + CategoryAttributeValue.COLUMN_OBJECT_TYPE + ", " + CategoryAttributeValue.COLUMN_ATTRIBUTE_ID + ", " + CategoryAttributeValue.COLUMN_ATTRIBUTE_VALUE + ") VALUES (?, ?, ?, ?) ");

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
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("DELETE FROM " + CategoryAttributeValue.TABLE_CATEGORY_ATTRIBUTE_VALUE + " WHERE " + CategoryAttributeValue.COLUMN_OBJECT_ID + " = ? AND " + CategoryAttributeValue.COLUMN_OBJECT_TYPE + " = ? ", object_id, object_type);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void deleteByAttribute_id(String attribute_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("DELETE FROM " + CategoryAttributeValue.TABLE_CATEGORY_ATTRIBUTE_VALUE + " WHERE " + CategoryAttributeValue.COLUMN_ATTRIBUTE_ID + " = ? ", attribute_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
