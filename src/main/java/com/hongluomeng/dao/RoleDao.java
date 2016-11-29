package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Role;

public class RoleDao {

	private Integer count(Role role) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + Role.KEY_TABLE_ROLE + " ");
		dynamicSQL.append("WHERE " + Role.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Role.KEY_GROUP_ID + " = ? ", role.getGroup_id());

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByGroup_id(String group_id) {
		Role role = new Role();
		role.setGroup_id(group_id);

		return count(role);
	}

	public Integer countByRole_keyNotEqualRole_id(String role_id, String role_key) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + Role.KEY_TABLE_ROLE + " ");
		dynamicSQL.append("WHERE " + Role.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.append("AND " + Role.KEY_ROLE_ID + " != ? ", role_id);
		dynamicSQL.append("AND " + Role.KEY_ROLE_KEY + " = ? ", role_key);

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	private List<Role> list(Role role, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Role.KEY_TABLE_ROLE + " ");
		dynamicSQL.append("WHERE " + Role.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Role.KEY_GROUP_ID + " = ? ", role.getGroup_id());
		dynamicSQL.append("ORDER BY " + Role.KEY_ROLE_SORT + " ASC ");
		dynamicSQL.appendPagination(m, n);

		return new Role().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Role> listByGroup_id(String group_id, Integer m, Integer n) {
		Role role = new Role();
		role.setGroup_id(group_id);

		return list(role, m, n);
	}

	private Role find(Role role) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Role.KEY_TABLE_ROLE + " ");
		dynamicSQL.append("WHERE " + Role.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Role.KEY_ROLE_ID + " = ? ", role.getRole_id());

		List<Role> roleList = new Role().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
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
		role.remove(Role.KEY_GROUP_ID);

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
