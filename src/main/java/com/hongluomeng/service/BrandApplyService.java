package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.cache.BrandApplyCache;
import com.hongluomeng.dao.BrandApplyDao;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.type.BrandApplyReviewEnum;

public class BrandApplyService extends BaseService {

	private BrandApplyDao brandApplyDao = new BrandApplyDao();
	private BrandApplyCache brandApplyCache = new BrandApplyCache();

	public Integer countByCategory_idAndUser_id(String category_id, String user_id) {
		return brandApplyDao.countByCategory_idAndUser_id(category_id, user_id);
	}

	public List<BrandApply> list(Integer m, Integer n) {
		return brandApplyDao.listByCategory_idAndUser_idAndBrand_apply_review_status("", "", "", m, n);
	}

	public List<BrandApply> listByCategory_idAndUser_id(String category_id, String user_id, Integer m, Integer n) {
		return brandApplyDao.listByCategory_idAndUser_idAndBrand_apply_review_status(category_id, user_id, BrandApplyReviewEnum.PASS.getKey(), m, n);
	}

	public List<BrandApply> listByUser_id(String user_id) {
		List<BrandApply> brandApplyList = brandApplyCache.getBrandApplyListByUser_id(user_id);

		if (brandApplyList == null) {
			brandApplyList = brandApplyDao.listByCategory_idAndUser_idAndBrand_apply_review_status("", user_id, "", 0, 0);

			brandApplyCache.setBrandApplyListByUser_id(brandApplyList, user_id);
		}

		return brandApplyList;
	}

	public BrandApply findByBrand_idAndUser_id(String brand_id, String user_id) {
		return brandApplyDao.findByBrand_idAndUser_id(brand_id, user_id);
	}

	public void save(String brand_id, String member_real_name, String member_identity_card, String member_identity_card_front_image, String member_identity_card_back_image, String request_user_id) {
        brandApplyCache.removeBrandApplyListByUser_id(request_user_id);

        brandApplyDao.save(brand_id, member_real_name, member_identity_card, member_identity_card_front_image, member_identity_card_back_image, request_user_id);
	}

	public void update(String brand_id, String member_real_name, String member_identity_card, String member_identity_card_front_image, String member_identity_card_back_image, String request_user_id) {
        brandApplyCache.removeBrandApplyListByUser_id(request_user_id);

        brandApplyDao.update(brand_id, member_real_name, member_identity_card, member_identity_card_front_image, member_identity_card_back_image, request_user_id);
	}

	public void reviewPass(String brand_id, String user_id, String request_user_id) {
		brandApplyCache.removeBrandApplyListByUser_id(user_id);

		brandApplyDao.updateStatus(brand_id, BrandApplyReviewEnum.PASS.getKey(), user_id, request_user_id);
	}

	public void reviewRefuse(String brand_id, String user_id, String request_user_id) {
        brandApplyCache.removeBrandApplyListByUser_id(user_id);

        brandApplyDao.updateStatus(brand_id, BrandApplyReviewEnum.REFUSE.getKey(), user_id, request_user_id);
	}

	public void reviewCancel(String brand_id, String user_id, String request_user_id) {
        brandApplyCache.removeBrandApplyListByUser_id(user_id);

        brandApplyDao.updateStatus(brand_id, BrandApplyReviewEnum.CANCEL.getKey(), user_id, request_user_id);
	}

}
