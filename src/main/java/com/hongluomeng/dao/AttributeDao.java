package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Attribute;
import com.hongluomeng.model.CategoryAttribute;

public class AttributeDao {

	private Integer count(Attribute attribute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Attribute.KEY_ATTRIBUTE + " ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Attribute attribute = new Attribute();

		return count(attribute);
	}

	private List<Attribute> list(Attribute attribute, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Attribute.KEY_ATTRIBUTE + " ");
		sql.append("ORDER BY " + Attribute.KEY_ATTRIBUTE_SORT + " ASC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Attribute> attributeList = attribute.find(sql.toString(), parameterList.toArray());
		return attributeList;
	}

	public List<Attribute> list(Integer m, Integer n) {
		Attribute attribute = new Attribute();

		return list(attribute, m, n);
	}

	public List<Attribute> listByCategory_id(String category_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + Attribute.KEY_ATTRIBUTE + ".* FROM " + Attribute.KEY_ATTRIBUTE + " ");

		if (! Utility.isNullOrEmpty(category_id)) {
			sql.append("LEFT JOIN (SELECT * FROM " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " WHERE " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ID + " = ?) AS " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + " ON " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " = " + Attribute.KEY_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_ID + " ");
			sql.append("WHERE " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_ATTRIBUTE_ID + " IS NULL ");

			parameterList.add(category_id);
		}
		sql.append("ORDER BY " + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE + "." + CategoryAttribute.KEY_CATEGORY_ATTRIBUTE_SORT + " ASC ");

		List<Attribute> attributeList = new Attribute().find(sql.toString(), parameterList.toArray());

		return attributeList;
	}

	private Attribute find(Attribute attribute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Attribute.KEY_ATTRIBUTE + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(attribute.getAttribute_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Attribute.KEY_ATTRIBUTE_ID + " = ? ");
			parameterList.add(attribute.getAttribute_id());

			isExit = true;
		}

		if(! isExit) {
			return null;
		}

		List<Attribute> attributeList = attribute.find(sql.toString(), parameterList.toArray());
		if(attributeList.size() == 0) {
			return null;
		} else {
			return attributeList.get(0);
		}
	}

	public Attribute findByAttribute_id(String attribute_id) {
		Attribute attribute = new Attribute();
		attribute.setAttribute_id(attribute_id);

		return find(attribute);
	}

	public void save(Attribute attribute, String user_id) {
		attribute.setAttribute_id(Utility.getUUID());
		attribute.setAttribute_create_user_id(user_id);
		attribute.setAttribute_create_time(new Date());
		attribute.setAttribute_update_user_id(user_id);
		attribute.setAttribute_update_time(new Date());

		attribute.save();
	}

	public void update(Attribute attribute, String user_id) {
		attribute.remove(Attribute.KEY_ATTRIBUTE_CREATE_USER_ID);
		attribute.remove(Attribute.KEY_ATTRIBUTE_CREATE_TIME);
		attribute.setAttribute_update_user_id(user_id);
		attribute.setAttribute_update_time(new Date());

		attribute.update();
	}

	public void delete(Attribute attribute) {
		attribute.delete();
	}

}
