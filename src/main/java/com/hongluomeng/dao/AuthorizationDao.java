package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Authorization;

public class AuthorizationDao {

	private Integer count(Authorization authorization) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT COUNT(*) FROM " + Authorization.TABLE_AUTHORIZATION + " ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Authorization authorization = new Authorization();

		return count(authorization);
	}

	private List<Authorization> list(Authorization authorization, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Authorization.TABLE_AUTHORIZATION + " ");
		myDynamicSQL.append("ORDER BY " + Authorization.COLUMN_AUTHORIZATION_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		return new Authorization().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<Authorization> list(Integer m, Integer n) {
		Authorization authorization = new Authorization();

		return list(authorization, m, n);
	}

	private Authorization find(Authorization authorization) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Authorization.TABLE_AUTHORIZATION + " ");
		myDynamicSQL.append("WHERE 1 = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Authorization.COLUMN_AUTHORIZATION_ID + " = ? ", authorization.getAuthorization_id());

		List<Authorization> authorizationList = new Authorization().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if(authorizationList.size() == 0) {
			return null;
		} else {
			return authorizationList.get(0);
		}
	}

	public Authorization findByAuthorization_id(String authorization_id) {
		Authorization authorization = new Authorization();
		authorization.setAuthorization_id(authorization_id);

		authorization.checkAuthorization_id();

		return find(authorization);
	}

	public void save(Authorization authorization) {
		authorization.setAuthorization_id(Utility.getUUID());

		authorization.save();
	}

	public void updateToken(Authorization authorization) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + Authorization.TABLE_AUTHORIZATION + " SET " + Authorization.COLUMN_AUTHORIZATION_TOKEN + " = ? WHERE " + Authorization.COLUMN_AUTHORIZATION_ID + " = ? ");

		parameterList.add(authorization.getAuthorization_token());
		parameterList.add(authorization.getAuthorization_id());

		Db.update(sql.toString(), parameterList.toArray());
	}

}
