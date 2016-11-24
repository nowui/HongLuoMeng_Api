package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.hongluomeng.common.Private;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.User;

public class UserDao {

	private Integer count(User user) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("WHERE " + User.KEY_USER_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + User.KEY_USER_ACCOUNT + " = ? ", user.getUser_account());
		dynamicSQL.isNullOrEmpty("AND " + User.KEY_USER_TYPE + " = ? ", user.getUser_type());

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByUser_accountNotEqualUser_id(String user_id, String user_account) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("WHERE " + User.KEY_USER_STATUS + " = 1 ");
		dynamicSQL.append("(" + User.KEY_USER_ID + " != ? AND " + User.KEY_USER_ACCOUNT + " = ? ) ", user_id, user_account);

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByUser_phoneNotEqualUser_id(String user_id, String user_phone) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("WHERE " + User.KEY_USER_STATUS + " = 1 ");
		dynamicSQL.append("(" + User.KEY_USER_ID + " != ? AND " + User.KEY_USER_PHONE + " = ? ) ", user_id, user_phone);

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByUser_emailNotEqualUser_id(String user_id, String user_email) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("WHERE " + User.KEY_USER_STATUS + " = 1 ");
		dynamicSQL.append("(" + User.KEY_USER_ID + " != ? AND " + User.KEY_USER_EMAIL + " = ? ) ", user_id, user_email);

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByWeibo_uidNotEqualUser_id(String user_id, String weibo_uid) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("WHERE " + User.KEY_USER_STATUS + " = 1 ");
		dynamicSQL.append("(" + User.KEY_USER_ID + " != ? AND " + User.KEY_WEIBO_UID + " = ? ) ", user_id, weibo_uid);

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByWechat_uidNotEqualUser_id(String user_id, String wechat_uid) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("WHERE " + User.KEY_USER_STATUS + " = 1 ");
		dynamicSQL.append("(" + User.KEY_USER_ID + " != ? AND " + User.KEY_WECHAT_UID + " = ? ) ", user_id, wechat_uid);

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	private List<User> list(User user, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("WHERE " + User.KEY_USER_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmptyForLike("AND " + User.KEY_USER_ACCOUNT + " = ? ", user.getUser_account());
		dynamicSQL.append("ORDER BY " + User.KEY_USER_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		List<User> userList = new User().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return userList;
	}

	public List<User> listByUser_account(String user_account, Integer m, Integer n) {
		User user = new User();
		user.setUser_account(user_account);

		return list(user, m, n);
	}

	private User find(User user) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("WHERE " + User.KEY_USER_STATUS + " = 1 ");
		dynamicSQL.isNullOrEmpty("AND " + User.KEY_USER_ID + " = ? ", user.getUser_id());
		dynamicSQL.isNullOrEmpty("AND " + User.KEY_USER_ACCOUNT + " = ? ", user.getUser_account());
		dynamicSQL.isNullOrEmpty("AND " + User.KEY_USER_PHONE + " = ? ", user.getUser_phone());
		dynamicSQL.isNullOrEmpty("AND " + User.KEY_USER_EMAIL + " = ? ", user.getUser_email());
		dynamicSQL.isNullOrEmpty("AND " + User.KEY_USER_EMAIL + " = ? ", user.getUser_email());
		dynamicSQL.isNullOrEmptyForOther("AND " + User.KEY_USER_PASSWORD + " = ? ", user.getUser_password(), HashKit.md5(Private.PRIVATE_KEY + user.getUser_password()));
		dynamicSQL.isNullOrEmpty("AND " + User.KEY_USER_TYPE + " = ? ", user.getUser_type());
		dynamicSQL.isNullOrEmpty("AND " + User.KEY_WEIBO_UID + " = ? ", user.getWeibo_uid());
		dynamicSQL.isNullOrEmpty("AND " + User.KEY_WECHAT_UID + " = ? ", user.getWechat_uid());

		List<User> userList = new User().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if (userList == null) {
			return null;
		} else {
			return userList.get(0);
		}
	}

	public User findByUser_id(String user_id) {
		User user = new User();
		user.setUser_id(user_id);

		Utility.checkIsNullOrEmpty(user_id);

		return find(user);
	}

	public User findByUser_accountAndUser_passwordAndUser_type(String user_account, String user_password, String user_type) {
		User user = new User();
		user.setUser_account(user_account);
		user.setUser_password(user_password);
		user.setUser_type(user_type);

		Utility.checkIsNullOrEmpty(user_account);
		Utility.checkIsNullOrEmpty(user_password);
		Utility.checkIsNullOrEmpty(user_type);

		return find(user);
	}

	public User findByUser_phoneAndUser_passwordAndUser_type(String user_phone, String user_password, String user_type) {
		User user = new User();
		user.setUser_phone(user_phone);
		user.setUser_password(user_password);
		user.setUser_type(user_type);

		Utility.checkIsNullOrEmpty(user_phone);
		Utility.checkIsNullOrEmpty(user_password);
		Utility.checkIsNullOrEmpty(user_type);

		return find(user);
	}

	public User findByWeibo_uid(String weibo_uid) {
		User user = new User();
		user.setWeibo_uid(weibo_uid);

		Utility.checkIsNullOrEmpty(weibo_uid);

		return find(user);
	}

	public User findByWechat_uid(String wechat_uid) {
		User user = new User();
		user.setWechat_uid(wechat_uid);

		Utility.checkIsNullOrEmpty(wechat_uid);

		return find(user);
	}

	private User getUser(String request_user_id) {
		User user = new User();
		user.setUser_id(Utility.getUUID());
		user.setUser_account("");
		user.setUser_password("");
		user.setUser_phone("");
		user.setUser_email("");
		user.setWeibo_uid("");
		user.setWeibo_access_token("");
		user.setWechat_uid("");
		user.setWechat_access_token("");
		user.setObject_id("");
		user.setUser_create_user_id(request_user_id);
		user.setUser_create_time(new Date());
		user.setUser_update_user_id(request_user_id);
		user.setUser_update_time(new Date());
		user.setUser_status(true);

		return user;
	}

	public String saveByAccount(String user_account, String user_password, String user_type, String request_user_id) {
		User user = getUser(request_user_id);

		user.setUser_account(user_account);
		user.setUser_password(HashKit.md5(Private.PRIVATE_KEY + user_password));
		user.setUser_type(user_type);

		user.save();

		return user.getUser_id();
	}

	public String saveByPhone(String user_phone, String user_password, String user_type, String request_user_id) {
		User user = getUser(request_user_id);

		user.setUser_phone(user_phone);
		user.setUser_password(HashKit.md5(Private.PRIVATE_KEY + user_password));
		user.setUser_type(user_type);

		user.save();

		return user.getUser_id();
	}

	public String saveByEmail(String user_email, String user_password, String user_type, String request_user_id) {
		User user = getUser(request_user_id);

		user.setUser_email(user_email);
		user.setUser_password(HashKit.md5(Private.PRIVATE_KEY + user_password));
		user.setUser_type(user_type);

		user.save();

		return user.getUser_id();
	}

	public String saveWeibo(String weibo_uid, String weibo_access_token, String user_type, String request_user_id) {
		User user = getUser(request_user_id);

		user.setWeibo_uid(weibo_uid);
		user.setWeibo_access_token(weibo_access_token);
		user.setUser_type(user_type);

		user.save();

		return user.getUser_id();
	}

	public String saveWechat(String wechat_uid, String wechat_access_token, String user_type, String request_user_id) {
		User user = getUser(request_user_id);

		user.setWechat_uid(wechat_uid);
		user.setWechat_access_token(wechat_access_token);
		user.setUser_type(user_type);

		user.save();

		return user.getUser_id();
	}

	public String updateWeibo(String weibo_uid, String weibo_access_token, String request_user_id) {
		User user = new User();

		user.setUser_id(request_user_id);
		user.setWeibo_uid(weibo_uid);
		user.setWeibo_access_token(weibo_access_token);
		user.setUser_update_user_id(request_user_id);
		user.setUser_update_time(new Date());

		user.update();

		return user.getUser_id();
	}

	public String updateWechat(String wechat_uid, String wechat_access_token, String request_user_id) {
		User user = new User();

		user.setUser_id(request_user_id);
		user.setWechat_uid(wechat_uid);
		user.setWechat_access_token(wechat_access_token);
		user.setUser_update_user_id(request_user_id);
		user.setUser_update_time(new Date());

		user.update();

		return user.getUser_id();
	}

	public void updateUser_account(User user, String request_user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();
		dynamicSQL.append("UPDATE " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("SET " + User.KEY_USER_ACCOUNT + " = ?, ", user.getUser_account());
		dynamicSQL.append(User.KEY_USER_UPDATE_USER_ID + " = ?, ", request_user_id);
		dynamicSQL.append(User.KEY_USER_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + User.KEY_USER_ID + " = ? ", user.getUser_id());

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void updateUser_passwordByUser_id(String user_id, String user_password, String request_user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("SET " + User.KEY_USER_PASSWORD + " = ?, ", HashKit.md5(Private.PRIVATE_KEY + user_password));
		dynamicSQL.append(User.KEY_USER_UPDATE_USER_ID + " = ?, ", request_user_id);
		dynamicSQL.append(User.KEY_USER_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + User.KEY_USER_ID + " = ? ", user_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void updateUser_passwordByUser_phone(String user_phone, String user_password, String request_user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("SET " + User.KEY_USER_PASSWORD + " = ?, ", HashKit.md5(Private.PRIVATE_KEY + user_password));
		dynamicSQL.append(User.KEY_USER_UPDATE_USER_ID + " = ?, ", request_user_id);
		dynamicSQL.append(User.KEY_USER_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + User.KEY_USER_PHONE + " = ? ", user_phone);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void updateObject_idByUser_id(String object_id, String user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("SET " + User.KEY_OBJECT_ID + " = ? ", object_id);
		dynamicSQL.append("WHERE " + User.KEY_USER_ID + " = ? ", user_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public void deleteByObject_id(String object_id, String request_user_id) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("UPDATE " + User.KEY_TABLE_USER + " ");
		dynamicSQL.append("SET " + User.KEY_USER_STATUS + " = 0, ");
		dynamicSQL.append(User.KEY_USER_UPDATE_USER_ID + " = ?, ", request_user_id);
		dynamicSQL.append(User.KEY_USER_UPDATE_TIME + " = ? ", new Date());
		dynamicSQL.append("WHERE " + User.KEY_OBJECT_ID + " = ? ", object_id);

		Db.update(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

}
