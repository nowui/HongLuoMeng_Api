package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.hongluomeng.type.UserEnum;

public class AdminService {

	private AdminDao adminDao = new AdminDao();
	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();
	private AuthorizationService authorizationService = new AuthorizationService();

	public Map<String, Object> list(JSONObject jsonObject) {
		Integer count = adminDao.count();

		List<Admin> adminList = adminDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Admin admin : adminList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Admin.KEY_ADMIN_ID, admin.getAdmin_id());
			map.put(Admin.KEY_ADMIN_NAME, admin.getAdmin_name());
			map.put(Admin.KEY_USER_ID, admin.getUser_id());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
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

		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		String user_id = userService.saveByAccount(userMap.getUser_account(), userMap.getUser_password(), UserEnum.ADMIN.getKey(), request_user_id);

		adminMap.setUser_id(user_id);

		adminDao.save(adminMap, request_user_id);

		userService.updateObject_idByUser_id(adminMap.getAdmin_id(), user_id);
	}

	public void update(JSONObject jsonObject) {
		Admin adminMap = jsonObject.toJavaObject(Admin.class);

		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		adminDao.update(adminMap, request_user_id);

		userService.updateUser_account(jsonObject);

		userService.updateUser_passwordByUser_id(userMap.getUser_id(), userMap.getUser_password(), request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Admin adminMap = jsonObject.toJavaObject(Admin.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		adminDao.delete(adminMap.getAdmin_id(), request_user_id);

		userService.deleteByObject_id(adminMap.getAdmin_id(), request_user_id);
	}

	public List<Map<String, Object>> listRole(JSONObject jsonObject) {
		Admin adminMap = jsonObject.toJavaObject(Admin.class);

		List<Map<String, Object>> list = roleService.listByUser_idAndUser_type(adminMap.getUser_id(), UserEnum.ADMIN.getKey());

		return list;
	}

	public void updateRole(JSONObject jsonObject) {
		Admin adminMap = jsonObject.toJavaObject(Admin.class);
		JSONArray jsonArray = jsonObject.getJSONArray(Const.KEY_LIST);

		List<UserRole> userRoleList = new ArrayList<UserRole>();

		for (int i = 0; i < jsonArray.size(); i++) {
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

		User user = userService.loginByUser_accountAndUser_passwordAndUser_type(userMap.getUser_account(), userMap.getUser_password(), UserEnum.ADMIN.getKey());

		if (user == null) {
			return null;
		} else {
			Map<String, Object> resultMap = new HashMap<String, Object>();

			String token = authorizationService.saveByUser_id(user.getUser_id());

			resultMap.put(Const.KEY_TOKEN, token);

			return resultMap;
		}
	}

}
