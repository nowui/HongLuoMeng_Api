package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Authorization;

public class AuthorizationDao {

	private Integer count(Authorization authorization) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Authorization.KEY_TABLE_AUTHORIZATION + " ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Authorization authorization = new Authorization();

		return count(authorization);
	}

	private List<Authorization> list(Authorization authorization, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Authorization.KEY_TABLE_AUTHORIZATION + " ");
		sql.append("ORDER BY " + Authorization.KEY_AUTHORIZATION_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Authorization> authorizationList = authorization.find(sql.toString(), parameterList.toArray());
		return authorizationList;
	}

	public List<Authorization> list(Integer m, Integer n) {
		Authorization authorization = new Authorization();

		return list(authorization, m, n);
	}

	private Authorization find(Authorization authorization) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Authorization.KEY_TABLE_AUTHORIZATION + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(authorization.getAuthorization_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Authorization.KEY_AUTHORIZATION_ID + " = ? ");
			parameterList.add(authorization.getAuthorization_id());

			isExit = true;
		}

		if(! isExit) {
			return null;
		}

		List<Authorization> authorizationList = authorization.find(sql.toString(), parameterList.toArray());
		if(authorizationList.size() == 0) {
			return null;
		} else {
			return authorizationList.get(0);
		}
	}

	public Authorization findByAuthorization_id(String authorization_id) {
		Authorization authorization = new Authorization();
		authorization.setAuthorization_id(authorization_id);

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
