package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.TopicDao;
import com.hongluomeng.model.Member;
import com.hongluomeng.model.Topic;
import com.hongluomeng.model.TopicComment;
import com.hongluomeng.model.TopicLike;

public class TopicService extends BaseService {

	private TopicDao topicDao = new TopicDao();
	private TopicLikeService topicLikeService = new TopicLikeService();
	private TopicCommentService topicCommentService = new TopicCommentService();

	public Map<String, Object> list(JSONObject jsonObject) {
		Integer count = topicDao.count();

		List<Topic> topicList = topicDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Topic topic : topicList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Topic.COLUMN_TOPIC_ID, topic.getTopic_id());
			map.put(Member.COLUMN_MEMBER_NAME, topic.getMember().getMember_name());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		Topic topicMap = jsonObject.toJavaObject(Topic.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Topic> topicList = topicDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Topic topic : topicList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Topic.COLUMN_TOPIC_ID, topic.getTopic_id());
            map.put(Topic.COLUMN_USER_ID, topic.getUser_id());
            map.put(Member.COLUMN_MEMBER_NAME, topic.getMember().getMember_name());
			map.put(Topic.COLUMN_TOPIC_TEXT, topic.getTopic_text());
			map.put(Topic.COLUMN_TOPIC_IMAGE, topic.getTopic_image());

			list.add(map);
		}

		return list;
	}

	public Topic find(JSONObject jsonObject) {
		Topic topicMap = jsonObject.toJavaObject(Topic.class);

		Topic topic = topicDao.findByTopic_id(topicMap.getTopic_id());

		return topic;
	}

	public void save(JSONObject jsonObject) {
		Topic topicMap = jsonObject.toJavaObject(Topic.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		topicDao.save(topicMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Topic topicMap = jsonObject.toJavaObject(Topic.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		topicDao.delete(topicMap.getTopic_id(), request_user_id);

        topicLikeService.deleteByTopic_id(topicMap.getTopic_id(), request_user_id);

        topicCommentService.deleteByTopic_id(topicMap.getTopic_id(), request_user_id);
	}

    public void saveLike(JSONObject jsonObject) {
        TopicLike topicLikeMap = jsonObject.toJavaObject(TopicLike.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        topicLikeService.save(topicLikeMap, request_user_id);
    }

    public void deleteLike(JSONObject jsonObject) {
        TopicLike topicLikeMap = jsonObject.toJavaObject(TopicLike.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        topicLikeService.delete(topicLikeMap.gettTopic_like_id(), request_user_id);
    }

    public void saveComment(JSONObject jsonObject) {
        TopicComment topicCommentMap = jsonObject.toJavaObject(TopicComment.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        topicCommentService.save(topicCommentMap, request_user_id);
    }

    public void deleteComment(JSONObject jsonObject) {
        TopicComment topicCommentMap = jsonObject.toJavaObject(TopicComment.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        topicCommentService.delete(topicCommentMap.gettTopic_comment_id(), request_user_id);
    }

}
