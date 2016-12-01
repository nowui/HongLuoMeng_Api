package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.AttributeDao;
import com.hongluomeng.model.Attribute;

public class AttributeService {

	private AttributeDao attributeDao = new AttributeDao();
	private CategoryAttributeService categoryAttributeService = new CategoryAttributeService();
	private CategoryAttributeValueService categoryAttributeValueService = new CategoryAttributeValueService();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		Integer count = attributeDao.count();

		List<Attribute> attributeList = attributeDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Attribute attribute : attributeList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Attribute.KEY_ATTRIBUTE_ID, attribute.getAttribute_id());
			map.put(Attribute.KEY_ATTRIBUTE_NAME, attribute.getAttribute_name());
			map.put(Attribute.KEY_ATTRIBUTE_TYPE, attribute.getAttribute_type());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public Attribute find(JSONObject jsonObject) {
		Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		Attribute attribute = attributeDao.findByAttribute_id(attributeMap.getAttribute_id());

		return attribute;
	}

	public void save(JSONObject jsonObject) {
		Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		attributeDao.save(attributeMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		attributeDao.update(attributeMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		attributeDao.delete(attributeMap.getAttribute_id(), request_user_id);

		categoryAttributeService.deleteByAttribute_id(attributeMap.getAttribute_id());

		categoryAttributeValueService.deleteByAttribute_id(attributeMap.getAttribute_id());
	}

}
