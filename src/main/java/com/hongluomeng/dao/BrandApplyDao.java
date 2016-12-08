package com.hongluomeng.dao;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.model.Brand;
import com.hongluomeng.model.BrandApply;
import com.hongluomeng.model.Member;
import com.hongluomeng.type.BrandApplyReviewEnum;

public class BrandApplyDao extends BaseDao {

	private Integer count(BrandApply brandApply, String category_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT COUNT(*) FROM " + BrandApply.TABLE_BRAND_APPLY + " ");
		myDynamicSQL.append("LEFT JOIN " + Brand.TABLE_BRAND + " ON " + Brand.TABLE_BRAND + "." + Brand.COLUMN_BRAND_ID + " = " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_BRAND_ID + " ");
		myDynamicSQL.append("WHERE " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_USER_ID + " = ? ", brandApply.getUser_id());
		myDynamicSQL.isNullOrEmpty("AND " + Brand.TABLE_BRAND + "." + Brand.COLUMN_CATEGORY_ID + " = ? ", category_id);

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByCategory_idAndUser_id(String category_id, String user_id) {
		BrandApply brandApply = new BrandApply();
		brandApply.setUser_id(user_id);

		return count(brandApply, category_id);
	}

	private List<BrandApply> list(BrandApply brandApply, String category_id, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT " + BrandApply.TABLE_BRAND_APPLY + ".*, " + Brand.TABLE_BRAND + "." + Brand.COLUMN_BRAND_NAME + ", " + Member.TABLE_MEMBER + "." + Member.COLUMN_MEMBER_NAME + " FROM " + BrandApply.TABLE_BRAND_APPLY + " ");
		myDynamicSQL.append("LEFT JOIN " + Brand.TABLE_BRAND + " ON " + Brand.TABLE_BRAND + "." + Brand.COLUMN_BRAND_ID + " = " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_BRAND_ID + " ");
		myDynamicSQL.append("LEFT JOIN " + Member.TABLE_MEMBER + " ON " + Member.TABLE_MEMBER + "." + Member.COLUMN_USER_ID + " = " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_USER_ID + " ");
		myDynamicSQL.append("WHERE " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_USER_ID + " = ? ", brandApply.getUser_id());
		myDynamicSQL.isNullOrEmpty("AND " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS + " = ? ", brandApply.getBrand_apply_review_status());
		myDynamicSQL.isNullOrEmpty("AND " + Brand.TABLE_BRAND + "." + Brand.COLUMN_CATEGORY_ID + " = ? ", category_id);
		myDynamicSQL.append("ORDER BY " + BrandApply.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		return new BrandApply().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<BrandApply> listByCategory_idAndUser_idAndBrand_apply_review_status(String category_id, String user_id, String brand_apply_review_status, Integer m, Integer n) {
		BrandApply brandApply = new BrandApply();
		brandApply.setUser_id(user_id);
		brandApply.setBrand_apply_review_status(brand_apply_review_status);

		return list(brandApply, category_id, m, n);
	}

	private BrandApply find(BrandApply brandApply) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("SELECT " + BrandApply.TABLE_BRAND_APPLY + ".*, " + Brand.TABLE_BRAND + "." + Brand.COLUMN_BRAND_NAME + " FROM " + BrandApply.TABLE_BRAND_APPLY + " ");
		myDynamicSQL.append("LEFT JOIN " + Brand.TABLE_BRAND + " ON " + Brand.TABLE_BRAND + "." + Brand.COLUMN_BRAND_ID + " = " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_BRAND_ID + " ");
		myDynamicSQL.isNullOrEmpty("WHERE " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_BRAND_ID + " = ? ", brandApply.getBrand_id());
		myDynamicSQL.isNullOrEmpty("AND " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_USER_ID + " = ? ", brandApply.getUser_id());
		myDynamicSQL.append("AND " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + BrandApply.TABLE_BRAND_APPLY + "." + BrandApply.COLUMN_SYSTEM_CREATE_TIME + " DESC ");

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

		myDynamicSQL.append("UPDATE " + BrandApply.TABLE_BRAND_APPLY + " SET ");
		myDynamicSQL.append(BrandApply.COLUMN_MEMBER_REAL_NAME + " = ?, ", member_real_name);
		myDynamicSQL.append(BrandApply.COLUMN_MEMBER_IDENTITY_CARD + " = ?, ", member_identity_card);
		myDynamicSQL.append(BrandApply.COLUMN_MEMBER_IDENTITY_CARD_FRONT_IMAGE + " = ?, ", member_identity_card_front_image);
		myDynamicSQL.append(BrandApply.COLUMN_MEMBER_IDENTITY_CARD_BACK_IMAGE + " = ?, ", member_identity_card_back_image);
		myDynamicSQL.append(BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS + " = ?, ", BrandApplyReviewEnum.WAIT.getKey());
		myDynamicSQL.append(BrandApply.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(BrandApply.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + BrandApply.COLUMN_BRAND_ID + " = ? ", brand_id);
		myDynamicSQL.append("AND " + BrandApply.COLUMN_USER_ID + " = ? ", request_user_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void updateStatus(String brand_id, String brand_apply_review_status, String user_id, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + BrandApply.TABLE_BRAND_APPLY + " SET ");
		myDynamicSQL.append(BrandApply.COLUMN_BRAND_APPLY_REVIEW_STATUS + " = ?, ", brand_apply_review_status);
		myDynamicSQL.append(BrandApply.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(BrandApply.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + BrandApply.COLUMN_BRAND_ID + " = ? ", brand_id);
		myDynamicSQL.append("AND " + BrandApply.COLUMN_USER_ID + " = ? ", user_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
