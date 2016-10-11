package com.hongluomeng.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.ProductSkuDao;
import com.hongluomeng.model.ProductSku;

public class ProductSkuService {

	private ProductSkuDao memberLevelDao = new ProductSkuDao();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Product_sku productSkuMap = jsonObject.toJavaObject(Product_sku.class);

		Integer count = memberLevelDao.count();

		List<ProductSku> memberLevelList = memberLevelDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, memberLevelList);

		return resultMap;
	}

	public List<ProductSku> listAll() {
		return memberLevelDao.list(0, 0);
	}

	public ProductSku find(JSONObject jsonObject) {
		ProductSku productSkuMap = jsonObject.toJavaObject(ProductSku.class);

		ProductSku productSku = memberLevelDao.findByProduct_sku_id(productSkuMap.getProduct_sku_id());

		return productSku;
	}

	public void save(JSONObject jsonObject) {
		ProductSku productSkuMap = jsonObject.toJavaObject(ProductSku.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberLevelDao.save(productSkuMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		ProductSku productSkuMap = jsonObject.toJavaObject(ProductSku.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberLevelDao.update(productSkuMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		ProductSku productSkuMap = jsonObject.toJavaObject(ProductSku.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberLevelDao.delete(productSkuMap.getProduct_sku_id(), request_user_id);
	}

}
