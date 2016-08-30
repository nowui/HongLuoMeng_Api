package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.MemberLevel;

public class MemberLevelDao {

	private Integer count(MemberLevel memberLevel) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + MemberLevel.KEY_MEMBER_LEVEL + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(MemberLevel.KEY_MEMBER_LEVEL_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		MemberLevel memberLevel = new MemberLevel();

		return count(memberLevel);
	}

	private List<MemberLevel> list(MemberLevel memberLevel, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + MemberLevel.KEY_MEMBER_LEVEL + " ");

		Boolean isExit = false;

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(MemberLevel.KEY_MEMBER_LEVEL_STATUS + " = 1 ");

		sql.append("ORDER BY " + MemberLevel.KEY_MEMBER_LEVEL_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<MemberLevel> memberLevelList = memberLevel.find(sql.toString(), parameterList.toArray());
		return memberLevelList;
	}

	public List<MemberLevel> list(Integer m, Integer n) {
		MemberLevel memberLevel = new MemberLevel();

		return list(memberLevel, m, n);
	}

	private MemberLevel find(MemberLevel memberLevel) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + MemberLevel.KEY_MEMBER_LEVEL + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(memberLevel.getMember_level_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(MemberLevel.KEY_MEMBER_LEVEL_ID + " = ? ");
			parameterList.add(memberLevel.getMember_level_id());

			isExit = true;
		}

		if(isExit) {
			sql.append("AND ");
		} else {
			sql.append("WHERE ");
		}
		sql.append(MemberLevel.KEY_MEMBER_LEVEL_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<MemberLevel> memberLevelList = memberLevel.find(sql.toString(), parameterList.toArray());
		if(memberLevelList.size() == 0) {
			return null;
		} else {
			return memberLevelList.get(0);
		}
	}

	public MemberLevel findByMember_level_id(String memberLevel_id) {
		MemberLevel memberLevel = new MemberLevel();
		memberLevel.setMember_level_id(memberLevel_id);

		return find(memberLevel);
	}

	public void save(MemberLevel memberLevel, String request_user_id) {
		memberLevel.setMember_level_id(Utility.getUUID());
		memberLevel.setMember_level_create_user_id(request_user_id);
		memberLevel.setMember_level_create_time(new Date());
		memberLevel.setMember_level_update_user_id(request_user_id);
		memberLevel.setMember_level_update_time(new Date());
		memberLevel.setMember_level_status(true);

		memberLevel.save();
	}

	public void update(MemberLevel memberLevel, String request_user_id) {
		memberLevel.remove(MemberLevel.KEY_MEMBER_LEVEL_CREATE_USER_ID);
		memberLevel.remove(MemberLevel.KEY_MEMBER_LEVEL_CREATE_TIME);
		memberLevel.setMember_level_update_user_id(request_user_id);
		memberLevel.setMember_level_update_time(new Date());

		memberLevel.update();
	}

	public void delete(String memberLevel_id, String request_user_id) {
		MemberLevel memberLevel = new MemberLevel();
		memberLevel.setMember_level_id(memberLevel_id);
		memberLevel.setMember_level_update_user_id(request_user_id);
		memberLevel.setMember_level_update_time(new Date());
		memberLevel.setMember_level_status(false);

		memberLevel.update();
	}

}
