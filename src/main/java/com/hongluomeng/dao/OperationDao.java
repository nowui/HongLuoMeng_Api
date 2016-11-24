package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Operation;
import com.hongluomeng.model.Role;
import com.hongluomeng.model.RoleOperation;
import com.hongluomeng.model.User;
import com.hongluomeng.model.UserRole;

public class OperationDao {

	private Integer count(Operation operation) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + Operation.KEY_TABLE_OPERATION + " ");
		dynamicSQL.append("WHERE " + Operation.KEY_OPERATION_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Operation.KEY_MENU_ID + " = ? ", operation.getMenu_id());

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByMenu_id(String menu_id) {
		Operation operation = new Operation();
		operation.setMenu_id(menu_id);

		return count(operation);
	}

	public Integer countByOperation_idAndOperation_key(String operation_id, String operation_key) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + Operation.KEY_OPERATION_KEY + " ");
		dynamicSQL.append("WHERE " + Operation.KEY_OPERATION_STATUS + " = 1 ");
		dynamicSQL.append("AND " + Operation.KEY_OPERATION_ID + " != ? ", operation_id);
		dynamicSQL.append("AND " + Operation.KEY_OPERATION_KEY + " = ? ", operation_key);

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public List<Operation> list(Operation operation, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Operation.KEY_TABLE_OPERATION + " ");
		dynamicSQL.append("WHERE " + Operation.KEY_OPERATION_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Operation.KEY_MENU_ID + " = ? ", operation.getMenu_id());
		dynamicSQL.appendPagination(m, n);

		return operation.find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Operation> listUserRoleByUser_id(String user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT " + Operation.KEY_TABLE_OPERATION + ".* FROM " + Operation.KEY_TABLE_OPERATION + " ");
		dynamicSQL.append("LEFT JOIN " + RoleOperation.KEY_TABLE_ROLE_OPERATION + " ON " + Operation.KEY_TABLE_OPERATION + "." + Operation.KEY_OPERATION_ID + " = " + RoleOperation.KEY_TABLE_ROLE_OPERATION + "." + RoleOperation.KEY_OPERATION_ID + " ");
		dynamicSQL.append("LEFT JOIN " + UserRole.KEY_TABLE_USER_ROLE + " ON " + RoleOperation.KEY_TABLE_ROLE_OPERATION + "." + RoleOperation.KEY_ROLE_ID + " = " + UserRole.KEY_TABLE_USER_ROLE + "." + UserRole.KEY_ROLE_ID + " ");
		dynamicSQL.append("WHERE " + UserRole.KEY_TABLE_USER_ROLE + "." + UserRole.KEY_USER_ID + " = ? ", user_id);
		dynamicSQL.append("AND " + Operation.KEY_OPERATION_STATUS + " = 1 ");

		return new Operation().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Operation> listByUser_id(String user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT " + Operation.KEY_TABLE_OPERATION + ".* FROM " + Operation.KEY_TABLE_OPERATION + " ");
		dynamicSQL.append("LEFT JOIN " + RoleOperation.KEY_TABLE_ROLE_OPERATION + " ON " + Operation.KEY_TABLE_OPERATION + "." + Operation.KEY_OPERATION_ID + " = " + RoleOperation.KEY_TABLE_ROLE_OPERATION + "." + RoleOperation.KEY_OPERATION_ID + " ");
		dynamicSQL.append("LEFT JOIN " + Role.KEY_TABLE_ROLE + " ON " + RoleOperation.KEY_TABLE_ROLE_OPERATION + "." + RoleOperation.KEY_ROLE_ID + " = " + Role.KEY_TABLE_ROLE + "." + Role.KEY_ROLE_ID + " ");
		dynamicSQL.append("LEFT JOIN " + User.KEY_TABLE_USER + " ON " + Role.KEY_TABLE_ROLE + "." + Role.KEY_ROLE_KEY + " = " + User.KEY_TABLE_USER + "." + User.KEY_USER_TYPE + " ");
		dynamicSQL.append("WHERE " + User.KEY_TABLE_USER + "." + User.KEY_USER_ID + " = ? ", user_id);
		dynamicSQL.append("AND " + Operation.KEY_OPERATION_STATUS + " = 1 ");

		return new Operation().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	private Operation find(Operation operation) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Operation.KEY_TABLE_OPERATION + " ");
		dynamicSQL.append("WHERE " + Operation.KEY_OPERATION_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Operation.KEY_OPERATION_ID + " = ? ", operation.getOperation_id());

		List<Operation> operationList = new Operation().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if(operationList == null) {
			return null;
		} else {
			return operationList.get(0);
		}
	}

	public Operation findByOperation_id(String operation_id) {
		Operation operation = new Operation();
		operation.setOperation_id(operation_id);

		Utility.checkIsNullOrEmpty(operation_id);

		return find(operation);
	}

	public void save(Operation operation, String request_user_id) {
		operation.setOperation_id(Utility.getUUID());
		operation.setOperation_create_user_id(request_user_id);
		operation.setOperation_create_time(new Date());
		operation.setOperation_update_user_id(request_user_id);
		operation.setOperation_update_time(new Date());
		operation.setOperation_status(true);

		operation.save();
	}

	public void update(Operation operation, String request_user_id) {
		operation.remove(Operation.KEY_MENU_ID);
		operation.remove(Operation.KEY_OPERATION_CREATE_USER_ID);
		operation.remove(Operation.KEY_OPERATION_CREATE_TIME);
		operation.setOperation_update_user_id(request_user_id);
		operation.setOperation_update_time(new Date());

		operation.update();
	}

	public void delete(String operation_id, String request_user_id) {
		Operation operation = new Operation();
		operation.setOperation_id(operation_id);
		operation.setOperation_update_user_id(request_user_id);
		operation.setOperation_update_time(new Date());
		operation.setOperation_status(false);

		operation.update();
	}

}
