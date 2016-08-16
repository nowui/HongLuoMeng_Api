package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.AdminDao;
import com.hongluomeng.model.Admin;
import com.hongluomeng.model.User;
import com.hongluomeng.model.UserRole;
import com.hongluomeng.type.AccountEnum;
import com.hongluomeng.type.UserEnum;

public class AdminService {

	private AdminDao adminDao = new AdminDao();
	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();

	public Integer count(JSONObject jsonObject) {
		//Admin adminMap = jsonObject.toJavaObject(Admin.class);

		return adminDao.count();
	}

	public List<Admin> list(JSONObject jsonObject) {
		//Admin adminMap = jsonObject.toJavaObject(Admin.class);

		return adminDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
	}

	public Admin find(JSONObject jsonObject) {
		Admin adminMap = jsonObject.toJavaObject(Admin.class);

		Admin admin = adminDao.findByAdmin_id(adminMap.getAdmin_id());

		User user = userService.findByUser_id(admin.getUser_id());

		admin.setUser_account(user.getUser_account());

		return admin;
	}

	public void save(JSONObject jsonObject) {
		Admin adminMap = jsonObject.toJavaObject(Admin.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		adminDao.save(adminMap, request_user_id);

		String user_id = userService.saveByObject_idAndUser_type(AccountEnum.ACCOUNT, jsonObject, adminMap.getAdmin_id(), UserEnum.ADMIN.getKey());

		adminDao.updateUser_idByAdmin_id(user_id, adminMap.getAdmin_id());
	}

	public void update(JSONObject jsonObject) {
		Admin adminMap = jsonObject.toJavaObject(Admin.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		adminDao.update(adminMap, request_user_id);

		userService.updateUser_account(jsonObject);

		userService.updateUser_password(jsonObject);
	}

	public void delete(JSONObject jsonObject) {
		Admin adminMap = jsonObject.toJavaObject(Admin.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		adminDao.delete(adminMap.getAdmin_id(), request_user_id);

		userService.deleteByObject_id(adminMap.getAdmin_id(), request_user_id);
	}

	public List<Map<String, Object>> listRole(JSONObject jsonObject) {
		Admin adminMap = jsonObject.toJavaObject(Admin.class);

		List<Map<String, Object>> list  = roleService.listByUser_idAndUser_type(adminMap.getUser_id(), UserEnum.ADMIN.getKey());

        return list;
	}

	public void updateRole(JSONObject jsonObject) {
		Admin adminMap = jsonObject.toJavaObject(Admin.class);
		JSONArray jsonArray = jsonObject.getJSONArray(UserRole.KEY_USER_ROLE);

		List<UserRole> userRoleList = new ArrayList<UserRole>();

		for(int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);

			UserRole userRole = object.toJavaObject(UserRole.class);
			userRole.setUser_id(adminMap.getUser_id());
			userRole.setUser_type(UserEnum.ADMIN.getKey());

			userRoleList.add(userRole);
		}

		roleService.updateUserRole(userRoleList, adminMap.getUser_id(), UserEnum.ADMIN.getKey());
	}

	public Map<String, Object> login(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		return userService.loginByUser_accountAndUser_password(userMap.getUser_account(), userMap.getUser_password());
	}

}
