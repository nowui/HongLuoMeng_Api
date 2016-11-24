package com.hongluomeng.cache;

import org.ehcache.UserManagedCache;

import java.util.*;

import static org.ehcache.config.builders.UserManagedCacheBuilder.newUserManagedCacheBuilder;

public class BaseCache {

	public static UserManagedCache<String, List> ehcacheList = newUserManagedCacheBuilder(String.class, List.class).identifier("data-cache-list").build(true);
	public static Map<String, Set<String>> map = new HashMap<String, Set<String>>();

	public Set<String> getMapByKey(String key) {
		Set<String> set;

		if (map.containsKey(key)) {
			set = map.get(key);
		} else {
			set = new HashSet<String>();
		}

		return set;
	}

	public void setMapByKeyAndId(String key, String id) {
		Set<String> set;

		if (map.containsKey(key)) {
			set = map.get(key);
		} else {
			set = new HashSet<String>();
		}

		set.add(key + "_" + id);

		map.put(key, set);
	}

	public void removeMapByKeyAndId(String key, String id) {
		Set<String> set;

		if (map.containsKey(key)) {
			set = map.get(key);

			set.remove(key + "_" + id);
		} else {
			set = new HashSet<String>();
		}

		map.put(key, set);
	}

	public void removeMapByKey(String key) {
		Set<String> set = new HashSet<String>();

		map.put(key, set);
	}

}
