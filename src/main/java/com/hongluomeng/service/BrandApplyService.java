package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.BrandApplyDao;
import com.hongluomeng.model.BrandApply;

public class BrandApplyService {

	private BrandApplyDao brandApplyDao = new BrandApplyDao();

	public Integer count() {
		return brandApplyDao.count();
	}

	public Integer countByBrand_idAndUser_id(String brand_id, String user_id) {
		return brandApplyDao.countByBrand_idAndUser_id(brand_id, user_id);
	}

	public List<BrandApply> list(Integer m, Integer n) {
		return brandApplyDao.list(m, n);
	}

	public BrandApply findByBrand_id(String brand_id, String user_id){
		return brandApplyDao.findByBrand_id(brand_id, user_id);
	}

	public void save(String brand_id, String member_real_name, String member_identity_card, String member_identity_card_front_image, String member_identity_card_back_image, String request_user_id) {
		brandApplyDao.save(brand_id, member_real_name, member_identity_card, member_identity_card_front_image, member_identity_card_back_image, request_user_id);
	}

	public void review(String brand_id, String user_id, String request_user_id) {
		brandApplyDao.review(brand_id, user_id, request_user_id);
	}

}
