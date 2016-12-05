package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
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

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (MemberLevel memberLevel : memberLevelList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(MemberLevel.KEY_MEMBER_LEVEL_ID, memberLevel.getMember_level_id());
			map.put(MemberLevel.KEY_MEMBER_LEVEL_NAME, memberLevel.getMember_level_name());
			map.put(MemberLevel.KEY_MEMBER_LEVEL_VALUE, memberLevel.getMember_level_value());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public List<MemberLevel> listAll() {
		List<MemberLevel> memberLevelList = memberLevelDao.list(0, 0);

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
