package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.model.TopicComment;
import com.hongluomeng.model.TopicLike;
import com.hongluomeng.service.TopicService;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Topic;

public class TopicController extends BaseController {

	private TopicService topicService = new TopicService();

	@ActionKey(Url.URL_TOPIC_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = topicService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_TOPIC_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Topic topicValidator = jsonObject.toJavaObject(Topic.class);

		topicValidator.checkTopic_id();

		Topic topic = topicService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(topic));
	}

    @ActionKey(Url.URL_TOPIC_GET)
    public void get() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Topic topicValidator = jsonObject.toJavaObject(Topic.class);

        topicValidator.checkTopic_id();

        Map<String, Object> resultMap = topicService.get(jsonObject);

        renderJson(Utility.setSuccessResponse(resultMap));
    }

	@ActionKey(Url.URL_TOPIC_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Topic topicValidator = jsonObject.toJavaObject(Topic.class);

		topicValidator.checkTopic_text();

		topicValidator.checkTopic_image();

		topicService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_TOPIC_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Topic topicValidator = jsonObject.toJavaObject(Topic.class);

		topicValidator.checkTopic_id();

		topicService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_TOPIC_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		List<Map<String, Object>> resultList = topicService.getList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

    @ActionKey(Url.URL_TOPIC_LIEK_SAVE)
    public void saveLike() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        TopicLike topicLikeValidator = jsonObject.toJavaObject(TopicLike.class);

        topicLikeValidator.checkTopic_id();

        topicService.saveLike(jsonObject);

        renderJson(Utility.setSuccessResponse());
    }

    @ActionKey(Url.URL_TOPIC_LIEK_DELETE)
    public void deleteLike() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        TopicLike topicLikeValidator = jsonObject.toJavaObject(TopicLike.class);

        topicLikeValidator.checkTopic_id();

        topicService.deleteLike(jsonObject);

        renderJson(Utility.setSuccessResponse());
    }

    @ActionKey(Url.URL_TOPIC_COMMENT_LIST)
    public void getCommentList() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        Utility.checkPageAndLimit(jsonObject);

        Map<String, Object> resultMap = topicService.getCommentList(jsonObject);

        renderJson(Utility.setSuccessResponse(resultMap));
    }

    @ActionKey(Url.URL_TOPIC_COMMENT_SAVE)
    public void saveComment() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        TopicComment topicCommentValidator = jsonObject.toJavaObject(TopicComment.class);

        topicCommentValidator.checkTopic_id();
        topicCommentValidator.checkTopic_comment_reply_to_member_id();
        topicCommentValidator.checkTopic_comment_text();

        topicService.saveComment(jsonObject);

        renderJson(Utility.setSuccessResponse());
    }

    @ActionKey(Url.URL_TOPIC_COMMENT_DELETE)
    public void deleteComment() {
        JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

        TopicComment topicCommentValidator = jsonObject.toJavaObject(TopicComment.class);

        topicCommentValidator.checkTopic_comment_id();

        topicService.deleteComment(jsonObject);

        renderJson(Utility.setSuccessResponse());
    }

}
