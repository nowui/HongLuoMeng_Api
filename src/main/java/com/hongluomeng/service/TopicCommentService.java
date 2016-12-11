package com.hongluomeng.service;

import java.util.List;

import com.hongluomeng.dao.TopicCommentDao;
import com.hongluomeng.model.TopicComment;

public class TopicCommentService extends BaseService {

	private TopicCommentDao topicCommentDao = new TopicCommentDao();

    public Integer count() {
        return topicCommentDao.count();
    }

    public List<TopicComment> list(Integer m, Integer n) {
        return topicCommentDao.list(m, n);
    }

    public List<TopicComment> listByTopic_id(String topic_id) {
        return topicCommentDao.listByTopic_id(topic_id);
    }

    public List<TopicComment> listByTopicIdList(List<String> topicIdList) {
        return topicCommentDao.listByTopicIdList(topicIdList);
    }

    public void save(TopicComment topicComment, String request_user_id) {
        topicCommentDao.save(topicComment, request_user_id);
    }

    public void delete(String topic_comment_id, String request_user_id) {
        topicCommentDao.delete(topic_comment_id, request_user_id);
    }

    public void deleteByTopic_id(String topic_id, String request_user_id) {
        topicCommentDao.deleteByTopic_id(topic_id, request_user_id);
    }

}
