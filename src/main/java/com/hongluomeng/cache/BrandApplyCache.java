package com.hongluomeng.cache;

import java.util.List;

import com.hongluomeng.common.Const;
import com.hongluomeng.model.BrandApply;

public class BrandApplyCache extends BaseCache {

	@SuppressWarnings("unchecked")
	public List<BrandApply> getBrandListByUser_id(String user_id) {
		return (List<BrandApply>) ehcacheList.get(Const.CACHE_BRAND_APPLY + "_" + user_id);
	}

	public void setBrandApplyListByUser_id(List<BrandApply> brandApplyList, String user_id) {
		ehcacheList.put(Const.CACHE_BRAND_APPLY + "_" + user_id, brandApplyList);

		setMapByKeyAndId(Const.CACHE_BRAND_APPLY, user_id);
	}

	public void removeBrandApplyList() {
		ehcacheList.removeAll(getMapByKey(Const.CACHE_BRAND_APPLY));

		removeMapByKey(Const.CACHE_BRAND_APPLY);
	}

	public void removeBrandApplyListByUser_id(String user_id) {
		ehcacheList.remove(Const.CACHE_BRAND_APPLY + "_" + user_id);

		removeMapByKeyAndId(Const.CACHE_BRAND_APPLY, user_id);
	}

}
