package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.Attribute;
import com.hongluomeng.model.ProductAttribute;

public class CategoryAttributeDao {

	private List<CategoryAttribute> list(CategoryAttribute categoryAttribute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + ".*, " + Attribute.KEY_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_NAME + " FROM " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " ");
		sql.append("LEFT JOIN " + Attribute.KEY_ATTRIBUTE + " ON " + Attribute.KEY_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " = " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(categoryAttribute.getCategory_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ? ");
			parameterList.add(categoryAttribute.getCategory_id());

			isExit = true;
		}
		sql.append("ORDER BY " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " ASC ");

		List<Record> recordList = Db.find(sql.toString(), parameterList.toArray());

		List<CategoryAttribute> categoryAttributeList = new ArrayList<CategoryAttribute>();

		for (Record record : recordList) {
			CategoryAttribute c = new CategoryAttribute();

			c.setCategory_id(record.getStr(CategoryAttribute.KEY_CATEGORY_ID));
			c.setAttribute_id(record.getStr(CategoryAttribute.KEY_ATTRIBUTE_ID));
			c.setAttribute_name(record.getStr(CategoryAttribute.KEY_ATTRIBUTE_NAME));
			c.setCategory_attribute_sort(record.getInt(CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT));

			categoryAttributeList.add(c);
		}

		return categoryAttributeList;
	}

	public List<CategoryAttribute> listByCategory_id(String category_id) {
		CategoryAttribute categoryAttribute = new CategoryAttribute();
		categoryAttribute.setCategory_id(category_id);

		if (Utility.isNullOrEmpty(category_id)) {
			return new ArrayList<CategoryAttribute>();
		}

		return list(categoryAttribute);
	}

	public List<CategoryAttribute> listByProduct_idAndCategory_id(String product_id, String category_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + ".*, " + Attribute.KEY_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_NAME + ", IFNULL(" + ProductAttribute.KEY_PRODUCT_ATTRIBUTE + "." + ProductAttribute.KEY_ATTRIBUTE_VALUE + ", '') AS " + ProductAttribute.KEY_ATTRIBUTE_VALUE + " FROM " + Attribute.KEY_ATTRIBUTE + " ");
		sql.append("LEFT JOIN " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " ON " + Attribute.KEY_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " = " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " ");

		sql.append("LEFT JOIN (SELECT * FROM " + ProductAttribute.KEY_PRODUCT_ATTRIBUTE + " WHERE " + ProductAttribute.KEY_PRODUCT_ATTRIBUTE + "." + ProductAttribute.KEY_PRODUCT_ID + " = ?) AS " + ProductAttribute.KEY_PRODUCT_ATTRIBUTE + " ON " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " = " + ProductAttribute.KEY_PRODUCT_ATTRIBUTE + "." + ProductAttribute.KEY_ATTRIBUTE_ID + " ");
		parameterList.add(product_id);

		sql.append("WHERE " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ? ");
		parameterList.add(category_id);

		sql.append("ORDER BY " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " ASC ");

		List<Record> recordList = Db.find(sql.toString(), parameterList.toArray());

		List<CategoryAttribute> categoryAttributeList = new ArrayList<CategoryAttribute>();

		for (Record record : recordList) {
			CategoryAttribute c = new CategoryAttribute();

			c.setCategory_id(record.getStr(CategoryAttribute.KEY_CATEGORY_ID));
			c.setAttribute_id(record.getStr(CategoryAttribute.KEY_ATTRIBUTE_ID));
			c.setAttribute_name(record.getStr(CategoryAttribute.KEY_ATTRIBUTE_NAME));
			c.setCategory_attribute_sort(record.getInt(CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT));
			c.setAttribute_value(record.getStr(CategoryAttribute.KEY_ATTRIBUTE_VALUE));

			categoryAttributeList.add(c);
		}

		return categoryAttributeList;
	}

	public List<Attribute> listNotUsedAttributeByCategory_id(String category_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + Attribute.KEY_ATTRIBUTE + ".* FROM " + Attribute.KEY_ATTRIBUTE + " ");

