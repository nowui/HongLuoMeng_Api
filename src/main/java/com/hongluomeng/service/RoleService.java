package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.RoleDao;
import com.hongluomeng.model.Category;
import com.hongluomeng.model.Operation;
import com.hongluomeng.model.Role;
import com.hongluomeng.model.RoleOperation;
import com.hongluomeng.model.UserRole;
import com.hongluomeng.type.CatetoryEnum;

public class RoleService {

	private RoleDao roleDao = new RoleDao();
	private CategoryService categoryService = new CategoryService();
	private OperationService operationService = new OperationService();
	private RoleOperationService roleOperationService = new RoleOperationService();
	private UserRoleService userRoleService = new UserRoleService();
	private CacheService cacheService = new CacheService();

	public Map<String, Object> list(JSONObject jsonObject) {
		Role roleMap = jsonObject.toJavaObject(Role.class);

		Integer count = roleDao.countByGroup_id(roleMap.getGroup_id());

		List<Role> roleList = roleDao.listByGroup_id(roleMap.getGroup_id(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, roleList);

		return resultMap;
	}

	public List<Map<String, Object>> listOperation(JSONObject jsonObject) {
		Role roleMap = jsonObject.toJavaObject(Role.class);

		Category category = categoryService.findByCategory_key(CatetoryEnum.MENU.getKey());

		if(category == null) {
			throw new RuntimeException("没有该分类");
		}

		List<Category> categoryList = categoryService.listByCategory_path(category.getCategory_id());

		List<Operation> operationList = operationService.listAll();

		List<RoleOperation> roleOperationList = new ArrayList<RoleOperation>();

		if(! Utility.isNullOrEmpty(roleMap.getRole_id())) {
			roleOperationList = roleOperationService.listByRole_id(roleMap.getRole_id());
		}

		List<Map<String, Object>> resultList = getOperationChildrenList(categoryList, category.getCategory_id(), operationList, roleOperationList);

		return resultList;
	}

	public List<Map<String, Object>> listByUser_idAndUser_type(String user_id, String user_type) {
		Category category = categoryService.findByCategory_key(user_type);

		if(category == null) {
			throw new RuntimeException("没有该分类");
		}

		List<Role> roleList = roleDao.listByGroup_id(category.getCategory_id(), 0, 0);

		List<UserRole> userRoleList = userRoleService.listByUser_idAndUser_type(user_id, user_type);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for(Role role : roleList) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(Const.KEY_ID, role.getRole_id());
			resultMap.put(Const.KEY_NAME, role.getRole_name());
			resultMap.put(Const.KEY_SELECTED, false);

			for(UserRole userRole : userRoleList) {
				if(userRole.getRole_id().equals(role.getRole_id())) {
					resultMap.put(Const.KEY_SELECTED, true);

					break;
				}
			}

			resultList.add(resultMap);
		}

		return resultList;
	}

	/*private List<Map<String, Object>> getRoleChildrenList(List<Category> categoryList, String parent_id, List<Role> roleList, List<UserRole> userRoleList) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for (Category category : categoryList) {
			if (category.getParent_id().equals(parent_id)) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put(Const.KEY_ID, "parent_" + category.getCategory_id());
				resultMap.put(Const.KEY_NAME, category.getCategory_name());

				boolean isAdd = true;

				List<Map<String, Object>> childrenList = getRoleChildrenList(categoryList, category.getCategory_id(), roleList, userRoleList);
				if (childrenList.size() > 0) {
					resultMap.put(Const.KEY_CHILDREN, childrenList);
				} else {
					List<Map<String, Object>> userRoleChildrenList = getUserRoleChildrenList(roleList, category.getCategory_id(), userRoleList);

					if(userRoleChildrenList.size() == 0) {
						isAdd = false;
					}

					resultMap.put(Const.KEY_CHILDREN, userRoleChildrenList);
				}

				if (isAdd) {
					resultList.add(resultMap);
				}
			}
		}
		return resultList;
	}*/

