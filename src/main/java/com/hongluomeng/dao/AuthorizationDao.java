package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Authorization;

public class AuthorizationDao {

	private Integer count(Authorization authorization) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT COUNT(*) FROM " + Authorization.KEY_TABLE_AUTHORIZATION + " ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Authorization authorization = new Authorization();

		return count(authorization);
	}

	private List<Authorization> list(Authorization authorization, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Authorization.KEY_TABLE_AUTHORIZATION + " ");
		dynamicSQL.append("ORDER BY " + Authorization.KEY_AUTHORIZATION_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		return new Authorization().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Authorization> list(Integer m, Integer n) {
		Authorization authorization = new Authorization();

		return list(authorization, m, n);
	}

	private Authorization find(Authorization authorization) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Authorization.KEY_TABLE_AUTHORIZATION + " ");
		dynamicSQL.append("WHERE 1 = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Authorization.KEY_AUTHORIZATION_ID + " = ? ", authorization.getAuthorization_id());

		List<Authorization> authorizationList = new Authorization().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
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

		StringBuffer sql = new StringBuffer("UPDATE " + Authorization.KEY_TABLE_AUTHORIZATION + " SET " + Authorization.KEY_AUTHORIZATION_TOKEN + " = ? WHERE " + Authorization.KEY_AUTHORIZATION_ID + " = ? ");

		parameterList.add(authorization.getAuthorization_token());
		parameterList.add(authorization.getAuthorization_id());

		Db.update(sql.toString(), parameterList.toArray());
	}

}
