package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.RoleOperationDao;
import com.hongluomeng.model.RoleOperation;

public class RoleOperationService extends BaseService {

	private RoleOperationDao roleOperationDao = new RoleOperationDao();

	public List<RoleOperation> listByRole_id(String role_id) {
		return roleOperationDao.listByRole_id(role_id);
	}

	public void save(List<RoleOperation> roleOperationList) {
		roleOperationDao.save(roleOperationList);
	}

	public void deleteByRole_id(List<RoleOperation> roleOperationList, String role_id) {
		roleOperationDao.deleteByRole_id(roleOperationList, role_id);
	}

}
