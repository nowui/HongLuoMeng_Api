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
import com.hongluomeng.type.CatetoryEnum;

public class BrandService {

	private BrandDao brandDao = new BrandDao();
	private BrandApplyService brandApplyService = new BrandApplyService();
	private CategoryService categoryService = new CategoryService();
	private MemberService memberService = new MemberService();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Brand brandMap = jsonObject.toJavaObject(Brand.class);

		Integer count = brandDao.count();

		List<Brand> brandList = brandDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, brandList);

		return resultMap;
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		List<Brand> brandList = brandDao.listByCategory_idForApply(brandMap.getCategory_id(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for(Brand brand : brandList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Brand.KEY_BRAND_ID, brand.getBrand_id());
			map.put(Brand.KEY_BRAND_NAME, brand.getBrand_name());
			map.put(Brand.KEY_BRAND_LOGO, brand.getBrand_logo());

			if(brand.getBrand_apply_count() == 0) {
				map.put(Brand.KEY_BRAND_IS_APPLY, false);
			} else {
				map.put(Brand.KEY_BRAND_IS_APPLY, true);
			}

			if(brand.getBrand_review_count() == 0) {
				map.put(Brand.KEY_BRAND_IS_REVIEW, false);
			} else {
				map.put(Brand.KEY_BRAND_IS_REVIEW, true);
			}

			list.add(map);
		}

		return list;
	}

	public List<Brand> listByUser_id(String user_id) {
		return brandDao.list(0, 0);
	}

	public List<Map<String, Object>> getMyList(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Brand> brandList = brandDao.listByCategory_idForMy(brandMap.getCategory_id(), request_user_id, Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for(Brand brand : brandList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Brand.KEY_BRAND_ID, brand.getBrand_id());
			map.put(Brand.KEY_BRAND_NAME, brand.getBrand_name());
			map.put(Brand.KEY_BRAND_LOGO, brand.getBrand_logo());
			map.put(Brand.KEY_BRAND_IS_APPLY, true);

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

	public Brand get(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		Brand brand = brandDao.findByBrand_idForApply(brandMap.getBrand_id());

		if(brand.getBrand_apply_count() == 0) {
			brand.setBrand_is_apply(false);
		} else {
			brand.setBrand_is_apply(true);
		}

		if(brand.getBrand_review_count() == 0) {
			brand.setBrand_is_review(false);
		} else {
			brand.setBrand_is_review(true);
		}

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

	public void apply(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Integer count = brandApplyService.countByBrand_idAndUser_id(brandMap.getBrand_id(), request_user_id);

		if(count == 0) {
			Member memberMap = jsonObject.toJavaObject(Member.class);

			memberService.updateInfo(memberMap.getMember_real_name(), memberMap.getMember_identity_card(), memberMap.getMember_identity_card_front_image(), memberMap.getMember_identity_card_back_image(), request_user_id);

			brandApplyService.save(brandMap.getBrand_id(), memberMap.getMember_real_name(), memberMap.getMember_identity_card(), memberMap.getMember_identity_card_front_image(), memberMap.getMember_identity_card_back_image(), request_user_id);
		} else {
			throw new RuntimeException("这品牌已经申请过,不能再申请!");
		}
	}

	public List<Map<String, Object>> getCategoryList(JSONObject jsonObject) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		List<Category> categoryList = categoryService.listByCategory_key(CatetoryEnum.BRAND.getKey());

		for(Category category : categoryList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Category.KEY_CATEGORY_ID, category.getCategory_id());
			map.put(Category.KEY_CATEGORY_NAME, category.getCategory_name());
			list.add(map);
		}

		return list;
	}

	public Map<String, Object> listCategory(JSONObject jsonObject) {
		return categoryService.treeByCategory_key(CatetoryEnum.BRAND.getKey());
	}

	public Category findCategory(JSONObject jsonObject) {
		return categoryService.find(jsonObject);
	}

	public void saveCategory(JSONObject jsonObject) {
		categoryService.save(jsonObject);
	}

	public void updateCategory(JSONObject jsonObject) {
		categoryService.update(jsonObject);
	}

	public void deleteCategory(JSONObject jsonObject) {
		categoryService.delete(jsonObject);
	}

	public Map<String, Object> listApply(JSONObject jsonObject) {
		Integer count = brandApplyService.count();

		List<BrandApply> brandApplyList = brandApplyService.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, brandApplyList);

		return resultMap;
	}

	public BrandApply findApply(JSONObject jsonObject) {
		BrandApply brandApplyMap = jsonObject.toJavaObject(BrandApply.class);

		BrandApply brandApply = brandApplyService.findByBrand_id(brandApplyMap.getBrand_id(), brandApplyMap.getUser_id());

		return brandApply;
	}

	public void reviewApply(JSONObject jsonObject) {
		BrandApply brandApplyMap = jsonObject.toJavaObject(BrandApply.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		brandApplyService.review(brandApplyMap.getBrand_id(), brandApplyMap.getUser_id(), request_user_id);
	}

}
