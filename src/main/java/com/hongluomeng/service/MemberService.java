package com.hongluomeng.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.MemberDao;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.Sms;
import com.hongluomeng.model.User;
import com.hongluomeng.type.SmsEnum;
import com.hongluomeng.type.UserEnum;

public class MemberService {

	private MemberDao memberDao = new MemberDao();
	private UserService userService = new UserService();
	private AuthorizationService authorizationService = new AuthorizationService();
	private SmsService smsService = new SmsService();

	public Integer count(JSONObject jsonObject) {
		//Member memberMap = jsonObject.toJavaObject(Member.class);

		return memberDao.count();
	}

	public List<Member> list(JSONObject jsonObject) {
		//Member memberMap = jsonObject.toJavaObject(Member.class);

		return memberDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
	}

	public Member find(JSONObject jsonObject) {
		Member memberMap = jsonObject.toJavaObject(Member.class);

		Member member = memberDao.findByMember_id(memberMap.getMember_id());

		return member;
	}

	public void delete(JSONObject jsonObject) {
		Member memberMap = jsonObject.toJavaObject(Member.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberDao.delete(memberMap.getMember_id(), request_user_id);

		userService.deleteByObject_id(memberMap.getMember_id(), request_user_id);
	}

	public Map<String, Object> register(JSONObject jsonObject) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Sms sms = jsonObject.toJavaObject(Sms.class);

		User userMap = jsonObject.toJavaObject(User.class);

		String sms_type = SmsEnum.REGISTER.getKey();

		Integer count = smsService.countBySms_phoneAndSms_codeAndMinute(sms_type, userMap.getUser_phone(), sms.getSms_code(), 30);

		if (count == 0) {
			throw new RuntimeException("验证码已经过期");
		}

		String user_id = userService.saveByPhone(userMap.getUser_phone(), userMap.getUser_password(), UserEnum.MEMBER.getKey(), request_user_id);

		Member memberMap = jsonObject.toJavaObject(Member.class);
		memberMap.setMember_name(userMap.getUser_phone());
		memberMap.setMember_image("");
		memberMap.setUser_id(user_id);

		memberDao.save(memberMap, request_user_id);

		userService.updateObject_idByUser_id(memberMap.getMember_id(), user_id);

		smsService.updateSms_statusBySms_phone(sms_type, userMap.getUser_phone(), sms.getSms_code());

		String token = authorizationService.saveByUser_id(user_id);

		resultMap.put(Const.KEY_TOKEN, token);

		return resultMap;
	}

	public void resetPassword(JSONObject jsonObject) {
		Sms sms = jsonObject.toJavaObject(Sms.class);

		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		String sms_type = SmsEnum.RESET_PASSWORD.getKey();

		Integer count = smsService.countBySms_phoneAndSms_codeAndMinute(sms_type, userMap.getUser_phone(), sms.getSms_code(), 30);

		if (count == 0) {
			throw new RuntimeException("验证码已经过期");
		}

		smsService.updateSms_statusBySms_phone(sms_type, userMap.getUser_phone(), sms.getSms_code());

		userService.updateUser_passwordByUser_phone(userMap.getUser_phone(), userMap.getUser_password(), request_user_id);
	}

	public Map<String, Object> login(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		return userService.loginByUser_phoneAndUser_password(userMap.getUser_phone(), userMap.getUser_password());
	}

	public Map<String, Object> oauthWeibo(JSONObject jsonObject) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		String user_id = userService.saveWeibo(userMap.getWeibo_uid(), userMap.getWeibo_access_token(), UserEnum.MEMBER.getKey(), request_user_id);

		//注册会员信息
		if(! Utility.isNullOrEmpty(user_id)) {
			User user = userService.findByWeibo_uid(userMap.getWeibo_uid());

			user_id = user.getUser_id();

			Member memberMap = jsonObject.toJavaObject(Member.class);
			memberMap.setUser_id(user_id);

			memberDao.save(memberMap, request_user_id);
		}

		String token = authorizationService.saveByUser_id(user_id);

		resultMap.put(Const.KEY_TOKEN, token);

		return resultMap;
	}

	public Map<String, Object> oauthWechat(JSONObject jsonObject) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		String user_id = userService.saveWechat(userMap.getWechat_uid(), userMap.getWechat_access_token(), UserEnum.MEMBER.getKey(), request_user_id);

		//注册会员信息
		if(! Utility.isNullOrEmpty(user_id)) {
			User user = userService.findByWechat_uid(userMap.getWechat_uid());

			user_id = user.getUser_id();

			Member memberMap = jsonObject.toJavaObject(Member.class);
			memberMap.setUser_id(user_id);

			memberDao.save(memberMap, request_user_id);
		}

		String token = authorizationService.saveByUser_id(user_id);

		resultMap.put(Const.KEY_TOKEN, token);

		return resultMap;
	}

	public void bindWeibo(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		userService.updateWeibo(userMap.getWeibo_uid(), userMap.getWeibo_access_token(), request_user_id);
	}

	public void bindWechat(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		userService.updateWechat(userMap.getWechat_uid(), userMap.getWechat_access_token(), request_user_id);
	}

}
