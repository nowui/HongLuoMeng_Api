package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.ProductLockStock;

public class ProductLockStockDao {

	private Integer count(ProductLockStock productLockStock) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		ProductLockStock productLockStock = new ProductLockStock();

		return count(productLockStock);
	}

	private List<ProductLockStock> list(ProductLockStock productLockStock, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ");

		Boolean isExit = false;

		if(! Utility.isNullOrEmpty(productLockStock.getProductSkuIdList().size())) {
			for(String product_sku_id : productLockStock.getProductSkuIdList()) {
				if(isExit) {
					sql.append("AND ");
				} else {
					sql.append("WHERE ");
				}

				sql.append(ProductLockStock.KEY_PRODUCT_SKU_ID + " = ? ");
				parameterList.add(product_sku_id);

				isExit = true;
			}
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_STATUS + " = 1 ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<ProductLockStock> productLockStockList = productLockStock.find(sql.toString(), parameterList.toArray());
		return productLockStockList;
	}

	public List<ProductLockStock> listByProductSkuIdList(List<String> productSkuIdList) {
		ProductLockStock productLockStock = new ProductLockStock();
		productLockStock.setProductSkuIdList(productSkuIdList);

		return list(productLockStock, 0, 0);
	}

	public void save(List<ProductLockStock> productLockStockList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ( ");
		sql.append(ProductLockStock.KEY_ORDER_ID + ", ");
		sql.append(ProductLockStock.KEY_PRODUCT_SKU_ID + ", ");
		sql.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK + ", ");
		sql.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_CREATE_USER_ID + ", ");
		sql.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_CREATE_TIME + ", ");
		sql.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_UPDATE_USER_ID + ", ");
		sql.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_UPDATE_TIME + ", ");
		sql.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_EXPIRE_TIME + ", ");
		sql.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_STATUS + " ");
		sql.append(") VALUES ( ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("?, ");
		sql.append("? ");
		sql.append(") ");

		for(ProductLockStock productLockStock : productLockStockList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(productLockStock.getOrder_id());
			objectList.add(productLockStock.getProduct_sku_id());
			objectList.add(productLockStock.getProduct_lock_stock());
			objectList.add(request_user_id);
			objectList.add(new Date());
			objectList.add(request_user_id);
			objectList.add(new Date());

			Calendar  calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, 10);
			objectList.add(calendar.getTime());

			objectList.add(true);

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

	public void delete(String order_id, String request_user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " SET " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK_STATUS + " = 0, " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK_UPDATE_USER_ID + " = ?, " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK_UPDATE_TIME + " = ? WHERE " + ProductLockStock.KEY_ORDER_ID + " = ? ");

		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(order_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

}
