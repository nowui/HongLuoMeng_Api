package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.RoleOperation;

public class RoleOperationDao {

	private List<RoleOperation> list(RoleOperation roleOperation) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + RoleOperation.KEY_ROLE_OPERATION + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(roleOperation.getRole_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(RoleOperation.KEY_ROLE_ID + " = ? ");
			parameterList.add(roleOperation.getRole_id());

			isExit = true;
		}

		List<RoleOperation> roleOperationList = roleOperation.find(sql.toString(), parameterList.toArray());
		return roleOperationList;
	}

	public List<RoleOperation> listByRole_id(String role_id) {
		RoleOperation roleOperation = new RoleOperation();
		roleOperation.setRole_id(role_id);

		return list(roleOperation);
	}

	public void save(List<RoleOperation> roleOperationList) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + RoleOperation.KEY_ROLE_OPERATION + " (" + RoleOperation.KEY_ROLE_ID + ", " + RoleOperation.KEY_OPERATION_ID + ") SELECT ?, ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM " + RoleOperation.KEY_ROLE_OPERATION + " WHERE " + RoleOperation.KEY_ROLE_ID + " = ? AND " + RoleOperation.KEY_OPERATION_ID + " = ?) ");

		for(RoleOperation roleOperation : roleOperationList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(roleOperation.getRole_id());
			objectList.add(roleOperation.getOperation_id());
			objectList.add(roleOperation.getRole_id());
			objectList.add(roleOperation.getOperation_id());

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

	public void deleteByRole_id(List<RoleOperation> roleOperationList, String role_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("DELETE FROM " + RoleOperation.KEY_ROLE_OPERATION + " WHERE ");

		if (roleOperationList.size() > 0) {
			sql.append(RoleOperation.KEY_OPERATION_ID + " NOT IN (SELECT A." + RoleOperation.KEY_OPERATION_ID + " FROM (SELECT " + RoleOperation.KEY_OPERATION_ID + " FROM " + RoleOperation.KEY_ROLE_OPERATION + " WHERE ");

			int i = 0;
			for(RoleOperation roleOperation : roleOperationList) {
				if(i > 0) {
					sql.append("OR ");
				}
				i++;

				sql.append("(" + RoleOperation.KEY_ROLE_ID + " = ? ");
				sql.append("AND " + RoleOperation.KEY_OPERATION_ID + " = ?) ");
				parameterList.add(roleOperation.getRole_id());
				parameterList.add(roleOperation.getOperation_id());
			}
			sql.append(") AS A) AND ");
		}
		sql.append(RoleOperation.KEY_ROLE_ID + " = ? ");
		parameterList.add(role_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

	public void deleteByOperation_id(String operation_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("DELETE FROM " + RoleOperation.KEY_ROLE_OPERATION + " WHERE " + RoleOperation.KEY_OPERATION_ID + " = ? ");

		parameterList.add(operation_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

}
