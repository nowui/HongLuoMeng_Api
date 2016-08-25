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
	@ActionKey(Const.URL_MEMBER_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
    }

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_LOGIN)
	public void login() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> map = memberService.login(jsonObject);

		if (map == null) {
	        renderJson(Utility.setResponse(CodeEnum.CODE_400, "用户名或者密码不正确", null));
		} else {
	        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
		}
	}

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_WEIBO_OAUTH)
	public void oauthWeibo() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> map = memberService.oauthWeibo(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
	}

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_WECHAT_OAUTH)
	public void oauthWechat() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> map = memberService.oauthWechat(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
	}

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_WEIBO_BIND)
	public void bindWeibo() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.bindWeibo(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_WECHAT_BIND)
	public void bindWechat() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.bindWechat(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_REGISTER)
	public void register() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> map = memberService.register(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
	}

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_RESET_PASSWORD)
	public void resetPassword() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.resetPassword(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(MemberValidator.class)
	@ActionKey(Const.URL_MEMBER_UPLOAD_IMAGE)
	public void uploadImage() {
		//JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}
