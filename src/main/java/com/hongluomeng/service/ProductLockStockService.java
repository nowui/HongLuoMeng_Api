package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.ProductLockStockDao;
import com.hongluomeng.model.ProductLockStock;

public class ProductLockStockService {

	private ProductLockStockDao productLockStockDao = new ProductLockStockDao();

	public List<ProductLockStock> listByProductSkuIdList(List<String> productSkuIdList) {
		List<ProductLockStock> productLockStockList = productLockStockDao.listByProductSkuIdList(productSkuIdList);

		return productLockStockList;
	}

	public void save(List<ProductLockStock> productLockStockList, String request_user_id) {

		productLockStockDao.save(productLockStockList, request_user_id);
	}

	public void delete(String order_id, String request_user_id) {
		productLockStockDao.delete(order_id, request_user_id);
	}

}
