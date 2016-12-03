package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.MemberLevel;

public class MemberLevelDao {

	private Integer count(MemberLevel memberLevel) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + " ");
		myDynamicSQL.append("WHERE " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_SYSTEM_STATUS + " = 1 ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		MemberLevel memberLevel = new MemberLevel();

		return count(memberLevel);
	}

	private List<MemberLevel> list(MemberLevel memberLevel, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + " ");
		myDynamicSQL.append("WHERE " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("ORDER BY " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_MEMBER_LEVEL_VALUE + " ASC ");
		myDynamicSQL.appendPagination(m, n);

		return memberLevel.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<MemberLevel> list(Integer m, Integer n) {
		MemberLevel memberLevel = new MemberLevel();

		return list(memberLevel, m, n);
	}

	private MemberLevel find(MemberLevel memberLevel) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + " ");
		myDynamicSQL.append("WHERE " + MemberLevel.KEY_TABLE_MEMBER_LEVEL + "." + MemberLevel.KEY_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + MemberLevel.KEY_MEMBER_LEVEL_ID + " = ? ", memberLevel.getMember_level_id());

		List<MemberLevel> memberLevelList = new MemberLevel().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if(memberLevelList.size() == 0) {
			return null;
		} else {
			return memberLevelList.get(0);
		}
	}

	public MemberLevel findByMember_level_id(String member_level_id) {
		MemberLevel memberLevel = new MemberLevel();
		memberLevel.setMember_level_id(member_level_id);

		memberLevel.checkMember_level_id();

		return find(memberLevel);
	}

	public void save(MemberLevel memberLevel, String request_user_id) {
		memberLevel.setMember_level_id(Utility.getUUID());

		memberLevel.initForSave(request_user_id);

		memberLevel.save();
	}

	public void update(MemberLevel memberLevel, String request_user_id) {
		memberLevel.initForUpdate(request_user_id);

		memberLevel.update();
	}

	public void delete(String member_level_id, String request_user_id) {
		MemberLevel memberLevel = new MemberLevel();
		memberLevel.setMember_level_id(member_level_id);

		memberLevel.initForDelete(request_user_id);

		memberLevel.update();
	}

}
