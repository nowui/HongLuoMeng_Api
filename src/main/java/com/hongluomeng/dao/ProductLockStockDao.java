package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.ProductLockStock;

public class ProductLockStockDao {

	private Integer count(ProductLockStock productLockStock) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ");
		myDynamicSQL.append("" + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + "." + ProductLockStock.KEY_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		ProductLockStock productLockStock = new ProductLockStock();

		return count(productLockStock);
	}

	private List<ProductLockStock> list(ProductLockStock productLockStock, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ");
		myDynamicSQL.append("WHERE " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + "." + ProductLockStock.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK_EXPIRE_TIME + " > ? ", new Date());

		if(! Utility.isNullOrEmpty(productLockStock.getProductSkuIdList())) {
			myDynamicSQL.append("AND (");
			for(int i = 0; i < productLockStock.getProductSkuIdList().size(); i++) {
				String product_sku_id = productLockStock.getProductSkuIdList().get(i);

				if(i > 0) {
					myDynamicSQL.append("OR ");
				}

				myDynamicSQL.append(ProductLockStock.KEY_PRODUCT_SKU_ID + " = ? ", product_sku_id);
			}
			myDynamicSQL.append(") ");
		}

		myDynamicSQL.append("GROUP BY " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + "." + ProductLockStock.KEY_PRODUCT_SKU_ID + " ");
		myDynamicSQL.appendPagination(m, n);

		List<ProductLockStock> productLockStockList = new ProductLockStock().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
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
		sql.append(ProductLockStock.KEY_SYSTEM_CREATE_USER_ID + ", ");
		sql.append(ProductLockStock.KEY_SYSTEM_CREATE_TIME + ", ");
		sql.append(ProductLockStock.KEY_SYSTEM_UPDATE_USER_ID + ", ");
		sql.append(ProductLockStock.KEY_SYSTEM_UPDATE_TIME + ", ");
		sql.append(ProductLockStock.KEY_PRODUCT_LOCK_STOCK_EXPIRE_TIME + ", ");
		sql.append(ProductLockStock.KEY_SYSTEM_STATUS + " ");
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
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ");
		myDynamicSQL.append("SET " + ProductLockStock.KEY_SYSTEM_STATUS + " = 0, ");
		myDynamicSQL.append(ProductLockStock.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(ProductLockStock.KEY_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + ProductLockStock.KEY_ORDER_NO + " = ? ", order_no);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