		sql.append("LEFT JOIN (SELECT * FROM " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " WHERE " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ?) AS " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " ON " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " = " + Attribute.KEY_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " ");
		sql.append("WHERE " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " IS NULL ");
		sql.append("ORDER BY " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " ASC ");

		parameterList.add(category_id);

		List<Attribute> attributeList = new Attribute().find(sql.toString(), parameterList.toArray());

		return attributeList;
	}

	private CategoryAttribute find(CategoryAttribute categoryAttribute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + ".*, " + Attribute.KEY_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_NAME + " FROM " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " ");
		sql.append("LEFT JOIN " + Attribute.KEY_ATTRIBUTE + " ON " + Attribute.KEY_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " = " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(categoryAttribute.getCategory_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ? ");
			parameterList.add(categoryAttribute.getCategory_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(categoryAttribute.getAttribute_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ");
			parameterList.add(categoryAttribute.getAttribute_id());

			isExit = true;
		}

		if(! isExit) {
			return null;
		}

		List<Record> recordList = Db.find(sql.toString(), parameterList.toArray());

		if(recordList.size() == 0) {
			return null;
		} else {
			Record record = recordList.get(0);

			CategoryAttribute c = new CategoryAttribute();

			c.setCategory_id(record.getStr(CategoryAttribute.KEY_CATEGORY_ID));
			c.setAttribute_id(record.getStr(CategoryAttribute.KEY_ATTRIBUTE_ID));
			c.setAttribute_name(record.getStr(CategoryAttribute.KEY_ATTRIBUTE_NAME));
			c.setCategory_attribute_sort(record.getInt(CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT));

			return c;
		}
	}

	public CategoryAttribute findByCategory_idAndAttribute_id(String category_id, String attribute_id) {
		CategoryAttribute categoryAttribute = new CategoryAttribute();
		categoryAttribute.setCategory_id(category_id);
		categoryAttribute.setAttribute_id(attribute_id);

		return find(categoryAttribute);
	}

	public void save(CategoryAttribute categoryAttribute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " (" + CategoryAttribute.KEY_CATEGORY_ID + ", " + CategoryAttribute.KEY_ATTRIBUTE_ID + ", " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + ") SELECT ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " WHERE " + CategoryAttribute.KEY_CATEGORY_ID + " = ? AND " + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ?) ");

		parameterList.add(categoryAttribute.getCategory_id());
		parameterList.add(categoryAttribute.getAttribute_id());
		parameterList.add(categoryAttribute.getCategory_attribute_sort());
		parameterList.add(categoryAttribute.getCategory_id());
		parameterList.add(categoryAttribute.getAttribute_id());

		Db.update(sql.toString(), parameterList.toArray());
	}

	public void update(CategoryAttribute categoryAttribute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " SET " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " = ? WHERE " + CategoryAttribute.KEY_CATEGORY_ID + " = ? AND " + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ");

		parameterList.add(categoryAttribute.getCategory_attribute_sort());
		parameterList.add(categoryAttribute.getCategory_id());
		parameterList.add(categoryAttribute.getAttribute_id());

		Db.update(sql.toString(), parameterList.toArray());
	}

	public void delete(CategoryAttribute categoryAttribute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("DELETE FROM " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " WHERE " + CategoryAttribute.KEY_CATEGORY_ID + " = ? AND " + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ");

		parameterList.add(categoryAttribute.getCategory_id());
		parameterList.add(categoryAttribute.getAttribute_id());

		Db.update(sql.toString(), parameterList.toArray());
	}

	public void deleteByCategory_id(String category_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("DELETE FROM " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " WHERE ");
		sql.append(CategoryAttribute.KEY_CATEGORY_ID + " = ? ");

		parameterList.add(category_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

	public void deleteByAttribute_id(String attribute_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("DELETE FROM " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " WHERE ");
		sql.append(CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ");

		parameterList.add(attribute_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

}
