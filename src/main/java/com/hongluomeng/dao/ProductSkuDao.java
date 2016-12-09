package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.Product;
import com.hongluomeng.model.ProductLockStock;
import com.hongluomeng.model.ProductSku;

public class ProductSkuDao extends BaseDao {

	private Integer count(ProductSku productSku) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + ProductSku.TABLE_PRODUCT_SKU + " ");
		myDynamicSQL.append("WHERE " + ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		ProductSku productSku = new ProductSku();

		return count(productSku);
	}

	private List<ProductSku> list(ProductSku productSku, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + ProductSku.TABLE_PRODUCT_SKU + " ");
		myDynamicSQL.append("WHERE " + ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + ProductSku.COLUMN_PRODUCT_ID + " = ? ", productSku.getProduct_id());
//		if (! Utility.isNullOrEmpty(productSku.getProductSkuIdList())) {
//			for(String product_sku_id : productSku.getProductSkuIdList()) {
//				myDynamicSQL.append("AND " + ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_SKU_ID + " = ? ", product_sku_id);
//			}
//		}
		myDynamicSQL.append("ORDER BY " + ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		return new ProductSku().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<ProductSku> listByProduct_id(String product_id) {
		ProductSku productSku = new ProductSku();
		productSku.setProduct_id(product_id);

		return list(productSku, 0, 0);
	}

	public List<ProductSku> listByProductSkuIdList(List<String> productSkuIdList) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT " + ProductSku.TABLE_PRODUCT_SKU + ".*, ");
		myDynamicSQL.append(Category.TABLE_CATEGORY + "." + Category.COLUMN_CATEGORY_ID + ", ");
		myDynamicSQL.append(Category.TABLE_CATEGORY + "." + Category.COLUMN_CATEGORY_NAME + ", ");
		myDynamicSQL.append(Brand.TABLE_BRAND + "." + Brand.COLUMN_BRAND_ID + ", ");
		myDynamicSQL.append(Brand.TABLE_BRAND + "." + Brand.COLUMN_BRAND_NAME + ", ");
		myDynamicSQL.append(Product.KEY_TABLE_PRODUCT + ".*, ");
		myDynamicSQL.append(ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_ATTRIBUTE_VALUE + ", ");
		myDynamicSQL.append(ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_PRICE + ", ");
		myDynamicSQL.append(ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_MEMBER_LEVEL_PRICE + ", ");
		myDynamicSQL.append(ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_STOCK + ", ");
		myDynamicSQL.append(ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_SYSTEM_STATUS + ", ");
		myDynamicSQL.append("IFNULL(" + ProductLockStock.TABLE_PRODUCT_LOCK_STOCK + "." + ProductLockStock.COLUMN_PRODUCT_LOCK_STOCK + ", 0) AS " + ProductLockStock.COLUMN_PRODUCT_LOCK_STOCK + " ");
		myDynamicSQL.append("FROM " + ProductSku.TABLE_PRODUCT_SKU + " ");
		myDynamicSQL.append("LEFT JOIN " + Product.KEY_TABLE_PRODUCT + " ON " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_PRODUCT_ID + " = " + ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_ID + " ");
		myDynamicSQL.append("LEFT JOIN " + Category.TABLE_CATEGORY + " ON " + Category.TABLE_CATEGORY + "." + Category.COLUMN_CATEGORY_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_CATEGORY_ID + " ");
		myDynamicSQL.append("LEFT JOIN " + Brand.TABLE_BRAND + " ON " + Brand.TABLE_BRAND + "." + Brand.COLUMN_BRAND_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_BRAND_ID + " ");
		myDynamicSQL.append("LEFT JOIN (SELECT " + ProductLockStock.COLUMN_PRODUCT_SKU_ID + ", SUM(" + ProductLockStock.COLUMN_PRODUCT_LOCK_STOCK + ") AS " + ProductLockStock.COLUMN_PRODUCT_LOCK_STOCK + " FROM " + ProductLockStock.TABLE_PRODUCT_LOCK_STOCK + " WHERE " + ProductLockStock.COLUMN_SYSTEM_STATUS + " = 1 AND " + ProductLockStock.COLUMN_PRODUCT_LOCK_STOCK_EXPIRE_TIME + " > ? GROUP BY " + ProductLockStock.COLUMN_PRODUCT_SKU_ID + ") AS " + ProductLockStock.TABLE_PRODUCT_LOCK_STOCK + " ON " + ProductLockStock.TABLE_PRODUCT_LOCK_STOCK + "." + ProductLockStock.COLUMN_PRODUCT_SKU_ID + " = " + ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_SKU_ID + " ", new Date());
		myDynamicSQL.append("WHERE " + ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_SYSTEM_STATUS + " = 1 ");
		if (productSkuIdList.size() > 0) {
			for(int i = 0; i < productSkuIdList.size(); i++) {
				String product_sku_id = productSkuIdList.get(i);

				if(i == 0) {
					myDynamicSQL.append("AND (");
				} else {
					myDynamicSQL.append("OR ");
				}

				myDynamicSQL.append(ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_PRODUCT_SKU_ID + " = ? ", product_sku_id);
			}
			myDynamicSQL.append(") ");
		}

		return new ProductSku().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	private ProductSku find(ProductSku productSku) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + ProductSku.TABLE_PRODUCT_SKU + " ");
		myDynamicSQL.append("WHERE " + ProductSku.TABLE_PRODUCT_SKU + "." + ProductSku.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + ProductSku.COLUMN_PRODUCT_SKU_ID + " = ? ", productSku.getProduct_sku_id());

		List<ProductSku> productSkuList = new ProductSku().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if(productSkuList.size() == 0) {
			return null;
		} else {
			return productSkuList.get(0);
		}
	}

	public ProductSku findByProduct_sku_id(String product_sku_id) {
		ProductSku productSku = new ProductSku();
		productSku.setProduct_sku_id(product_sku_id);

		productSku.checkProduct_sku_id();

		return find(productSku);
	}

	public void save(String product_id, List<ProductSku> productSkuList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + ProductSku.TABLE_PRODUCT_SKU + " ( ");
		sql.append(ProductSku.COLUMN_PRODUCT_SKU_ID + ", ");
		sql.append(ProductSku.COLUMN_PRODUCT_ID + ", ");
		sql.append(ProductSku.COLUMN_PRODUCT_ATTRIBUTE_VALUE + ", ");
		sql.append(ProductSku.COLUMN_PRODUCT_PRICE + ", ");
		sql.append(ProductSku.COLUMN_MEMBER_LEVEL_PRICE + ", ");
		sql.append(ProductSku.COLUMN_PRODUCT_STOCK + ", ");
		sql.append(ProductSku.COLUMN_SYSTEM_CREATE_USER_ID + ", ");
		sql.append(ProductSku.COLUMN_SYSTEM_CREATE_TIME + ", ");
		sql.append(ProductSku.COLUMN_SYSTEM_UPDATE_USER_ID + ", ");
		sql.append(ProductSku.COLUMN_SYSTEM_UPDATE_TIME + ", ");
		sql.append(ProductSku.COLUMN_SYSTEM_STATUS + " ");
		sql.append(") VALUES ( ");
		sql.append("?, ");
		sql.append("?, ");
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

		for(ProductSku productSku : productSkuList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(Utility.getUUID());
			objectList.add(product_id);
			objectList.add(productSku.getProduct_attribute_value().toJSONString());
			objectList.add(productSku.getProduct_price());
			objectList.add(productSku.getMember_level_price().toJSONString());
			objectList.add(productSku.getProduct_stock());
			objectList.add(request_user_id);
			objectList.add(new Date());
			objectList.add(request_user_id);
			objectList.add(new Date());
			objectList.add(true);

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

	public void update(List<ProductSku> productSkuList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("UPDATE " + ProductSku.TABLE_PRODUCT_SKU + " ");
		sql.append("SET " + ProductSku.COLUMN_PRODUCT_STOCK + " = ?, ");
		sql.append(ProductSku.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ");
		sql.append(ProductSku.COLUMN_SYSTEM_UPDATE_TIME + " = ? ");
		sql.append("WHERE " + ProductSku.COLUMN_PRODUCT_ID + " = ? ");
		sql.append("AND " + ProductSku.COLUMN_PRODUCT_ATTRIBUTE_VALUE + " = ? ");
		sql.append("AND " + ProductSku.COLUMN_PRODUCT_PRICE + " = ? ");
		sql.append("AND " + ProductSku.COLUMN_SYSTEM_STATUS + " = 1 ");

		for(ProductSku productSku : productSkuList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(productSku.getProduct_stock());
			objectList.add(request_user_id);
			objectList.add(new Date());
			objectList.add(productSku.getProduct_id());
			objectList.add(productSku.getProduct_attribute_value().toJSONString());
			objectList.add(productSku.getProduct_price());

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

	public void delete(List<String> productSkuIdList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("UPDATE " + ProductSku.TABLE_PRODUCT_SKU + " ");
		sql.append("SET " + ProductSku.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ");
		sql.append(ProductSku.COLUMN_SYSTEM_UPDATE_TIME + " = ?, ");
		sql.append(ProductSku.COLUMN_SYSTEM_STATUS + " = 0 ");
		sql.append("WHERE " + ProductSku.COLUMN_PRODUCT_SKU_ID + " = ? ");

		for(String product_sku_id : productSkuIdList) {
			List<Object> objectList = new ArrayList<Object>();

			objectList.add(request_user_id);
			objectList.add(new Date());
			objectList.add(product_sku_id);

			parameterList.add(objectList.toArray());
		}

		Db.batch(sql.toString(), Utility.getObjectArray(parameterList), Const.BATCH_SIZE);
	}

}
