package com.hongluomeng.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.MemberDeliveryDao;
import com.hongluomeng.model.MemberDelivery;

public class MemberDeliveryService {

	private MemberDeliveryDao memberDeliveryDao = new MemberDeliveryDao();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Member_delivery memberDeliveryMap = jsonObject.toJavaObject(Member_delivery.class);

		Integer count = memberDeliveryDao.count();

		List<MemberDelivery> memberDeliveryList = memberDeliveryDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, memberDeliveryList);

		return resultMap;
	}

	public MemberDelivery find(JSONObject jsonObject) {
		MemberDelivery memberDeliveryMap = jsonObject.toJavaObject(MemberDelivery.class);

		MemberDelivery memberDelivery = memberDeliveryDao.findByMember_delivery_id(memberDeliveryMap.getMember_delivery_id());

		return memberDelivery;
	}

	public void save(JSONObject jsonObject) {
		MemberDelivery memberDeliveryMap = jsonObject.toJavaObject(MemberDelivery.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberDeliveryDao.save(memberDeliveryMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		MemberDelivery memberDeliveryMap = jsonObject.toJavaObject(MemberDelivery.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberDeliveryDao.update(memberDeliveryMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		MemberDelivery memberDeliveryMap = jsonObject.toJavaObject(MemberDelivery.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberDeliveryDao.delete(memberDeliveryMap.getMember_delivery_id(), request_user_id);
	}

	public List<MemberDelivery> getist(JSONObject jsonObject) {
		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		return memberDeliveryDao.listByUser_id(request_user_id);
	}

}