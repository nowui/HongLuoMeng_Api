package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Const;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.RankingDao;
import com.hongluomeng.model.Ranking;

public class RankingService extends BaseService {

	private RankingDao rankingDao = new RankingDao();
	private CategoryService categoryService = new CategoryService();
	private MemberService memberService = new MemberService();

	public Map<String, Object> list(JSONObject jsonObject) {
        Ranking rankingMap = jsonObject.toJavaObject(Ranking.class);

		Integer count = rankingDao.countByRanking_type(rankingMap.getRanking_type());

		List<Ranking> rankingList = rankingDao.listByRanking_type(rankingMap.getRanking_type(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Ranking ranking : rankingList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Ranking.COLUMN_RANKING_ID, ranking.getRanking_id());
            map.put(Ranking.COLUMN_RANKING_NAME, ranking.getRanking_name());
            map.put(Ranking.COLUMN_RANKING_TYPE, ranking.getRanking_type());
            map.put(Ranking.COLUMN_RANKING_SORT, ranking.getRanking_sort());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public List<Map<String, Object>> getList(JSONObject jsonObject) {
		Ranking rankingMap = jsonObject.toJavaObject(Ranking.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		List<Ranking> rankingList = rankingDao.listByRanking_type(rankingMap.getRanking_type(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Ranking ranking : rankingList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Ranking.COLUMN_RANKING_ID, ranking.getRanking_id());
			map.put(Ranking.COLUMN_RANKING_NAME, ranking.getRanking_name());
//			map.put(Ranking.COLUMN_RANKING_IMAGE, Utility.packageImagePath(ranking.getRanking_image(), Const.UPLOAD_LARGE));
            map.put(Ranking.COLUMN_RANKING_IMAGE, ranking.getRanking_image());

			list.add(map);
		}

		return list;
	}

	public Ranking find(JSONObject jsonObject) {
		Ranking rankingMap = jsonObject.toJavaObject(Ranking.class);

		Ranking ranking = rankingDao.findByRanking_id(rankingMap.getRanking_id());

		return ranking;
	}

	public Map<String, Object> get(JSONObject jsonObject) {
		Ranking rankingMap = jsonObject.toJavaObject(Ranking.class);

        String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		Ranking ranking = rankingDao.findByRanking_id(rankingMap.getRanking_id());

        rankingDao.updateRanking_hits(rankingMap.getRanking_id(), ranking.getRanking_hits(), request_user_id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Ranking.COLUMN_RANKING_ID, ranking.getRanking_id());
        map.put(Ranking.COLUMN_RANKING_NAME, ranking.getRanking_name());
        map.put(Ranking.COLUMN_RANKING_TITLE, ranking.getRanking_title());
        map.put(Ranking.COLUMN_RANKING_SOURCE, ranking.getRanking_source());
        map.put(Ranking.COLUMN_RANKING_IMAGE, ranking.getRanking_image());
        map.put(Ranking.COLUMN_RANKING_HITS, ranking.getRanking_hits());
        map.put(Ranking.COLUMN_RANKING_CONTENT, ranking.getRanking_content());
        map.put(Ranking.COLUMN_SYSTEM_CREATE_TIME, ranking.getSystem_create_time());

		return map;
	}

	public void save(JSONObject jsonObject) {
		Ranking rankingMap = jsonObject.toJavaObject(Ranking.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		rankingDao.save(rankingMap, request_user_id);
	}

	public void update(JSONObject jsonObject) {
		Ranking rankingMap = jsonObject.toJavaObject(Ranking.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		rankingDao.update(rankingMap, request_user_id);
	}

	public void delete(JSONObject jsonObject) {
		Ranking rankingMap = jsonObject.toJavaObject(Ranking.class);

		String request_user_id = jsonObject.getString(Const.KEY_REQUEST_USER_ID);

		rankingDao.delete(rankingMap.getRanking_id(), request_user_id);
	}

}
