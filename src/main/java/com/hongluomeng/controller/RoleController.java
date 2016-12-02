package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Role;
import com.hongluomeng.service.RoleService;

public class RoleController extends BaseController {

	private RoleService roleService = new RoleService();

	@ActionKey(Url.URL_ROLE_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = roleService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_ROLE_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Role role = roleService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(role));
	}

	@ActionKey(Url.URL_ROLE_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		roleService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ROLE_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		roleService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ROLE_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		roleService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_ROLE_OPERATION_LIST)
	public void listOperatio() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Map<String, Object>> resultList = roleService.listOperation(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_ROLE_OPERATION_UPDATE)
	public void updateOperation() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		roleService.updateOperation(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

}
