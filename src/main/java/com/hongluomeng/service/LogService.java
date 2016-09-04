package com.hongluomeng.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.LogDao;
import com.hongluomeng.model.Log;

public class LogService {

	private LogDao logDao = new LogDao();

	public Map<String, Object> list(JSONObject jsonObject) {
		//Log logMap = jsonObject.toJavaObject(Log.class);

		Integer count = logDao.count();

		List<Log> logList = logDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));

		Map<String, Object> resultMap = Utility.setResultMap(count, logList);

		return resultMap;
	}

	public Log find(JSONObject jsonObject) {
		Log logMap = jsonObject.toJavaObject(Log.class);

		return logDao.findByLog_id(logMap.getLog_id());
	}

}
