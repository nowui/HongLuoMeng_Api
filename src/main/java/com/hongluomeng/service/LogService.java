package com.hongluomeng.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.LogDao;
import com.hongluomeng.model.Log;

public class LogService extends BaseService {

	private LogDao logDao = new LogDao();

	public Map<String, Object> list(JSONObject jsonObject) {
		Log logMap = jsonObject.toJavaObject(Log.class);

		Integer count = logDao.listByLog_urlAndLog_codeAndLog_platform(logMap.getLog_url(), logMap.getLog_code(), logMap.getLog_platform());

		List<Log> logList = logDao.listByLog_urlAndLog_codeAndLog_platform(logMap.getLog_url(), logMap.getLog_code(), logMap.getLog_platform(), Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (Log log : logList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Log.COLUMN_LOG_ID, log.getLog_id());
			map.put(Log.COLUMN_LOG_URL, log.getLog_url());
			map.put(Log.COLUMN_LOG_CREATE_TIME, log.getLog_create_time());
			map.put(Log.COLUMN_LOG_PLATFORM, log.getLog_platform());
			map.put(Log.COLUMN_LOG_CODE, log.getLog_code());
			map.put(Log.COLUMN_LOG_RUN_TIME, log.getLog_run_time());

			list.add(map);
		}

		Map<String, Object> resultMap = Utility.setResultMap(count, list);

		return resultMap;
	}

	public Log find(JSONObject jsonObject) {
		Log logMap = jsonObject.toJavaObject(Log.class);

		Log log = logDao.findByLog_id(logMap.getLog_id());

		return log;
	}

}
