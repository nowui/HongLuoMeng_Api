package com.hongluomeng.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.AttributeDao;
import com.hongluomeng.model.Attribute;

public class AttributeService {

	private AttributeDao attributeDao = new AttributeDao();
	private CategoryAttributeService categoryAttributeService = new CategoryAttributeService();
	private ProductAttributeService productAttributeService = new ProductAttributeService();

	public Integer count(JSONObject jsonObject) {
		//Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		return attributeDao.count();
	}

	public List<Attribute> list(JSONObject jsonObject) {
		//Attribute attributeMap = jsonObject.toJavaObject(Attribute.class);

		return attributeDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
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

		productAttributeService.deleteByAttribute_id(attributeMap.getAttribute_id());
	}

}
