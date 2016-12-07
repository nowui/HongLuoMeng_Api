package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Role;

public class RoleDao {

	private Integer count(Role role) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + Role.TABLE_ROLE + " ");
		myDynamicSQL.append("WHERE " + Role.TABLE_ROLE + "." + Role.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Role.COLUMN_GROUP_ID + " = ? ", role.getGroup_id());

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByGroup_id(String group_id) {
		Role role = new Role();
		role.setGroup_id(group_id);

		return count(role);
	}

	public Integer countByRole_keyNotEqualRole_id(String role_id, String role_key) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + Role.TABLE_ROLE + " ");
		myDynamicSQL.append("WHERE " + Role.TABLE_ROLE + "." + Role.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND " + Role.COLUMN_ROLE_ID + " != ? ", role_id);
		myDynamicSQL.append("AND " + Role.COLUMN_ROLE_KEY + " = ? ", role_key);

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	private List<Role> list(Role role, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Role.TABLE_ROLE + " ");
		myDynamicSQL.append("WHERE " + Role.TABLE_ROLE + "." + Role.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Role.COLUMN_GROUP_ID + " = ? ", role.getGroup_id());
		myDynamicSQL.append("ORDER BY " + Role.COLUMN_ROLE_SORT + " ASC ");
		myDynamicSQL.appendPagination(m, n);

		return new Role().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<Role> listByGroup_id(String group_id, Integer m, Integer n) {
		Role role = new Role();
		role.setGroup_id(group_id);

		return list(role, m, n);
	}

	private Role find(Role role) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Role.TABLE_ROLE + " ");
		myDynamicSQL.append("WHERE " + Role.TABLE_ROLE + "." + Role.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Role.COLUMN_ROLE_ID + " = ? ", role.getRole_id());

		List<Role> roleList = new Role().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if(roleList == null) {
			return null;
		} else {
			return roleList.get(0);
		}
	}

	public Role findByRole_id(String role_id) {
		Role role = new Role();
		role.setRole_id(role_id);

		role.checkRole_id();

		return find(role);
	}

	public void save(Role role, String request_user_id) {
		role.setRole_id(Utility.getUUID());

		role.initForSave(request_user_id);

		role.save();
	}

	public void update(Role role, String request_user_id) {
		role.remove(Role.COLUMN_GROUP_ID);

		role.initForUpdate(request_user_id);

		role.update();
	}

	public void delete(String role_id, String request_user_id) {
		Role role = new Role();
		role.setRole_id(role_id);

		role.initForDelete(request_user_id);

		role.update();
	}

}
