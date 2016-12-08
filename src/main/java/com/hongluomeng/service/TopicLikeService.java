package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.TopicLikeDao;
import com.hongluomeng.model.TopicLike;

public class TopicLikeService extends BaseService {

	private TopicLikeDao topicLikeDao = new TopicLikeDao();

	public List<TopicLike> listByTopic_id(String topic_id) {
		List<TopicLike> topicLikeList = topicLikeDao.listByTopic_id(topic_id);

		return topicLikeList;
	}

    public void save(TopicLike topicLike, String request_user_id) {
        topicLikeDao.save(topicLike, request_user_id);
    }

    public void delete(String topic_like_id, String request_user_id) {
        topicLikeDao.delete(topic_like_id, request_user_id);
    }

    public void deleteByTopic_id(String topic_id, String request_user_id) {
        topicLikeDao.deleteByTopic_id(topic_id, request_user_id);
    }

}
