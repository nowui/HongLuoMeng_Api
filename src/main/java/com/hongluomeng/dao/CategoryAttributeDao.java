package com.hongluomeng.dao;

import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.model.CategoryAttribute;
import com.hongluomeng.model.Attribute;

public class CategoryAttributeDao {

	private List<CategoryAttribute> list(CategoryAttribute categoryAttribute) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + ".* ");
		dynamicSQL.append(", " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_NAME + " ");
		dynamicSQL.append(", " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_TYPE + " ");
		dynamicSQL.append(", " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_DEFAULT_VALUE + " ");
		dynamicSQL.append("FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " ");
		dynamicSQL.append("LEFT JOIN " + Attribute.KEY_TABLE_ATTRIBUTE + " ON " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " = " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " ");
		dynamicSQL.append("WHERE 1 = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ? ", categoryAttribute.getCategory_id());
		dynamicSQL.append("ORDER BY " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_TYPE + ", " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " ASC ");

		return new CategoryAttribute().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<CategoryAttribute> listByCategory_id(String category_id) {
		CategoryAttribute categoryAttribute = new CategoryAttribute();
		categoryAttribute.setCategory_id(category_id);

		categoryAttribute.checkCategory_id();

		return list(categoryAttribute);
	}

	public List<Attribute> listNotUsedAttributeByCategory_id(String category_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT " + Attribute.KEY_TABLE_ATTRIBUTE + ".* FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");
		dynamicSQL.append("LEFT JOIN (SELECT * FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " WHERE " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ?) AS " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " ON " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " = " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " ", category_id);
		dynamicSQL.append("WHERE " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " IS NULL ");
		dynamicSQL.append("AND " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_STATUS + " = 1 ");
		dynamicSQL.append("ORDER BY " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " ASC ");

		return new Attribute().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	private CategoryAttribute find(CategoryAttribute categoryAttribute) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + ".*, " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_NAME + " FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " ");
		dynamicSQL.append("LEFT JOIN " + Attribute.KEY_TABLE_ATTRIBUTE + " ON " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " = " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " ");
		dynamicSQL.append("WHERE 1 = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ? ", categoryAttribute.getCategory_id());
		dynamicSQL.isNullOrEmpty("AND " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ", categoryAttribute.getAttribute_id());

		List<CategoryAttribute> categoryAttributeList = new CategoryAttribute().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());

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
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("INSERT INTO " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " (");
		dynamicSQL.append(CategoryAttribute.KEY_CATEGORY_ID + ", ");
		dynamicSQL.append(CategoryAttribute.KEY_ATTRIBUTE_ID + ", ");
		dynamicSQL.append(CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT);
		dynamicSQL.append(") SELECT ");
		dynamicSQL.append("?, ", categoryAttribute.getCategory_id());
		dynamicSQL.append("?, ", categoryAttribute.getAttribute_id());
		dynamicSQL.append("? ", categoryAttribute.getCategory_attribute_sort());
		dynamicSQL.append("FROM DUAL WHERE NOT EXISTS (SELECT * FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " WHERE " + CategoryAttribute.KEY_CATEGORY_ID + " = ? AND " + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ?) ", categoryAttribute.getCategory_id(), categoryAttribute.getAttribute_id());

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void update(CategoryAttribute categoryAttribute) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " SET ");
		dynamicSQL.append(CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " = ? ", categoryAttribute.getCategory_attribute_sort());
		dynamicSQL.append("WHERE " + CategoryAttribute.KEY_CATEGORY_ID + " = ? ", categoryAttribute.getCategory_id());
		dynamicSQL.append("AND " + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ", categoryAttribute.getAttribute_id());

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void delete(CategoryAttribute categoryAttribute) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("DELETE FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " ");
		dynamicSQL.append("WHERE " + CategoryAttribute.KEY_CATEGORY_ID + " = ? ", categoryAttribute.getCategory_id());
		dynamicSQL.append("AND " + CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ", categoryAttribute.getAttribute_id());

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void deleteByCategory_id(String category_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("DELETE FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " WHERE ");
		dynamicSQL.append(CategoryAttribute.KEY_CATEGORY_ID + " = ? ", category_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void deleteByAttribute_id(String attribute_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("DELETE FROM " + CategoryAttribute.KEY_TABLE_CATEGORY_ATTRIBUTE + " WHERE ");
		dynamicSQL.append(CategoryAttribute.KEY_ATTRIBUTE_ID + " = ? ", attribute_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

}
