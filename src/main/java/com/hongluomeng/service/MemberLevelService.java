package com.hongluomeng.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.MemberLevelDao;
import com.hongluomeng.model.MemberLevel;

public class MemberLevelService {

	private MemberLevelDao memberLevelDao = new MemberLevelDao();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Member_level member_levelMap = jsonObject.toJavaObject(Member_level.class);

		Integer count = memberLevelDao.count();

		List<MemberLevel> memberLevelList = memberLevelDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, memberLevelList);

		return resultMap;
	}

	public List<MemberLevel> listAll() {
		return memberLevelDao.list(0, 0);
	}

	public MemberLevel find(JSONObject jsonObject) {
		MemberLevel member_levelMap = jsonObject.toJavaObject(MemberLevel.class);

		MemberLevel member_level = memberLevelDao.findByMember_level_id(member_levelMap.getMember_level_id());

		return member_level;
	}

	public void save(JSONObject jsonObject) {
		MemberLevel member_levelMap = jsonObject.toJavaObject(MemberLevel.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberLevelDao.save(member_levelMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		MemberLevel member_levelMap = jsonObject.toJavaObject(MemberLevel.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberLevelDao.update(member_levelMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		MemberLevel member_levelMap = jsonObject.toJavaObject(MemberLevel.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberLevelDao.delete(member_levelMap.getMember_level_id(), request_user_id);
	}

}
