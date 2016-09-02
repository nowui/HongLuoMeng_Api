package com.hongluomeng.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.MemberLevel;
import com.hongluomeng.service.MemberLevelService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.MemberLevelValidator;

public class MemberLevelController extends BaseController {

	private MemberLevelService memberLevelService = new MemberLevelService();

	@Before(MemberLevelValidator.class)
	@ActionKey(Const.URL_MEMBER_LEVEL_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<MemberLevel> memberLevelList = memberLevelService.list(jsonObject);

		Integer count = memberLevelService.count(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", count, memberLevelList));
    }

	@Before(MemberLevelValidator.class)
	@ActionKey(Const.URL_MEMBER_LEVEL_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		MemberLevel memberLevel = memberLevelService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", memberLevel));
    }

	@Before(MemberLevelValidator.class)
	@ActionKey(Const.URL_MEMBER_LEVEL_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberLevelService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(MemberLevelValidator.class)
	@ActionKey(Const.URL_MEMBER_LEVEL_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberLevelService.update(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(MemberLevelValidator.class)
	@ActionKey(Const.URL_MEMBER_LEVEL_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberLevelService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}