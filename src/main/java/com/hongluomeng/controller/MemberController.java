package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.upload.UploadFile;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;
import com.hongluomeng.service.MemberService;
import com.hongluomeng.type.CodeEnum;
import com.hongluomeng.validator.MemberValidator;

public class MemberController extends BaseController {

	private MemberService memberService = new MemberService();

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = memberService.list(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", resultMap));
    }

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Member member = memberService.find(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", member));
    }

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.delete(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
    }

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_LOGIN)
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
	@ActionKey(Url.URL_MEMBER_WEIBO_OAUTH)
	public void oauthWeibo() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> map = memberService.oauthWeibo(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
	}

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_WECHAT_OAUTH)
	public void oauthWechat() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> map = memberService.oauthWechat(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
	}

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_WEIBO_BIND)
	public void bindWeibo() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.bindWeibo(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_WECHAT_BIND)
	public void bindWechat() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.bindWechat(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_REGISTER)
	public void register() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> map = memberService.register(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", map));
	}

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_NAME_UPDATE)
	public void updatName() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.updateName(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_PASSWORD_UPDATE)
	public void updatePassword() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.updatePassword(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_AVATAR_UPLOAD)
	public void uploadAvatar() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		UploadFile uploadFile = getFile("file", request_user_id, 1024 * 1024);

		JSONObject avatarObject = memberService.uploadAvatar(uploadFile, request_user_id);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", avatarObject));
	}

	@Before(MemberValidator.class)
	@ActionKey(Url.URL_MEMBER_WEIBO_UPDATE)
	public void uploadWeibo() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.updateWeibo(jsonObject);

        renderJson(Utility.setResponse(CodeEnum.CODE_200, "", null));
	}

}
