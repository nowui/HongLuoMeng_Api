package com.hongluomeng.dao;

import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.hongluomeng.model.Category;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;

public class BrandDao extends BaseDao {

	private Integer count(Brand brand) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + Brand.TABLE_BRAND + " ");
		myDynamicSQL.append("WHERE " + Brand.TABLE_BRAND + "." + Brand.COLUMN_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByCategory_id(String category_id) {
		Brand brand = new Brand();
		brand.setCategory_id(category_id);

		return count(brand);
	}

	private List<Brand> list(Brand brand, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT " + Brand.TABLE_BRAND + ".*, " + Category.TABLE_CATEGORY + "." + Category.COLUMN_CATEGORY_NAME + " FROM " + Brand.TABLE_BRAND + " ");
		myDynamicSQL.append("LEFT JOIN " + Category.TABLE_CATEGORY + " ON " + Category.TABLE_CATEGORY + "." + Category.COLUMN_CATEGORY_ID + " = " + Brand.TABLE_BRAND + "." + Brand.COLUMN_CATEGORY_ID + " ");
		myDynamicSQL.append("WHERE " + Brand.TABLE_BRAND + "." + Brand.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Brand.TABLE_BRAND + "." + Brand.COLUMN_CATEGORY_ID + " = ? ", brand.getCategory_id());
		myDynamicSQL.append("ORDER BY " + Brand.TABLE_BRAND + "." + Brand.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		return new Brand().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<Brand> listByCategory_id(String category_id, Integer m, Integer n) {
		Brand brand = new Brand();
		brand.setCategory_id(category_id);

		return list(brand, m, n);
	}

	private Brand find(Brand brand) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Brand.TABLE_BRAND + " ");
		myDynamicSQL.append("WHERE " + Brand.TABLE_BRAND + "." + Brand.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Brand.COLUMN_BRAND_ID + " = ? ", brand.getBrand_id());

		List<Brand> brandList = new Brand().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if (brandList.size() == 0) {
			return null;
		} else {
			return brandList.get(0);
		}
	}

	public Brand findByBrand_id(String brand_id) {
		Brand brand = new Brand();
		brand.setBrand_id(brand_id);

		brand.checkBrand_id();

		return find(brand);
	}

	public void save(Brand brand, String request_user_id) {
		brand.setBrand_id(Utility.getUUID());

		brand.initForSave(request_user_id);

		brand.save();
	}

	public void update(Brand brand, String request_user_id) {
		brand.initForUpdate(request_user_id);

		brand.update();
	}

	public void delete(String brand_id, String request_user_id) {
		Brand brand = new Brand();
		brand.setBrand_id(brand_id);

		brand.initForDelete(request_user_id);

		brand.update();
	}

}
