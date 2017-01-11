package com.hongluomeng.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Url;
import com.hongluomeng.service.RankingService;
import com.jfinal.core.ActionKey;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Ranking;

public class RankingController extends BaseController {

	private RankingService rankingService = new RankingService();

	@ActionKey(Url.URL_RANKING_LIST)
	public void list() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

		Map<String, Object> resultMap = rankingService.list(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

	@ActionKey(Url.URL_RANKING_FIND)
	public void find() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Ranking rankingValidator = jsonObject.toJavaObject(Ranking.class);

		rankingValidator.checkRanking_id();

		Ranking ranking = rankingService.find(jsonObject);

		renderJson(Utility.setSuccessResponse(ranking));
	}

	@ActionKey(Url.URL_RANKING_SAVE)
	public void save() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Ranking rankingValidator = jsonObject.toJavaObject(Ranking.class);

        rankingValidator.checkRanking_type();

        rankingValidator.checkRanking_name();

        rankingValidator.checkRanking_title();

        rankingValidator.checkRanking_source();

		rankingValidator.checkRanking_image();

		rankingValidator.checkRanking_hits();

        rankingValidator.checkRanking_sort();

		rankingValidator.checkRanking_content();

		rankingService.save(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_RANKING_UPDATE)
	public void update() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Ranking rankingValidator = jsonObject.toJavaObject(Ranking.class);

		rankingValidator.checkRanking_id();

        rankingValidator.checkRanking_type();

		rankingValidator.checkRanking_name();

        rankingValidator.checkRanking_title();

        rankingValidator.checkRanking_source();

		rankingValidator.checkRanking_image();

        rankingValidator.checkRanking_hits();

        rankingValidator.checkRanking_sort();

		rankingValidator.checkRanking_content();

		rankingService.update(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_RANKING_DELETE)
	public void delete() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Ranking rankingValidator = jsonObject.toJavaObject(Ranking.class);

		rankingValidator.checkRanking_id();

		rankingService.delete(jsonObject);

		renderJson(Utility.setSuccessResponse());
	}

	@ActionKey(Url.URL_RANKING_LIST_GET)
	public void getList() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Utility.checkPageAndLimit(jsonObject);

        Ranking rankingValidator = jsonObject.toJavaObject(Ranking.class);

        rankingValidator.checkRanking_type();

		List<Map<String, Object>> resultList = rankingService.getList(jsonObject);

		renderJson(Utility.setSuccessResponse(resultList));
	}

	@ActionKey(Url.URL_RANKING_GET)
	public void get() {
		JSONObject jsonObject = getAttr(Const.KEY_REQUEST);

		Ranking rankingValidator = jsonObject.toJavaObject(Ranking.class);

		rankingValidator.checkRanking_id();

		Map<String, Object> resultMap = rankingService.get(jsonObject);

		renderJson(Utility.setSuccessResponse(resultMap));
	}

}
