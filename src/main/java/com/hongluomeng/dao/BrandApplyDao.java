package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Member;
import com.hongluomeng.type.BrandApplyReviewEnum;

public class BrandApplyDao {

	private Integer count(BrandApply brandApply) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT COUNT(*) FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		myDynamicSQL.append("WHERE " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + BrandApply.KEY_BRAND_ID + " = ? ", brandApply.getBrand_id());
		myDynamicSQL.isNullOrEmpty("AND " + BrandApply.KEY_USER_ID + " = ? ", brandApply.getUser_id());

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		BrandApply brandApply = new BrandApply();

		return count(brandApply);
	}

	public Integer countByBrand_idAndUser_id(String brand_id, String user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT COUNT(*) FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		myDynamicSQL.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " = " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " ");
		myDynamicSQL.append("WHERE " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = ? ", brand_id);
		myDynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ? ", user_id);
		myDynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " != '" + BrandApplyReviewEnum.REFUSE.getKey() + "' ");
		myDynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " != '" + BrandApplyReviewEnum.CANCEL.getKey() + "' ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();

	}

	private List<BrandApply> list(BrandApply brandApply, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT " + BrandApply.KEY_TABLE_BRAND_APPLY + ".*, " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_NAME + ", " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_NAME + " FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		myDynamicSQL.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " ");
		myDynamicSQL.append("LEFT JOIN " + Member.KEY_TABLE_MEMBER + " ON " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_USER_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " ");
		myDynamicSQL.append("WHERE " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + BrandApply.KEY_SYSTEM_CREATE_TIME + " DESC ");

		return new BrandApply().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<BrandApply> list(Integer m, Integer n) {
		BrandApply brandApply = new BrandApply();

		return list(brandApply, m, n);
	}

	private BrandApply find(BrandApply brandApply) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT " + BrandApply.KEY_TABLE_BRAND_APPLY + ".*, " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_NAME + " FROM " + BrandApply.KEY_TABLE_BRAND_APPLY + " ");
		myDynamicSQL.append("LEFT JOIN " + Brand.KEY_TABLE_BRAND + " ON " + Brand.KEY_TABLE_BRAND + "." + Brand.KEY_BRAND_ID + " = " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " ");
		myDynamicSQL.isNullOrEmpty("WHERE " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_BRAND_ID + " = ? ", brandApply.getBrand_id());
		myDynamicSQL.isNullOrEmpty("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_USER_ID + " = ? ", brandApply.getUser_id());
		myDynamicSQL.append("AND " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + BrandApply.KEY_TABLE_BRAND_APPLY + "." + BrandApply.KEY_SYSTEM_CREATE_TIME + " DESC ");

		List<BrandApply> brandApplyList = brandApply.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if (brandApplyList.size() == 0) {
			return null;
		} else {
			return brandApplyList.get(0);
		}
	}

	public BrandApply findByBrand_idAndUser_id(String brand_id, String user_id) {
		BrandApply brandApply = new BrandApply();
		brandApply.setBrand_id(brand_id);
		brandApply.setUser_id(user_id);

		brandApply.checkBrand_id();
		brandApply.checkUser_id();

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
		brandApply.setBrand_apply_review_status(BrandApplyReviewEnum.WAIT.getKey());

		brandApply.initForSave(request_user_id);

		brandApply.save();
	}

	public void update(String brand_id, String member_real_name, String member_identity_card, String member_identity_card_front_image, String member_identity_card_back_image, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + BrandApply.KEY_TABLE_BRAND_APPLY + " SET ");
		myDynamicSQL.append(BrandApply.KEY_MEMBER_REAL_NAME + " = ?, ", member_real_name);
		myDynamicSQL.append(BrandApply.KEY_MEMBER_IDENTITY_CARD + " = ?, ", member_identity_card);
		myDynamicSQL.append(BrandApply.KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE + " = ?, ", member_identity_card_front_image);
		myDynamicSQL.append(BrandApply.KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE + " = ?, ", member_identity_card_back_image);
		myDynamicSQL.append(BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " = ?, ", BrandApplyReviewEnum.WAIT.getKey());
		myDynamicSQL.append(BrandApply.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(BrandApply.KEY_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + BrandApply.KEY_BRAND_ID + " = ? ", brand_id);
		myDynamicSQL.append("AND " + BrandApply.KEY_USER_ID + " = ? ", request_user_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void updateStatus(String brand_id, String brand_apply_review_status, String user_id, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + BrandApply.KEY_TABLE_BRAND_APPLY + " SET ");
		myDynamicSQL.append(BrandApply.KEY_BRAND_APPLY_REVIEW_STATUS + " = ?, ", brand_apply_review_status);
		myDynamicSQL.append(BrandApply.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(BrandApply.KEY_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + BrandApply.KEY_BRAND_ID + " = ? ", brand_id);
		myDynamicSQL.append("AND " + BrandApply.KEY_USER_ID + " = ? ", user_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
