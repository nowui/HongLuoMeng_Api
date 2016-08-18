package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Operation;
import com.hongluomeng.model.RoleOperation;
import com.hongluomeng.model.UserRole;

public class OperationDao {

	private Integer count(Operation operation) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Operation.KEY_OPERATION + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(operation.getOperation_key())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append("(" + Operation.KEY_OPERATION_ID + " != ? AND " + Operation.KEY_OPERATION_KEY + " = ? ) ");
			parameterList.add(operation.getOperation_id());
			parameterList.add(operation.getOperation_key());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(operation.getMenu_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Operation.KEY_MENU_ID + " = ? ");
			parameterList.add(operation.getMenu_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Operation.KEY_OPERATION_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer countByMenu_id(String menu_id) {
		Operation operation = new Operation();
		operation.setMenu_id(menu_id);

		return count(operation);
	}

	public Integer countByOperation_idAndOperation_key(String operation_id, String operation_key) {
		Operation operation = new Operation();
		operation.setOperation_id(operation_id);
		operation.setOperation_key(operation_key);

		return count(operation);
	}

	public List<Operation> list(Operation operation, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Operation.KEY_OPERATION + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(operation.getMenu_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Operation.KEY_MENU_ID + " = ? ");
			parameterList.add(operation.getMenu_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Operation.KEY_OPERATION_STATUS + " = 1 ");

		sql.append("ORDER BY " + Operation.KEY_OPERATION_SORT + " ASC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Operation> operationList = operation.find(sql.toString(), parameterList.toArray());
		return operationList;
	}

	public List<Operation> listByUser_id(String user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + Operation.KEY_OPERATION + ".* FROM " + Operation.KEY_OPERATION + " ");
		sql.append("LEFT JOIN " + RoleOperation.KEY_ROLE_OPERATION + " ON " + Operation.KEY_OPERATION + "." + Operation.KEY_OPERATION_ID + " = " + RoleOperation.KEY_ROLE_OPERATION + "." + RoleOperation.KEY_OPERATION_ID + " ");
		sql.append("LEFT JOIN " + UserRole.KEY_USER_ROLE + " ON " + RoleOperation.KEY_ROLE_OPERATION + "." + RoleOperation.KEY_ROLE_ID + " = " + UserRole.KEY_USER_ROLE + "." + UserRole.KEY_ROLE_ID + " ");
		sql.append("WHERE " + UserRole.KEY_USER_ROLE + "." + UserRole.KEY_USER_ID + " = ? ");
		sql.append("AND " + Operation.KEY_OPERATION_STATUS + " = 1 ");
		parameterList.add(user_id);

		Operation operation = new Operation();

		List<Operation> operationList = operation.find(sql.toString(), parameterList.toArray());

		return operationList;
	}

	public Operation find(Operation operation) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Operation.KEY_OPERATION + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(operation.getOperation_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Operation.KEY_OPERATION_ID + " = ? ");
			parameterList.add(operation.getOperation_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Operation.KEY_OPERATION_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<Operation> operationList = operation.find(sql.toString(), parameterList.toArray());
		if(operationList.size() == 0) {
			return null;
		} else {
			return operationList.get(0);
		}
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
