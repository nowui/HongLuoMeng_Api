package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.MemberLevel;

public class MemberDao {

	private Integer count(Member member) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Member.KEY_TABLE_MEMBER + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Member.KEY_MEMBER_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Member member = new Member();

		return count(member);
	}

	private List<Member> list(Member member, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Member.KEY_TABLE_MEMBER + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Member.KEY_MEMBER_STATUS + " = 1 ");

		sql.append("ORDER BY " + Member.KEY_MEMBER_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Member> memberList = member.find(sql.toString(), parameterList.toArray());
		return memberList;
	}

	public List<Member> list(Integer m, Integer n) {
		Member member = new Member();

		return list(member, m, n);
	}

	private Member find(Member member) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT " + Member.KEY_TABLE_MEMBER + ".* ");
		sql.append(", IFNULL(" + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_MEMBER_LEVEL_NAME + ", '') AS " + MemberLevel.KEY_MEMBER_LEVEL_NAME + " ");
		sql.append(", IFNULL(" + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_MEMBER_LEVEL_VALUE + ", 0) AS " + MemberLevel.KEY_MEMBER_LEVEL_VALUE + " ");
		sql.append("FROM " + Member.KEY_TABLE_MEMBER + " ");
		sql.append("LEFT JOIN " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + " ON " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_MEMBER_LEVEL_ID + " = " + Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_LEVEL_ID + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(member.getMember_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_ID + " = ? ");
			parameterList.add(member.getMember_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(member.getUser_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Member.KEY_TABLE_MEMBER + "." + Member.KEY_USER_ID + " = ? ");
			parameterList.add(member.getUser_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Member.KEY_TABLE_MEMBER + "." + Member.KEY_MEMBER_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<Member> memberList = member.find(sql.toString(), parameterList.toArray());
		if(memberList.size() == 0) {
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
		Member member = findByUser_id(user_id);

		member.setMember_name(member_name);
		member.setMember_update_user_id(user_id);
		member.setMember_update_time(new Date());

		member.update();
	}

	public Member updateMember_avatarByUser_id(String member_avatar, String user_id, Boolean isOverwrite) {
		Member member = findByUser_id(user_id);

		if(Utility.isNullOrEmpty(member.getMember_avatar()) || isOverwrite) {
			member.setMember_avatar(member_avatar);
			member.setMember_update_user_id(user_id);
			member.setMember_update_time(new Date());

			member.update();
		}

		return member;
	}

	public void updateInfo(String member_real_name, String member_identity_card, String member_identity_card_front_image, String member_identity_card_back_image, String request_user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + Member.KEY_TABLE_MEMBER + " SET ");
		sql.append(Member.KEY_MEMBER_REAL_NAME + " = ? ");
		sql.append("," + Member.KEY_MEMBER_IDENTITY_CARD + " = ? ");
		sql.append("," + Member.KEY_MEMBER_IDENTITY_CARD_FRONT_IMAGE + " = ? ");
		sql.append("," + Member.KEY_MEMBER_IDENTITY_CARD_BACK_IMAGE + " = ? ");
		sql.append("," + Member.KEY_MEMBER_UPDATE_USER_ID + " = ? ");
		sql.append("," + Member.KEY_MEMBER_UPDATE_TIME + " = ? ");
		sql.append("WHERE " + Member.KEY_USER_ID + " = ?  ");

		parameterList.add(member_real_name);
		parameterList.add(member_identity_card);
		parameterList.add(member_identity_card_front_image);
		parameterList.add(member_identity_card_back_image);
		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(request_user_id);

		Db.update(sql.toString(), parameterList.toArray());
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
