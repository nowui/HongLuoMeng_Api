package com.hongluomeng.dao;

import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Product;
import com.hongluomeng.type.BrandApplyReviewEnum;

public class ProductDao extends BaseDao {

	private Integer count(Product product) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT COUNT(*) FROM " + Product.KEY_TABLE_PRODUCT + " ");
		myDynamicSQL.append("WHERE " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmptyForLike("AND " + Product.COLUMN_PRODUCT_NAME + " LIKE ? ", product.getProduct_name());

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count(String product_name) {
		Product product = new Product();
		product.setProduct_name(product_name);

		return count(product);
	}

	private List<Product> list(Product product, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Product.KEY_TABLE_PRODUCT + " ");
		myDynamicSQL.append("WHERE " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Product.COLUMN_PRODUCT_IS_SALE + " = ?, ", product.getProduct_is_sale());
		myDynamicSQL.isNullOrEmptyForLike("AND " + Product.COLUMN_PRODUCT_NAME + " LIKE ? ", product.getProduct_name());
		myDynamicSQL.isNullOrEmpty("AND " + Product.COLUMN_CATEGORY_ID + " = ? ", product.getCategory_id());
		myDynamicSQL.isNullOrEmpty("AND " + Product.COLUMN_BRAND_ID + " = ? ", product.getBrand_id());
		myDynamicSQL.isNullOrEmpty("AND " + Product.COLUMN_PRODUCT_IS_HOT + " = ? ", product.getProduct_is_hot());
		myDynamicSQL.append("ORDER BY " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

        System.out.println(myDynamicSQL.sql.toString());

		List<Product> productList = new Product().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());

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

		Utility.checkNull(category_id, "分类编号");

		return list(productMap, m, n);
	}

	public List<Product> listByHot(Integer m, Integer n) {
		Product productMap = new Product();
		productMap.setProduct_is_hot(true);

		return list(productMap, m, n);
	}

	public List<Product> listByBrand_id(String brand_id, Integer m, Integer n) {
		Product productMap = new Product();
		productMap.setBrand_id(brand_id);

		return list(productMap, m, n);
	}

	public List<Product> listByUser_id(String user_id, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT " + Product.KEY_TABLE_PRODUCT + ".* FROM " + Product.KEY_TABLE_PRODUCT + " ");
		myDynamicSQL.append("LEFT JOIN (SELECT * FROM " + BrandApply.TABLE_BRAND_APPLY + " WHERE " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_USER_ID + " = ? AND " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS + " = '" + BrandApplyReviewEnum.PASS.getKey() + "' AND " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_SYSTEM_STATUS + " = 1) " + BrandApply.TABLE_BRAND_APPLY + " ON " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_BRAND_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_BRAND_ID + " ", user_id);
		myDynamicSQL.append("WHERE " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_USER_ID + " = ? ", user_id);
		myDynamicSQL.append("ORDER BY " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		List<Product> productList = new Product().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());

		return productList;
	}

	private Product find(Product product) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT * FROM " + Product.KEY_TABLE_PRODUCT + " ");
		myDynamicSQL.append("WHERE " + Product.KEY_TABLE_PRODUCT + "." + Product.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Product.COLUMN_PRODUCT_ID + " = ? ", product.getProduct_id());

		List<Product> productList = new Product().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());

		if(productList.size() == 0) {
			return null;
		} else {
			return productList.get(0);
		}
	}

	public Product findByProduct_id(String product_id) {
		Product product = new Product();
		product.setProduct_id(product_id);

		product.checkProduct_id();

		return find(product);
	}

	public String save(Product product, String request_user_id) {
		product.setProduct_id(Utility.getUUID());

		product.initForSave(request_user_id);

		product.save();

		return product.getProduct_id();
	}

	public void update(Product product, String request_user_id) {
		product.initForUpdate(request_user_id);

		product.update();
	}

	public void delete(String product_id, String request_user_id) {
		Product product = new Product();
		product.setProduct_id(product_id);

		product.initForDelete(request_user_id);

		product.update();
	}

}
