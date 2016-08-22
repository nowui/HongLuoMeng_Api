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
import com.hongluomeng.type.AccountEnum;
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

		Member member = memberDao.findByMember_id(memberMap.getMember_id());;

		return member;
	}

	public Map<String, Object> save(JSONObject jsonObject) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Sms sms = jsonObject.toJavaObject(Sms.class);

		User userMap = jsonObject.toJavaObject(User.class);

		String sms_type = SmsEnum.REGISTER.getKey();

		Integer count = smsService.countBySms_phoneAndSms_codeAndMinute(sms_type, userMap.getUser_phone(), sms.getSms_code(), 30);

		if (count == 0) {
			throw new RuntimeException("验证码已经过期");
		}

		smsService.updateSms_statusBySms_phone(sms_type, userMap.getUser_phone(), sms.getSms_code());

		Member memberMap = jsonObject.toJavaObject(Member.class);
		memberMap.setMember_name(userMap.getUser_phone());
		memberMap.setUser_id("");

		memberDao.save(memberMap, request_user_id);

		String user_id = userService.saveByObject_idAndUser_type(AccountEnum.PHONE, jsonObject, memberMap.getMember_id(), UserEnum.MEMBER.getKey());

		memberDao.updateUser_idByMember_id(user_id, memberMap.getMember_id());

		String token = authorizationService.saveByUser_id(user_id);

		resultMap.put(Const.KEY_TOKEN, token);

		return resultMap;
	}

	public Map<String, Object> login(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		return userService.loginByUser_phoneAndUser_password(userMap.getUser_phone(), userMap.getUser_password());
	}



	public Map<String, Object> oauth(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		Integer count = userService.countByOauth(jsonObject);

		if(count == 0) {

		}

		return null;
	}

}
