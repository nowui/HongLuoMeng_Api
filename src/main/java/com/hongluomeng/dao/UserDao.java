package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.User;
import com.hongluomeng.type.AccountEnum;
import com.hongluomeng.type.UserEnum;

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
		}

		if (! Utility.isNullOrEmpty(user.getWeibo_open_id())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			sql.append(User.KEY_WEIBO_OPEN_ID + " = ? ");
			parameterList.add(user.getWeibo_open_id());
		}

		if (! Utility.isNullOrEmpty(user.getWeibo_access_token())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			sql.append(User.KEY_WEIBO_ACCESS_TOKEN + " = ? ");
			parameterList.add(user.getWeibo_access_token());
		}

		if (! Utility.isNullOrEmpty(user.getWechat_open_id())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			sql.append(User.KEY_WECHAT_OPEN_ID + " = ? ");
			parameterList.add(user.getWechat_open_id());
		}

		if (! Utility.isNullOrEmpty(user.getWechat_access_token())) {
			if(isExit) {
				sql.append("AND ");
			} else {
				sql.append("WHERE ");
			}

			sql.append(User.KEY_WECHAT_ACCESS_TOKEN + " = ? ");
			parameterList.add(user.getWechat_access_token());
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

	public Integer countByWeiBo(String weibo_open_id, String weibo_access_token) {
		User user = new User();
		user.setWeibo_open_id(weibo_open_id);
		user.setWeibo_access_token(weibo_access_token);
		user.setUser_type(UserEnum.MEMBER.getKey());

		return count(user);
	}

	public Integer countByWechat(String wechat_open_id, String wechat_access_token) {
		User user = new User();
		user.setWechat_open_id(wechat_open_id);
		user.setWechat_access_token(wechat_access_token);
		user.setUser_type(UserEnum.MEMBER.getKey());

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

	public User findByUser_accountAndUser_password(String user_account, String user_password) {
		User user = new User();
		user.setUser_account(user_account);
		user.setUser_password(user_password);

		return find(user);
	}

	public User findByUser_phoneAndUser_password(String user_phone, String user_password) {
		User user = new User();
		user.setUser_phone(user_phone);
		user.setUser_password(user_password);

		return find(user);
	}

	public String save(AccountEnum accountEnum, User user, String request_user_id) {
		user.setUser_id(Utility.getUUID());
		user.setUser_password(HashKit.md5(Const.PRIVATE_KEY + user.getUser_password()));
		if (! accountEnum.equals(AccountEnum.ACCOUNT)) {
			user.setUser_account("");
		}
		if (! accountEnum.equals(AccountEnum.PHONE)) {
			user.setUser_phone("");
		}
		if (! accountEnum.equals(AccountEnum.EMAIL)) {
			user.setUser_email("");
		}
		if (! accountEnum.equals(AccountEnum.WEIBO)) {
			user.setWeibo_open_id("");
			user.setWeibo_access_token("");
		}
		if (! accountEnum.equals(AccountEnum.WECHAT)) {
			user.setWechat_open_id("");
			user.setWechat_access_token("");
		}
		user.setUser_create_user_id(request_user_id);
		user.setUser_create_time(new Date());
		user.setUser_update_user_id(request_user_id);
		user.setUser_update_time(new Date());
		user.setUser_status(true);

		user.save();

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

	public void updateUser_password(User user, String request_user_id) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("UPDATE " + User.KEY_USER + " SET " + User.KEY_USER_PASSWORD + " = ?, " + User.KEY_USER_UPDATE_USER_ID + " = ?, " + User.KEY_USER_UPDATE_TIME + " = ? WHERE " + User.KEY_USER_ID + " = ? ");

		parameterList.add(HashKit.md5(Const.PRIVATE_KEY + user.getUser_password()));
		parameterList.add(request_user_id);
		parameterList.add(new Date());
		parameterList.add(user.getUser_id());

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
