package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.ProductLockStock;

public class ProductLockStockDao {

	private Integer count(ProductLockStock productLockStock) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ");
		dynamicSQL.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_STATUS + " = 1 ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		ProductLockStock productLockStock = new ProductLockStock();

		return count(productLockStock);
	}

	private List<ProductLockStock> list(ProductLockStock productLockStock, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ");
		dynamicSQL.append("WHERE " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK_STATUS + " = 1 ");
		dynamicSQL.append("AND " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK_EXPIRE_TIME + " > ? ", new Date());

		if(! Utility.isNullOrEmpty(productLockStock.getProductSkuIdList())) {
			dynamicSQL.append("AND (");
			for(int i = 0; i < productLockStock.getProductSkuIdList().size(); i++) {
				String product_sku_id = productLockStock.getProductSkuIdList().get(i);

				if(i > 0) {
					dynamicSQL.append("OR ");
				}

				dynamicSQL.append(ProductLockStock.KEY_PRODUCT_SKU_ID + " = ? ", product_sku_id);
			}
			dynamicSQL.append(") ");
		}

		dynamicSQL.append("GROUP BY " + ProductLockStock.KEY_PRODUCT_SKU_ID + " ");
		dynamicSQL.appendPagination(m, n);

		List<ProductLockStock> productLockStockList = new ProductLockStock().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if (productLockStockList == null) {
			return null;
		} else {
			return productLockStockList;
		}
	}

	public List<ProductLockStock> listByProductSkuIdList(List<String> productSkuIdList) {
		ProductLockStock productLockStock = new ProductLockStock();
		productLockStock.setProductSkuIdList(productSkuIdList);

		return list(productLockStock, 0, 0);
	}

	public void save(List<ProductLockStock> productLockStockList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ( ");
		sql.append(ProductLockStock.KEY_ORDER_NO + ", ");
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

			objectList.add(productLockStock.getOrder_no());
			objectList.add(productLockStock.getProduct_sku_id());
			objectList.add(productLockStock.getProduct_lock_stock());
			objectList.add(request_user_id);
			objectList.add(new Date());
			objectList.add(request_user_id);
			objectList.add(new Date());

			Calendar  calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, Const.ORDER_TIMEOUT_EXPRESS);
			objectList.add(calendar.getTime());

			objectList.add(true);

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

	public void delete(String order_no, String request_user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ");
		dynamicSQL.append("SET " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK_STATUS + " = 0, ");
		dynamicSQL.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_UPDATE_USER_ID + " = ?, ", request_user_id);
		dynamicSQL.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + ProductLockStock.KEY_ORDER_NO + " = ? ", order_no);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

}
