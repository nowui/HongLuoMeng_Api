package com.hongluomeng.service;

import java.util.*;

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
    private PushService pushService = new PushService();
    private MemberService memberService = new MemberService();

	public Map<String, Object> list(JSONObject jsonObject) {
		Integer count = topicDao.count();

		List<Topic> topicList = topicDao.list(false, "", Utility.getStarNumber(jsonObject), "", Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Topic topic : topicList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Topic.COLUMN_TOPIC_ID, topic.getTopic_id());
			map.put(Member.COLUMN_MEMBER_NAME, topic.getMember().getMember_name());
            map.put(Topic.COLUMN_SYSTEM_CREATE_TIME, topic.getSystem_create_time());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject, boolean isMy) {
		Topic topicMap = jsonObject.toJavaObject(Topic.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        String system_create_time = "";

        if (! Utility.isNullOrEmpty(topicMap.getTopic_id())) {
            Topic topic = topicDao.findByTopic_id(topicMap.getTopic_id());

            system_create_time = topic.getSystem_create_time();
        }

		List<Topic> topicList = topicDao.list(isMy, request_user_id, Utility.getStarNumber(jsonObject), system_create_time, Utility.getEndNumber(jsonObject));

        List<String> topicIdList = new ArrayList<String>();

        for (Topic topic : topicList) {
            topicIdList.add(topic.getTopic_id());
        }

        List<TopicLike> topicLikeList = topicLikeService.listByTopicIdList(topicIdList);

        List<TopicComment> topicCommentList = topicCommentService.listByTopicIdList(topicIdList);

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		for (Topic topic : topicList) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put(Topic.COLUMN_TOPIC_ID, topic.getTopic_id());
            resultMap.put(Member.COLUMN_MEMBER_ID, topic.getMember().getMember_id());
            resultMap.put(Member.COLUMN_MEMBER_NAME, topic.getMember().getMember_name());
            resultMap.put(Member.COLUMN_MEMBER_AVATAR, topic.getMember().getMember_avatar_small());
            resultMap.put(Topic.COLUMN_TOPIC_TEXT, topic.getTopic_text());
            resultMap.put(Topic.COLUMN_TOPIC_IMAGE, topic.getTopic_image());
            resultMap.put(Topic.KEY_TOPIC_LARGE_IMAGE, topic.getTopic_large_image());
            resultMap.put(Topic.COLUMN_SYSTEM_CREATE_TIME, topic.getSystem_create_time());
            resultMap.put(Topic.KEY_TOPIC_IS_HAVE_LIKE, topicLikeService.check(topic.getTopic_id(), request_user_id));

            List<Map<String, Object>> topicLikeResultList = new ArrayList<Map<String, Object>>();
            for (TopicLike topicLike : topicLikeList) {
                if(topicLike.getTopic_id().equals(topic.getTopic_id())) {
                    Map<String, Object> topicLikResultMap = new HashMap<String, Object>();
                    topicLikResultMap.put(TopicLike.COLUMN_TOPIC_LIKE_ID, topicLike.gettTopic_like_id());
                    topicLikResultMap.put(Member.COLUMN_MEMBER_ID, topicLike.getMember().getMember_id());
                    topicLikResultMap.put(Member.COLUMN_MEMBER_NAME, topicLike.getMember().getMember_name());
                    topicLikResultMap.put(Member.COLUMN_MEMBER_AVATAR, topicLike.getMember().getMember_avatar_small());

                    topicLikeResultList.add(topicLikResultMap);
                }
            }
            resultMap.put(Topic.COLUMN_TOPIC_LIKE_LIST, topicLikeResultList);

            List<Map<String, Object>> topicCommentResultList = new ArrayList<Map<String, Object>>();
            for (TopicComment topicComment : topicCommentList) {
                if(topicComment.getTopic_id().equals(topic.getTopic_id())) {
                    Map<String, Object> topicCommentResultMap = new HashMap<String, Object>();
                    topicCommentResultMap.put(Member.COLUMN_MEMBER_ID, topicComment.getMember().getMember_id());
                    topicCommentResultMap.put(Member.COLUMN_MEMBER_NAME, topicComment.getMember().getMember_name());
                    topicCommentResultMap.put(Member.COLUMN_MEMBER_AVATAR, topicComment.getMember().getMember_avatar_small());
                    topicCommentResultMap.put(TopicComment.COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_ID, topicComment.getTopic_comment_reply_to_member_id());
                    topicCommentResultMap.put(TopicComment.COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_NAME, topicComment.getTopic_comment_reply_to_member_name());
                    topicCommentResultMap.put(TopicComment.COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_AVATAR, topicComment.getTopic_comment_reply_to_member_avatar());
                    topicCommentResultMap.put(TopicComment.COLUMN_TOPIC_COMMENT_TEXT, topicComment.getTopic_comment_text());

                    topicCommentResultList.add(topicCommentResultMap);
                }
            }
            resultMap.put(Topic.COLUMN_TOPIC_COMMENT_LIST, topicCommentResultList);

            resultMap.put(Topic.KEY_TOPIC_URL,"http://api.hongluomeng.nowui.com/topic/share?id=" + topic.getTopic_id());

            resultList.add(resultMap);
		}

		return resultList;
	}

	public Topic find(JSONObject jsonObject) {
		Topic topicMap = jsonObject.toJavaObject(Topic.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Topic topic = topicDao.findByTopic_id(topicMap.getTopic_id());

        List<TopicLike> topicLikeList = topicLikeService.listByTopic_id(topic.getTopic_id());
        for(TopicLike topicLike : topicLikeList) {
            topicLike.keep(TopicLike.COLUMN_TOPIC_LIKE_ID, Member.COLUMN_MEMBER_NAME, TopicLike.COLUMN_SYSTEM_CREATE_TIME);
        }
        topic.setTopicLikeList(topicLikeList);

        List<TopicComment> topicCommentList = topicCommentService.listByTopic_id(topic.getTopic_id());
        for(TopicComment topicComment : topicCommentList) {
            topicComment.keep(TopicComment.COLUMN_TOPIC_COMMENT_ID, Member.COLUMN_MEMBER_NAME, TopicComment.COLUMN_TOPIC_COMMENT_TEXT, TopicComment.COLUMN_SYSTEM_CREATE_TIME);
        }
        topic.setTopicCommentList(topicCommentList);
        topic.set(Topic.KEY_TOPIC_IS_HAVE_LIKE, topicLikeService.check(topic.getTopic_id(), request_user_id));

		return topic;
	}

    public Map<String, Object> get(JSONObject jsonObject) {
        Topic topicMap = jsonObject.toJavaObject(Topic.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        Topic topic = topicDao.findByTopic_id(topicMap.getTopic_id());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Topic.COLUMN_TOPIC_ID, topic.getTopic_id());
        map.put(Member.COLUMN_MEMBER_ID, topic.getMember().getMember_id());
        map.put(Member.COLUMN_MEMBER_NAME, topic.getMember().getMember_name());
        map.put(Member.COLUMN_MEMBER_AVATAR, topic.getMember().getMember_avatar_small());
        map.put(Topic.COLUMN_TOPIC_TEXT, topic.getTopic_text());
        map.put(Topic.COLUMN_TOPIC_IMAGE, topic.getTopic_image());
        map.put(Topic.KEY_TOPIC_LARGE_IMAGE, topic.getTopic_large_image());
        map.put(Topic.COLUMN_SYSTEM_CREATE_TIME, topic.getSystem_create_time());
        map.put(Topic.KEY_TOPIC_IS_HAVE_LIKE, topicLikeService.check(topic.getTopic_id(), request_user_id));

        List<String> topicIdList = new ArrayList<String>();
        topicIdList.add(topic.getTopic_id());

        List<TopicLike> topicLikeList = topicLikeService.listByTopicIdList(topicIdList);

        List<TopicComment> topicCommentList = topicCommentService.listByTopicIdList(topicIdList);

        List<Map<String, Object>> topicLikeResultList = new ArrayList<Map<String, Object>>();
        for (TopicLike topicLike : topicLikeList) {
            if(topicLike.getTopic_id().equals(topic.getTopic_id())) {
                Map<String, Object> topicLikMap = new HashMap<String, Object>();
                topicLikMap.put(TopicLike.COLUMN_TOPIC_LIKE_ID, topicLike.gettTopic_like_id());
                topicLikMap.put(Member.COLUMN_MEMBER_ID, topicLike.getMember().getMember_id());
                topicLikMap.put(Member.COLUMN_MEMBER_NAME, topicLike.getMember().getMember_name());
                topicLikMap.put(Member.COLUMN_MEMBER_AVATAR, topicLike.getMember().getMember_avatar_small());

                topicLikeResultList.add(topicLikMap);
            }
        }
        map.put(Topic.COLUMN_TOPIC_LIKE_LIST, topicLikeResultList);

        List<Map<String, Object>> topicCommentResultList = new ArrayList<Map<String, Object>>();
        for (TopicComment topicComment : topicCommentList) {
            if(topicComment.getTopic_id().equals(topic.getTopic_id())) {
                Map<String, Object> topicCommentMap = new HashMap<String, Object>();
                topicCommentMap.put(Member.COLUMN_MEMBER_ID, topicComment.getMember().getMember_id());
                topicCommentMap.put(Member.COLUMN_MEMBER_NAME, topicComment.getMember().getMember_name());
                topicCommentMap.put(Member.COLUMN_MEMBER_AVATAR, topicComment.getMember().getMember_avatar_small());
                topicCommentMap.put(TopicComment.COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_ID, topicComment.getTopic_comment_reply_to_member_id());
                topicCommentMap.put(TopicComment.COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_NAME, topicComment.getTopic_comment_reply_to_member_name());
                topicCommentMap.put(TopicComment.COLUMN_TOPIC_COMMENT_REPLY_TO_MEMBER_AVATAR, topicComment.getTopic_comment_reply_to_member_avatar());
                topicCommentMap.put(TopicComment.COLUMN_TOPIC_COMMENT_TEXT, topicComment.getTopic_comment_text());

                topicCommentResultList.add(topicCommentMap);
            }
        }
        map.put(Topic.COLUMN_TOPIC_COMMENT_LIST, topicCommentResultList);

        return map;
    }

	public void save(JSONObject jsonObject) {
		Topic topicMap = jsonObject.toJavaObject(Topic.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		topicDao.save(topicMap, request_user_id);
	}

	public void delete(JSONObject jsonObject, boolean isAdmin) {
		Topic topicMap = jsonObject.toJavaObject(Topic.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		topicDao.delete(topicMap.getTopic_id(), request_user_id, isAdmin);

        topicLikeService.deleteByTopic_id(topicMap.getTopic_id(), request_user_id, isAdmin);

        topicCommentService.deleteByTopic_id(topicMap.getTopic_id(), request_user_id, isAdmin);
	}

    public void saveLike(JSONObject jsonObject) {
        TopicLike topicLikeMap = jsonObject.toJavaObject(TopicLike.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        topicLikeService.save(topicLikeMap, request_user_id);
    }

    public void deleteLike(JSONObject jsonObject) {
        TopicLike topicLikeMap = jsonObject.toJavaObject(TopicLike.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        topicLikeService.delete(topicLikeMap.getTopic_id(), request_user_id);
    }

    public Map<String, Object> getCommentList(JSONObject jsonObject) {
        Topic topicMap = jsonObject.toJavaObject(Topic.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        Integer count = topicCommentService.count();

        List<TopicComment> topicCommentList = topicCommentService.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (TopicComment topicComment : topicCommentList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(TopicComment.COLUMN_TOPIC_COMMENT_ID, topicComment.gettTopic_comment_id());
            map.put(Member.COLUMN_MEMBER_NAME, topicComment.getMember().getMember_name());
            map.put(TopicComment.COLUMN_TOPIC_COMMENT_TEXT, topicComment.getTopic_comment_text());
            map.put(TopicComment.COLUMN_SYSTEM_CREATE_TIME, topicComment.getSystem_create_time());

            list.add(map);
        }

        Map<String, Object> resultMap = Utility.setResultMap(count, list);

        return resultMap;
    }

    public void saveComment(JSONObject jsonObject) {
        TopicComment topicCommentMap = jsonObject.toJavaObject(TopicComment.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        topicCommentService.save(topicCommentMap, request_user_id);

        Topic topic = topicDao.findByTopic_id(topicCommentMap.getTopic_id());

        if (! topic.getUser_id().equals(request_user_id)) {
            Member member = memberService.findByUser_id(request_user_id);

            pushService.send(member.getMember_name() + "评论了你的红圈", topicCommentMap.getTopic_id(), topic.getUser_id());
        }
    }

    public void deleteComment(JSONObject jsonObject, boolean isAdmin) {
        TopicComment topicCommentMap = jsonObject.toJavaObject(TopicComment.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

        topicCommentService.delete(topicCommentMap.gettTopic_comment_id(), request_user_id, isAdmin);
    }

}
