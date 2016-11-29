package com.hongluomeng.model;

import java.util.Date;

import com.hongluomeng.common.Utility;

public class MemberLevel extends Base<MemberLevel> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_MEMBER_LEVEL = "table_member_level";
	public static final String KEY_MEMBER_LEVEL_ID = "member_level_id";
	public static final String KEY_MEMBER_LEVEL_NAME = "member_level_name";
	public static final String KEY_MEMBER_LEVEL_VALUE = "member_level_value";
	public static final String KEY_MEMBER_LEVEL_SORT = "member_level_sort";

	public String getMember_level_id() {
		return getStr(KEY_MEMBER_LEVEL_ID);
	}

	public void setMember_level_id(String member_level_id) {
		set(KEY_MEMBER_LEVEL_ID, member_level_id);
	}

	public void checkMember_level_id() {
		Utility.checkStringLength(getMember_level_id(), 32, "会员等级编号");
	}

	public String getMember_level_name() {
		return getStr(KEY_MEMBER_LEVEL_NAME);
	}

	public void setMember_level_name(String member_level_name) {
		set(KEY_MEMBER_LEVEL_NAME, member_level_name);
	}

	public void checkMember_level_name() {
		Utility.checkStringLength(getMember_level_name(), 3, 20, "会员等级名称");
	}

	public Integer getMember_level_value() {
		return Utility.getIntegerValue(get(KEY_MEMBER_LEVEL_VALUE));
	}

	public void setMember_level_value(Integer member_level_value) {
		set(KEY_MEMBER_LEVEL_VALUE, member_level_value);
	}

	public void checkMember_level_value() {
		Utility.checkIntegerLength(getMember_level_value(), 1, 11, "会员等级粉丝数");
	}

	public Integer getMember_level_sort() {
		return Utility.getIntegerValue(get(KEY_MEMBER_LEVEL_SORT));
	}

	public void setMember_level_sort(Integer member_level_sort) {
		set(KEY_MEMBER_LEVEL_SORT, member_level_sort);
	}

	public void checkMember_level_sort() {
		Utility.checkIntegerLength(getMember_level_sort(), 1, 3, "会员等级排序");
	}

}
