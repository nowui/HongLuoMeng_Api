package com.hongluomeng.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.dao.ProductSkuDao;
import com.hongluomeng.model.ProductSku;

public class ProductSkuService {

	private ProductSkuDao productSkuDao = new ProductSkuDao();

	public List<ProductSku> listByProduct_id(String product_id) {
		List<ProductSku> productSkuList = productSkuDao.listByProduct_id(product_id);

		for(ProductSku productSku : productSkuList) {
			productSku.setProduct_id(null);
			productSku.setProduct_sku_status(null);
		}

		return productSkuList;
	}

	public List<ProductSku> listByProductSkuIdList(List<String> productSkuIdList) {
		return productSkuDao.listByProductSkuIdList(productSkuIdList);
	}

	public ProductSku find(JSONObject jsonObject) {
		ProductSku productSkuMap = jsonObject.toJavaObject(ProductSku.class);

		ProductSku productSku = productSkuDao.findByProduct_sku_id(productSkuMap.getProduct_sku_id());

		return productSku;
	}

	public void save(List<ProductSku> productSkuList, String request_user_id) {
		if(productSkuList.size() > 0) {
			productSkuDao.save(productSkuList, request_user_id);
		}
	}

	public void update(List<ProductSku> productSkuList, String request_user_id) {
		if(productSkuList.size() > 0) {
			productSkuDao.update(productSkuList, request_user_id);
		}
	}

	public void delete(List<String> productSkuIdList, String request_user_id) {
		if(productSkuIdList.size() > 0) {
			productSkuDao.delete(productSkuIdList, request_user_id);
		}
	}

}
