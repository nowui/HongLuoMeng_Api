package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.User;

public class UserDao {

	private Integer count(User user) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + User.KEY_USER + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(user.getUser_account())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			if (Utility.isNullOrEmpty(user.getUser_id())) {
				sql.append(User.KEY_USER_ACCOUNT + " = ? ");
				parameterList.add(user.getUser_account());
			} else {
				sql.append("(" + User.KEY_USER_ID + " != ? AND " + User.KEY_USER_ACCOUNT + " = ? ) ");
				parameterList.add(user.getUser_id());
				parameterList.add(user.getUser_account());
			}

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getUser_phone())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			if (Utility.isNullOrEmpty(user.getUser_id())) {
				sql.append(User.KEY_USER_PHONE + " = ? ");
				parameterList.add(user.getUser_phone());
			} else {
				sql.append("(" + User.KEY_USER_ID + " != ? AND " + User.KEY_USER_PHONE + " = ? ) ");
				parameterList.add(user.getUser_id());
				parameterList.add(user.getUser_phone());
			}

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getUser_email())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			if (Utility.isNullOrEmpty(user.getUser_id())) {
				sql.append(User.KEY_USER_EMAIL + " = ? ");
				parameterList.add(user.getUser_email());
			} else {
				sql.append("(" + User.KEY_USER_ID + " != ? AND " + User.KEY_USER_EMAIL + " = ? ) ");
				parameterList.add(user.getUser_id());
				parameterList.add(user.getUser_email());
			}

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getUser_type())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			sql.append(User.KEY_USER_TYPE + " = ? ");
			parameterList.add(user.getUser_type());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getWeibo_uid())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			if (Utility.isNullOrEmpty(user.getUser_id())) {
				sql.append(User.KEY_WEIBO_UID + " = ? ");
				parameterList.add(user.getWeibo_uid());
			} else {
				sql.append("(" + User.KEY_USER_ID + " != ? AND " + User.KEY_WEIBO_UID + " = ? ) ");
				parameterList.add(user.getUser_id());
				parameterList.add(user.getWeibo_uid());
			}

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getWechat_uid())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			if (Utility.isNullOrEmpty(user.getUser_id())) {
				sql.append(User.KEY_WECHAT_UID + " = ? ");
				parameterList.add(user.getWechat_uid());
			} else {
				sql.append("(" + User.KEY_USER_ID + " != ? AND " + User.KEY_WECHAT_UID + " = ? ) ");
				parameterList.add(user.getUser_id());
				parameterList.add(user.getWechat_uid());
			}

			isExit = true;
		}

		if(isExit) {
			sql.append(" AND ");
		} else {
			sql.append(" WHERE ");
		}
		sql.append(User.KEY_USER_STATUS + " = 1 ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer countByUser_idAndUser_account(String user_id, String user_account) {
		User user = new User();
		user.setUser_id(user_id);
		user.setUser_account(user_account);

		return count(user);
	}

	public Integer countByUser_idAndUser_phone(String user_id, String user_phone) {
		User user = new User();
		user.setUser_id(user_id);
		user.setUser_phone(user_phone);

		return count(user);
	}

	public Integer countByUser_idAndUser_email(String user_id, String user_email) {
		User user = new User();
		user.setUser_id(user_id);
		user.setUser_email(user_email);

		return count(user);
	}

	public Integer countByWeibo(String user_id, String weibo_uid) {
		User user = new User();
		user.setUser_id(user_id);
		user.setWeibo_uid(weibo_uid);

		return count(user);
	}

	public Integer countByWechat(String user_id, String wechat_uid) {
		User user = new User();
		user.setUser_id(user_id);
		user.setWechat_uid(wechat_uid);

		return count(user);
	}

	private List<User> list(User user, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + User.KEY_USER + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(user.getUser_account())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(User.KEY_USER_ACCOUNT + " like ? ");
			parameterList.add("%" + user.getUser_account() + "%");

			isExit = true;
		}

		if(isExit) {
			sql.append(" AND ");
		} else {
			sql.append(" WHERE ");
		}
		sql.append(User.KEY_USER_STATUS + " = 1 ");

		sql.append("ORDER BY " + User.KEY_USER_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<User> userList = user.find(sql.toString(), parameterList.toArray());
		return userList;
	}

	public List<User> listByUser_account(String user_account, Integer m, Integer n) {
		User user = new User();
		user.setUser_account(user_account);

		return list(user, m, n);
	}

	private User find(User user) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + User.KEY_USER + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(user.getUser_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(User.KEY_USER_ID + " = ? ");
			parameterList.add(user.getUser_id());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getUser_account())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(User.KEY_USER_ACCOUNT + " = ? ");
			parameterList.add(user.getUser_account());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getUser_phone())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(User.KEY_USER_PHONE + " = ? ");
			parameterList.add(user.getUser_phone());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getUser_email())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(User.KEY_USER_EMAIL + " = ? ");
			parameterList.add(user.getUser_email());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getUser_password())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(User.KEY_USER_PASSWORD + " = ? ");
			parameterList.add(HashKit.md5(Const.PRIVATE_KEY + user.getUser_password()));

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getUser_type())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			sql.append(User.KEY_USER_TYPE + " = ? ");
			parameterList.add(user.getUser_type());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getWeibo_uid())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(User.KEY_WEIBO_UID + " = ? ");
			parameterList.add(user.getWeibo_uid());

			isExit = true;
		}

		if (! Utility.isNullOrEmpty(user.getWechat_uid())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(User.KEY_WECHAT_UID + " = ? ");
			parameterList.add(user.getWechat_uid());

			isExit = true;
		}

		if(isExit) {
			sql.append(" AND ");
		} else {
			sql.append(" WHERE ");
		}
		sql.append(User.KEY_USER_STATUS + " = 1 ");

		if(! isExit) {
			return null;
		}

		List<User> userList = user.find(sql.toString(), parameterList.toArray());
		if(userList.size() == 0) {
			return null;
		} else {
			return userList.get(0);
		}
	}

	public User findByUser_id(String user_id) {
		User user = new User();
		user.setUser_id(user_id);

		return find(user);
	}

	public User findByUser_accountAndUser_passwordAndUser_type(String user_account, String user_password, String user_type) {
		User user = new User();
		user.setUser_account(user_account);
		user.setUser_password(user_password);
		user.setUser_type(user_type);

		return find(user);
	}

	public User findByUser_phoneAndUser_passwordAndUser_type(String user_phone, String user_password, String user_type) {
		User user = new User();
		user.setUser_phone(user_phone);
		user.setUser_password(user_password);
		user.setUser_type(user_type);

		return find(user);
	}

	public User findByWeibo_uid(String weibo_uid) {
		User user = new User();
		user.setWeibo_uid(weibo_uid);

		return find(user);
	}

	public User findByWechat_uid(String wechat_uid) {
		User user = new User();
		user.setWechat_uid(wechat_uid);;

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
		user.setUser_password(HashKit.md5(Const.PRIVATE_KEY + user_password));
		user.setUser_type(user_type);

		user.save();

		return user.getUser_id();
	}

	public String saveByPhone(String user_phone, String user_password, String user_type, String request_user_id) {
		User user = getUser(request_user_id);

		user.setUser_phone(user_phone);
		user.setUser_password(HashKit.md5(Const.PRIVATE_KEY + user_password));
		user.setUser_type(user_type);

		user.save();

		return user.getUser_id();
	}

	public String saveByEmail(String user_email, String user_password, String user_type, String request_user_id) {
		User user = getUser(request_user_id);

		user.setUser_email(user_email);
		user.setUser_password(HashKit.md5(Const.PRIVATE_KEY + user_password));
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
		List<Object> parameterList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("UPDATE " + User.KEY_USER + " SET " + User.KEY_USER_ACCOUNT + " = ?, " + User.KEY_USER_UPDATE_USER_ID + " = ?, " + User.KEY_USER_UPDATE_TIME + " = ? WHERE " + User.KEY_USER_ID + " = ? ");

		parameterList.add(user.getUser_account());
		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(user.getUser_id());

		Db.update(sql.toString(), parameterList.toArray());
	}

	public void updateUser_passwordByUser_id(String user_id, String user_password, String request_user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + User.KEY_USER + " SET " + User.KEY_USER_PASSWORD + " = ?, " + User.KEY_USER_UPDATE_USER_ID + " = ?, " + User.KEY_USER_UPDATE_TIME + " = ? WHERE " + User.KEY_USER_ID + " = ? ");

		parameterList.add(HashKit.md5(Const.PRIVATE_KEY + user_password));
		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(user_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

	public void updateUser_passwordByUser_phone(String user_phone, String user_password, String request_user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + User.KEY_USER + " SET " + User.KEY_USER_PASSWORD + " = ?, " + User.KEY_USER_UPDATE_USER_ID + " = ?, " + User.KEY_USER_UPDATE_TIME + " = ? WHERE " + User.KEY_USER_PHONE + " = ? ");

		parameterList.add(HashKit.md5(Const.PRIVATE_KEY + user_password));
		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(user_phone);

		Db.update(sql.toString(), parameterList.toArray());
	}

	public void updateObject_idByUser_id(String object_id, String user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + User.KEY_USER + " SET " + User.KEY_OBJECT_ID + " = ? WHERE " + User.KEY_USER_ID + " = ? ");

		parameterList.add(object_id);
		parameterList.add(user_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

	public void deleteByObject_id(String object_id, String request_user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + User.KEY_USER + " SET " + User.KEY_USER_STATUS + " = 0, " + User.KEY_USER_UPDATE_USER_ID + " = ?, " + User.KEY_USER_UPDATE_TIME + " = ? WHERE " + User.KEY_OBJECT_ID + " = ? ");

		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(object_id);

		Db.update(sql.toString(), parameterList.toArray());
	}

}
