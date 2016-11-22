package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Product;
import com.hongluomeng.type.BrandApplyReviewEnum;

public class ProductDao {

	private Integer count(Product product) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT COUNT(*) FROM " + Product.KEY_TABLE_PRODUCT + " ");
		dynamicSQL.append("WHERE " + Product.KEY_PRODUCT_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmptyForLike("AND " + Product.KEY_PRODUCT_NAME + " LIKE ? ", product.getProduct_name());

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count(String product_name) {
		Product product = new Product();
		product.setProduct_name(product_name);

		return count(product);
	}

	private List<Product> list(Product product, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Product.KEY_TABLE_PRODUCT + " ");
		dynamicSQL.append("WHERE " + Product.KEY_PRODUCT_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmptyForLike("AND " + Product.KEY_PRODUCT_NAME + " LIKE ? ", product.getProduct_name());
		dynamicSQL.isNullOrEmpty("AND " + Product.KEY_CATEGORY_ID + " = ? ", product.getCategory_id());
		dynamicSQL.isNullOrEmpty("AND " + Product.KEY_BRAND_ID + " = ? ", product.getBrand_id());
		dynamicSQL.append("ORDER BY " + Product.KEY_PRODUCT_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		List<Product> productList = new Product().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());

		return productList;
	}

	public List<Product> list(String product_name, Integer m, Integer n) {
		Product productMap = new Product();
		productMap.setProduct_name(product_name);

		return list(productMap, m, n);
	}

	public List<Product> listByCategory_id(String category_id, Integer m, Integer n) {
		Product productMap = new Product();
		productMap.setCategory_id(category_id);

		return list(productMap, m, n);
	}

	public List<Product> listByBrand_id(String brand_id, Integer m, Integer n) {
		Product productMap = new Product();
		productMap.setBrand_id(brand_id);

		return list(productMap, m, n);
	}

	public List<Product> listByUser_id(String user_id, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT " + Product.KEY_TABLE_PRODUCT + ".* FROM " + Product.KEY_TABLE_PRODUCT + " ");
		dynamicSQL.append("LEFT JOIN (SELECT * FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " WHERE " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ? AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " = '" + BrandApplyReviewEnum.PASS.getKey() + "' AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1) " + BrandApply.KEY_TABLE_BRAND_APPLY + " ON " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_BRAND_ID + " ", user_id);
		dynamicSQL.append("WHERE " + Product.KEY_PRODUCT_STATUS + " = 1 ");
		dynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ? ", user_id);
		dynamicSQL.append("ORDER BY " + Product.KEY_PRODUCT_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		List<Product> productList = new Product().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());

		return productList;
	}

	private Product find(Product product) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT * FROM " + Product.KEY_TABLE_PRODUCT + " ");
		dynamicSQL.append("WHERE " + Product.KEY_PRODUCT_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Product.KEY_PRODUCT_ID + " = ? ", product.getProduct_id());

		List<Product> productList = new Product().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());

		if(productList == null) {
			return null;
		} else {
			return productList.get(0);
		}
	}

	public Product findByProduct_id(String product_id) {
		Product product = new Product();
		product.setProduct_id(product_id);

		Utility.checkIsNullOrEmpty(product_id);

		return find(product);
	}

	public String save(Product product, String user_id) {
		product.setProduct_id(Utility.getUUID());
		product.setProduct_create_user_id(user_id);
		product.setProduct_create_time(new Date());
		product.setProduct_update_user_id(user_id);
		product.setProduct_update_time(new Date());
		product.setProduct_status(true);

		product.save();

		return product.getProduct_id();
	}

	public void update(Product product, String user_id) {
		product.remove(Product.KEY_PRODUCT_CREATE_USER_ID);
		product.remove(Product.KEY_PRODUCT_CREATE_TIME);
		product.setProduct_update_user_id(user_id);
		product.setProduct_update_time(new Date());

		product.update();
	}

	public void delete(String product_id, String request_user_id) {
		Product product = new Product();
		product.setProduct_id(product_id);
		product.setProduct_update_user_id(request_user_id);
		product.setProduct_update_time(new Date());
		product.setProduct_status(false);

		product.update();
	}

}
