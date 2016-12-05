package com.hongluomeng.cache;

import java.util.List;

import com.hongluomeng.common.Const;
import com.hongluomeng.model.Brand;

public class BrandCache extends BaseCache {

	@SuppressWarnings("unchecked")
	public List<Brand> getBrandListByUser_id(String user_id) {
		return (List<Brand>) ehcacheList.get(Const.CACHE_BRAND + "_" + user_id);
	}

	public void setBrandListByUser_id(List<Brand> brandList, String user_id) {
		ehcacheList.put(Const.CACHE_BRAND + "_" + user_id, brandList);

		setMapByKeyAndId(Const.CACHE_BRAND, user_id);
	}

	public void removeBrandList() {
		ehcacheList.removeAll(getMapByKey(Const.CACHE_BRAND));

		removeMapByKey(Const.CACHE_BRAND);
	}

	public void removeBrandListByUser_id(String user_id) {
		ehcacheList.remove(Const.CACHE_BRAND + "_" + user_id);

		removeMapByKeyAndId(Const.CACHE_BRAND, user_id);
	}

}
