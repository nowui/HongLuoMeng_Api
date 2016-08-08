package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.UserRole;

public class UserRoleDao {

	private List<UserRole> list(UserRole userRole) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + UserRole.KEY_USER_ROLE + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(userRole.getUser_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(UserRole.KEY_USER_ID + " = ? ");
			parameterList.add(userRole.getUser_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(userRole.getUser_type())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(UserRole.KEY_USER_TYPE + " = ? ");
			parameterList.add(userRole.getUser_type());

			isExit = true;
		}

		List<UserRole> userRoleList = userRole.find(sql.toString(), parameterList.toArray());
		return userRoleList;
	}

	public List<UserRole> listByUser_idAndUser_type(String user_id, String user_type) {
		UserRole userRole = new UserRole();
		userRole.setUser_id(user_id);
		userRole.setUser_type(user_type);

		return list(userRole);
	}

	public void save(List<UserRole> userRoleList, String user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + UserRole.KEY_USER_ROLE + " (" + UserRole.KEY_USER_ID + ", " + UserRole.KEY_USER_TYPE + ", " + UserRole.KEY_ROLE_ID + ") SELECT ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM " + UserRole.KEY_USER_ROLE + " WHERE " + UserRole.KEY_USER_ID + " = ? AND " + UserRole.KEY_USER_TYPE + " = ? AND " + UserRole.KEY_ROLE_ID + " = ?) ");

		for(UserRole userRole : userRoleList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(userRole.getUser_id());
			objectList.add(userRole.getUser_type());
			objectList.add(userRole.getRole_id());
			objectList.add(userRole.getUser_id());
			objectList.add(userRole.getUser_type());
			objectList.add(userRole.getRole_id());

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.batchSize);
	}

	public void delete(List<UserRole> userRoleList, String user_id, String user_type) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("DELETE FROM " + UserRole.KEY_USER_ROLE + " WHERE ");

		if (userRoleList.size() > 0) {
			sql.append(UserRole.KEY_ROLE_ID + " NOT IN (SELECT A." + UserRole.KEY_ROLE_ID + " FROM (SELECT " + UserRole.KEY_ROLE_ID + " FROM " + UserRole.KEY_USER_ROLE + " WHERE ");

			int i = 0;
			for(UserRole userRole : userRoleList) {
				if(i > 0) {
					sql.append("OR ");
				}
				i++;

				sql.append("(" + UserRole.KEY_USER_ID + " = ? ");
				sql.append("AND " + UserRole.KEY_USER_TYPE + " = ? ");
				sql.append("AND " + UserRole.KEY_ROLE_ID + " = ?) ");
				parameterList.add(userRole.getUser_id());
				parameterList.add(userRole.getUser_type());
				parameterList.add(userRole.getRole_id());
			}
			sql.append(") AS A) AND ");
		}
		sql.append(UserRole.KEY_USER_ID + " = ? ");
		sql.append("AND " + UserRole.KEY_USER_TYPE + " = ? ");
		parameterList.add(user_id);
		parameterList.add(user_type);

		Db.update(sql.toString(), parameterList.toArray());
	}

}
