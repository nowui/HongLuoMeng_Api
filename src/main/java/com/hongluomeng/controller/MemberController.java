package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;
import com.hongluomeng.service.MemberService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.MemberValidator;

public class MemberController extends BaseController {

	private MemberService memberService = new MemberService();

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		List<Member> memberList = memberService.list(jsonObject);

		Integer count = memberService.count(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", count, memberList));
    }

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Member member = memberService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", member));
    }

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_LOGIN)
	public void memberLogin() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> map = memberService.login(jsonObject);

		if (map == null) {
	        renderJson(Utility.setResponse(CodeEnum.CODE_400, "用户名或者密码不正确", null));
		} else {
	        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
		}
	}

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_REGISTER)
	public void memberRegister() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> map = memberService.save(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
	}

}
