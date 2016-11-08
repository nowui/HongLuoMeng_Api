package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.type.BrandApplyReviewEnum;

public class BrandDao {

	private Integer count(Brand brand) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Brand.KEY_TABLE_BRAND + " ");

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

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Brand.KEY_TABLE_BRAND + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(brand.getCategory_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Brand.KEY_CATEGORY_ID + " = ? ");
			parameterList.add(brand.getCategory_id());

			isExit = true;
		}

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

	public List<Brand> listByCategory_idForApply(String category_id, String user_id, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + Brand.KEY_TABLE_BRAND + ".*, IFNULL(" + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + ", '" + BrandApplyReviewEnum.NONE.getKey() + "') AS " + Brand.KEY_BRAND_APPLY_REVIEW_STATUS + " FROM " + Brand.KEY_TABLE_BRAND + " ");
		sql.append("LEFT JOIN (SELECT * FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " WHERE " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ?) AS " + BrandApply.KEY_TABLE_BRAND_APPLY + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " ");
		sql.append("WHERE " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_STATUS + " = 1 ");

		parameterList.add(user_id);

		if (! Utility.isNullOrEmpty(category_id)) {
			sql.append("AND " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_CATEGORY_ID + " = ? ");
			parameterList.add(category_id);
		}

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Brand> brandList = new Brand().find(sql.toString(), parameterList.toArray());
		return brandList;
	}

	public List<Brand> listByCategory_idForMy(String category_id, String user_id, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + Brand.KEY_TABLE_BRAND + ".*, " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_CREATE_TIME + " AS " + Brand.KEY_BRAND_APPLY_CREATE_TIME + " FROM " + Brand.KEY_TABLE_BRAND + " ");
		sql.append("INNER JOIN (SELECT * FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " WHERE " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ? AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " = '" + BrandApplyReviewEnum.PASS.getKey() + "') AS " + BrandApply.KEY_TABLE_BRAND_APPLY + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " ");
		sql.append("WHERE " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_STATUS + " = 1 ");

		parameterList.add(user_id);

		if (! Utility.isNullOrEmpty(category_id)) {
			sql.append("AND " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_CATEGORY_ID + " = ? ");
			parameterList.add(category_id);
		}

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Brand> brandList = new Brand().find(sql.toString(), parameterList.toArray());
		return brandList;
	}

	private Brand find(Brand brand) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Brand.KEY_TABLE_BRAND + " ");

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

	public Brand findByBrand_idAndUser_id(String brand_id, String user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + Brand.KEY_TABLE_BRAND + ".*, IFNULL(" + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + ", '" + BrandApplyReviewEnum.NONE.getKey() + "') AS " + Brand.KEY_BRAND_APPLY_REVIEW_STATUS + " FROM " + Brand.KEY_TABLE_BRAND + " ");
		sql.append("LEFT JOIN (SELECT * FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " WHERE " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ? ORDER BY " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_CREATE_TIME + " DESC LIMIT 0, 1) AS " + BrandApply.KEY_TABLE_BRAND_APPLY + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " ");
		sql.append("WHERE " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_STATUS + " = 1 ");
		sql.append("AND " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = ? ");
		sql.append("GROUP BY " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " ");

		parameterList.add(user_id);
		parameterList.add(brand_id);

		System.out.println(sql.toString());

		List<Brand> brandList = new Brand().find(sql.toString(), parameterList.toArray());
		if(brandList.size() == 0) {
			return null;
		} else {
			return brandList.get(0);
		}
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
