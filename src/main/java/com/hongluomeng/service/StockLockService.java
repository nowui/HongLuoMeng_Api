package com.hongluomeng.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.dao.StockLockDao;
import com.hongluomeng.model.StockLock;

public class StockLockService {

	private StockLockDao stockLockDao = new StockLockDao();

	public List<StockLock> listByProductSkuIdList(List<String> productSkuIdList) {
		List<StockLock> stockLockList = stockLockDao.listByProductSkuIdList(productSkuIdList);

		return stockLockList;
	}

	public void save(JSONObject jsonObject) {
		StockLock stockLockMap = jsonObject.toJavaObject(StockLock.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		stockLockDao.save(stockLockMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		StockLock stockLockMap = jsonObject.toJavaObject(StockLock.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		stockLockDao.delete(stockLockMap.getOrder_id(), request_user_id);
	}

}
