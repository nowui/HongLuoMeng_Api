package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.BrandDao;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.Member;
import com.hongluomeng.type.BrandApplyReviewEnum;
import com.hongluomeng.type.CatetoryEnum;

public class BrandService extends BaseService {

	private BrandDao brandDao = new BrandDao();

	private BrandApplyService brandApplyService = new BrandApplyService();
	private CategoryService categoryService = new CategoryService();
	private MemberService memberService = new MemberService();

	public Map<String, Object> list(JSONObject jsonObject) {
		String category_id = "";

		Integer count = brandDao.countByCategory_id(category_id);

		List<Brand> brandList = brandDao.listByCategory_id(category_id, Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Brand brand : brandList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Brand.COLUMN_BRAND_ID, brand.getBrand_id());
			map.put(Brand.COLUMN_BRAND_NAME, brand.getBrand_name());
			map.put(Category.COLUMN_CATEGORY_NAME, brand.getCategory().getCategory_name());

			list.add(map);
		}

		return Utility.setResultMap(count, list);
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Brand> brandList = brandDao.listByCategory_id(brandMap.getCategory_id(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<BrandApply> brandApplyList = brandApplyService.listByUser_id(request_user_id);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Brand brand : brandList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Brand.COLUMN_BRAND_ID, brand.getBrand_id());
			map.put(Brand.COLUMN_BRAND_NAME, brand.getBrand_name());
			map.put(Brand.COLUMN_BRAND_LOGO, brand.getBrand_logo());
			map.put(BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS, BrandApplyReviewEnum.NONE.getKey());

			for(BrandApply brandApply : brandApplyList) {
				if(brandApply.getBrand_id().equals(brand.getBrand_id())) {
					map.put(BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS, brandApply.getBrand_apply_review_status());

					break;
				}
			}

			list.add(map);
		}

