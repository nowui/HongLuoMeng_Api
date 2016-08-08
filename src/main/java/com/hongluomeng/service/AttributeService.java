package com.hongluomeng.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.AttributeDao;
import com.hongluomeng.model.Attribute;

public class AttributeService {

	private AttributeDao attributeDao = new AttributeDao();

	public Integer count(JSONObject jsonObject) {
		//Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		return attributeDao.count();
	}

	public List<Attribute> list(JSONObject jsonObject) {
		//Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		return attributeDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
	}

	public List<Attribute> listByCategory_id(String category_id) {
		return attributeDao.listByCategory_id(category_id);
	}

	public Attribute find(JSONObject jsonObject) {
		Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		Attribute attribute = attributeDao.findByAttribute_id(attributeMap.getAttribute_id());

		return attribute;
	}

	public void save(JSONObject jsonObject) {
		Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		attributeDao.save(attributeMap, jsonObject.getString(Const.KEY_REQUEST_USER_ID));
	}

	public void update(JSONObject jsonObject) {
		Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		attributeDao.update(attributeMap, jsonObject.getString(Const.KEY_REQUEST_USER_ID));
	}

	public void delete(JSONObject jsonObject) {
		Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		attributeDao.delete(attributeMap);
	}

}
