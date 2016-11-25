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
		//Member_level memberLevelMap = jsonObject.toJavaObject(Member_level.class);

		Integer count = memberLevelDao.count();

		List<MemberLevel> memberLevelList = memberLevelDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, memberLevelList);

		return resultMap;
	}

	public List<MemberLevel> listAll() {
		List<MemberLevel> memberLevelList = memberLevelDao.list(0, 0);

		for (MemberLevel memberLevel : memberLevelList) {
			memberLevel.setMember_level_sort(null);
			memberLevel.setMember_level_status(null);
		}

		return memberLevelList;
	}

	public MemberLevel find(JSONObject jsonObject) {
		MemberLevel memberLevelMap = jsonObject.toJavaObject(MemberLevel.class);

		MemberLevel memberLevel = memberLevelDao.findByMember_level_id(memberLevelMap.getMember_level_id());

		return memberLevel;
	}

	public void save(JSONObject jsonObject) {
		MemberLevel memberLevelMap = jsonObject.toJavaObject(MemberLevel.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberLevelDao.save(memberLevelMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		MemberLevel memberLevelMap = jsonObject.toJavaObject(MemberLevel.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberLevelDao.update(memberLevelMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		MemberLevel memberLevelMap = jsonObject.toJavaObject(MemberLevel.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberLevelDao.delete(memberLevelMap.getMember_level_id(), request_user_id);
	}

}
