package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.ProductAttribute;

public class ProductAttributeDao {

	public void saveByProduct_idAndCategory_Attribute(String product_id, List<CategoryAttribute> categoryAttributeList) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + ProductAttribute.KEY_TABLE_PRODUCT_ATTRIBUTE + " (" + ProductAttribute.KEY_PRODUCT_ID + ", " + ProductAttribute.KEY_ATTRIBUTE_ID + ", " + ProductAttribute.KEY_ATTRIBUTE_VALUE + ") VALUES (?, ?, ?) ");

		for(CategoryAttribute categoryAttribute : categoryAttributeList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(product_id);
			objectList.add(categoryAttribute.getAttribute_id());
			objectList.add(categoryAttribute.getAttribute_value());

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

	public void deleteByProduct_id(String product_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("DELETE FROM " + ProductAttribute.KEY_TABLE_PRODUCT_ATTRIBUTE + " WHERE " + ProductAttribute.KEY_PRODUCT_ID + " = ? ", product_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void deleteByAttribute_id(String attribute_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("DELETE FROM " + ProductAttribute.KEY_TABLE_PRODUCT_ATTRIBUTE + " WHERE " + ProductAttribute.KEY_ATTRIBUTE_ID + " = ? ", attribute_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

}
