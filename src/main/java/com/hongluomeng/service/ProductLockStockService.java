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

	public void delete(String order_no, String request_user_id) {
		productLockStockDao.delete(order_no, request_user_id);
	}

}
