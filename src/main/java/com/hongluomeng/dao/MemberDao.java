package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Member;

public class MemberDao {

	private Integer count(Member member) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Member.KEY_MEMBER + " ");

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

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Member.KEY_MEMBER + " ");

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

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Member.KEY_MEMBER + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(member.getMember_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Member.KEY_MEMBER_ID + " = ? ");
			parameterList.add(member.getMember_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(Member.KEY_MEMBER_STATUS + " = 1 ");

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

	public void save(Member member, String request_user_id) {
		member.setMember_id(Utility.getUUID());
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

	public void updateUser_idByMember_id(String user_id, String member_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + Member.KEY_MEMBER + " SET " + Member.KEY_USER_ID + " = ? WHERE " + Member.KEY_MEMBER_ID + " = ? ");

		parameterList.add(user_id);
		parameterList.add(member_id);

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
