package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.RoleOperation;

public class RoleOperationDao {

	private List<RoleOperation> list(RoleOperation roleOperation) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + RoleOperation.KEY_TABLE_ROLE_OPERATION + " ");
		dynamicSQL.append("WHERE 1 = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + RoleOperation.KEY_ROLE_ID + " = ? ", roleOperation.getRole_id());

		return roleOperation.find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<RoleOperation> listByRole_id(String role_id) {
		RoleOperation roleOperation = new RoleOperation();
		roleOperation.setRole_id(role_id);

		roleOperation.checkRole_id();

		return list(roleOperation);
	}

	public void save(List<RoleOperation> roleOperationList) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + RoleOperation.KEY_TABLE_ROLE_OPERATION + " (" + RoleOperation.KEY_ROLE_ID + ", " + RoleOperation.KEY_OPERATION_ID + ") SELECT ?, ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM " + RoleOperation.KEY_TABLE_ROLE_OPERATION + " WHERE " + RoleOperation.KEY_ROLE_ID + " = ? AND " + RoleOperation.KEY_OPERATION_ID + " = ?) ");

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
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("DELETE FROM " + RoleOperation.KEY_TABLE_ROLE_OPERATION + " WHERE ");

		if (roleOperationList.size() > 0) {
			dynamicSQL.append(RoleOperation.KEY_OPERATION_ID + " NOT IN (SELECT A." + RoleOperation.KEY_OPERATION_ID + " FROM (SELECT " + RoleOperation.KEY_OPERATION_ID + " FROM " + RoleOperation.KEY_TABLE_ROLE_OPERATION + " WHERE ");

			for(int i = 0; i < roleOperationList.size(); i++) {
				RoleOperation roleOperation = roleOperationList.get(i);
				if(i > 0) {
					dynamicSQL.append("OR ");
				}

				dynamicSQL.append("(" + RoleOperation.KEY_ROLE_ID + " = ? ", roleOperation.getRole_id());
				dynamicSQL.append("AND " + RoleOperation.KEY_OPERATION_ID + " = ?) ", roleOperation.getOperation_id());
			}
			dynamicSQL.append(") AS A) AND ");
		}
		dynamicSQL.append(RoleOperation.KEY_ROLE_ID + " = ? ", role_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void deleteByOperation_id(String operation_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("DELETE FROM " + RoleOperation.KEY_TABLE_ROLE_OPERATION + " WHERE " + RoleOperation.KEY_OPERATION_ID + " = ? ", operation_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

}
