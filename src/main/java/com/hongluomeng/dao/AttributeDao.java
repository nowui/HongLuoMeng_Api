package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Attribute;

public class AttributeDao {

	private Integer count(Attribute attribute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Attribute.KEY_ATTRIBUTE_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Attribute attribute = new Attribute();

		return count(attribute);
	}

	private List<Attribute> list(Attribute attribute, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Attribute.KEY_ATTRIBUTE_STATUS + " = 1 ");

		sql.append("ORDER BY " + Attribute.KEY_ATTRIBUTE_TYPE + ", " + Attribute.KEY_ATTRIBUTE_SORT + " ASC ");

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

	private Attribute find(Attribute attribute) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(attribute.getAttribute_id())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}
			sql.append(Attribute.KEY_ATTRIBUTE_ID + " = ? ");
			parameterList.add(attribute.getAttribute_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Attribute.KEY_ATTRIBUTE_STATUS + " = 1 ");

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

	public void save(Attribute attribute, String request_user_id) {
		attribute.setAttribute_id(Utility.getUUID());
		attribute.setAttribute_create_user_id(request_user_id);
		attribute.setAttribute_create_time(new Date());
		attribute.setAttribute_update_user_id(request_user_id);
		attribute.setAttribute_update_time(new Date());
		attribute.setAttribute_status(true);

		attribute.save();
	}

	public void update(Attribute attribute, String request_user_id) {
		attribute.remove(Attribute.KEY_ATTRIBUTE_CREATE_USER_ID);
		attribute.remove(Attribute.KEY_ATTRIBUTE_CREATE_TIME);
		attribute.setAttribute_update_user_id(request_user_id);
		attribute.setAttribute_update_time(new Date());

		attribute.update();
	}

	public void delete(String attribute_id, String request_user_id) {
		Attribute attribute = new Attribute();
		attribute.setAttribute_id(attribute_id);
		attribute.setAttribute_update_user_id(request_user_id);
		attribute.setAttribute_update_time(new Date());
		attribute.setAttribute_status(false);

		attribute.update();
	}

}