		return list;
	}

	public List<Brand> listAll() {
		Brand brand = new Brand();

		return brandDao.listByCategory_id("", 0, 0);
	}

	public List<Map<String, Object>> getMyList(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<BrandApply> brandApplyList = brandApplyService.listByCategory_idAndUser_id(brandMap.getCategory_id(), request_user_id, Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (BrandApply brandApply : brandApplyList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Brand.COLUMN_BRAND_ID, brandApply.getBrand_id());
			map.put(Brand.COLUMN_BRAND_NAME, brandApply.getBrand().getBrand_name());
			map.put(Brand.COLUMN_BRAND_LOGO, brandApply.getBrand().getBrand_logo());
			map.put(Brand.COLUMN_BRAND_APPLY_CREATE_TIME, brandApply.getSystem_create_time());
			map.put(Brand.COLUMN_BRAND_APPLY_EXPIRE_TIME, brandApply.getSystem_create_time());

			list.add(map);
		}

		return list;
	}

	public Brand find(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		Brand brand;

		List<Category> categoryList = categoryService.listByCategory_key(CatetoryEnum.BRAND.getKey());

		if (Utility.isNullOrEmpty(brandMap.getBrand_id())) {
			brand = new Brand();
		} else {
			brand = brandDao.findByBrand_id(brandMap.getBrand_id());
		}

		brand.setCategoryList(categoryList);

		return brand;
	}

    public Brand findByBrand_id(String brand_id) {
        return brandDao.findByBrand_id(brand_id);
    }

	public Map<String, Object> get(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Brand brand = brandDao.findByBrand_id(brandMap.getBrand_id());

		List<BrandApply> brandApplyList = brandApplyService.listByUser_id(request_user_id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Brand.COLUMN_BRAND_ID, brand.getBrand_id());
		map.put(Brand.COLUMN_BRAND_NAME, brand.getBrand_name());
		map.put(Brand.COLUMN_BRAND_LOGO, brand.getBrand_logo());
		map.put(Brand.COLUMN_BRAND_BACKGROUND, brand.getBrand_background());
		map.put(Brand.COLUMN_BRAND_INTRODUCE, brand.getBrand_introduce());
		map.put(Brand.COLUMN_BRAND_AGREEMENT, brand.getBrand_agreement());
		map.put(BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS, BrandApplyReviewEnum.NONE.getKey());

		for(BrandApply brandApply : brandApplyList) {
			if(brandApply.getBrand_id().equals(brand.getBrand_id())) {
				map.put(BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS, brandApply.getBrand_apply_review_status());

				break;
			}
		}

		return map;
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

	public Map<String, Object> listApply(JSONObject jsonObject) {
		Integer count = brandApplyService.countByCategory_idAndUser_id("", "");

		List<BrandApply> brandApplyList = brandApplyService.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (BrandApply brandApply : brandApplyList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(BrandApply.COLUMN_BRAND_ID, brandApply.getBrand_id());
			map.put(Brand.COLUMN_BRAND_NAME, brandApply.getBrand().getBrand_name());
			map.put(BrandApply.COLUMN_USER_ID, brandApply.getUser_id());
			map.put(BrandApply.COLUMN_MEMBER_REAL_NAME, brandApply.getMember_real_name());
			map.put(Member.COLUMN_MEMBER_NAME, brandApply.getMember().getMember_name());
			map.put(BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS, brandApply.getBrand_apply_review_status_value());
			map.put(BrandApply.COLUMN_SYSTEM_CREATE_TIME, brandApply.getSystem_create_time());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public BrandApply findApply(JSONObject jsonObject) {
		BrandApply brandApplyMap = jsonObject.toJavaObject(BrandApply.class);

		BrandApply brandApply = brandApplyService.findByBrand_idAndUser_id(brandApplyMap.getBrand_id(), brandApplyMap.getUser_id());
		brandApply.put(Brand.COLUMN_BRAND_NAME, brandApply.getBrand().getBrand_name());

		return brandApply;
	}

	public void apply(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		BrandApply brandApply = brandApplyService.findByBrand_idAndUser_id(brandMap.getBrand_id(), request_user_id);

		if (brandApply == null) {
			Member memberMap = jsonObject.toJavaObject(Member.class);

			memberService.updateInfo(memberMap.getMember_real_name(), memberMap.getMember_identity_card(), memberMap.getMember_identity_card_front_image(), memberMap.getMember_identity_card_back_image(), request_user_id);

			brandApplyService.save(brandMap.getBrand_id(), memberMap.getMember_real_name(), memberMap.getMember_identity_card(), memberMap.getMember_identity_card_front_image(), memberMap.getMember_identity_card_back_image(), request_user_id);
		} else {
			if (brandApply.getBrand_apply_review_status().equals(BrandApplyReviewEnum.WAIT.getKey()) || brandApply.getBrand_apply_review_status().equals(BrandApplyReviewEnum.PASS.getKey())) {
				throw new RuntimeException("这品牌已经申请过,不能再申请!");
			} else {
				Member memberMap = jsonObject.toJavaObject(Member.class);

				memberService.updateInfo(memberMap.getMember_real_name(), memberMap.getMember_identity_card(), memberMap.getMember_identity_card_front_image(), memberMap.getMember_identity_card_back_image(), request_user_id);

				brandApplyService.update(brandMap.getBrand_id(), memberMap.getMember_real_name(), memberMap.getMember_identity_card(), memberMap.getMember_identity_card_front_image(), memberMap.getMember_identity_card_back_image(), request_user_id);
			}
		}
	}

	public void reviewPass(JSONObject jsonObject) {
		BrandApply brandApplyMap = jsonObject.toJavaObject(BrandApply.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		brandApplyService.reviewPass(brandApplyMap.getBrand_id(), brandApplyMap.getUser_id(), request_user_id);
	}

	public void reviewRefuse(JSONObject jsonObject) {
		BrandApply brandApplyMap = jsonObject.toJavaObject(BrandApply.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		brandApplyService.reviewRefuse(brandApplyMap.getBrand_id(), brandApplyMap.getUser_id(), request_user_id);
	}

	public void reviewCancel(JSONObject jsonObject) {
		BrandApply brandApplyMap = jsonObject.toJavaObject(BrandApply.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		brandApplyService.reviewCancel(brandApplyMap.getBrand_id(), request_user_id, request_user_id);
	}

	public Boolean checkIsApply(String brand_id, String request_user_id) {
		Boolean result = false;

		List<BrandApply> brandApplyList = brandApplyService.listByUser_id(request_user_id);

		for (BrandApply brandApply : brandApplyList) {
			if (brandApply.getBrand_id().equals(brand_id) && brandApply.getBrand_apply_review_status().equals(BrandApplyReviewEnum.PASS.getKey())) {
				result = true;

				break;
			}
		}

		return result;
	}


}
