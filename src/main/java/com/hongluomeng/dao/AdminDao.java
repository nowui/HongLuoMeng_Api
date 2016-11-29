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
		dynamicSQL.append("WHERE " + Admin.KEY_SYSTEM_STATUS + " = 1 ");

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
		dynamicSQL.append("WHERE " + Admin.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.append("ORDER BY " + Admin.KEY_SYSTEM_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		return new Admin().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Admin> list(Integer m, Integer n) {
		Admin admin = new Admin();

		return list(admin, m, n);
	}

	private Admin find(Admin admin) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Admin.KEY_TABLE_ADMIN + " ");
		dynamicSQL.append("WHERE " + Admin.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Admin.KEY_ADMIN_ID + " = ? ", admin.getAdmin_id());

		List<Admin> adminList = new Admin().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if (adminList.size() == 0) {
			return null;
		} else {
			return adminList.get(0);
		}
	}

	public Admin findByAdmin_id(String admin_id) {
		Admin admin = new Admin();
		admin.setAdmin_id(admin_id);

		admin.checkAdmin_id();

		return find(admin);
	}

	public void save(Admin admin, String request_user_id) {
		admin.setAdmin_id(Utility.getUUID());

		admin.initForSave(request_user_id);

		admin.save();
	}

	public void update(Admin admin, String request_user_id) {
		admin.remove(Admin.KEY_USER_ID);

		admin.initForUpdate(request_user_id);

		admin.update();
	}

	public void delete(String admin_id, String request_user_id) {
		Admin admin = new Admin();
		admin.setAdmin_id(admin_id);

		admin.initForDelete(request_user_id);

		admin.update();
	}

}
