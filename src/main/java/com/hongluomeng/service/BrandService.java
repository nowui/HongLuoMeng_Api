package com.hongluomeng.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.BrandDao;
import com.hongluomeng.model.Brand;

public class BrandService {

	private BrandDao brandDao = new BrandDao();

	public Integer count(JSONObject jsonObject) {
		//Brand brandMap = jsonObject.toJavaObject(Brand.class);

		return brandDao.count();
	}

	public List<Brand> list(JSONObject jsonObject) {
		//Brand brandMap = jsonObject.toJavaObject(Brand.class);

		return brandDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
	}

	public List<Brand> listByUser_id(String user_id) {
		return brandDao.list(0, 0);
	}

	public Brand find(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		Brand brand = brandDao.findByBrand_id(brandMap.getBrand_id());

		return brand;
	}

	public void save(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		brandDao.save(brandMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		brandDao.update(brandMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		brandDao.delete(brandMap.getBrand_id(), request_user_id);
	}

}
