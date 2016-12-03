package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.MemberLevel;

public class MemberDao {

	private Integer count(Member member) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + Member.KEY_TABLE_MEMBER + " ");
		myDynamicSQL.append("WHERE " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Member member = new Member();

		return count(member);
	}

	private List<Member> list(Member member, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Member.KEY_TABLE_MEMBER + " ");
		myDynamicSQL.append("WHERE " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		return new Member().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<Member> list(Integer m, Integer n) {
		Member member = new Member();

		return list(member, m, n);
	}

	private Member find(Member member) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT " + Member.KEY_TABLE_MEMBER + ".*, ");
		myDynamicSQL.append("IFNULL(" + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_MEMBER_LEVEL_NAME + ", '') AS " + MemberLevel.KEY_MEMBER_LEVEL_NAME + ", ");
		myDynamicSQL.append("IFNULL(" + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_MEMBER_LEVEL_VALUE + ", 0) AS " + MemberLevel.KEY_MEMBER_LEVEL_VALUE + " ");
		myDynamicSQL.append("FROM " + Member.KEY_TABLE_MEMBER + " ");
		myDynamicSQL.append("LEFT JOIN " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + " ON " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_MEMBER_LEVEL_ID + " = " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_LEVEL_ID + " ");
		myDynamicSQL.append("WHERE " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_ID + " = ? ", member.getMember_id());
		myDynamicSQL.isNullOrEmpty("AND " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_USER_ID + " = ? ", member.getUser_id());

		List<Member> memberList = new Member().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if (memberList.size() == 0) {
			return null;
		} else {
			return memberList.get(0);
		}
	}

	public Member findByMember_id(String member_id) {
		Member member = new Member();
		member.setMember_id(member_id);

		member.checkMember_id();

		return find(member);
	}

	public Member findByUser_id(String user_id) {
		Member member = new Member();
		member.setUser_id(user_id);

		member.checkUser_id();

		return find(member);
	}

	public void save(Member member, String request_user_id) {
		member.setMember_id(Utility.getUUID());
		member.setMember_level_id("");
		member.setMember_real_name("");
		member.setMember_identity_card("");
		member.setMember_identity_card_front_image("");
		member.setMember_identity_card_back_image("");
		if (Utility.isNullOrEmpty(member.getMember_weibo_fans())) {
			member.setMember_weibo_fans(0);
		}
		if (Utility.isNullOrEmpty(member.getMember_weibo_friend())) {
			member.setMember_weibo_friend(0);
		}
		if(Utility.isNull(member.getMember_status())) {
			member.setMember_status(false);
		}

		member.initForSave(request_user_id);

		member.save();
	}

	public void update(Member member, String request_user_id) {
		member.initForUpdate(request_user_id);

		member.update();
	}

	public void updateMember_nameByUser_id(String member_name, String user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + Member.KEY_TABLE_MEMBER + " SET ");
		myDynamicSQL.append(Member.KEY_MEMBER_NAME + " = ?, ", member_name);
		myDynamicSQL.append(Member.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ", user_id);
		myDynamicSQL.append(Member.KEY_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + Member.KEY_USER_ID + " = ?  ", user_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void updateMember_Levle_idAndMember_weibo_fansAndMember_weibo_friend(String member_level_id, Integer member_weibo_fans, Integer member_weibo_friend, String user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + Member.KEY_TABLE_MEMBER + " SET ");
		myDynamicSQL.append(Member.KEY_MEMBER_LEVEL_ID + " = ?, ", member_level_id);
		myDynamicSQL.append(Member.KEY_MEMBER_WEIBO_FANS + " = ?, ", member_weibo_fans);
		myDynamicSQL.append(Member.KEY_MEMBER_WEIBO_FRIEND + " = ?, ", member_weibo_friend);
		myDynamicSQL.isTrueOrFalse(Member.KEY_MEMBER_STATUS + " = 1, ", member_weibo_fans > 300000);
		myDynamicSQL.append(Member.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ", user_id);
		myDynamicSQL.append(Member.KEY_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + Member.KEY_USER_ID + " = ?  ", member_weibo_fans);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public Member updateMember_avatarByUser_id(String member_avatar, String user_id, Boolean isOverwrite) {
		Member member = findByUser_id(user_id);

		if (Utility.isNullOrEmpty(member.getMember_avatar()) || isOverwrite) {
			member.setMember_avatar(member_avatar);

			member.initForUpdate(user_id);

			member.update();
		}

		return member;
	}

	public void updateInfo(String member_real_name, String member_identity_card, String member_identity_card_front_image, String member_identity_card_back_image, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + Member.KEY_TABLE_MEMBER + " SET ");
		myDynamicSQL.append(Member.KEY_MEMBER_REAL_NAME + " = ?, ", member_real_name);
		myDynamicSQL.append(Member.KEY_MEMBER_IDENTITY_CARD + " = ?, ", member_identity_card);
		myDynamicSQL.append(Member.KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE + " = ?, ", member_identity_card_front_image);
		myDynamicSQL.append(Member.KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE + " = ?, ", member_identity_card_back_image);
		myDynamicSQL.append(Member.KEY_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(Member.KEY_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + Member.KEY_USER_ID + " = ? ", request_user_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void updateMember_status(String member_id, String request_user_id) {
		Member member = new Member();
		member.setMember_id(member_id);
		member.setMember_status(true);

		member.initForUpdate(request_user_id);

		member.update();
	}

	public void delete(String member_id, String request_user_id) {
		Member member = new Member();
		member.setMember_id(member_id);

		member.initForDelete(request_user_id);

		member.update();
	}

}
