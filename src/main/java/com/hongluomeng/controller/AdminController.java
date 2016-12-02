package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.model.Admin;
import com.hongluomeng.model.User;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.service.AdminService;

public class AdminController extends BaseController {

	private AdminService adminService = new AdminService();

	@ActionKey(Url.URL_ADMIN_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = adminService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_ADMIN_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Admin adminValidator = jsonObject.toJavaObject(Admin.class);

		adminValidator.checkAdmin_id();

		Admin admin = adminService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(admin));
	}

	@ActionKey(Url.URL_ADMIN_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Admin adminValidator = jsonObject.toJavaObject(Admin.class);

		User userValidator = jsonObject.toJavaObject(User.class);

		adminValidator.checkAdmin_name();

		userValidator.checkUser_account();

		userValidator.checkUser_password();

		adminService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ADMIN_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Admin adminValidator = jsonObject.toJavaObject(Admin.class);

		User userValidator = jsonObject.toJavaObject(User.class);

		adminValidator.checkAdmin_id();

		adminValidator.checkAdmin_name();

		userValidator.checkUser_account();

		adminService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ADMIN_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Admin adminValidator = jsonObject.toJavaObject(Admin.class);

		adminValidator.checkAdmin_id();

		adminService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ADMIN_OPERATION_LIST)
	public void listOperation() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		User userValidator = jsonObject.toJavaObject(User.class);

		userValidator.checkUser_id();

		List<Map<String, Object>> resultList = adminService.listRole(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_ADMIN_OPERATION_UPDATE)
	public void updateOperation() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		User userValidator = jsonObject.toJavaObject(User.class);

		userValidator.checkUser_id();

		Utility.checkList(jsonObject);

		adminService.updateRole(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ADMIN_LOGIN)
	public void login() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		User userValidator = jsonObject.toJavaObject(User.class);

		userValidator.checkUser_account();

		userValidator.checkUser_password();

		Map<String, Object> resultMap = adminService.login(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

}
