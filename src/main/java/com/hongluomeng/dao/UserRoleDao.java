package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.UserRole;

public class UserRoleDao {

	private List<UserRole> list(UserRole userRole) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + UserRole.KEY_TABLE_USER_ROLE + " ");
		myDynamicSQL.append("WHERE 1 = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + UserRole.KEY_USER_ID + " = ? ", userRole.getUser_id());
		myDynamicSQL.isNullOrEmpty("AND " + UserRole.KEY_USER_TYPE + " = ? ", userRole.getUser_type());

		return userRole.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
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
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("DELETE FROM " + UserRole.KEY_TABLE_USER_ROLE + " WHERE ");

		if (userRoleList.size() > 0) {
			myDynamicSQL.append(UserRole.KEY_ROLE_ID + " NOT IN (SELECT A." + UserRole.KEY_ROLE_ID + " FROM (SELECT " + UserRole.KEY_ROLE_ID + " FROM " + UserRole.KEY_TABLE_USER_ROLE + " WHERE ");

			for(int i = 0; i < userRoleList.size(); i++) {
				UserRole userRole = userRoleList.get(i);
				if(i > 0) {
					myDynamicSQL.append("OR ");
				}

				myDynamicSQL.append("(" + UserRole.KEY_USER_ID + " = ? ", userRole.getUser_id());
				myDynamicSQL.append("AND " + UserRole.KEY_USER_TYPE + " = ? ", userRole.getUser_type());
				myDynamicSQL.append("AND " + UserRole.KEY_ROLE_ID + " = ?) ", userRole.getRole_id());
			}
			myDynamicSQL.append(") AS A) AND ");
		}
		myDynamicSQL.append(UserRole.KEY_USER_ID + " = ? ", user_id);
		myDynamicSQL.append("AND " + UserRole.KEY_USER_TYPE + " = ? ", user_type);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
