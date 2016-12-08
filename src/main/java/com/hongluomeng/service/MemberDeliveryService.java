package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.MemberDeliveryDao;
import com.hongluomeng.model.MemberDelivery;

public class MemberDeliveryService extends BaseService {

	private MemberDeliveryDao memberDeliveryDao = new MemberDeliveryDao();

	public Map<String, Object> list(JSONObject jsonObject) {
		Integer count = memberDeliveryDao.count();

		List<MemberDelivery> memberDeliveryList = memberDeliveryDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (MemberDelivery memberDelivery : memberDeliveryList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(MemberDelivery.COLUMN_MEMBER_DELIVERY_ID, memberDelivery.getMember_delivery_id());
			map.put(MemberDelivery.COLUMN_MEMBER_DELIVERY_NAME, memberDelivery.getMember_delivery_name());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public MemberDelivery find(JSONObject jsonObject) {
		MemberDelivery memberDeliveryMap = jsonObject.toJavaObject(MemberDelivery.class);

		MemberDelivery memberDelivery = memberDeliveryDao.findByMember_delivery_id(memberDeliveryMap.getMember_delivery_id());

		return memberDelivery;
	}

	public MemberDelivery findByMember_delivery_id(String member_delivery_id) {
		return memberDeliveryDao.findByMember_delivery_id(member_delivery_id);
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

	public List<Map<String, Object>> getist(JSONObject jsonObject) {
		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<MemberDelivery> memberDeliveryList = memberDeliveryDao.listByUser_id(request_user_id);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for (MemberDelivery memberDelivery : memberDeliveryList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(MemberDelivery.COLUMN_MEMBER_DELIVERY_ID, memberDelivery.getMember_delivery_id());
			map.put(MemberDelivery.COLUMN_MEMBER_DELIVERY_NAME, memberDelivery.getMember_delivery_name());
			map.put(MemberDelivery.COLUMN_MEMBER_DELIVERY_PHONE, memberDelivery.getMember_delivery_phone());
			map.put(MemberDelivery.COLUMN_MEMBER_DELIVERY_PROVINCE, memberDelivery.getMember_delivery_province());
			map.put(MemberDelivery.COLUMN_MEMBER_DELIVERY_CITY, memberDelivery.getMember_delivery_city());
			map.put(MemberDelivery.COLUMN_MEMBER_DELIVERY_AREA, memberDelivery.getMember_delivery_area());
			map.put(MemberDelivery.COLUMN_MEMBER_DELIVERY_ADDRESS, memberDelivery.getMember_delivery_address());

			resultList.add(map);
		}

		return resultList;
	}

}
