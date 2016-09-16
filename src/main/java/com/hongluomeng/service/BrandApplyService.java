package com.hongluomeng.service;

import com.hongluomeng.dao.BrandApplyDao;

public class BrandApplyService {

	private BrandApplyDao brandApplyDao = new BrandApplyDao();

	public Integer countByBrand_idAndUser_id(String brand_id, String user_id) {
		return brandApplyDao.countByBrand_idAndUser_id(brand_id, user_id);
	}

	public void save(String brand_id, String request_user_id) {
		brandApplyDao.save(brand_id, request_user_id);
	}

}
