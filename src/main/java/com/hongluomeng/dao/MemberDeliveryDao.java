package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.MemberDelivery;

public class MemberDeliveryDao {

	private Integer count(MemberDelivery memberDelivery) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + MemberDelivery.KEY_MEMBER_DELIVERY + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(MemberDelivery.KEY_MEMBER_DELIVERY_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		MemberDelivery memberDelivery = new MemberDelivery();

		return count(memberDelivery);
	}

	private List<MemberDelivery> list(MemberDelivery memberDelivery, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append(MemberDelivery.KEY_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_ID + ", ");
		sql.append(MemberDelivery.KEY_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_NAME + ", ");
		sql.append(MemberDelivery.KEY_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_PHONE + ", ");
		sql.append("province." + Category.KEY_CATEGORY_NAME + " AS " + MemberDelivery.KEY_MEMBER_DELIVERY_PROVINCE + ", ");
		sql.append("city." + Category.KEY_CATEGORY_NAME + " AS " + MemberDelivery.KEY_MEMBER_DELIVERY_CITY + ", ");
		sql.append("area." + Category.KEY_CATEGORY_NAME + " AS " + MemberDelivery.KEY_MEMBER_DELIVERY_AREA + ", ");
		sql.append(MemberDelivery.KEY_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_ADDRESS + " ");
		sql.append("FROM " + MemberDelivery.KEY_MEMBER_DELIVERY + " ");
		sql.append("LEFT JOIN " + Category.KEY_CATEGORY + " AS province ON province." + Category.KEY_CATEGORY_ID + " = " + MemberDelivery.KEY_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_PROVINCE + " ");
		sql.append("LEFT JOIN " + Category.KEY_CATEGORY + " AS city ON city." + Category.KEY_CATEGORY_ID + " = " + MemberDelivery.KEY_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_CITY + " ");
		sql.append("LEFT JOIN " + Category.KEY_CATEGORY + " AS area ON area." + Category.KEY_CATEGORY_ID + " = " + MemberDelivery.KEY_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_AREA + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(memberDelivery.getUser_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(MemberDelivery.KEY_MEMBER_DELIVERY + "." + MemberDelivery.KEY_USER_ID + " = ? ");
			parameterList.add(memberDelivery.getUser_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(MemberDelivery.KEY_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_STATUS + " = 1 ");

		sql.append("ORDER BY " + MemberDelivery.KEY_MEMBER_DELIVERY + "." + MemberDelivery.KEY_MEMBER_DELIVERY_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<MemberDelivery> memberDeliveryList = memberDelivery.find(sql.toString(), parameterList.toArray());
		return memberDeliveryList;
	}

	public List<MemberDelivery> list(Integer m, Integer n) {
		MemberDelivery memberDelivery = new MemberDelivery();

		return list(memberDelivery, m, n);
	}

	public List<MemberDelivery> listByUser_id(String user_id) {
		MemberDelivery memberDelivery = new MemberDelivery();
		memberDelivery.setUser_id(user_id);

		return list(memberDelivery, 0, 0);
	}

	private MemberDelivery find(MemberDelivery memberDelivery) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + MemberDelivery.KEY_MEMBER_DELIVERY + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(memberDelivery.getMember_delivery_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(MemberDelivery.KEY_MEMBER_DELIVERY_ID + " = ? ");
			parameterList.add(memberDelivery.getMember_delivery_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(MemberDelivery.KEY_MEMBER_DELIVERY_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<MemberDelivery> memberDeliveryList = memberDelivery.find(sql.toString(), parameterList.toArray());
		if(memberDeliveryList.size() == 0) {
			return null;
		} else {
			return memberDeliveryList.get(0);
		}
	}

	public MemberDelivery findByMember_delivery_id(String memberDelivery_id) {
		MemberDelivery memberDelivery = new MemberDelivery();
		memberDelivery.setMember_delivery_id(memberDelivery_id);

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
