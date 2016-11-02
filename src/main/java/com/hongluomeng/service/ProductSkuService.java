package com.hongluomeng.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.dao.ProductSkuDao;
import com.hongluomeng.model.ProductSku;

public class ProductSkuService {

	private ProductSkuDao memberLevelDao = new ProductSkuDao();

	public List<ProductSku> listByProduct_id(String product_id) {
		List<ProductSku> productSkuList = memberLevelDao.listByProduct_id(product_id);

		for(ProductSku productSku : productSkuList) {
			productSku.setProduct_id(null);
			productSku.setProduct_sku_status(null);
		}

		return productSkuList;
	}

	public ProductSku find(JSONObject jsonObject) {
		ProductSku productSkuMap = jsonObject.toJavaObject(ProductSku.class);

		ProductSku productSku = memberLevelDao.findByProduct_sku_id(productSkuMap.getProduct_sku_id());

		return productSku;
	}

	public void save(ProductSku productSku, String request_user_id) {
		memberLevelDao.save(productSku, request_user_id);
	}

	/*public void update(JSONObject jsonObject) {
		ProductSku productSkuMap = jsonObject.toJavaObject(ProductSku.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		memberLevelDao.update(productSkuMap, request_user_id);
	}*/

	public void delete(String product_sku_id, String request_user_id) {
		memberLevelDao.delete(product_sku_id, request_user_id);
	}

}
