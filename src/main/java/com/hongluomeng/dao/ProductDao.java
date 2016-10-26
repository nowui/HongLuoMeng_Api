package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Product;

public class ProductDao {

	private Integer count(Product product) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Product.KEY_PRODUCT + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(product.getProduct_name())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}
			sql.append(Product.KEY_PRODUCT_NAME + " LIKE ? ");
			parameterList.add("%" + product.getProduct_name() + "%");

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Product.KEY_PRODUCT_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count(String product_name) {
		Product product = new Product();
		product.setProduct_name(product_name);

		return count(product);
	}

	private List<Product> list(Product product, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Product.KEY_PRODUCT + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(product.getProduct_name())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}
			sql.append(Product.KEY_PRODUCT_NAME + " LIKE ? ");
			parameterList.add("%" + product.getProduct_name() + "%");

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(product.getCategory_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Product.KEY_CATEGORY_ID + " = ? ");
			parameterList.add(product.getCategory_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(product.getBrand_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Product.KEY_BRAND_ID + " = ? ");
			parameterList.add(product.getBrand_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Product.KEY_PRODUCT_STATUS + " = 1 ");

		sql.append("ORDER BY " + Product.KEY_PRODUCT_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Product> productList = product.find(sql.toString(), parameterList.toArray());
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

	private Product find(Product product) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Product.KEY_PRODUCT + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(product.getProduct_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Product.KEY_PRODUCT_ID + " = ? ");
			parameterList.add(product.getProduct_id());

			isExit = true;
		}

		if(! isExit) {
			return null;
		}

		List<Product> productList = product.find(sql.toString(), parameterList.toArray());
		if(productList.size() == 0) {
			return null;
		} else {
			return productList.get(0);
		}
	}

	public Product findByProduct_id(String product_id) {
		Product product = new Product();
		product.setProduct_id(product_id);

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
