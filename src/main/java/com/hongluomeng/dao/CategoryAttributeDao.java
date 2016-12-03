package com.hongluomeng.dao;

import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.Attribute;

public class CategoryAttributeDao {

	private List<CategoryAttribute> list(CategoryAttribute categoryAttribute) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + ".* ");
		myDynamicSQL.append(", " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_NAME + " ");
		myDynamicSQL.append(", " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_TYPE + " ");
		myDynamicSQL.append(", " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_DEFAULT_VALUE + " ");
		myDynamicSQL.append("FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " ");
		myDynamicSQL.append("LEFT JOIN " + Attribute.KEY_TABLE_ATTRIBUTE + " ON " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " = " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " ");
		myDynamicSQL.append("WHERE 1 = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ? ", categoryAttribute.getCategory_id());
		myDynamicSQL.append("ORDER BY " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_TYPE + ", " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " ASC ");

		return new CategoryAttribute().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<CategoryAttribute> listByCategory_id(String category_id) {
		CategoryAttribute categoryAttribute = new CategoryAttribute();
		categoryAttribute.setCategory_id(category_id);

		categoryAttribute.checkCategory_id();

		return list(categoryAttribute);
	}

	public List<Attribute> listNotUsedAttributeByCategory_id(String category_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT " + Attribute.KEY_TABLE_ATTRIBUTE + ".* FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");
		myDynamicSQL.append("LEFT JOIN (SELECT * FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " WHERE " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ?) AS " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " ON " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " = " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " ", category_id);
		myDynamicSQL.append("WHERE " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " IS NULL ");
		myDynamicSQL.append("AND " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " ASC ");

		return new Attribute().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	private CategoryAttribute find(CategoryAttribute categoryAttribute) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + ".*, " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_NAME + " FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " ");
		myDynamicSQL.append("LEFT JOIN " + Attribute.KEY_TABLE_ATTRIBUTE + " ON " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " = " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " ");
		myDynamicSQL.append("WHERE 1 = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ? ", categoryAttribute.getCategory_id());
		myDynamicSQL.isNullOrEmpty("AND " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ", categoryAttribute.getAttribute_id());

		List<CategoryAttribute> categoryAttributeList = new CategoryAttribute().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());

		if (categoryAttributeList.size() == 0) {
			return null;
		} else {
			return categoryAttributeList.get(0);
		}
	}

	public CategoryAttribute findByCategory_idAndAttribute_id(String category_id, String attribute_id) {
		CategoryAttribute categoryAttribute = new CategoryAttribute();
		categoryAttribute.setCategory_id(category_id);
		categoryAttribute.setAttribute_id(attribute_id);

		categoryAttribute.checkCategory_id();
		categoryAttribute.checkAttribute_id();

		return find(categoryAttribute);
	}

	public void save(CategoryAttribute categoryAttribute) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("INSERT INTO " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " (");
		myDynamicSQL.append(CategoryAttribute.KEY_CATEGORY_ID + ", ");
		myDynamicSQL.append(CategoryAttribute.KEY_ATTRIBUTE_ID + ", ");
		myDynamicSQL.append(CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT);
		myDynamicSQL.append(") SELECT ");
		myDynamicSQL.append("?, ", categoryAttribute.getCategory_id());
		myDynamicSQL.append("?, ", categoryAttribute.getAttribute_id());
		myDynamicSQL.append("? ", categoryAttribute.getCategory_attribute_sort());
		myDynamicSQL.append("FROM DUAL WHERE NOT EXISTS (SELECT * FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " WHERE " + CategoryAttribute.KEY_CATEGORY_ID + " = ? AND " + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ?) ", categoryAttribute.getCategory_id(), categoryAttribute.getAttribute_id());

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void update(CategoryAttribute categoryAttribute) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " SET ");
		myDynamicSQL.append(CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " = ? ", categoryAttribute.getCategory_attribute_sort());
		myDynamicSQL.append("WHERE " + CategoryAttribute.KEY_CATEGORY_ID + " = ? ", categoryAttribute.getCategory_id());
		myDynamicSQL.append("AND " + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ", categoryAttribute.getAttribute_id());

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void delete(CategoryAttribute categoryAttribute) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("DELETE FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " ");
		myDynamicSQL.append("WHERE " + CategoryAttribute.KEY_CATEGORY_ID + " = ? ", categoryAttribute.getCategory_id());
		myDynamicSQL.append("AND " + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ", categoryAttribute.getAttribute_id());

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void deleteByCategory_id(String category_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("DELETE FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " WHERE ");
		myDynamicSQL.append(CategoryAttribute.KEY_CATEGORY_ID + " = ? ", category_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void deleteByAttribute_id(String attribute_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("DELETE FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " WHERE ");
		myDynamicSQL.append(CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ", attribute_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
