package com.hongluomeng.controller;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.model.Sms;
import com.hongluomeng.model.User;
import com.jfinal.core.ActionKey;
import com.jfinal.upload.UploadFile;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;
import com.hongluomeng.service.MemberService;

public class MemberController extends BaseController {

	private MemberService memberService = new MemberService();

	@ActionKey(Url.URL_MEMBER_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = memberService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_MEMBER_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Member memberValidator = jsonObject.toJavaObject(Member.class);

		memberValidator.checkMember_id();

		Member member = memberService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(member));
	}

	@ActionKey(Url.URL_MEMBER_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Member memberValidator = jsonObject.toJavaObject(Member.class);

		memberValidator.checkMember_id();

		memberService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_MEMBER_LOGIN)
	public void login() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		User userValidator = jsonObject.toJavaObject(User.class);

		userValidator.checkUser_phone();

		userValidator.checkUser_password();

		Map<String, Object> resultMap = memberService.login(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_MEMBER_WEIBO_OAUTH)
	public void oauthWeibo() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Member memberValidator = jsonObject.toJavaObject(Member.class);

		User userValidator = jsonObject.toJavaObject(User.class);

		memberValidator.checkMember_name();

		memberValidator.checkMember_avatar();

		memberValidator.checkMember_avatar_small(jsonObject.getString(Member.COLUMN_MEMBER_AVATAR_SMALL));

		memberValidator.checkMember_avatar_large(jsonObject.getString(Member.COLUMN_MEMBER_AVATAR_LARGE));

		userValidator.checkWeibo_uid();

		userValidator.checkWeibo_access_token();

		memberValidator.checkMember_weibo_fans();

		memberValidator.checkMember_weibo_friend();

		Map<String, Object> resultMap = memberService.oauthWeibo(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_MEMBER_WECHAT_OAUTH)
	public void oauthWechat() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Member memberValidator = jsonObject.toJavaObject(Member.class);

		User userValidator = jsonObject.toJavaObject(User.class);

		memberValidator.checkMember_name();

		memberValidator.checkMember_avatar();

		memberValidator.checkMember_avatar_small(jsonObject.getString(Member.COLUMN_MEMBER_AVATAR_SMALL));

		memberValidator.checkMember_avatar_large(jsonObject.getString(Member.COLUMN_MEMBER_AVATAR_LARGE));

		userValidator.checkWechat_uid();

		userValidator.checKWechat_access_token();

		Map<String, Object> resultMap = memberService.oauthWechat(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_MEMBER_WEIBO_BIND)
	public void bindWeibo() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Member memberValidator = jsonObject.toJavaObject(Member.class);

		User userValidator = jsonObject.toJavaObject(User.class);

		memberValidator.checkMember_name();

		memberValidator.checkMember_avatar();

		memberValidator.checkMember_avatar_small(jsonObject.getString(Member.COLUMN_MEMBER_AVATAR_SMALL));

		memberValidator.checkMember_avatar_large(jsonObject.getString(Member.COLUMN_MEMBER_AVATAR_LARGE));

		userValidator.checkWeibo_uid();

		userValidator.checkWeibo_access_token();

		memberValidator.checkMember_weibo_fans();

		memberValidator.checkMember_weibo_friend();

		memberService.bindWeibo(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_MEMBER_WECHAT_BIND)
	public void bindWechat() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Member memberValidator = jsonObject.toJavaObject(Member.class);

		User userValidator = jsonObject.toJavaObject(User.class);

		memberValidator.checkMember_name();

		memberValidator.checkMember_avatar();

		memberValidator.checkMember_avatar_small(jsonObject.getString(Member.COLUMN_MEMBER_AVATAR_SMALL));

		memberValidator.checkMember_avatar_large(jsonObject.getString(Member.COLUMN_MEMBER_AVATAR_LARGE));

		userValidator.checkWechat_uid();

		userValidator.checKWechat_access_token();

		memberService.bindWechat(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_MEMBER_REGISTER)
	public void register() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		User userValidator = jsonObject.toJavaObject(User.class);

		userValidator.checkUser_phone();

		userValidator.checkUser_password();

		Sms smsValidator = jsonObject.toJavaObject(Sms.class);

		smsValidator.checkSms_code();

		Map<String, Object> resultMap = memberService.register(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_MEMBER_NAME_UPDATE)
	public void updatName() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Member memberValidator = jsonObject.toJavaObject(Member.class);

		memberValidator.checkMember_name();

		memberService.updateName(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_MEMBER_PASSWORD_UPDATE)
	public void updatePassword() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		User userValidator = jsonObject.toJavaObject(User.class);

		userValidator.checkUser_phone();

		userValidator.checkUser_password();

		memberService.updatePassword(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_MEMBER_AVATAR_UPLOAD)
	public void uploadAvatar() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		UploadFile uploadFile = getFile("file", request_user_id, 1024 * 1024);

		Map<String, Object> resultMap = memberService.uploadAvatar(uploadFile, request_user_id);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_MEMBER_WEIBO_UPDATE)
	public void uploadWeibo() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		memberService.updateWeibo(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_MEMBER_STATUS_GET)
	public void getStatus() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Map<String, Object> resultMap = memberService.getStatus(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_MEMBER_STATUS_UPDATE)
	public void updateStatus() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Member memberValidator = jsonObject.toJavaObject(Member.class);

		memberValidator.checkMember_id();

		memberService.updateStatus(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

}
