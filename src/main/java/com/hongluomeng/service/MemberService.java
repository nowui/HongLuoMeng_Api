package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.MemberDao;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.MemberLevel;
import com.hongluomeng.model.Sms;
import com.hongluomeng.model.User;
import com.hongluomeng.type.SmsEnum;
import com.hongluomeng.type.UserEnum;
import com.jfinal.upload.UploadFile;

public class MemberService {

	private MemberDao memberDao = new MemberDao();
	private UserService userService = new UserService();
	private AuthorizationService authorizationService = new AuthorizationService();
	private SmsService smsService = new SmsService();
	private UploadService uploadService = new UploadService();
	private MemberLevelService memberLevelService = new MemberLevelService();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Member memberMap = jsonObject.toJavaObject(Member.class);

		Integer count = memberDao.count();

		List<Member> memberList = memberDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Member member : memberList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Member.KEY_MEMBER_ID, member.getMember_id());
			map.put(Member.KEY_MEMBER_NAME, member.getMember_name());
			map.put(Member.KEY_MEMBER_WEIBO_FANS, member.getMember_weibo_fans());
			map.put(Member.KEY_MEMBER_STATUS, member.getMember_status());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public Member find(JSONObject jsonObject) {
		Member memberMap = jsonObject.toJavaObject(Member.class);

		Member member = memberDao.findByMember_id(memberMap.getMember_id());

		return member;
	}

	public Member findByUser_id(String user_id) {
		return memberDao.findByUser_id(user_id);
	}

	public void delete(JSONObject jsonObject) {
		Member memberMap = jsonObject.toJavaObject(Member.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberDao.delete(memberMap.getMember_id(), request_user_id);

		userService.deleteByObject_id(memberMap.getMember_id(), request_user_id);
	}

	public Map<String, Object> login(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		User user = userService.loginByUser_phoneAndUser_passwordAndUser_type(userMap.getUser_phone(), userMap.getUser_password(), UserEnum.MEMBER.getKey());

		if (user == null) {
			return null;
		} else {
			String user_id = user.getUser_id();

			String token = authorizationService.saveByUser_id(user_id);

			Member member = memberDao.findByUser_id(user_id);

			Map<String, Object> resultMap = packageResultMap(token, member);

			return resultMap;
		}
	}

	public Map<String, Object> register(JSONObject jsonObject) {
		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Sms sms = jsonObject.toJavaObject(Sms.class);

		User userMap = jsonObject.toJavaObject(User.class);

		String sms_type = SmsEnum.REGISTER.getKey();

		Integer count = smsService.countBySms_phoneAndSms_codeAndMinute(sms_type, userMap.getUser_phone(), sms.getSms_code(), Const.SMS_TIMEOUT_EXPRESS);

		if (count == 0) {
			throw new RuntimeException("验证码已经过期");
		}

		String user_id = userService.saveByPhone(userMap.getUser_phone(), userMap.getUser_password(), UserEnum.MEMBER.getKey(), request_user_id);

		Member memberMap = jsonObject.toJavaObject(Member.class);
		memberMap.setMember_name(userMap.getUser_phone());
		memberMap.setMember_avatar("");
		memberMap.setUser_id(user_id);

		memberDao.save(memberMap, request_user_id);

		userService.updateObject_idByUser_id(memberMap.getMember_id(), user_id);

		smsService.updateSms_statusBySms_phone(sms_type, userMap.getUser_phone(), sms.getSms_code());

		String token = authorizationService.saveByUser_id(user_id);

		Map<String, Object> resultMap = packageResultMap(token, memberMap);

		return resultMap;
	}

	public void updateName(JSONObject jsonObject) {
		Member memberMap = jsonObject.toJavaObject(Member.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberDao.updateMember_nameByUser_id(memberMap.getMember_name(), request_user_id);
	}

	public void updatePassword(JSONObject jsonObject) {
		Sms sms = jsonObject.toJavaObject(Sms.class);

		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		String sms_type = SmsEnum.PASSWORD.getKey();

		Integer count = smsService.countBySms_phoneAndSms_codeAndMinute(sms_type, userMap.getUser_phone(), sms.getSms_code(), Const.SMS_TIMEOUT_EXPRESS);

		if (count == 0) {
			throw new RuntimeException("验证码已经过期");
		}

		smsService.updateSms_statusBySms_phone(sms_type, userMap.getUser_phone(), sms.getSms_code());

		userService.updateUser_passwordByUser_phone(userMap.getUser_phone(), userMap.getUser_password(), request_user_id);
	}

	public Map<String, Object> oauthWeibo(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		String user_id = userService.saveWeibo(userMap.getWeibo_uid(), userMap.getWeibo_access_token(), UserEnum.MEMBER.getKey(), request_user_id);

		Member memberMap = jsonObject.toJavaObject(Member.class);

		if (Utility.isNullOrEmpty(user_id)) {
			User user = userService.findByWeibo_uid(userMap.getWeibo_uid());

			user_id = user.getUser_id();

			String member_level_id = checkMemberLevel(memberMap.getMember_weibo_fans());

			memberDao.updateMember_Levle_idAndMember_weibo_fansAndMember_weibo_friend(member_level_id, memberMap.getMember_weibo_fans(), memberMap.getMember_weibo_friend(), user_id);

			//更新会员头像并且返回会员信息
			memberMap = memberDao.updateMember_avatarByUser_id(packageAvatar(jsonObject), user_id, false);
		} else {
			memberMap.setUser_id(user_id);

			memberMap.setMember_avatar(packageAvatar(jsonObject));

			memberDao.save(memberMap, request_user_id);

			userService.updateObject_idByUser_id(memberMap.getMember_id(), user_id);
		}

		String token = authorizationService.saveByUser_id(user_id);

		Map<String, Object> resultMap = packageResultMap(token, memberMap);

		return resultMap;
	}

	public Map<String, Object> oauthWechat(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		String user_id = userService.saveWechat(userMap.getWechat_uid(), userMap.getWechat_access_token(), UserEnum.MEMBER.getKey(), request_user_id);

		Member memberMap;

		if (Utility.isNullOrEmpty(user_id)) {
			User user = userService.findByWechat_uid(userMap.getWechat_uid());

			user_id = user.getUser_id();

			//更新会员头像并且返回会员信息
			memberMap = memberDao.updateMember_avatarByUser_id(packageAvatar(jsonObject), user_id, false);
		} else {
			memberMap = jsonObject.toJavaObject(Member.class);
			memberMap.setUser_id(user_id);

			memberMap.setMember_avatar(packageAvatar(jsonObject));

			memberDao.save(memberMap, request_user_id);

			userService.updateObject_idByUser_id(memberMap.getMember_id(), user_id);
		}

		String token = authorizationService.saveByUser_id(user_id);

		Map<String, Object> resultMap = packageResultMap(token, memberMap);

		return resultMap;
	}

	public void bindWeibo(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		Member memberMap = jsonObject.toJavaObject(Member.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		String member_level_id = checkMemberLevel(memberMap.getMember_weibo_fans());

		memberDao.updateMember_Levle_idAndMember_weibo_fansAndMember_weibo_friend(member_level_id, memberMap.getMember_weibo_fans(), memberMap.getMember_weibo_friend(), request_user_id);

		//更新会员头像
		memberDao.updateMember_avatarByUser_id(packageAvatar(jsonObject), request_user_id, false);

		//更新用户微博帐号
		userService.updateWeibo(userMap.getWeibo_uid(), userMap.getWeibo_access_token(), request_user_id);
	}

	public void bindWechat(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		//更新会员头像
		memberDao.updateMember_avatarByUser_id(packageAvatar(jsonObject), request_user_id, false);

		//更新用户微信帐号
		userService.updateWechat(userMap.getWechat_uid(), userMap.getWechat_access_token(), request_user_id);
	}

	public JSONObject uploadAvatar(UploadFile uploadFile, String request_user_id) {
		String path = uploadService.uploadImage(uploadFile, request_user_id);

		JSONObject avatarObject = new JSONObject();
		avatarObject.put(Const.UPLOAD_NORMAL, path);
		avatarObject.put(Const.UPLOAD_SMALL, Utility.packageImagePath(path, Const.UPLOAD_SMALL));
		avatarObject.put(Const.UPLOAD_LARGE, Utility.packageImagePath(path, Const.UPLOAD_LARGE));

		memberDao.updateMember_avatarByUser_id(avatarObject.toJSONString(), request_user_id, true);

		return avatarObject;
	}

	private String packageAvatar(JSONObject jsonObject) {
		Member memberMap = jsonObject.toJavaObject(Member.class);

		JSONObject avatarObject = new JSONObject();
		avatarObject.put(Const.UPLOAD_NORMAL, memberMap.getMember_avatar());
		avatarObject.put(Const.UPLOAD_SMALL, jsonObject.getString(Member.KEY_MEMBER_AVATAR_SMALL));
		avatarObject.put(Const.UPLOAD_LARGE, jsonObject.getString(Member.KEY_MEMBER_AVATAR_LARGE));

		return avatarObject.toJSONString();
	}

	private Map<String, Object> packageResultMap(String token, Member member) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put(Const.KEY_TOKEN, token);
		resultMap.put(Member.KEY_MEMBER_NAME, member.getMember_name());
		resultMap.put(Member.KEY_MEMBER_LEVEL_ID, member.getMember_level_id());
		resultMap.put(Member.KEY_MEMBER_LEVEL_NAME, member.getMember_level_name());
		resultMap.put(Member.KEY_MEMBER_WEIBO_FANS, member.getMember_weibo_fans());
		resultMap.put(Member.KEY_MEMBER_STATUS, member.getMember_status());

		if (Utility.isNullOrEmpty(member.getMember_avatar())) {
			resultMap.put(Member.KEY_MEMBER_AVATAR, "");
			resultMap.put(Member.KEY_MEMBER_AVATAR_SMALL, "");
			resultMap.put(Member.KEY_MEMBER_AVATAR_LARGE, "");
		} else {
			//JSONObject avatarObject = JSONObject.parseObject(member.getMember_avatar());

			resultMap.put(Member.KEY_MEMBER_AVATAR, member.getMember_avatar().getString(Const.UPLOAD_NORMAL));
			resultMap.put(Member.KEY_MEMBER_AVATAR_SMALL, member.getMember_avatar().getString(Const.UPLOAD_SMALL));
			resultMap.put(Member.KEY_MEMBER_AVATAR_LARGE, member.getMember_avatar().getString(Const.UPLOAD_LARGE));
		}

		return resultMap;
	}

	public void updateInfo(String member_real_name, String member_identity_card, String member_identity_card_front_image, String member_identity_card_back_image, String request_user_id) {
		memberDao.updateInfo(member_real_name, member_identity_card, member_identity_card_front_image, member_identity_card_back_image, request_user_id);
	}

	public void updateWeibo(JSONObject jsonObject) {
		Member memberMap = jsonObject.toJavaObject(Member.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		String member_level_id = checkMemberLevel(memberMap.getMember_weibo_fans());

		memberDao.updateMember_Levle_idAndMember_weibo_fansAndMember_weibo_friend(member_level_id, memberMap.getMember_weibo_fans(), memberMap.getMember_weibo_friend(), request_user_id);
	}

	public String checkMemberLevel(Integer member_weibo_fans) {
		String member_level_id = "";

		List<MemberLevel> memberLevelList = memberLevelService.listAll();

		if(memberLevelList.size() == 0) {
			return "";
		}

		for(int i = memberLevelList.size() - 1; i > 0; i--) {
			MemberLevel memberLevel = memberLevelList.get(i);

			if(memberLevel.getMember_level_value() < member_weibo_fans) {
				member_level_id = memberLevel.getMember_level_id();

				break;
			}
		}

		return member_level_id;
	}

	public Map<String, Object> getStatus(JSONObject jsonObject) {
		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Member member = memberDao.findByUser_id(request_user_id);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Member.KEY_MEMBER_STATUS, member.getMember_status());

		return resultMap;
	}

	public void updateStatus(JSONObject jsonObject) {
		Member memberMap = jsonObject.toJavaObject(Member.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberDao.updateMember_status(memberMap.getMember_id(), request_user_id);
	}

}
