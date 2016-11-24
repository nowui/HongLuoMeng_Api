package com.hongluomeng.cache;

import org.ehcache.UserManagedCache;

import java.util.*;

import static org.ehcache.config.builders.UserManagedCacheBuilder.newUserManagedCacheBuilder;

public class BaseCache {

	protected static UserManagedCache<String, List> ehcacheList = newUserManagedCacheBuilder(String.class, List.class).identifier("data-cache-list").build(true);
	protected static UserManagedCache<String, Object> ehcacheObject = newUserManagedCacheBuilder(String.class, Object.class).identifier("data-cache-object").build(true);
	protected static Map<String, Set<String>> map = new HashMap<String, Set<String>>();

	protected Set<String> getMapByKey(String key) {
		Set<String> set;

		if (map.containsKey(key)) {
			set = map.get(key);
		} else {
			set = new HashSet<String>();
		}

		return set;
	}

	protected void setMapByKeyAndId(String key, String id) {
		Set<String> set;

		if (map.containsKey(key)) {
			set = map.get(key);
		} else {
			set = new HashSet<String>();
		}

		set.add(key + "_" + id);

		map.put(key, set);
	}

	protected void removeMapByKeyAndId(String key, String id) {
		Set<String> set;

		if (map.containsKey(key)) {
			set = map.get(key);

			set.remove(key + "_" + id);
		} else {
			set = new HashSet<String>();
		}

		map.put(key, set);
	}

	protected void removeMapByKey(String key) {
		Set<String> set = new HashSet<String>();

		map.put(key, set);
	}

}
