package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Attribute;

public class AttributeDao {

	private Integer count(Attribute attribute) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT COUNT(*) FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");
		myDynamicSQL.append("WHERE " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Attribute attribute = new Attribute();

		return count(attribute);
	}

	private List<Attribute> list(Attribute attribute, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");
		myDynamicSQL.append("WHERE " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_ATTRIBUTE_TYPE + ", " + Attribute.KEY_ATTRIBUTE_SORT + " ASC ");
		myDynamicSQL.appendPagination(m, n);

		return new Attribute().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<Attribute> list(Integer m, Integer n) {
		Attribute attribute = new Attribute();

		return list(attribute, m, n);
	}

	private Attribute find(Attribute attribute) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Attribute.KEY_TABLE_ATTRIBUTE + " ");
		myDynamicSQL.append("WHERE " + Attribute.KEY_TABLE_ATTRIBUTE + "." + Attribute.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Attribute.KEY_ATTRIBUTE_ID + " = ? ", attribute.getAttribute_id());

		List<Attribute> attributeList = attribute.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if (attributeList.size() == 0) {
			return null;
		} else {
			return attributeList.get(0);
		}
	}

	public Attribute findByAttribute_id(String attribute_id) {
		Attribute attribute = new Attribute();
		attribute.setAttribute_id(attribute_id);

		attribute.checkAttribute_id();

		return find(attribute);
	}

	public void save(Attribute attribute, String request_user_id) {
		attribute.setAttribute_id(Utility.getUUID());

		attribute.initForSave(request_user_id);

		attribute.save();
	}

	public void update(Attribute attribute, String request_user_id) {
		attribute.remove(Attribute.KEY_SYSTEM_STATUS);

		attribute.initForUpdate(request_user_id);

		attribute.update();
	}

	public void delete(String attribute_id, String request_user_id) {
		Attribute attribute = new Attribute();
		attribute.setAttribute_id(attribute_id);

		attribute.initForDelete(request_user_id);

		attribute.update();
	}

}
