package com.hongluomeng.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;

import org.ehcache.UserManagedCache;

import static org.ehcache.config.builders.UserManagedCacheBuilder.newUserManagedCacheBuilder;

import com.hongluomeng.common.Const;
import com.hongluomeng.model.Operation;

public class CacheService {

    @SuppressWarnings("rawtypes")
	private static UserManagedCache<String, List> ehcache = newUserManagedCacheBuilder(String.class, List.class).identifier("data-cache").build(true);
    private static Map<String, Set<String>> map = new HashMap<String, Set<String>>();

	@SuppressWarnings("unchecked")
	public List<Operation> getOperationListByUser_id(String user_id) {
		return (List<Operation>) ehcache.get(Const.CACHE_OPERATION + "_" + user_id);
	}

	public void setOperationListByUser_id(List<Operation> operationList, String user_id) {
		ehcache.put(Const.CACHE_OPERATION + "_" + user_id, operationList);

		setMapByKeyAndId(Const.CACHE_OPERATION, user_id);
	}

	public void removeOperationList() {
		ehcache.removeAll(getMapByKey(Const.CACHE_OPERATION));

		removeMapByKey(Const.CACHE_OPERATION);
	}

	public void removeOperationListByUser_id(String user_id) {
		ehcache.remove(Const.CACHE_OPERATION + "_" + user_id);

		removeMapByKeyAndId(Const.CACHE_OPERATION, user_id);
	}

	private Set<String> getMapByKey(String key) {
		Set<String> set;

		if (map.containsKey(key)) {
			set = map.get(key);
		} else {
			set = new HashSet<String>();
		}

		return set;
	}

	private void setMapByKeyAndId(String key, String id) {
		Set<String> set;

		if (map.containsKey(key)) {
			set = map.get(key);
		} else {
			set = new HashSet<String>();
		}

		set.add(key + "_" + id);

		map.put(key, set);
	}

	private void removeMapByKeyAndId(String key, String id) {
		Set<String> set;

		if (map.containsKey(key)) {
			set = map.get(key);

			set.remove(key + "_" + id);
		} else {
			set = new HashSet<String>();
		}

		map.put(key, set);
	}

	private void removeMapByKey(String key) {
		Set<String> set = new HashSet<String>();

		map.put(key, set);
	}

}
