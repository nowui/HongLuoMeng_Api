package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.ProductSku;

public class ProductSkuDao {

	private Integer count(ProductSku productSku) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + ProductSku.KEY_PRODUCT_SKU + " ");

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

		StringBuffer sql = new StringBuffer("SELECT * FROM " + ProductSku.KEY_PRODUCT_SKU + " ");

		Boolean isExit = false;

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

	public List<ProductSku> list(Integer m, Integer n) {
		ProductSku productSku = new ProductSku();

		return list(productSku, m, n);
	}

	private ProductSku find(ProductSku productSku) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + ProductSku.KEY_PRODUCT_SKU + " ");

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

	public ProductSku findByProduct_sku_id(String productSku_id) {
		ProductSku productSku = new ProductSku();
		productSku.setProduct_sku_id(productSku_id);

		return find(productSku);
	}

	public void save(ProductSku productSku, String request_user_id) {
		productSku.setProduct_sku_id(Utility.getUUID());
		productSku.setProduct_sku_create_user_id(request_user_id);
		productSku.setProduct_sku_create_time(new Date());
		productSku.setProduct_sku_update_user_id(request_user_id);
		productSku.setProduct_sku_update_time(new Date());
		productSku.setProduct_sku_status(true);

		productSku.save();
	}

	public void update(ProductSku productSku, String request_user_id) {
		productSku.remove(ProductSku.KEY_PRODUCT_SKU_CREATE_USER_ID);
		productSku.remove(ProductSku.KEY_PRODUCT_SKU_CREATE_TIME);
		productSku.setProduct_sku_update_user_id(request_user_id);
		productSku.setProduct_sku_update_time(new Date());

		productSku.update();
	}

	public void delete(String productSku_id, String request_user_id) {
		ProductSku productSku = new ProductSku();
		productSku.setProduct_sku_id(productSku_id);
		productSku.setProduct_sku_update_user_id(request_user_id);
		productSku.setProduct_sku_update_time(new Date());
		productSku.setProduct_sku_status(false);

		productSku.update();
	}

}
