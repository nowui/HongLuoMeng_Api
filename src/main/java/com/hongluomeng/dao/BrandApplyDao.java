package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Member;
import com.hongluomeng.type.BrandApplyReviewEnum;

public class BrandApplyDao {

	private Integer count(BrandApply brandApply) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT COUNT(*) FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		dynamicSQL.append("WHERE " + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + BrandApply.KEY_BRAND_ID + " = ? ", brandApply.getBrand_id());
		dynamicSQL.isNullOrEmpty("AND " + BrandApply.KEY_USER_ID + " = ? ", brandApply.getUser_id());

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		BrandApply brandApply = new BrandApply();

		return count(brandApply);
	}

	public Integer countByBrand_idAndUser_id(String brand_id, String user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT COUNT(*) FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		dynamicSQL.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " = " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " ");
		dynamicSQL.append("WHERE " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = ? ", brand_id);
		dynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ? ", user_id);
		dynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 ");
		dynamicSQL.append("AND " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_STATUS + " = 1 ");
		dynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " != '" + BrandApplyReviewEnum.REFUSE.getKey() + "' ");
		dynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " != '" + BrandApplyReviewEnum.CANCEL.getKey() + "' ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();

	}

	private List<BrandApply> list(BrandApply brandApply, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT " + BrandApply.KEY_TABLE_BRAND_APPLY + ".*, " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_NAME + ", " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_NAME + " FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		dynamicSQL.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " ");
		dynamicSQL.append("LEFT JOIN " + Member.KEY_TABLE_MEMBER + " ON " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_USER_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " ");
		dynamicSQL.append("WHERE " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 ");
		dynamicSQL.append("ORDER BY " + BrandApply.KEY_BRAND_APPLY_CREATE_TIME + " DESC ");

		return new BrandApply().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<BrandApply> list(Integer m, Integer n) {
		BrandApply brandApply = new BrandApply();

		return list(brandApply, m, n);
	}

	private BrandApply find(BrandApply brandApply) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("SELECT " + BrandApply.KEY_TABLE_BRAND_APPLY + ".*, " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_NAME + " FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		dynamicSQL.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " ");
		dynamicSQL.isNullOrEmpty("WHERE " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " = ? ", brandApply.getBrand_id());
		dynamicSQL.isNullOrEmpty("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ? ", brandApply.getUser_id());
		dynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_STATUS + " = 1 ");
		dynamicSQL.append("ORDER BY " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_CREATE_TIME + " DESC ");

		List<BrandApply> brandApplyList = brandApply.find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if(brandApplyList == null) {
			return null;
		} else {
			return brandApplyList.get(0);
		}
	}

	public BrandApply findByBrand_idAndUser_id(String brand_id, String user_id) {
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
		brandApply.setBrand_apply_review_status(BrandApplyReviewEnum.WAIT.getKey());

		brandApply.save();
	}

	public void update(String brand_id, String member_real_name, String member_identity_card, String member_identity_card_front_image, String member_identity_card_back_image, String request_user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + BrandApply.KEY_TABLE_BRAND_APPLY + " SET ");
		dynamicSQL.append(BrandApply.KEY_MEMBER_REAL_NAME + " = ?, ", member_real_name);
		dynamicSQL.append(BrandApply.KEY_MEMBER_IDENTITY_CARD + " = ?, ", member_identity_card);
		dynamicSQL.append(BrandApply.KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE + " = ?, ", member_identity_card_front_image);
		dynamicSQL.append(BrandApply.KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE + " = ?, ", member_identity_card_back_image);
		dynamicSQL.append(BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " = ?, ", BrandApplyReviewEnum.WAIT.getKey());
		dynamicSQL.append(BrandApply.KEY_BRAND_APPLY_UPDATE_USER_ID + " = ?, ", request_user_id);
		dynamicSQL.append(BrandApply.KEY_BRAND_APPLY_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + BrandApply.KEY_BRAND_ID + " = ? ", brand_id);
		dynamicSQL.append("AND " + BrandApply.KEY_USER_ID + " = ? ", request_user_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void updateStatus(String brand_id, String brand_apply_review_status, String user_id, String request_user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + BrandApply.KEY_TABLE_BRAND_APPLY + " SET ");
		dynamicSQL.append(BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " = ?, ", brand_apply_review_status);
		dynamicSQL.append(BrandApply.KEY_BRAND_APPLY_UPDATE_USER_ID + " = ?, ", request_user_id);
		dynamicSQL.append(BrandApply.KEY_BRAND_APPLY_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + BrandApply.KEY_BRAND_ID + " = ? ", brand_id);
		dynamicSQL.append("AND " + BrandApply.KEY_USER_ID + " = ? ", user_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

}
