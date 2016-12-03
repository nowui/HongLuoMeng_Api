package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.MemberDelivery;

public class MemberDeliveryDao {

	private Integer count(MemberDelivery memberDelivery) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + " ");
		myDynamicSQL.append("WHERE " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_USER_ID + " = ? ", memberDelivery.getUser_id());

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		MemberDelivery memberDelivery = new MemberDelivery();

		return count(memberDelivery);
	}

	private List<MemberDelivery> list(MemberDelivery memberDelivery, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT ");
		myDynamicSQL.append(MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_ID + ", ");
		myDynamicSQL.append(MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_NAME + ", ");
		myDynamicSQL.append(MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_PHONE + ", ");
		myDynamicSQL.append("province." + Category.KEY_CATEGORY_NAME + " AS " + MemberDelivery.KEY_MEMBER_DELIVERY_PROVINCE + ", ");
		myDynamicSQL.append("city." + Category.KEY_CATEGORY_NAME + " AS " + MemberDelivery.KEY_MEMBER_DELIVERY_CITY + ", ");
		myDynamicSQL.append("area." + Category.KEY_CATEGORY_NAME + " AS " + MemberDelivery.KEY_MEMBER_DELIVERY_AREA + ", ");
		myDynamicSQL.append(MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_ADDRESS + " ");
		myDynamicSQL.append("FROM " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + " ");
		myDynamicSQL.append("LEFT JOIN " + Category.KEY_TABLE_CATEGORY + " AS province ON province." + Category.KEY_CATEGORY_ID + " = " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_PROVINCE + " ");
		myDynamicSQL.append("LEFT JOIN " + Category.KEY_TABLE_CATEGORY + " AS city ON city." + Category.KEY_CATEGORY_ID + " = " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_CITY + " ");
		myDynamicSQL.append("LEFT JOIN " + Category.KEY_TABLE_CATEGORY + " AS area ON area." + Category.KEY_CATEGORY_ID + " = " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_AREA + " ");
		myDynamicSQL.append("WHERE " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_USER_ID + " = ? ", memberDelivery.getUser_id());
		myDynamicSQL.append("ORDER BY " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		return new MemberDelivery().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
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
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + " ");
		myDynamicSQL.append("WHERE " + MemberDelivery.KEY_TABLE_MEMBER_DELIVERY + "." + MemberDelivery.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + MemberDelivery.KEY_MEMBER_DELIVERY_ID + " = ? ", memberDelivery.getMember_delivery_id());

		List<MemberDelivery> memberDeliveryList = new MemberDelivery().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if(memberDeliveryList.size() == 0) {
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

		memberDelivery.initForSave(request_user_id);

		memberDelivery.save();
	}

	public void update(MemberDelivery memberDelivery, String request_user_id) {
		memberDelivery.remove(MemberDelivery.KEY_USER_ID);

		memberDelivery.initForUpdate(request_user_id);

		memberDelivery.update();
	}

	public void delete(String memberDelivery_id, String request_user_id) {
		MemberDelivery memberDelivery = new MemberDelivery();
		memberDelivery.setMember_delivery_id(memberDelivery_id);

		memberDelivery.initForDelete(request_user_id);

		memberDelivery.update();
	}

}
