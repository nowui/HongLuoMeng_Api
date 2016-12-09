package com.hongluomeng.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;

public class Member extends Base<Member> {

	private static final long serialVersionUID = 1L;

	public static final String TABLE_MEMBER = "table_member";
	public static final String COLUMN_MEMBER_ID = "member_id";
	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_MEMBER_LEVEL_ID = "member_level_id";
	public static final String COLUMN_MEMBER_LEVEL_NAME = "member_level_name";
	public static final String COLUMN_MEMBER_LEVEL_VALUE = "member_level_value";
	public static final String COLUMN_MEMBER_NAME = "member_name";
	public static final String COLUMN_MEMBER_AVATAR = "member_avatar";
	public static final String COLUMN_MEMBER_AVATAR_SMALL = "member_avatar_small";
	public static final String COLUMN_MEMBER_AVATAR_LARGE = "member_avatar_large";
	public static final String COLUMN_MEMBER_REAL_NAME = "member_real_name";
	public static final String COLUMN_MEMBER_IDENTITY_CARD = "member_identity_card";
	public static final String COLUMN_MEMBER_IDENTITY_CARD_FRONT_IMAGE = "member_identity_card_front_image";
	public static final String COLUMN_MEMBER_IDENTITY_CARD_BACK_IMAGE = "member_identity_card_back_image";
	public static final String COLUMN_MEMBER_WEIBO_FANS = "member_weibo_fans";
	public static final String COLUMN_MEMBER_WEIBO_FRIEND = "member_weibo_friend";
	public static final String COLUMN_MEMBER_STATUS = "member_status";

	public String getMember_id() {
		return getStr(COLUMN_MEMBER_ID);
	}

	public void setMember_id(String member_id) {
		set(COLUMN_MEMBER_ID, member_id);
	}

	public void checkMember_id() {
		Utility.checkStringLength(getMember_id(), 32, "会员编号");
	}

	public String getUser_id() {
		return getStr(COLUMN_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(COLUMN_USER_ID, user_id);
	}

	public void checkUser_id() {
		Utility.checkStringLength(getUser_id(), 32, "用户编号");
	}

	public String getMember_level_id() {
		return getStr(COLUMN_MEMBER_LEVEL_ID);
	}

	public void setMember_level_id(String member_level_id) {
		set(COLUMN_MEMBER_LEVEL_ID, member_level_id);
	}

	public void checkMember_level_id() {
		Utility.checkStringLength(getMember_level_id(), 32, "会员等级编号");
	}

	public String getMember_level_name() {
		return getStr(COLUMN_MEMBER_LEVEL_NAME);
	}

	public Integer getMember_level_value() {
		return Utility.getIntegerValue(get(COLUMN_MEMBER_LEVEL_VALUE));
	}

	public String getMember_name() {
		return getStr(COLUMN_MEMBER_NAME);
	}

	public void setMember_name(String member_name) {
		set(COLUMN_MEMBER_NAME, member_name);
	}

	public void checkMember_name() {
		Utility.checkStringLength(getMember_name(), 3, 20, "会员名称");
	}

	public JSONObject getMember_avatar() {
		return JSONObject.parseObject(getStr(COLUMN_MEMBER_AVATAR));
	}

    public String getMember_avatar_normal() {
        if(Utility.isNull(getMember_avatar())) {
            return "";
        } else {
            return getMember_avatar().getString(Const.UPLOAD_NORMAL);
        }
    }

    public String getMember_avatar_small() {
        if(Utility.isNull(getMember_avatar())) {
            return "";
        } else {
            return getMember_avatar().getString(Const.UPLOAD_SMALL);
        }
    }

    public String getMember_avatar_large() {
        if(Utility.isNull(getMember_avatar())) {
            return "";
        } else {
            return getMember_avatar().getString(Const.UPLOAD_LARGE);
        }
    }

	public void setMember_avatar(String member_avatar) {
		set(COLUMN_MEMBER_AVATAR, member_avatar);
	}

	public void checkMember_avatar() {
		Utility.checkStringLength(getStr(COLUMN_MEMBER_AVATAR), 0, 300, "会员头像");
	}

	public void checkMember_avatar_small(String member_avatar_small) {
		Utility.checkStringLength(member_avatar_small, 0, 300, "会员小头像");
	}

	public void checkMember_avatar_large(String member_avatar_large) {
		Utility.checkStringLength(member_avatar_large, 0, 300, "会员大头像");
	}

	public String getMember_real_name() {
		return getStr(COLUMN_MEMBER_REAL_NAME);
	}

	public void setMember_real_name(String member_real_name) {
		set(COLUMN_MEMBER_REAL_NAME, member_real_name);
	}

	public void checkMember_real_name() {
		Utility.checkStringLength(getMember_real_name(), 3, 300, "真实姓名");
	}

	public String getMember_identity_card() {
		return getStr(COLUMN_MEMBER_IDENTITY_CARD);
	}

	public void setMember_identity_card(String member_identity_card) {
		set(COLUMN_MEMBER_IDENTITY_CARD, member_identity_card);
	}

	public void checkMember_identity_card() {
		Utility.checkStringLength(getMember_identity_card(), 0, 18, "身份证号码");
	}

	public String getMember_identity_card_front_image() {
		return getStr(COLUMN_MEMBER_IDENTITY_CARD_FRONT_IMAGE);
	}

	public void setMember_identity_card_front_image(String member_identity_card_front_image) {
		set(COLUMN_MEMBER_IDENTITY_CARD_FRONT_IMAGE, member_identity_card_front_image);
	}

	public void checkMember_identity_card_front_image() {
		Utility.checkStringLength(getMember_identity_card_front_image(), 0, 100, "身份证照片正面");
	}

	public String getMember_identity_card_back_image() {
		return getStr(COLUMN_MEMBER_IDENTITY_CARD_BACK_IMAGE);
	}

	public void setMember_identity_card_back_image(String member_identity_card_back_image) {
		set(COLUMN_MEMBER_IDENTITY_CARD_BACK_IMAGE, member_identity_card_back_image);
	}

	public void checkMember_identity_card_back_image() {
		Utility.checkStringLength(getMember_identity_card_back_image(), 0, 100, "身份证照片反面");
	}

	public Integer getMember_weibo_fans() {
		return Utility.getIntegerValue(get(COLUMN_MEMBER_WEIBO_FANS));
	}

	public void setMember_weibo_fans(Integer member_weibo_fans) {
		set(COLUMN_MEMBER_WEIBO_FANS, member_weibo_fans);
	}

	public void checkMember_weibo_fans() {
		Utility.checkIntegerLength(getMember_weibo_fans(), 0, 11, "微博粉丝数");
	}

	public Integer getMember_weibo_friend() {
		return Utility.getIntegerValue(get(COLUMN_MEMBER_WEIBO_FRIEND));
	}

	public void setMember_weibo_friend(Integer member_weibo_friend) {
		set(COLUMN_MEMBER_WEIBO_FRIEND, member_weibo_friend);
	}

	public void checkMember_weibo_friend() {
		Utility.checkIntegerLength(getMember_weibo_friend(), 0, 11, "微博好友数");
	}

	public Boolean getMember_status() {
		return getBoolean(COLUMN_MEMBER_STATUS);
	}

	public void setMember_status(Boolean member_status) {
		set(COLUMN_MEMBER_STATUS, member_status);
	}

}
