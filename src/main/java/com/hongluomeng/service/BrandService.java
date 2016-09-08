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
import com.hongluomeng.model.Category;
import com.hongluomeng.type.CatetoryEnum;

public class BrandService {

	private BrandDao brandDao = new BrandDao();
	private CategoryService categoryService = new CategoryService();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Brand brandMap = jsonObject.toJavaObject(Brand.class);

		Integer count = brandDao.count();

		List<Brand> brandList = brandDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, brandList);

		return resultMap;
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		Brand brandMap = jsonObject.toJavaObject(Brand.class);

		List<Brand> brandList = brandDao.listByCategory_id(brandMap.getCategory_id(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for(Brand brand : brandList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Brand.KEY_BRAND_ID, brand.getBrand_id());
			map.put(Brand.KEY_BRAND_NAME, brand.getBrand_name());
			map.put(Brand.KEY_BRAND_LOGO, brand.getBrand_logo());
			map.put(Brand.KEY_BRAND_IS_SIGN, false);
			list.add(map);
		}

		return list;
	}

	public List<Brand> listByUser_id(String user_id) {
		return brandDao.list(0, 0);
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

}
