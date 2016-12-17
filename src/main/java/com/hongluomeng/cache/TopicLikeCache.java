package com.hongluomeng.cache;

import java.util.List;

import com.hongluomeng.common.Const;
import com.hongluomeng.model.TopicLike;

public class TopicLikeCache extends BaseCache {

	@SuppressWarnings("unchecked")
	public List<TopicLike> getTopicLikeListByUser_id(String user_id) {
		return (List<TopicLike>) ehcacheList.get(Const.CACHE_TOPIC_LIKE + "_" + user_id);
	}

	public void setTopicLikeListByUser_id(List<TopicLike> topicLikeList, String user_id) {
		ehcacheList.put(Const.CACHE_TOPIC_LIKE + "_" + user_id, topicLikeList);

		setMapByKeyAndId(Const.CACHE_TOPIC_LIKE, user_id);
	}

	public void removeTopicLikeList() {
		ehcacheList.removeAll(getMapByKey(Const.CACHE_TOPIC_LIKE));

		removeMapByKey(Const.CACHE_TOPIC_LIKE);
	}

	public void removeTopicLikeListByUser_id(String user_id) {
		ehcacheList.remove(Const.CACHE_TOPIC_LIKE + "_" + user_id);

		removeMapByKeyAndId(Const.CACHE_TOPIC_LIKE, user_id);
	}

}
