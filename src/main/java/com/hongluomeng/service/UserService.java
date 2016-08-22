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
import com.hongluomeng.type.AccountEnum;
import com.hongluomeng.type.CatetoryEnum;

public class UserService {

	private UserDao userDao = new UserDao();
	private CategoryService categoryService = new CategoryService();
	private AuthorizationService authorizationService = new AuthorizationService();
	private OperationService operationService= new OperationService();

	public Integer count(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		return userDao.countByUser_idAndUser_account("", userMap.getUser_account());
	}

	public Integer countByUser_phone(String user_phone) {
		return userDao.countByUser_idAndUser_phone("", user_phone);
	}

	public Integer countByOauth(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		String platform = jsonObject.getString("platform");

		if(platform.equals(AccountEnum.WEIBO.getKey())) {
			return userDao.countByWeiBo(userMap.getWeibo_open_id(), userMap.getWeibo_access_token());
		} else if(platform.equals(AccountEnum.WECHAT.getKey())) {
			return userDao.countByWechat(userMap.getWechat_open_id(), userMap.getWechat_access_token());
		}

		return 1;
	}

	public List<User> list(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		return userDao.listByUser_account(userMap.getUser_account(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
	}

	public User findByUser_id(String user_id) {
		return userDao.findByUser_id(user_id);
	}

	/*public Map<String, Object> login(JSONObject jsonObject) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		User userMap = jsonObject.toJavaObject(User.class);

		User user = userDao.findByUser_accountAndUser_password(userMap.getUser_account(), userMap.getUser_password());

		if (user == null) {
			return null;
		} else {
			String token = authorizationService.saveByUser_id(user.getUser_id());

			resultMap.put(Const.KEY_TOKEN, token);

			return resultMap;
		}
	}*/

	public Map<String, Object> loginByUser_accountAndUser_password(String user_account, String user_password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		User user = userDao.findByUser_accountAndUser_password(user_account, user_password);

		if (user == null) {
			return null;
		} else {
			String token = authorizationService.saveByUser_id(user.getUser_id());

			resultMap.put(Const.KEY_TOKEN, token);

			return resultMap;
		}
	}

	public Map<String, Object> loginByUser_phoneAndUser_password(String user_phone, String user_password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		User user = userDao.findByUser_phoneAndUser_password(user_phone, user_password);

		if (user == null) {
			return null;
		} else {
			String token = authorizationService.saveByUser_id(user.getUser_id());

			resultMap.put(Const.KEY_TOKEN, token);

			return resultMap;
		}
	}

	public List<Map<String, Object>> menu(JSONObject jsonObject) {
		Category category = categoryService.findByCategory_key(CatetoryEnum.MENU.getKey());

		if(category == null) {
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

	public String saveByObject_idAndUser_type(AccountEnum accountEnum, JSONObject jsonObject, String object_id, String user_type) {
		User userMap = jsonObject.toJavaObject(User.class);
		userMap.setObject_id(object_id);
		userMap.setUser_type(user_type);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Integer count = 0;

		switch(accountEnum) {
			case ACCOUNT:
				count = userDao.countByUser_idAndUser_account("", userMap.getUser_account());

				break;
			case PHONE:
				count = userDao.countByUser_idAndUser_phone("", userMap.getUser_phone());

				break;
			case EMAIL:
				count = userDao.countByUser_idAndUser_email("", userMap.getUser_email());

				break;
			case WEIBO:
				break;
			case WECHAT:
				break;
		}

		if (count == 0) {
			return userDao.save(accountEnum, userMap, request_user_id);
		} else {
			throw new RuntimeException("帐号已经存在");
		}
	}

	public void updateUser_account(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Integer count = userDao.countByUser_idAndUser_account(userMap.getUser_id(), userMap.getUser_account());

		if (count == 0) {
			userDao.updateUser_account(userMap, request_user_id);
		} else {
			throw new RuntimeException("帐号已经存在");
		}
	}

	public void updateUser_password(JSONObject jsonObject) {
		User userMap = jsonObject.toJavaObject(User.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		if (! Utility.isNullOrEmpty(userMap.getUser_password())) {
			userDao.updateUser_password(userMap, request_user_id);
		}
	}

	public void deleteByObject_id(String object_id, String request_user_id) {
		userDao.deleteByObject_id(object_id, request_user_id);
	}

}
