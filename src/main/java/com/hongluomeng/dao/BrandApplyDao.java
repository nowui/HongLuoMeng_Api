package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Member;

public class BrandApplyDao {

	private Integer count(BrandApply brandApply) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");

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
	}

	public Integer countByBrand_idAndUser_id(String brand_id, String user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		sql.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " = " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " ");

		sql.append("WHERE " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ? ");
		parameterList.add(user_id);

		sql.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 ");

		sql.append("AND " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = ? ");
		parameterList.add(brand_id);

		sql.append("AND " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();

	}

	private List<BrandApply> list(BrandApply brandApply, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + BrandApply.KEY_TABLE_BRAND_APPLY + ".*, " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_NAME + ", " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_NAME + " FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		sql.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " ");
		sql.append("LEFT JOIN " + Member.KEY_TABLE_MEMBER + " ON " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_USER_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 ");

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

	private BrandApply find(BrandApply brandApply) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + BrandApply.KEY_TABLE_BRAND_APPLY + ".*, " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_NAME + " FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		sql.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(brandApply.getBrand_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " = ? ");
			parameterList.add(brandApply.getBrand_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(brandApply.getUser_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ? ");
			parameterList.add(brandApply.getUser_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<BrandApply> brandApplyList = brandApply.find(sql.toString(), parameterList.toArray());
		if(brandApplyList.size() == 0) {
			return null;
		} else {
			return brandApplyList.get(0);
		}
	}

	public BrandApply findByBrand_id(String brand_id, String user_id) {
		BrandApply brandApply = new BrandApply();
		brandApply.setBrand_id(brand_id);
		brandApply.setUser_id(user_id);

		return find(brandApply);
	}

	public void save(String brand_id, String member_real_name, String member_identity_card, String member_identity_card_front_image, String member_identity_card_back_image, String request_user_id) {
		BrandApply brandApply = new BrandApply();
		brandApply.setBrand_id(brand_id);
		brandApply.setUser_id(request_user_id);
		brandApply.setMember_real_name(member_real_name);
		brandApply.setMember_identity_card(member_identity_card);
		brandApply.setMember_identity_card_front_image(member_identity_card_front_image);
		brandApply.setMember_identity_card_back_image(member_identity_card_back_image);
		brandApply.setBrand_apply_create_user_id(request_user_id);
		brandApply.setBrand_apply_create_time(new Date());
		brandApply.setBrand_apply_update_user_id(request_user_id);
		brandApply.setBrand_apply_update_time(new Date());
		brandApply.setBrand_apply_status(true);
		brandApply.setBrand_apply_review_status(false);

		brandApply.save();
	}

	public void review(String brand_id, String user_id, String request_user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + BrandApply.KEY_TABLE_BRAND_APPLY + " SET " + BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " = 1, " + BrandApply.KEY_BRAND_APPLY_UPDATE_USER_ID + " = ?, " + BrandApply.KEY_BRAND_APPLY_UPDATE_TIME + " = ? WHERE " + BrandApply.KEY_BRAND_ID + " = ? AND " + BrandApply.KEY_USER_ID + " = ? ");

		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(brand_id);
		parameterList.add(user_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

	/*public void delete(String brand_id, String request_user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + BrandApply.KEY_BRAND_APPLY + " SET " + BrandApply.KEY_BRAND_APPLY_STATUS + " = 0, " + BrandApply.KEY_BRAND_APPLY_UPDATE_USER_ID + " = ?, " + BrandApply.KEY_BRAND_APPLY_UPDATE_TIME + " = ? WHERE " + BrandApply.KEY_BRAND_ID + " = ? AND " + BrandApply.KEY_USER_ID + " = ? ");

		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(brand_id);
		parameterList.add(request_user_id);

		Db.update(sql.toString(), parameterList.toArray());
	}*/

}
