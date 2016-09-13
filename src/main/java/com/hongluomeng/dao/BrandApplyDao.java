package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.model.BrandApply;

public class BrandApplyDao {

	/*private Integer count(BrandApply brandApply) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + BrandApply.KEY_BRAND_APPLY + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		BrandApply brandApply = new BrandApply();

		return count(brandApply);
	}*/

	private List<BrandApply> list(BrandApply brandApply, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + BrandApply.KEY_BRAND_APPLY + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 ");

		sql.append("ORDER BY " + BrandApply.KEY_BRAND_APPLY_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<BrandApply> brandApplyList = brandApply.find(sql.toString(), parameterList.toArray());
		return brandApplyList;
	}

	public List<BrandApply> list(Integer m, Integer n) {
		BrandApply brandApply = new BrandApply();

		return list(brandApply, m, n);
	}

	public void save(BrandApply brandApply, String request_user_id) {
		brandApply.setBrand_apply_create_user_id(request_user_id);
		brandApply.setBrand_apply_create_time(new Date());
		brandApply.setBrand_apply_update_user_id(request_user_id);
		brandApply.setBrand_apply_update_time(new Date());
		brandApply.setBrand_apply_status(true);

		brandApply.save();
	}

	public void update(BrandApply brandApply, String request_user_id) {
		brandApply.remove(BrandApply.KEY_BRAND_APPLY_CREATE_USER_ID);
		brandApply.remove(BrandApply.KEY_BRAND_APPLY_CREATE_TIME);
		brandApply.setBrand_apply_update_user_id(request_user_id);
		brandApply.setBrand_apply_update_time(new Date());

		brandApply.update();
	}

	public void delete(String brand_id, String request_user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + BrandApply.KEY_BRAND_APPLY + " SET " + BrandApply.KEY_BRAND_APPLY_STATUS + " = 0, " + BrandApply.KEY_BRAND_APPLY_UPDATE_USER_ID + " = ?, " + BrandApply.KEY_BRAND_APPLY_UPDATE_TIME + " = ? WHERE " + BrandApply.KEY_BRAND_ID + " = ? AND " + BrandApply.KEY_USER_ID + " = ? ");

		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(brand_id);
		parameterList.add(request_user_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

}
