package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.UserDao;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.Operation;
import com.hongluomeng.model.User;
import com.hongluomeng.type.CatetoryEnum;

public class UserService {

	private UserDao userDao = new UserDao();
	private CategoryService categoryService = new CategoryService();
	private OperationService operationService = new OperationService();

	public Integer count(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		return userDao.countByUser_accountNotEqualUser_id("", userMap.getUser_account());
	}

	public Integer countByUser_phone(String user_phone) {
		return userDao.countByUser_phoneNotEqualUser_id("", user_phone);
	}

	public Integer countByWeiboOauth(String weibo_uid, String weibo_access_token) {
		return userDao.countByWeibo_uidNotEqualUser_id(weibo_uid, weibo_access_token);
	}

	public Integer countByWechatOauth(String wechat_uid, String wechat_access_token) {
		return userDao.countByWechat_uidNotEqualUser_id(wechat_uid, wechat_access_token);
	}

	public List<User> list(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		return userDao.listByUser_account(userMap.getUser_account(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
	}

	public User findByUser_id(String user_id) {
		return userDao.findByUser_id(user_id);
	}

	public User findByWeibo_uid(String weibo_uid) {
		return userDao.findByWeibo_uid(weibo_uid);
	}

	public User findByWechat_uid(String wechat_uid) {
		return userDao.findByWechat_uid(wechat_uid);
	}

	public User loginByUser_accountAndUser_passwordAndUser_type(String user_account, String user_password, String user_type) {
		return userDao.findByUser_accountAndUser_passwordAndUser_type(user_account, user_password, user_type);
	}

	public User loginByUser_phoneAndUser_passwordAndUser_type(String user_phone, String user_password, String user_type) {
		return userDao.findByUser_phoneAndUser_passwordAndUser_type(user_phone, user_password, user_type);
	}

	public List<Map<String, Object>> menu(JSONObject jsonObject) {
		Category category = categoryService.findByCategory_key(CatetoryEnum.MENU.getKey());

		if (category == null) {
			throw new RuntimeException("没有该分类");
		}

		List<Category> categoryList = categoryService.listByCategory_path(category.getCategory_id());

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Operation> operationList = operationService.listByUser_id(request_user_id);

		List<Map<String, Object>> resultList = getMenuChildrenList(categoryList, category.getCategory_id(), operationList);

		return resultList;
	}

	private List<Map<String, Object>> getMenuChildrenList(List<Category> categoryList, String parent_id, List<Operation> operationList) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for (Category category : categoryList) {
			if (category.getParent_id().equals(parent_id)) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put(Const.KEY_ID, category.getCategory_id());
				resultMap.put(Const.KEY_NAME, category.getCategory_name());
				resultMap.put(Const.KEY_LINK, category.getCategory_value());
				resultMap.put(Const.KEY_ICON, category.getCategory_description());

				boolean isAdd = true;

				List<Map<String, Object>> childrenList = getMenuChildrenList(categoryList, category.getCategory_id(), operationList);

				if (childrenList.size() > 0) {
					resultMap.put(Const.KEY_CHILDREN, childrenList);
				} else {
					isAdd = getMenuOperationChildrenList(category.getCategory_id(), operationList);


				}

				if (isAdd) {
					resultList.add(resultMap);
				}
			}
		}

		return resultList;
	}


	private boolean getMenuOperationChildrenList(String menu_id, List<Operation> operationList) {
		for (Operation operation : operationList) {
			if (operation.getMenu_id().equals(menu_id)) {
				if (operation.getOperation_key().indexOf("_list") > -1 && !operation.getOperation_key().equals("upload_list")) {
					return true;
				}
			}
		}

		return false;
	}

	public String saveByAccount(String user_account, String user_password, String user_type, String request_user_id) {
		Integer count = userDao.countByUser_accountNotEqualUser_id("", user_account);

		if (count == 0) {
			return userDao.saveByAccount(user_account, user_password, user_type, request_user_id);
		} else {
			throw new RuntimeException("帐号已经存在");
		}
	}

	public String saveByPhone(String user_phone, String user_password, String user_type, String request_user_id) {
		Integer count = userDao.countByUser_phoneNotEqualUser_id("", user_phone);

		if (count == 0) {
			return userDao.saveByPhone(user_phone, user_password, user_type, request_user_id);
		} else {
			throw new RuntimeException("帐号已经存在");
		}
	}

	public String saveByEmail(String user_email, String user_password, String user_type, String request_user_id) {

		Integer count = userDao.countByUser_emailNotEqualUser_id("", user_email);

		if (count == 0) {
			return userDao.saveByEmail(user_email, user_password, user_type, request_user_id);
		} else {
			throw new RuntimeException("帐号已经存在");
		}
	}

	public String saveWeibo(String weibo_uid, String weibo_access_token, String user_type, String request_user_id) {
		Integer count = userDao.countByWeibo_uidNotEqualUser_id("", weibo_uid);

		if (count == 0) {
			return userDao.saveWeibo(weibo_uid, weibo_access_token, user_type, request_user_id);
		} else {
			return "";
		}
	}

	public String saveWechat(String wechat_uid, String wechat_access_token, String user_type, String request_user_id) {
		Integer count = userDao.countByWechat_uidNotEqualUser_id("", wechat_uid);

		if (count == 0) {
			return userDao.saveWechat(wechat_uid, wechat_access_token, user_type, request_user_id);
		} else {
			return "";
		}
	}

	public void updateWeibo(String weibo_uid, String weibo_access_token, String request_user_id) {
		Integer count = userDao.countByWeibo_uidNotEqualUser_id(request_user_id, weibo_uid);

		if (count == 0) {
			userDao.updateWeibo(weibo_uid, weibo_access_token, request_user_id);
		} else {
			throw new RuntimeException("该微博帐号已经绑定过");
		}
	}

	public void updateWechat(String wechat_uid, String wechat_access_token, String request_user_id) {
		Integer count = userDao.countByWechat_uidNotEqualUser_id(request_user_id, wechat_uid);

		if (count == 0) {
			userDao.updateWechat(wechat_uid, wechat_access_token, request_user_id);
		} else {
			throw new RuntimeException("该微信帐号已经绑定过");
		}
	}

	public void updateUser_account(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Integer count = userDao.countByUser_accountNotEqualUser_id(userMap.getUser_id(), userMap.getUser_account());

		if (count == 0) {
			userDao.updateUser_account(userMap, request_user_id);
		} else {
			throw new RuntimeException("帐号已经存在");
		}
	}

	public void updateUser_passwordByUser_id(String user_id, String user_password, String request_user_id) {
		if (!Utility.isNullOrEmpty(user_password)) {
			userDao.updateUser_passwordByUser_id(user_id, user_password, request_user_id);
		}
	}

	public void updateUser_passwordByUser_phone(String user_phone, String user_password, String request_user_id) {
		userDao.updateUser_passwordByUser_phone(user_phone, user_password, request_user_id);
	}

	public void updateObject_idByUser_id(String object_id, String user_id) {
		userDao.updateObject_idByUser_id(object_id, user_id);
	}

	public void deleteByObject_id(String object_id, String request_user_id) {
		userDao.deleteByObject_id(object_id, request_user_id);
	}

}
