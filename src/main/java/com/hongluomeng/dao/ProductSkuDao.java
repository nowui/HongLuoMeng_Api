package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.Product;
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
		sql.append(ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_SKU_STATUS + " ");
		sql.append(" ");
		sql.append("FROM " + ProductSku.KEY_TABLE_PRODUCT_SKU + " ");
		sql.append("LEFT JOIN " + Product.KEY_TABLE_PRODUCT + " ON " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_PRODUCT_ID + " = " + ProductSku.KEY_TABLE_PRODUCT_SKU + "." + ProductSku.KEY_PRODUCT_ID + " ");
		sql.append("LEFT JOIN " + Category.KEY_TABLE_CATEGORY + " ON " + Category.KEY_TABLE_CATEGORY + "." + Category.KEY_CATEGORY_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_CATEGORY_ID + " ");
		sql.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + Product.KEY_TABLE_PRODUCT + "." + Product.KEY_BRAND_ID + " ");

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

		List<ProductSku> productSkuList = new ProductSku().find(sql.toString(), parameterList.toArray());
		return productSkuList;
	}

	/*public List<ProductSku> listByProductSkuIdList(List<String> productSkuIdList) {
		ProductSku productSku = new ProductSku();
		productSku.setProductSkuIdList(productSkuIdList);

		return list(productSku, 0, 0);
	}*/

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

	public void save(ProductSku productSku, String request_user_id) {
		productSku.setProduct_sku_id(Utility.getUUID());
		productSku.setProduct_sku_create_user_id(request_user_id);
		productSku.setProduct_sku_create_time(new Date());
		productSku.setProduct_sku_update_user_id(request_user_id);
		productSku.setProduct_sku_update_time(new Date());
		productSku.setProduct_sku_status(true);

		productSku.save();
	}

	/*public void update(ProductSku productSku, String request_user_id) {
		productSku.remove(ProductSku.KEY_PRODUCT_SKU_CREATE_USER_ID);
		productSku.remove(ProductSku.KEY_PRODUCT_SKU_CREATE_TIME);
		productSku.setProduct_sku_update_user_id(request_user_id);
		productSku.setProduct_sku_update_time(new Date());

		productSku.update();
	}*/

	public void delete(String product_sku_id, String request_user_id) {
		ProductSku productSku = new ProductSku();
		productSku.setProduct_sku_id(product_sku_id);
		productSku.setProduct_sku_update_user_id(request_user_id);
		productSku.setProduct_sku_update_time(new Date());
		productSku.setProduct_sku_status(false);

		productSku.update();
	}

}
