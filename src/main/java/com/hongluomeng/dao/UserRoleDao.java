package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.UserRole;

public class UserRoleDao {

	private List<UserRole> list(UserRole userRole) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + UserRole.KEY_TABLE_USER_ROLE + " ");
		dynamicSQL.append("WHERE 1 = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + UserRole.KEY_USER_ID + " = ? ", userRole.getUser_id());
		dynamicSQL.isNullOrEmpty("AND " + UserRole.KEY_USER_TYPE + " = ? ", userRole.getUser_type());

		return userRole.find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<UserRole> listByUser_idAndUser_type(String user_id, String user_type) {
		UserRole userRole = new UserRole();
		userRole.setUser_id(user_id);
		userRole.setUser_type(user_type);

		return list(userRole);
	}

	public void save(List<UserRole> userRoleList, String user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + UserRole.KEY_TABLE_USER_ROLE + " (" + UserRole.KEY_USER_ID + ", " + UserRole.KEY_USER_TYPE + ", " + UserRole.KEY_ROLE_ID + ") SELECT ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM " + UserRole.KEY_TABLE_USER_ROLE + " WHERE " + UserRole.KEY_USER_ID + " = ? AND " + UserRole.KEY_USER_TYPE + " = ? AND " + UserRole.KEY_ROLE_ID + " = ?) ");

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

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

	public void delete(List<UserRole> userRoleList, String user_id, String user_type) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("DELETE FROM " + UserRole.KEY_TABLE_USER_ROLE + " WHERE ");

		if (userRoleList.size() > 0) {
			dynamicSQL.append(UserRole.KEY_ROLE_ID + " NOT IN (SELECT A." + UserRole.KEY_ROLE_ID + " FROM (SELECT " + UserRole.KEY_ROLE_ID + " FROM " + UserRole.KEY_TABLE_USER_ROLE + " WHERE ");

			for(int i = 0; i < userRoleList.size(); i++) {
				UserRole userRole = userRoleList.get(i);
				if(i > 0) {
					dynamicSQL.append("OR ");
				}

				dynamicSQL.append("(" + UserRole.KEY_USER_ID + " = ? ", userRole.getUser_id());
				dynamicSQL.append("AND " + UserRole.KEY_USER_TYPE + " = ? ", userRole.getUser_type());
				dynamicSQL.append("AND " + UserRole.KEY_ROLE_ID + " = ?) ", userRole.getRole_id());
			}
			dynamicSQL.append(") AS A) AND ");
		}
		dynamicSQL.append(UserRole.KEY_USER_ID + " = ? ", user_id);
		dynamicSQL.append("AND " + UserRole.KEY_USER_TYPE + " = ? ", user_type);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

}
