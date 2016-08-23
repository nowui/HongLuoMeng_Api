package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Admin;

public class AdminDao {

	private Integer count(Admin admin) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Admin.KEY_ADMIN + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Admin.KEY_ADMIN_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Admin admin = new Admin();

		return count(admin);
	}

	private List<Admin> list(Admin admin, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Admin.KEY_ADMIN + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Admin.KEY_ADMIN_STATUS + " = 1 ");

		sql.append("ORDER BY " + Admin.KEY_ADMIN_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Admin> adminList = admin.find(sql.toString(), parameterList.toArray());
		return adminList;
	}

	public List<Admin> list(Integer m, Integer n) {
		Admin admin = new Admin();

		return list(admin, m, n);
	}

	private Admin find(Admin admin) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Admin.KEY_ADMIN + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(admin.getAdmin_id())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}
			sql.append(Admin.KEY_ADMIN_ID + " = ? ");
			parameterList.add(admin.getAdmin_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Admin.KEY_ADMIN_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<Admin> adminList = admin.find(sql.toString(), parameterList.toArray());
		if(adminList.size() == 0) {
			return null;
		} else {
			return adminList.get(0);
		}
	}

	public Admin findByAdmin_id(String admin_id) {
		Admin admin = new Admin();
		admin.setAdmin_id(admin_id);

		return find(admin);
	}

	public void save(Admin admin, String request_user_id) {
		admin.setAdmin_id(Utility.getUUID());
		admin.setAdmin_create_user_id(request_user_id);
		admin.setAdmin_create_time(new Date());
		admin.setAdmin_update_user_id(request_user_id);
		admin.setAdmin_update_time(new Date());
		admin.setAdmin_status(true);

		admin.save();
	}

	public void update(Admin admin, String request_user_id) {
		admin.remove(Admin.KEY_USER_ID);
		admin.remove(Admin.KEY_ADMIN_CREATE_USER_ID);
		admin.remove(Admin.KEY_ADMIN_CREATE_TIME);
		admin.setAdmin_update_user_id(request_user_id);
		admin.setAdmin_update_time(new Date());

		admin.update();
	}

	/*public void updateUser_idByAdmin_id(String user_id, String admin_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + Admin.KEY_ADMIN + " SET " + Admin.KEY_USER_ID + " = ? WHERE " + Admin.KEY_ADMIN_ID + " = ? ");

		parameterList.add(user_id);
		parameterList.add(admin_id);

		Db.update(sql.toString(), parameterList.toArray());
	}*/

	public void delete(String admin_id, String request_user_id) {
		Admin admin = new Admin();
		admin.setAdmin_id(admin_id);
		admin.setAdmin_update_user_id(request_user_id);
		admin.setAdmin_update_time(new Date());
		admin.setAdmin_status(false);

		admin.update();
	}

}
