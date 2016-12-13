package com.hongluomeng.dao;

import java.util.Date;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.hongluomeng.common.Private;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.User;

public class UserDao extends BaseDao {

	private Integer count(User user) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + User.TABLE_USER + " ");
		myDynamicSQL.append("WHERE " + User.TABLE_USER + "." + User.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + User.COLUMN_USER_ACCOUNT + " = ? ", user.getUser_account());
		myDynamicSQL.isNullOrEmpty("AND " + User.COLUMN_USER_TYPE + " = ? ", user.getUser_type());

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByUser_accountNotEqualUser_id(String user_id, String user_account) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + User.TABLE_USER + " ");
		myDynamicSQL.append("WHERE " + User.TABLE_USER + "." + User.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND (" + User.COLUMN_USER_ID + " != ? AND " + User.COLUMN_USER_ACCOUNT + " = ? ) ", user_id, user_account);

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByUser_phoneNotEqualUser_id(String user_id, String user_phone) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + User.TABLE_USER + " ");
		myDynamicSQL.append("WHERE " + User.TABLE_USER + "." + User.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND (" + User.COLUMN_USER_ID + " != ? AND " + User.COLUMN_USER_PHONE + " = ? ) ", user_id, user_phone);

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByUser_emailNotEqualUser_id(String user_id, String user_email) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + User.TABLE_USER + " ");
		myDynamicSQL.append("WHERE " + User.TABLE_USER + "." + User.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND (" + User.COLUMN_USER_ID + " != ? AND " + User.COLUMN_USER_EMAIL + " = ? ) ", user_id, user_email);

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByWeibo_uidNotEqualUser_id(String user_id, String weibo_uid) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + User.TABLE_USER + " ");
		myDynamicSQL.append("WHERE " + User.TABLE_USER + "." + User.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND (" + User.COLUMN_USER_ID + " != ? AND " + User.COLUMN_WEIBO_UID + " = ? ) ", user_id, weibo_uid);

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer countByWechat_uidNotEqualUser_id(String user_id, String wechat_uid) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + User.TABLE_USER + " ");
		myDynamicSQL.append("WHERE " + User.TABLE_USER + "." + User.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.append("AND (" + User.COLUMN_USER_ID + " != ? AND " + User.COLUMN_WECHAT_UID + " = ? ) ", user_id, wechat_uid);

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	private List<User> list(User user, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + User.TABLE_USER + " ");
		myDynamicSQL.append("WHERE " + User.TABLE_USER + "." + User.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmptyForLike("AND " + User.COLUMN_USER_ACCOUNT + " = ? ", user.getUser_account());
		myDynamicSQL.append("ORDER BY " + User.COLUMN_SYSTEM_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		List<User> userList = new User().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return userList;
	}

	public List<User> listByUser_account(String user_account, Integer m, Integer n) {
		User user = new User();
		user.setUser_account(user_account);

		return list(user, m, n);
	}

	private User find(User user) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + User.TABLE_USER + " ");
		myDynamicSQL.append("WHERE " + User.TABLE_USER + "." + User.COLUMN_SYSTEM_STATUS + " = 1 ");
		myDynamicSQL.isNullOrEmpty("AND " + User.COLUMN_USER_ID + " = ? ", user.getUser_id());
		myDynamicSQL.isNullOrEmpty("AND " + User.COLUMN_USER_ACCOUNT + " = ? ", user.getUser_account());
		myDynamicSQL.isNullOrEmpty("AND " + User.COLUMN_USER_PHONE + " = ? ", user.getUser_phone());
		myDynamicSQL.isNullOrEmpty("AND " + User.COLUMN_USER_EMAIL + " = ? ", user.getUser_email());
		myDynamicSQL.isNullOrEmptyForOther("AND " + User.COLUMN_USER_PASSWORD + " = ? ", user.getUser_password(), HashKit.md5(Private.PRIVATE_KEY + user.getUser_password()));
		myDynamicSQL.isNullOrEmpty("AND " + User.COLUMN_USER_TYPE + " = ? ", user.getUser_type());
		myDynamicSQL.isNullOrEmpty("AND " + User.COLUMN_WEIBO_UID + " = ? ", user.getWeibo_uid());
		myDynamicSQL.isNullOrEmpty("AND " + User.COLUMN_WECHAT_UID + " = ? ", user.getWechat_uid());

		List<User> userList = new User().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if (userList.size() == 0) {
			return null;
		} else {
			return userList.get(0);
		}
	}

	public User findByUser_id(String user_id) {
		User user = new User();
		user.setUser_id(user_id);

		user.checkUser_id();

		return find(user);
	}

	public User findByUser_accountAndUser_passwordAndUser_type(String user_account, String user_password, String user_type) {
		User user = new User();
		user.setUser_account(user_account);
		user.setUser_password(user_password);
		user.setUser_type(user_type);

		user.checkUser_account();
		user.checkUser_password();
		user.checkUser_type();

		return find(user);
	}

	public User findByUser_phoneAndUser_passwordAndUser_type(String user_phone, String user_password, String user_type) {
		User user = new User();
		user.setUser_phone(user_phone);
		user.setUser_password(user_password);
		user.setUser_type(user_type);

		user.checkUser_phone();
		user.checkUser_password();
		user.checkUser_type();

		return find(user);
	}

	public User findByWeibo_uid(String weibo_uid) {
		User user = new User();
		user.setWeibo_uid(weibo_uid);

		user.checkWeibo_uid();

		return find(user);
	}

	public User findByWechat_uid(String wechat_uid) {
		User user = new User();
		user.setWechat_uid(wechat_uid);

		user.checkWechat_uid();

		return find(user);
	}

	private User initUser(String request_user_id) {
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

		user.initForSave(request_user_id);

		return user;
	}

	public String saveByAccount(String user_account, String user_password, String user_type, String request_user_id) {
		User user = initUser(request_user_id);

		user.setUser_account(user_account);
		user.setUser_password(HashKit.md5(Private.PRIVATE_KEY + user_password));
		user.setUser_type(user_type);

		user.save();

		return user.getUser_id();
	}

	public User saveByPhone(String user_phone, String user_password, String user_type, String request_user_id) {
		User user = initUser(request_user_id);

		user.setUser_phone(user_phone);
		user.setUser_password(HashKit.md5(Private.PRIVATE_KEY + user_password));
		user.setUser_type(user_type);

		user.save();

		return user;
	}

	public String saveByEmail(String user_email, String user_password, String user_type, String request_user_id) {
		User user = initUser(request_user_id);

		user.setUser_email(user_email);
		user.setUser_password(HashKit.md5(Private.PRIVATE_KEY + user_password));
		user.setUser_type(user_type);

		user.save();

		return user.getUser_id();
	}

	public User saveWeibo(String weibo_uid, String weibo_access_token, String user_type, String request_user_id) {
		User user = initUser(request_user_id);

		user.setWeibo_uid(weibo_uid);
		user.setWeibo_access_token(weibo_access_token);
		user.setUser_type(user_type);

		user.save();

		return user;
	}

	public User saveWechat(String wechat_uid, String wechat_access_token, String user_type, String request_user_id) {
		User user = initUser(request_user_id);

		user.setWechat_uid(wechat_uid);
		user.setWechat_access_token(wechat_access_token);
		user.setUser_type(user_type);

		user.save();

		return user;
	}

	public String updateWeibo(String weibo_uid, String weibo_access_token, String request_user_id) {
		User user = new User();

		user.setUser_id(request_user_id);
		user.setWeibo_uid(weibo_uid);
		user.setWeibo_access_token(weibo_access_token);

		user.initForUpdate(request_user_id);

		user.update();

		return user.getUser_id();
	}

	public String updateWechat(String wechat_uid, String wechat_access_token, String request_user_id) {
		User user = new User();

		user.setUser_id(request_user_id);
		user.setWechat_uid(wechat_uid);
		user.setWechat_access_token(wechat_access_token);

		user.initForUpdate(request_user_id);

		user.update();

		return user.getUser_id();
	}

	public void updateUser_account(User user, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();
		myDynamicSQL.append("UPDATE " + User.TABLE_USER + " ");
		myDynamicSQL.append("SET " + User.COLUMN_USER_ACCOUNT + " = ?, ", user.getUser_account());
		myDynamicSQL.append(User.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(User.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + User.COLUMN_USER_ID + " = ? ", user.getUser_id());

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void updateUser_passwordByUser_id(String user_id, String user_password, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + User.TABLE_USER + " ");
		myDynamicSQL.append("SET " + User.COLUMN_USER_PASSWORD + " = ?, ", HashKit.md5(Private.PRIVATE_KEY + user_password));
		myDynamicSQL.append(User.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(User.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + User.COLUMN_USER_ID + " = ? ", user_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void updateUser_passwordByUser_phone(String user_phone, String user_password, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + User.TABLE_USER + " ");
		myDynamicSQL.append("SET " + User.COLUMN_USER_PASSWORD + " = ?, ", HashKit.md5(Private.PRIVATE_KEY + user_password));
		myDynamicSQL.append(User.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(User.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + User.COLUMN_USER_PHONE + " = ? ", user_phone);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void updateObject_idByUser_id(String object_id, String user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + User.TABLE_USER + " ");
		myDynamicSQL.append("SET " + User.COLUMN_OBJECT_ID + " = ? ", object_id);
		myDynamicSQL.append("WHERE " + User.COLUMN_USER_ID + " = ? ", user_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public void deleteByObject_id(String object_id, String request_user_id) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("UPDATE " + User.TABLE_USER + " ");
		myDynamicSQL.append("SET " + User.COLUMN_SYSTEM_STATUS + " = 0, ");
		myDynamicSQL.append(User.COLUMN_SYSTEM_UPDATE_USER_ID + " = ?, ", request_user_id);
		myDynamicSQL.append(User.COLUMN_SYSTEM_UPDATE_TIME + " = ? ", new Date());
		myDynamicSQL.append("WHERE " + User.COLUMN_OBJECT_ID + " = ? ", object_id);

		Db.update(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

}
