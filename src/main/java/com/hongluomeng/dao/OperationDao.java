package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Operation;
import com.hongluomeng.model.Role;
import com.hongluomeng.model.RoleOperation;
import com.hongluomeng.model.User;
import com.hongluomeng.model.UserRole;

public class OperationDao {

    private Integer count(Operation operation) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT COUNT(*) FROM " + Operation.TABLE_OPERATION + " ");
        myDynamicSQL.append("WHERE " + Operation.TABLE_OPERATION + "." + Operation.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + Operation.COLUMN_MENU_ID + " = ? ", operation.getMenu_id());

        Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
        return count.intValue();
    }

    public Integer countByMenu_id(String menu_id) {
        Operation operation = new Operation();
        operation.setMenu_id(menu_id);

        return count(operation);
    }

    public Integer countByOperation_idAndOperation_key(String operation_id, String operation_key) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT COUNT(*) FROM " + Operation.TABLE_OPERATION + " ");
        myDynamicSQL.append("WHERE " + Operation.TABLE_OPERATION + "." + Operation.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.append("AND " + Operation.COLUMN_OPERATION_ID + " != ? ", operation_id);
        myDynamicSQL.append("AND " + Operation.COLUMN_OPERATION_KEY + " = ? ", operation_key);

        Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
        return count.intValue();
    }

    public List<Operation> list(Operation operation, Integer m, Integer n) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT * FROM " + Operation.TABLE_OPERATION + " ");
        myDynamicSQL.append("WHERE " + Operation.TABLE_OPERATION + "." + Operation.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + Operation.COLUMN_MENU_ID + " = ? ", operation.getMenu_id());
        myDynamicSQL.append("ORDER BY " + Operation.COLUMN_OPERATION_SORT + " ASC ");
        myDynamicSQL.appendPagination(m, n);

        return operation.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
    }

    public List<Operation> listUserRoleByUser_id(String user_id) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT " + Operation.TABLE_OPERATION + ".* FROM " + Operation.TABLE_OPERATION + " ");
        myDynamicSQL.append("LEFT JOIN " + RoleOperation.TABLE_ROLE_OPERATION + " ON " + Operation.TABLE_OPERATION + "." + Operation.COLUMN_OPERATION_ID + " = " + RoleOperation.TABLE_ROLE_OPERATION + "." + RoleOperation.COLUMN_OPERATION_ID + " ");
        myDynamicSQL.append("LEFT JOIN " + UserRole.TABLE_USER_ROLE + " ON " + RoleOperation.TABLE_ROLE_OPERATION + "." + RoleOperation.COLUMN_ROLE_ID + " = " + UserRole.TABLE_USER_ROLE + "." + UserRole.COLUMN_ROLE_ID + " ");
        myDynamicSQL.append("WHERE " + UserRole.TABLE_USER_ROLE + "." + UserRole.COLUMN_USER_ID + " = ? ", user_id);
        myDynamicSQL.append("AND " + Operation.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.append("ORDER BY " + Operation.COLUMN_OPERATION_SORT + " ASC ");

        return new Operation().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
    }

    public List<Operation> listByUser_id(String user_id) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT " + Operation.TABLE_OPERATION + ".* FROM " + Operation.TABLE_OPERATION + " ");
        myDynamicSQL.append("LEFT JOIN " + RoleOperation.TABLE_ROLE_OPERATION + " ON " + Operation.TABLE_OPERATION + "." + Operation.COLUMN_OPERATION_ID + " = " + RoleOperation.TABLE_ROLE_OPERATION + "." + RoleOperation.COLUMN_OPERATION_ID + " ");
        myDynamicSQL.append("LEFT JOIN " + Role.TABLE_ROLE + " ON " + RoleOperation.TABLE_ROLE_OPERATION + "." + RoleOperation.COLUMN_ROLE_ID + " = " + Role.TABLE_ROLE + "." + Role.COLUMN_ROLE_ID + " ");
        myDynamicSQL.append("LEFT JOIN " + User.TABLE_USER + " ON " + Role.TABLE_ROLE + "." + Role.COLUMN_ROLE_KEY + " = " + User.TABLE_USER + "." + User.COLUMN_USER_TYPE + " ");
        myDynamicSQL.append("WHERE " + User.TABLE_USER + "." + User.COLUMN_USER_ID + " = ? ", user_id);
        myDynamicSQL.append("AND " + Operation.TABLE_OPERATION + "." + Operation.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.append("ORDER BY " + Operation.COLUMN_OPERATION_SORT + " ASC ");

        return new Operation().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
    }

    private Operation find(Operation operation) {
        MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

        myDynamicSQL.append("SELECT * FROM " + Operation.TABLE_OPERATION + " ");
        myDynamicSQL.append("WHERE " + Operation.COLUMN_SYSTEM_STATUS + " = 1 ");
        myDynamicSQL.isNullOrEmpty("AND " + Operation.COLUMN_OPERATION_ID + " = ? ", operation.getOperation_id());

        List<Operation> operationList = new Operation().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
        if (operationList.size() == 0) {
            return null;
        } else {
            return operationList.get(0);
        }
    }

    public Operation findByOperation_id(String operation_id) {
        Operation operation = new Operation();
        operation.setOperation_id(operation_id);

        operation.checkOperation_id();

        return find(operation);
    }

    public void save(Operation operation, String request_user_id) {
        operation.setOperation_id(Utility.getUUID());

        operation.initForSave(request_user_id);

        operation.save();
    }

    public void update(Operation operation, String request_user_id) {
        operation.remove(Operation.COLUMN_MENU_ID);

        operation.initForUpdate(request_user_id);

        operation.update();
    }

    public void delete(String operation_id, String request_user_id) {
        Operation operation = new Operation();
        operation.setOperation_id(operation_id);

        operation.initForDelete(request_user_id);

        operation.update();
    }

}
