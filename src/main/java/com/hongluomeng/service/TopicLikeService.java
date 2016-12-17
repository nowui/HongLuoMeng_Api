package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.cache.TopicLikeCache;
import com.hongluomeng.dao.TopicLikeDao;
import com.hongluomeng.model.TopicLike;

public class TopicLikeService extends BaseService {

	private TopicLikeDao topicLikeDao = new TopicLikeDao();
    private TopicLikeCache topicLikeCache = new TopicLikeCache();

    public List<TopicLike> listByUser_id(String user_id) {
        List<TopicLike> topicLikeList = topicLikeCache.getTopicLikeListByUser_id(user_id);

        if (topicLikeList == null) {
            topicLikeList = topicLikeDao.listByUser_id(user_id);

            topicLikeCache.setTopicLikeListByUser_id(topicLikeList, user_id);
        }

        return topicLikeList;
    }

    public boolean check(String topic_id, String user_id) {
        List<TopicLike> topicLikeList = listByUser_id(user_id);

        for (TopicLike topicLike : topicLikeList) {
            if (topicLike.getTopic_id().equals(topic_id)) {
                return true;
            }
        }

        return false;
    }

	public List<TopicLike> listByTopic_id(String topic_id) {
        return topicLikeDao.listByTopic_id(topic_id);
	}

    public List<TopicLike> listByTopicIdList(List<String> topicIdList) {
        return topicLikeDao.listByTopicIdList(topicIdList);
    }

    public void save(TopicLike topicLike, String request_user_id) {
        topicLikeDao.save(topicLike, request_user_id);

        topicLikeCache.removeTopicLikeListByUser_id(request_user_id);
    }

    public void delete(String topic_id, String request_user_id) {
        topicLikeDao.deleteByTopic_id(topic_id, request_user_id);

        topicLikeCache.removeTopicLikeListByUser_id(request_user_id);
    }

    public void deleteByTopic_id(String topic_id, String request_user_id) {
        topicLikeDao.deleteByTopic_id(topic_id, request_user_id);
    }

}
