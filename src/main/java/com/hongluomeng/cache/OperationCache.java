package com.hongluomeng.cache;

import java.util.List;

import com.hongluomeng.common.Const;
import com.hongluomeng.model.Operation;

public class OperationCache extends BaseCache {

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationListByUser_id(String user_id) {
		return (List<Operation>) ehcacheList.get(Const.CACHE_OPERATION + "_" + user_id);
	}

	public void setOperationListByUser_id(List<Operation> operationList, String user_id) {
		ehcacheList.put(Const.CACHE_OPERATION + "_" + user_id, operationList);

		setMapByKeyAndId(Const.CACHE_OPERATION, user_id);
	}

	public void removeOperationList() {
		ehcacheList.removeAll(getMapByKey(Const.CACHE_OPERATION));

		removeMapByKey(Const.CACHE_OPERATION);
	}

	public void removeOperationListByUser_id(String user_id) {
		ehcacheList.remove(Const.CACHE_OPERATION + "_" + user_id);

		removeMapByKeyAndId(Const.CACHE_OPERATION, user_id);
	}

}
