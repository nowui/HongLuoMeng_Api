package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(ProductSku.KEY_PRODUCT_SKU_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		ProductSku productSku = new ProductSku();

		return count(productSku);
	}

	private List<ProductSku> list(ProductSku productSku, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(productSku.getProduct_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(ProductSku.KEY_PRODUCT_ID + " = ? ");
			parameterList.add(productSku.getProduct_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(productSku.getProductSkuIdList())) {
			for(String product_sku_id : productSku.getProductSkuIdList()) {
				if(isExit) {
					sql.append("AND ");
				} else {
					sql.append("WHERE ");
				}

				sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " = ? ");
				parameterList.add(product_sku_id);

				isExit = true;
			}
		}

		if (! Utility.isNullOrEmpty(productSku.getProductSkuIdList())) {
			for(String product_sku_id : productSku.getProductSkuIdList()) {
				if(isExit) {
					sql.append("AND ");
				} else {
					sql.append("WHERE ");
				}

				sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " = ? ");
				parameterList.add(product_sku_id);

				isExit = true;
			}
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(ProductSku.KEY_PRODUCT_SKU_STATUS + " = 1 ");

		sql.append("ORDER BY " + ProductSku.KEY_PRODUCT_SKU_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<ProductSku> productSkuList = productSku.find(sql.toString(), parameterList.toArray());
		return productSkuList;
	}

	public List<ProductSku> listByProduct_id(String product_id) {
		ProductSku productSku = new ProductSku();
		productSku.setProduct_id(product_id);

		return list(productSku, 0, 0);
	}

	public List<ProductSku> listByProductSkuIdList(List<String> productSkuIdList) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + ".*, ");
		sql.append(Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_ID + ", ");
		sql.append(Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_NAME + ", ");
		sql.append(Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + ", ");
		sql.append(Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_NAME + ", ");
		sql.append(Product.KEY_TABLE_PRODUCT + ".*, ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_ATTRIBUTE_VALUE + ", ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_PRICE + ", ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_MEMBER_LEVEL_PRICE + ", ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_STOCK + ", ");
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_STATUS + ", ");
		sql.append("IFNULL(" + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + "." + ProductLockStock.KEY_PRODUCT_LOCK_STOCK + ", 0) AS " + ProductSku.KEY_PRODUCT_LOCK_STOCK + " ");
		sql.append(" ");
		sql.append("FROM " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");
		sql.append("LEFT JOIN " + Product.KEY_TABLE_PRODUCT + " ON " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_PRODUCT_ID + " = " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_ID + " ");
		sql.append("LEFT JOIN " + Category.KEY_TABLE_CATEGORY + " ON " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_CATEGORY_ID + " ");
		sql.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_BRAND_ID + " ");
		sql.append("LEFT JOIN (SELECT " + ProductLockStock.KEY_PRODUCT_SKU_ID + ", SUM(" + ProductLockStock.KEY_PRODUCT_LOCK_STOCK + ") AS " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK + " FROM " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " WHERE " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK_STATUS + " = 1 AND " + ProductLockStock.KEY_PRODUCT_LOCK_STOCK_EXPIRE_TIME + " > ? GROUP BY " + ProductLockStock.KEY_PRODUCT_SKU_ID + ") AS " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + " ON " + ProductLockStock.KEY_TABLE_PRODUCT_LOCK_STOCK + "." + ProductLockStock.KEY_PRODUCT_SKU_ID + " = " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " ");
		parameterList.add(new Date());

		Boolean isExit = false;

		if (productSkuIdList.size() > 0) {
			for(String product_sku_id : productSkuIdList) {
				if(isExit) {
					sql.append("AND ");
				} else {
					sql.append("WHERE ");
				}

				sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_ID + " = ? ");
				parameterList.add(product_sku_id);

				isExit = true;
			}
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_STATUS + " = 1 ");

		System.out.println(sql.toString());

		List<ProductSku> productSkuList = new ProductSku().find(sql.toString(), parameterList.toArray());
		return productSkuList;
	}

	private ProductSku find(ProductSku productSku) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(productSku.getProduct_sku_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(ProductSku.KEY_PRODUCT_SKU_ID + " = ? ");
			parameterList.add(productSku.getProduct_sku_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(ProductSku.KEY_PRODUCT_SKU_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<ProductSku> productSkuList = productSku.find(sql.toString(), parameterList.toArray());
		if(productSkuList.size() == 0) {
			return null;
		} else {
			return productSkuList.get(0);
		}
	}

	public ProductSku findByProduct_sku_id(String product_sku_id) {
		ProductSku productSku = new ProductSku();
		productSku.setProduct_sku_id(product_sku_id);

		return find(productSku);
	}

	public void save(List<ProductSku> productSkuList, String request_user_id) {
		List<Object[]> parameterList = new ArrayList<Object[]>();

		StringBuffer sql = new StringBuffer("INSERT INTO " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ( ");
		sql.append(ProductSku.KEY_PRODUCT_SKU_ID + ", ");
		sql.append(ProductSku.KEY_PRODUCT_ID + ", ");
		sql.append(ProductSku.KEY_PRODUCT_ATTRIBUTE_VALUE + ", ");
		sql.append(ProductSku.KEY_PRODUCT_PRICE + ", ");
		sql.append(ProductSku.KEY_MEMBER_LEVEL_PRICE + ", ");
		sql.append(ProductSku.KEY_PRODUCT_STOCK + ", ");
		sql.append(ProductSku.KEY_PRODUCT_SKU_CREATE_USER_ID + ", ");
		sql.append(ProductSku.KEY_PRODUCT_SKU_CREATE_TIME + ", ");
		sql.append(ProductSku.KEY_PRODUCT_SKU_UPDATE_USER_ID + ", ");
		sql.append(ProductSku.KEY_PRODUCT_SKU_UPDATE_TIME + ", ");
		sql.append(ProductSku.KEY_PRODUCT_SKU_STATUS + " ");
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
			objectList.add(productSku.getProduct_id());
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
		sql.append(ProductSku.KEY_PRODUCT_SKU_UPDATE_USER_ID + " = ?, ");
		sql.append(ProductSku.KEY_PRODUCT_SKU_UPDATE_TIME + " = ? ");
		sql.append("WHERE " + ProductSku.KEY_PRODUCT_ID + " = ? ");
		sql.append("AND " + ProductSku.KEY_PRODUCT_ATTRIBUTE_VALUE + " = ? ");
		sql.append("AND " + ProductSku.KEY_PRODUCT_PRICE + " = ? ");
		sql.append("AND " + ProductSku.KEY_PRODUCT_SKU_STATUS + " = 1 ");

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
		sql.append("SET " + ProductSku.KEY_PRODUCT_SKU_UPDATE_USER_ID + " = ?, ");
		sql.append(ProductSku.KEY_PRODUCT_SKU_UPDATE_TIME + " = ?, ");
		sql.append(ProductSku.KEY_PRODUCT_SKU_STATUS + " = 0 ");
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
