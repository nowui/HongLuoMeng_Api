package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;

public class BrandDao {

	private Integer count(Brand brand) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Brand.KEY_BRAND + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Brand.KEY_BRAND_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Brand brand = new Brand();

		return count(brand);
	}

	private List<Brand> list(Brand brand, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Brand.KEY_BRAND + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Brand.KEY_BRAND_STATUS + " = 1 ");

		sql.append("ORDER BY " + Brand.KEY_BRAND_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Brand> brandList = brand.find(sql.toString(), parameterList.toArray());
		return brandList;
	}

	public List<Brand> list(Integer m, Integer n) {
		Brand brand = new Brand();

		return list(brand, m, n);
	}

	public List<Brand> listByCategory_id(String category_id, Integer m, Integer n) {
		Brand brand = new Brand();
		brand.setCategory_id(category_id);

		return list(brand, m, n);
	}

	private Brand find(Brand brand) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Brand.KEY_BRAND + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(brand.getBrand_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Brand.KEY_BRAND_ID + " = ? ");
			parameterList.add(brand.getBrand_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Brand.KEY_BRAND_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<Brand> brandList = brand.find(sql.toString(), parameterList.toArray());
		if(brandList.size() == 0) {
			return null;
		} else {
			return brandList.get(0);
		}
	}

	public Brand findByBrand_id(String brand_id) {
		Brand brand = new Brand();
		brand.setBrand_id(brand_id);

		return find(brand);
	}

	public void save(Brand brand, String request_user_id) {
		brand.setBrand_id(Utility.getUUID());
		brand.setBrand_create_user_id(request_user_id);
		brand.setBrand_create_time(new Date());
		brand.setBrand_update_user_id(request_user_id);
		brand.setBrand_update_time(new Date());
		brand.setBrand_status(true);

		brand.save();
	}

	public void update(Brand brand, String request_user_id) {
		brand.remove(Brand.KEY_BRAND_CREATE_USER_ID);
		brand.remove(Brand.KEY_BRAND_CREATE_TIME);
		brand.setBrand_update_user_id(request_user_id);
		brand.setBrand_update_time(new Date());

		brand.update();
	}

	public void delete(String brand_id, String request_user_id) {
		Brand brand = new Brand();
		brand.setBrand_id(brand_id);
		brand.setBrand_update_user_id(request_user_id);
		brand.setBrand_update_time(new Date());
		brand.setBrand_status(false);

		brand.update();
	}

}
