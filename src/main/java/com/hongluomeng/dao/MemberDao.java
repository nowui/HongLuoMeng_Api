package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.MemberLevel;

public class MemberDao {

	private Integer count(Member member) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + Member.KEY_TABLE_MEMBER + " ");
		dynamicSQL.append("WHERE " + Member.KEY_MEMBER_STATUS + " = 1 ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Member member = new Member();

		return count(member);
	}

	private List<Member> list(Member member, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Member.KEY_TABLE_MEMBER + " ");
		dynamicSQL.append("WHERE " + Member.KEY_MEMBER_STATUS + " = 1 ");
		dynamicSQL.append("ORDER BY " + Member.KEY_MEMBER_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		return new Member().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Member> list(Integer m, Integer n) {
		Member member = new Member();

		return list(member, m, n);
	}

	private Member find(Member member) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT " + Member.KEY_TABLE_MEMBER + ".*, ");
		dynamicSQL.append("IFNULL(" + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_MEMBER_LEVEL_NAME + ", '') AS " + MemberLevel.KEY_MEMBER_LEVEL_NAME + ", ");
		dynamicSQL.append("IFNULL(" + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_MEMBER_LEVEL_VALUE + ", 0) AS " + MemberLevel.KEY_MEMBER_LEVEL_VALUE + " ");
		dynamicSQL.append("FROM " + Member.KEY_TABLE_MEMBER + " ");
		dynamicSQL.append("LEFT JOIN " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + " ON " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_MEMBER_LEVEL_ID + " = " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_LEVEL_ID + " ");
		dynamicSQL.append("WHERE " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_ID + " = ? ", member.getMember_id());
		dynamicSQL.isNullOrEmpty("AND " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_USER_ID + " = ? ", member.getUser_id());

		List<Member> memberList = new Member().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if (memberList == null) {
			return null;
		} else {
			return memberList.get(0);
		}
	}

	public Member findByMember_id(String member_id) {
		Member member = new Member();
		member.setMember_id(member_id);

		return find(member);
	}

	public Member findByUser_id(String user_id) {
		Member member = new Member();
		member.setUser_id(user_id);

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
		member.setMember_create_user_id(request_user_id);
		member.setMember_create_time(new Date());
		member.setMember_update_user_id(request_user_id);
		member.setMember_update_time(new Date());
		member.setMember_status(true);

		member.save();
	}

	public void update(Member member, String request_user_id) {
		member.remove(Member.KEY_MEMBER_CREATE_USER_ID);
		member.remove(Member.KEY_MEMBER_CREATE_TIME);
		member.setMember_update_user_id(request_user_id);
		member.setMember_update_time(new Date());

		member.update();
	}

	public void updateMember_nameByUser_id(String member_name, String user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + Member.KEY_TABLE_MEMBER + " SET ");
		dynamicSQL.append(Member.KEY_MEMBER_NAME + " = ?, ", member_name);
		dynamicSQL.append(Member.KEY_MEMBER_UPDATE_USER_ID + " = ?, ", user_id);
		dynamicSQL.append(Member.KEY_MEMBER_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + Member.KEY_USER_ID + " = ?  ", user_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void updateMember_weibo_fansAndMember_weibo_friend(int member_weibo_fans, int member_weibo_friend, String user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + Member.KEY_TABLE_MEMBER + " SET ");
		dynamicSQL.append(Member.KEY_MEMBER_WEIBO_FANS + " = ?, ", member_weibo_fans);
		dynamicSQL.append(Member.KEY_MEMBER_WEIBO_FRIEND + " = ?, ", member_weibo_friend);
		dynamicSQL.append(Member.KEY_MEMBER_UPDATE_USER_ID + " = ?, ", user_id);
		dynamicSQL.append(Member.KEY_MEMBER_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + Member.KEY_USER_ID + " = ?  ", user_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public Member updateMember_avatarByUser_id(String member_avatar, String user_id, Boolean isOverwrite) {
		Member member = findByUser_id(user_id);

		if (Utility.isNullOrEmpty(member.getMember_avatar()) || isOverwrite) {
			member.setMember_avatar(member_avatar);
			member.setMember_update_user_id(user_id);
			member.setMember_update_time(new Date());

			member.update();
		}

		return member;
	}

	public void updateInfo(String member_real_name, String member_identity_card, String member_identity_card_front_image, String member_identity_card_back_image, String request_user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + Member.KEY_TABLE_MEMBER + " SET ");
		dynamicSQL.append(Member.KEY_MEMBER_REAL_NAME + " = ?, ", member_real_name);
		dynamicSQL.append(Member.KEY_MEMBER_IDENTITY_CARD + " = ?, ", member_identity_card);
		dynamicSQL.append(Member.KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE + " = ?, ", member_identity_card_front_image);
		dynamicSQL.append(Member.KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE + " = ?, ", member_identity_card_back_image);
		dynamicSQL.append(Member.KEY_MEMBER_UPDATE_USER_ID + " = ?, ", request_user_id);
		dynamicSQL.append(Member.KEY_MEMBER_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + Member.KEY_USER_ID + " = ? ", request_user_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void delete(String member_id, String request_user_id) {
		Member member = new Member();
		member.setMember_id(member_id);
		member.setMember_status(false);
		member.setMember_update_user_id(request_user_id);
		member.setMember_update_time(new Date());

		member.update();
	}

}
