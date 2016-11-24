package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Attribute;

public class AttributeDao {

	private Integer count(Attribute attribute) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT COUNT(*) FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");
		dynamicSQL.append("WHERE " + Attribute.KEY_ATTRIBUTE_STATUS + " = 1 ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Attribute attribute = new Attribute();

		return count(attribute);
	}

	private List<Attribute> list(Attribute attribute, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");
		dynamicSQL.append("WHERE " + Attribute.KEY_ATTRIBUTE_STATUS + " = 1 ");
		dynamicSQL.append("ORDER BY " + Attribute.KEY_ATTRIBUTE_TYPE + ", " + Attribute.KEY_ATTRIBUTE_SORT + " ASC ");
		dynamicSQL.appendPagination(m, n);

		return new Attribute().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Attribute> list(Integer m, Integer n) {
		Attribute attribute = new Attribute();

		return list(attribute, m, n);
	}

	private Attribute find(Attribute attribute) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");
		dynamicSQL.append("WHERE " + Attribute.KEY_ATTRIBUTE_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Attribute.KEY_ATTRIBUTE_ID + " = ? ", attribute.getAttribute_id());

		List<Attribute> attributeList = attribute.find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if(attributeList.size() == 0) {
			return null;
		} else {
			return attributeList.get(0);
		}
	}

	public Attribute findByAttribute_id(String attribute_id) {
		Attribute attribute = new Attribute();
		attribute.setAttribute_id(attribute_id);

		Utility.checkIsNullOrEmpty(attribute_id);

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