	private List<Map<String, Object>> getOperationChildrenList(List<Category> categoryList, String parent_id, List<Operation> operationList, List<RoleOperation> roleOperationList) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for (Category category : categoryList) {
			if (category.getParent_id().equals(parent_id)) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put(Const.KEY_ID, "parent_" + category.getCategory_id());
				resultMap.put(Const.KEY_NAME, category.getCategory_name());

				boolean isAdd = true;

				List<Map<String, Object>> childrenList = getOperationChildrenList(categoryList, category.getCategory_id(), operationList, roleOperationList);
				if (childrenList.size() > 0) {
					resultMap.put(Const.KEY_CHILDREN, childrenList);
				} else {
					List<Map<String, Object>> roleOperationChildrenList = getRoleOperationChildrenList(operationList, category.getCategory_id(), roleOperationList);

					if(roleOperationChildrenList.size() == 0) {
						isAdd = false;
					}

					resultMap.put(Const.KEY_CHILDREN, roleOperationChildrenList);
				}

				if (isAdd) {
					resultList.add(resultMap);
				}
			}
		}
		return resultList;
	}

	/*private List<Map<String, Object>> getUserRoleChildrenList(List<Role> roleList, String group_id, List<UserRole> userRoleList) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for(Role role : roleList) {
			if(role.getGroup_id().equals(group_id)) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put(Const.KEY_ID, role.getRole_id());
				resultMap.put(Const.KEY_NAME, role.getRole_name());

				resultMap.put(Const.KEY_SELECTED, false);

				for(UserRole userRole : userRoleList) {
					if(role.getRole_id().equals(userRole.getRole_id())) {
						resultMap.put(Const.KEY_SELECTED, true);
					}
				}

				resultList.add(resultMap);
			}
		}

		return resultList;
	}*/

	private List<Map<String, Object>> getRoleOperationChildrenList(List<Operation> operationList, String menu_id, List<RoleOperation> roleOperationList) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for(Operation operation : operationList) {
			if(operation.getMenu_id().equals(menu_id)) {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put(Const.KEY_ID, operation.getOperation_id());
				resultMap.put(Const.KEY_NAME, operation.getOperation_name());

				resultMap.put(Const.KEY_SELECTED, false);

				for(RoleOperation roleOperation : roleOperationList) {
					if(operation.getOperation_id().equals(roleOperation.getOperation_id())) {
						resultMap.put(Const.KEY_SELECTED, true);
					}
				}


				resultList.add(resultMap);
			}
		}

		return resultList;
	}

	public Role find(JSONObject jsonObject) {
		Role roleMap = jsonObject.toJavaObject(Role.class);

		return roleDao.findByRole_id(roleMap.getRole_id());
	}

	public void save(JSONObject jsonObject) {
		Role roleMap = jsonObject.toJavaObject(Role.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Integer count = 0;

		if (! Utility.isNullOrEmpty(roleMap.getRole_key())) {
			count = roleDao.countByRole_idAndRole_key("", roleMap.getRole_key());
		}

		if(count == 0) {
			roleDao.save(roleMap, request_user_id);

			/*JSONArray jsonArray = (JSONArray) jsonObject.get(RoleOperation.KEY_ROLE_OPERATION);

			List<RoleOperation> roleOperationList = new ArrayList<RoleOperation>();

			for(int i = 0; i < jsonArray.size(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				RoleOperation roleOperation = object.toJavaObject(RoleOperation.class);
				roleOperation.setRole_id(roleMap.getRole_id());

				roleOperationList.add(roleOperation);
			}

			roleOperationService.save(roleOperationList);*/
		} else {
			throw new RuntimeException("键已经存在");
		}
	}

	public void update(JSONObject jsonObject) {
		Role roleMap = jsonObject.toJavaObject(Role.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Integer count = 0;

		if (! Utility.isNullOrEmpty(roleMap.getRole_key())) {
			count = roleDao.countByRole_idAndRole_key(roleMap.getRole_id(), roleMap.getRole_key());
		}

		if(count == 0) {
			roleDao.update(roleMap, request_user_id);

			/*JSONArray jsonArray = (JSONArray) jsonObject.get(RoleOperation.KEY_ROLE_OPERATION);

			List<RoleOperation> roleOperationList = new ArrayList<RoleOperation>();

			for(int i = 0; i < jsonArray.size(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);

				RoleOperation roleOperation = object.toJavaObject(RoleOperation.class);
				roleOperation.setRole_id(roleMap.getRole_id());

				roleOperationList.add(roleOperation);
			}

			roleOperationService.deleteByRole_id(roleOperationList, roleMap.getRole_id());

			roleOperationService.save(roleOperationList);

			cacheService.removeOperationList();*/
		} else {
			throw new RuntimeException("键已经存在");
		}
	}

	public void updateOperation(JSONObject jsonObject) {
		Role roleMap = jsonObject.toJavaObject(Role.class);

		JSONArray jsonArray = jsonObject.getJSONArray(RoleOperation.KEY_ROLE_OPERATION);

		List<RoleOperation> roleOperationList = new ArrayList<RoleOperation>();

		for(int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = jsonArray.getJSONObject(i);

			RoleOperation roleOperation = object.toJavaObject(RoleOperation.class);
			roleOperation.setRole_id(roleMap.getRole_id());

			roleOperationList.add(roleOperation);
		}

		roleOperationService.deleteByRole_id(roleOperationList, roleMap.getRole_id());

		roleOperationService.save(roleOperationList);

		cacheService.removeOperationList();
	}

	public void delete(JSONObject jsonObject) {
		Role roleMap = jsonObject.toJavaObject(Role.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		roleDao.delete(roleMap.getRole_id(), request_user_id);

		roleOperationService.deleteByRole_id(new ArrayList<RoleOperation>(), roleMap.getRole_id());

		cacheService.removeOperationList();
	}

	public void updateUserRole(List<UserRole> userRoleList, String user_id, String user_type) {
		userRoleService.save(userRoleList, user_id, user_type);
	}

}
