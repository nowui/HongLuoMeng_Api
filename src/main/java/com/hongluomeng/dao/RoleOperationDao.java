package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.RoleOperation;

public class RoleOperationDao extends BaseDao {

	private List<RoleOperation> list(RoleOperation roleOperation) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + RoleOperation.TABLE_ROLE_OPERATION + " ");
		myDynamicSQL.append("WHERE 1 = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + RoleOperation.COLUMN_ROLE_ID + " = ? ", roleOperation.getRole_id());

		return roleOperation.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<RoleOperation> listByRole_id(String role_id) {
		RoleOperation roleOperation = new RoleOperation();
		roleOperation.setRole_id(role_id);

		roleOperation.checkRole_id();

		return list(roleOperation);
	}

	public void save(List<RoleOperation> roleOperationList) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + RoleOperation.TABLE_ROLE_OPERATION + " (" + RoleOperation.COLUMN_ROLE_ID + ", " + RoleOperation.COLUMN_OPERATION_ID + ") SELECT ?, ? FROM DUAL WHERE NOT EXISTS (SELECT * FROM " + RoleOperation.TABLE_ROLE_OPERATION + " WHERE " + RoleOperation.COLUMN_ROLE_ID + " = ? AND " + RoleOperation.COLUMN_OPERATION_ID + " = ?) ");

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
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("DELETE FROM " + RoleOperation.TABLE_ROLE_OPERATION + " WHERE ");

		if (roleOperationList.size() > 0) {
			myDynamicSQL.append(RoleOperation.COLUMN_OPERATION_ID + " NOT IN (SELECT A." + RoleOperation.COLUMN_OPERATION_ID + " FROM (SELECT " + RoleOperation.COLUMN_OPERATION_ID + " FROM " + RoleOperation.TABLE_ROLE_OPERATION + " WHERE ");

			for(int i = 0; i < roleOperationList.size(); i++) {
				RoleOperation roleOperation = roleOperationList.get(i);
				if(i > 0) {
					myDynamicSQL.append("OR ");
				}

				myDynamicSQL.append("(" + RoleOperation.COLUMN_ROLE_ID + " = ? ", roleOperation.getRole_id());
				myDynamicSQL.append("AND " + RoleOperation.COLUMN_OPERATION_ID + " = ?) ", roleOperation.getOperation_id());
			}
			myDynamicSQL.append(") AS A) AND ");
		}
		myDynamicSQL.append(RoleOperation.COLUMN_ROLE_ID + " = ? ", role_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void deleteByOperation_id(String operation_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("DELETE FROM " + RoleOperation.TABLE_ROLE_OPERATION + " WHERE " + RoleOperation.COLUMN_OPERATION_ID + " = ? ", operation_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
