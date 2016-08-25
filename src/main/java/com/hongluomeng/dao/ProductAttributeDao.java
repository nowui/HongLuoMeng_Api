package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.ProductAttribute;

public class ProductAttributeDao {

	/*private List<ProductAttribute> list(ProductAttribute productAttribute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + ProductAttribute.KEY_PRODUCT_ATTRIBUTE + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(productAttribute.getProduct_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(ProductAttribute.KEY_PRODUCT_ID + " = ? ");
			parameterList.add(productAttribute.getProduct_id());

			isExit = true;
		}

		List<ProductAttribute> productAttributeList = productAttribute.find(sql.toString(), parameterList.toArray());
		return productAttributeList;
	}

	public List<ProductAttribute> listByProduct_id(String role_id) {
		ProductAttribute productAttribute = new ProductAttribute();
		productAttribute.setProduct_id(role_id);

		return list(productAttribute);
	}*/

	public void saveByProduct_idAndCategory_Attribute(String product_id, List<CategoryAttribute> categoryAttributeList) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + ProductAttribute.KEY_PRODUCT_ATTRIBUTE + " (" + ProductAttribute.KEY_PRODUCT_ID + ", " + ProductAttribute.KEY_ATTRIBUTE_ID + ", " + ProductAttribute.KEY_ATTRIBUTE_VALUE + ") VALUES (?, ?, ?) ");

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
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("DELETE FROM " + ProductAttribute.KEY_PRODUCT_ATTRIBUTE + " WHERE " + ProductAttribute.KEY_PRODUCT_ID + " = ? ");

		parameterList.add(product_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

	public void deleteByAttribute_id(String attribute_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("DELETE FROM " + ProductAttribute.KEY_PRODUCT_ATTRIBUTE + " WHERE " + ProductAttribute.KEY_ATTRIBUTE_ID + " = ? ");

		parameterList.add(attribute_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

}
