package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Role;

public class RoleDao {

	private Integer count(Role role) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Role.KEY_TABLE_ROLE + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(role.getGroup_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Role.KEY_GROUP_ID + " = ? ");
			parameterList.add(role.getGroup_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(role.getRole_key())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append("(" + Role.KEY_ROLE_ID + " != ? AND " + Role.KEY_ROLE_KEY + " = ? ) ");
			parameterList.add(role.getRole_id());
			parameterList.add(role.getRole_key());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Role.KEY_ROLE_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer countByGroup_id(String group_id) {
		Role role = new Role();
		role.setGroup_id(group_id);

		return count(role);
	}

	public Integer countByRole_idAndRole_key(String role_id, String role_key) {
		Role role = new Role();
		role.setRole_id(role_id);
		role.setRole_key(role_key);

		return count(role);
	}

	private List<Role> list(Role role, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Role.KEY_TABLE_ROLE + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(role.getGroup_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Role.KEY_GROUP_ID + " = ? ");
			parameterList.add(role.getGroup_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Role.KEY_ROLE_STATUS + " = 1 ");

		sql.append("ORDER BY " + Role.KEY_ROLE_SORT + " ASC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Role> roleList = role.find(sql.toString(), parameterList.toArray());
		return roleList;
	}

	public List<Role> listByGroup_id(String group_id, Integer m, Integer n) {
		Role role = new Role();
		role.setGroup_id(group_id);

		return list(role, m, n);
	}

	private Role find(Role role) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Role.KEY_TABLE_ROLE + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(role.getRole_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Role.KEY_ROLE_ID + " = ? ");
			parameterList.add(role.getRole_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Role.KEY_ROLE_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<Role> roleList = role.find(sql.toString(), parameterList.toArray());
		if(roleList.size() == 0) {
			return null;
		} else {
			return roleList.get(0);
		}
	}

	public Role findByRole_id(String role_id) {
		Role role = new Role();
		role.setRole_id(role_id);

		return find(role);
	}

	public void save(Role role, String request_user_id) {
		role.setRole_id(Utility.getUUID());
		role.setRole_create_user_id(request_user_id);
		role.setRole_create_time(new Date());
		role.setRole_update_user_id(request_user_id);
		role.setRole_update_time(new Date());
		role.setRole_status(true);

		role.save();
	}

	public void update(Role role, String request_user_id) {
		role.remove(Role.KEY_GROUP_ID);
		role.remove(Role.KEY_ROLE_CREATE_USER_ID);
		role.remove(Role.KEY_ROLE_CREATE_TIME);
		role.setRole_update_user_id(request_user_id);
		role.setRole_update_time(new Date());

		role.update();
	}

	public void delete(String role_id, String request_user_id) {
		Role role = new Role();
		role.setRole_id(role_id);
		role.setRole_update_user_id(request_user_id);
		role.setRole_update_time(new Date());
		role.setRole_status(false);

		role.update();
	}

}
