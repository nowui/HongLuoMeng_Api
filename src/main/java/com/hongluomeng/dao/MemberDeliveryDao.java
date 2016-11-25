package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.MemberDelivery;

public class MemberDeliveryDao {

	private Integer count(MemberDelivery memberDelivery) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + " ");
		dynamicSQL.append("WHERE " + MemberDelivery.KEY_MEMBER_DELIVERY_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_USER_ID + " = ? ", memberDelivery.getUser_id());

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		MemberDelivery memberDelivery = new MemberDelivery();

		return count(memberDelivery);
	}

	private List<MemberDelivery> list(MemberDelivery memberDelivery, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT ");
		dynamicSQL.append(MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_ID + ", ");
		dynamicSQL.append(MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_NAME + ", ");
		dynamicSQL.append(MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_PHONE + ", ");
		dynamicSQL.append("province." + Category.KEY_CATEGORY_NAME + " AS " + MemberDelivery.KEY_MEMBER_DELIVERY_PROVINCE + ", ");
		dynamicSQL.append("city." + Category.KEY_CATEGORY_NAME + " AS " + MemberDelivery.KEY_MEMBER_DELIVERY_CITY + ", ");
		dynamicSQL.append("area." + Category.KEY_CATEGORY_NAME + " AS " + MemberDelivery.KEY_MEMBER_DELIVERY_AREA + ", ");
		dynamicSQL.append(MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_ADDRESS + " ");
		dynamicSQL.append("FROM " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + " ");
		dynamicSQL.append("LEFT JOIN " + Category.KEY_TABLE_CATEGORY + " AS province ON province." + Category.KEY_CATEGORY_ID + " = " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_PROVINCE + " ");
		dynamicSQL.append("LEFT JOIN " + Category.KEY_TABLE_CATEGORY + " AS city ON city." + Category.KEY_CATEGORY_ID + " = " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_CITY + " ");
		dynamicSQL.append("LEFT JOIN " + Category.KEY_TABLE_CATEGORY + " AS area ON area." + Category.KEY_CATEGORY_ID + " = " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_AREA + " ");
		dynamicSQL.append("WHERE " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_STATUS + " = 1 ");
		dynamicSQL.append("AND " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_USER_ID + " = ? ", memberDelivery.getUser_id());
		dynamicSQL.append("ORDER BY " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		return new MemberDelivery().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<MemberDelivery> list(Integer m, Integer n) {
		MemberDelivery memberDelivery = new MemberDelivery();

		return list(memberDelivery, m, n);
	}

	public List<MemberDelivery> listByUser_id(String user_id) {
		MemberDelivery memberDelivery = new MemberDelivery();
		memberDelivery.setUser_id(user_id);

		memberDelivery.checkUser_id();

		return list(memberDelivery, 0, 0);
	}

	private MemberDelivery find(MemberDelivery memberDelivery) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + " ");
		dynamicSQL.append("WHERE " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + MemberDelivery.KEY_MEMBER_DELIVERY_ID + " = ? ", memberDelivery.getMember_delivery_id());

		List<MemberDelivery> memberDeliveryList = new MemberDelivery().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if(memberDeliveryList == null) {
			return null;
		} else {
			return memberDeliveryList.get(0);
		}
	}

	public MemberDelivery findByMember_delivery_id(String memberDelivery_id) {
		MemberDelivery memberDelivery = new MemberDelivery();
		memberDelivery.setMember_delivery_id(memberDelivery_id);

		memberDelivery.checkMember_delivery_id();

		return find(memberDelivery);
	}

	public void save(MemberDelivery memberDelivery, String request_user_id) {
		memberDelivery.setMember_delivery_id(Utility.getUUID());
		memberDelivery.setUser_id(request_user_id);
		memberDelivery.setMember_delivery_create_user_id(request_user_id);
		memberDelivery.setMember_delivery_create_time(new Date());
		memberDelivery.setMember_delivery_update_user_id(request_user_id);
		memberDelivery.setMember_delivery_update_time(new Date());
		memberDelivery.setMember_delivery_status(true);

		memberDelivery.save();
	}

	public void update(MemberDelivery memberDelivery, String request_user_id) {
		memberDelivery.remove(MemberDelivery.KEY_USER_ID);
		memberDelivery.remove(MemberDelivery.KEY_MEMBER_DELIVERY_CREATE_USER_ID);
		memberDelivery.remove(MemberDelivery.KEY_MEMBER_DELIVERY_CREATE_TIME);
		memberDelivery.setMember_delivery_update_user_id(request_user_id);
		memberDelivery.setMember_delivery_update_time(new Date());

		memberDelivery.update();
	}

	public void delete(String memberDelivery_id, String request_user_id) {
		MemberDelivery memberDelivery = new MemberDelivery();
		memberDelivery.setMember_delivery_id(memberDelivery_id);
		memberDelivery.setMember_delivery_update_user_id(request_user_id);
		memberDelivery.setMember_delivery_update_time(new Date());
		memberDelivery.setMember_delivery_status(false);

		memberDelivery.update();
	}

}
