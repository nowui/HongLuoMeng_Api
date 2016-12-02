package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.model.MemberLevel;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.service.MemberLevelService;

public class MemberLevelController extends BaseController {

	private MemberLevelService memberLevelService = new MemberLevelService();

	@ActionKey(Url.URL_MEMBER_LEVEL_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		MemberLevel memberLevelValidator = jsonObject.toJavaObject(MemberLevel.class);

		Map<String, Object> resultMap = memberLevelService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_MEMBER_LEVEL_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		MemberLevel memberLevelValidator = jsonObject.toJavaObject(MemberLevel.class);

		memberLevelValidator.checkMember_level_id();

		MemberLevel memberLevel = memberLevelService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(memberLevel));
	}

	@ActionKey(Url.URL_MEMBER_LEVEL_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		MemberLevel memberLevelValidator = jsonObject.toJavaObject(MemberLevel.class);

		memberLevelValidator.checkMember_level_name();

		memberLevelValidator.checkMember_level_value();

		memberLevelValidator.checkMember_level_sort();

		memberLevelService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_MEMBER_LEVEL_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		MemberLevel memberLevelValidator = jsonObject.toJavaObject(MemberLevel.class);

		memberLevelValidator.checkMember_level_id();

		memberLevelValidator.checkMember_level_name();

		memberLevelValidator.checkMember_level_value();

		memberLevelValidator.checkMember_level_sort();

		memberLevelService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_MEMBER_LEVEL_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		MemberLevel memberLevelValidator = jsonObject.toJavaObject(MemberLevel.class);

		memberLevelValidator.checkMember_level_id();

		memberLevelService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

}
