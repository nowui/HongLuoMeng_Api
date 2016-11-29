package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.Product;
import com.hongluomeng.model.ProductLockStock;
import com.hongluomeng.model.ProductSku;

public class ProductSkuDao {

	private Integer count(ProductSku productSku) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");
		dynamicSQL.append("WHERE " + ProductSku.KEY_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		ProductSku productSku = new ProductSku();

		return count(productSku);
	}

	private List<ProductSku> list(ProductSku productSku, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");
		dynamicSQL.append("WHERE " + ProductSku.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + ProductSku.KEY_PRODUCT_ID + " = ? ", productSku.getProduct_id());
		if (! Utility.isNullOrEmpty(productSku.getProductSkuIdList())) {
			for(String product_sku_id : productSku.getProductSkuIdList()) {
				dynamicSQL.append("AND " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " = ? ", product_sku_id);
			}
		}
		dynamicSQL.append("ORDER BY " + ProductSku.KEY_SYSTEM_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		return new ProductSku().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<ProductSku> listByProduct_id(String product_id) {
		ProductSku productSku = new ProductSku();
		productSku.setProduct_id(product_id);

		return list(productSku, 0, 0);
	}

	public List<ProductSku> listByProductSkuIdList(List<String> productSkuIdList) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT " + ProductSku.KEY_TABLE_PRODUCT_SKU + ".*, ");
		dynamicSQL.append(Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_ID + ", ");
		dynamicSQL.append(Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_NAME + ", ");
		dynamicSQL.append(Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + ", ");
		dynamicSQL.append(Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_NAME + ", ");
		dynamicSQL.append(Product.KEY_TABLE_PRODUCT + ".*, ");
		dynamicSQL.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_ATTRIBUTE_VALUE + ", ");
		dynamicSQL.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_PRICE + ", ");
		dynamicSQL.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_MEMBER_LEVEL_PRICE + ", ");
		dynamicSQL.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_STOCK + ", ");
		dynamicSQL.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_SYSTEM_STATUS + ", ");
		dynamicSQL.append("IFNULL(" + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + "." + ProductLockStock.KEY_PRODUCT_LOCK_STOCK + ", 0) AS " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK + " ");
		dynamicSQL.append("FROM " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");
		dynamicSQL.append("LEFT JOIN " + Product.KEY_TABLE_PRODUCT + " ON " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_PRODUCT_ID + " = " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_ID + " ");
		dynamicSQL.append("LEFT JOIN " + Category.KEY_TABLE_CATEGORY + " ON " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_CATEGORY_ID + " ");
		dynamicSQL.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_BRAND_ID + " ");
		dynamicSQL.append("LEFT JOIN (SELECT " + ProductLockStock.KEY_PRODUCT_SKU_ID + ", SUM(" + ProductLockStock.KEY_PRODUCT_LOCK_STOCK + ") AS " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK + " FROM " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " WHERE " + ProductLockStock.KEY_SYSTEM_STATUS + " = 1 AND " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK_EXPIRE_TIME + " > ? GROUP BY " + ProductLockStock.KEY_PRODUCT_SKU_ID + ") AS " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ON " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + "." + ProductLockStock.KEY_PRODUCT_SKU_ID + " = " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " ", new Date());
		dynamicSQL.append("WHERE " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_SYSTEM_STATUS + " = 1 ");
		if (productSkuIdList.size() > 0) {
			for(int i = 0; i < productSkuIdList.size(); i++) {
				String product_sku_id = productSkuIdList.get(i);

				if(i == 0) {
					dynamicSQL.append("AND(");
				} else {
					dynamicSQL.append("OR ");
				}

				dynamicSQL.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " = ? ", product_sku_id);
			}
			dynamicSQL.append(") ");
		}

		return new ProductSku().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	private ProductSku find(ProductSku productSku) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");
		dynamicSQL.append("WHERE " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_SYSTEM_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + ProductSku.KEY_PRODUCT_SKU_ID + " = ? ", productSku.getProduct_sku_id());

		List<ProductSku> productSkuList = new ProductSku().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if(productSkuList == null) {
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

		StringBuffer sql = new StringBuffer("INSERT INTO " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ( ");
		sql.append(ProductSku.KEY_PRODUCT_SKU_ID + ", ");
		sql.append(ProductSku.KEY_PRODUCT_ID + ", ");
		sql.append(ProductSku.KEY_PRODUCT_ATTRIBUTE_VALUE + ", ");
		sql.append(ProductSku.KEY_PRODUCT_PRICE + ", ");
		sql.append(ProductSku.KEY_MEMBER_LEVEL_PRICE + ", ");
		sql.append(ProductSku.KEY_PRODUCT_STOCK + ", ");
		sql.append(ProductSku.KEY_SYSTEM_CREATE_USER_ID + ", ");
		sql.append(ProductSku.KEY_SYSTEM_CREATE_TIME + ", ");
		sql.append(ProductSku.KEY_SYSTEM_UPDATE_USER_ID + ", ");
		sql.append(ProductSku.KEY_SYSTEM_UPDATE_TIME + ", ");
		sql.append(ProductSku.KEY_SYSTEM_STATUS + " ");
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

		StringBuffer sql = new StringBuffer("UPDATE " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");
		sql.append("SET " + ProductSku.KEY_PRODUCT_STOCK + " = ?, ");
		sql.append(ProductSku.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ");
		sql.append(ProductSku.KEY_SYSTEM_UPDATE_TIME + " = ? ");
		sql.append("WHERE " + ProductSku.KEY_PRODUCT_ID + " = ? ");
		sql.append("AND " + ProductSku.KEY_PRODUCT_ATTRIBUTE_VALUE + " = ? ");
		sql.append("AND " + ProductSku.KEY_PRODUCT_PRICE + " = ? ");
		sql.append("AND " + ProductSku.KEY_SYSTEM_STATUS + " = 1 ");

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

		StringBuffer sql = new StringBuffer("UPDATE " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");
		sql.append("SET " + ProductSku.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ");
		sql.append(ProductSku.KEY_SYSTEM_UPDATE_TIME + " = ?, ");
		sql.append(ProductSku.KEY_SYSTEM_STATUS + " = 0 ");
		sql.append("WHERE " + ProductSku.KEY_PRODUCT_SKU_ID + " = ? ");

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
