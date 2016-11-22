package com.hongluomeng.dao;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Admin;

public class AdminDao {

	private Integer count(Admin admin) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT COUNT(*) FROM " + Admin.KEY_TABLE_ADMIN + " ");
		dynamicSQL.append("WHERE " + Admin.KEY_ADMIN_STATUS + " = 1 ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Admin admin = new Admin();

		return count(admin);
	}

	private List<Admin> list(Admin admin, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Admin.KEY_TABLE_ADMIN + " ");
		dynamicSQL.append("WHERE " + Admin.KEY_ADMIN_STATUS + " = 1 ");
		dynamicSQL.append("ORDER BY " + Admin.KEY_ADMIN_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		List<Admin> adminList = new Admin().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return adminList;
	}

	public List<Admin> list(Integer m, Integer n) {
		Admin admin = new Admin();

		return list(admin, m, n);
	}

	private Admin find(Admin admin) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Admin.KEY_TABLE_ADMIN + " ");
		dynamicSQL.append("WHERE " + Admin.KEY_ADMIN_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Admin.KEY_ADMIN_ID + " = ? ", admin.getAdmin_id());

		List<Admin> adminList = new Admin().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if(adminList == null) {
			return null;
		} else {
			return adminList.get(0);
		}
	}

	public Admin findByAdmin_id(String admin_id) {
		Admin admin = new Admin();
		admin.setAdmin_id(admin_id);

		Utility.checkIsNullOrEmpty(admin_id);

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
