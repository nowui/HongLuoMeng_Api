package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.StockLock;

public class StockLockDao {

	private Integer count(StockLock stockLock) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + StockLock.KEY_TABLE_STOCK_LOCK + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(StockLock.KEY_STOCK_LOCK_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		StockLock stockLock = new StockLock();

		return count(stockLock);
	}

	private List<StockLock> list(StockLock stockLock, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + StockLock.KEY_TABLE_STOCK_LOCK + " ");

		Boolean isExit = false;

		if(! Utility.isNullOrEmpty(stockLock.getProductSkuIdList().size())) {
			for(String product_sku_id : stockLock.getProductSkuIdList()) {
				if(isExit) {
					sql.append("AND ");
				} else {
					sql.append("WHERE ");
				}

				sql.append(StockLock.KEY_PRODUCT_SKU_ID + " = ? ");
				parameterList.add(product_sku_id);

				isExit = true;
			}
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(StockLock.KEY_STOCK_LOCK_STATUS + " = 1 ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<StockLock> stockLockList = stockLock.find(sql.toString(), parameterList.toArray());
		return stockLockList;
	}

	public List<StockLock> listByProductSkuIdList(List<String> productSkuIdList) {
		StockLock stockLock = new StockLock();
		stockLock.setProductSkuIdList(productSkuIdList);

		return list(stockLock, 0, 0);
	}

	public void save(StockLock stockLock, String request_user_id) {
		stockLock.setStock_lock_create_user_id(request_user_id);
		stockLock.setStock_lock_create_time(new Date());
		stockLock.setStock_lock_update_user_id(request_user_id);
		stockLock.setStock_lock_update_time(new Date());

		Calendar  calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 10);
		stockLock.setStock_lock_expire_time(calendar.getTime());

		stockLock.setStock_lock_status(true);

		stockLock.save();
	}

	public void delete(String order_id, String request_user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + StockLock.KEY_TABLE_STOCK_LOCK + " SET " + StockLock.KEY_STOCK_LOCK_STATUS + " = 0, " + StockLock.KEY_STOCK_LOCK_UPDATE_USER_ID + " = ?, " + StockLock.KEY_STOCK_LOCK_UPDATE_TIME + " = ? WHERE " + StockLock.KEY_ORDER_ID + " = ? ");

		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(order_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

}
