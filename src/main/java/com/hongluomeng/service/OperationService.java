package com.hongluomeng.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.OperationDao;
import com.hongluomeng.model.Operation;

public class OperationService {

	private OperationDao operationDao = new OperationDao();
	private CacheService cacheService = new CacheService();

	public Integer count(JSONObject jsonObject) {
		Operation operationMap = jsonObject.toJavaObject(Operation.class);

		return operationDao.countByMenu_id(operationMap.getMenu_id());
	}

	public List<Operation> list(JSONObject jsonObject) {
		Operation operationMap = jsonObject.toJavaObject(Operation.class);

		Operation operationParameter = new Operation();
		operationParameter.setMenu_id(operationMap.getMenu_id());

		return operationDao.list(operationParameter, Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
	}

	public List<Operation> listByUser_id(String user_id) {
		List<Operation> operationList = cacheService.getOperationListByUser_id(user_id);

		if (operationList == null) {
			operationList = operationDao.listByUser_id(user_id);

			cacheService.setOperationListByUser_id(operationList, user_id);
		}
		return operationList;
	}

	public List<Operation> listAll() {
		return operationDao.list(new Operation(), 0,0);
	}

	public Operation find(JSONObject jsonObject) {
		Operation operationMap = jsonObject.toJavaObject(Operation.class);

		Operation operationParameter = new Operation();
		operationParameter.setOperation_id(operationMap.getOperation_id());

		return operationDao.find(operationParameter);
	}

	public void save(JSONObject jsonObject) {
		Operation operationMap = jsonObject.toJavaObject(Operation.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Integer count = 0;

		if (! Utility.isNullOrEmpty(operationMap.getOperation_key())) {
			count = operationDao.countByOperation_idAndOperation_key("", operationMap.getOperation_key());
		}

		if(count == 0) {
			operationDao.save(operationMap, request_user_id);
		} else {
			throw new RuntimeException("键已经存在");
		}
	}

	public void update(JSONObject jsonObject) {
		Operation operationMap = jsonObject.toJavaObject(Operation.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Integer count = 0;

		if (! Utility.isNullOrEmpty(operationMap.getOperation_key())) {
			count = operationDao.countByOperation_idAndOperation_key(operationMap.getOperation_id(), operationMap.getOperation_key());
		}

		if(count == 0) {
			operationDao.update(operationMap, request_user_id);
		} else {
			throw new RuntimeException("键已经存在");
		}
	}

	public void delete(JSONObject jsonObject) {
		Operation operationMap = jsonObject.toJavaObject(Operation.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		operationDao.delete(operationMap.getOperation_id(), request_user_id);
	}

}
