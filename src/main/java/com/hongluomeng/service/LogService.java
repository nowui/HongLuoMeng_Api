package com.hongluomeng.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.common.Utility;
import com.hongluomeng.dao.LogDao;
import com.hongluomeng.model.Log;

public class LogService {

	private LogDao logDao = new LogDao();

	public Integer count(JSONObject jsonObject) {
		//Log logMap = jsonObject.toJavaObject(Log.class);

		return logDao.count();
	}

	public List<Log> list(JSONObject jsonObject) {
		//Log logMap = jsonObject.toJavaObject(Log.class);

		return logDao.list(Utility.getStarNumber(jsonObject), Utility.getEndNumber(jsonObject));
	}

	public Log find(JSONObject jsonObject) {
		Log logMap = jsonObject.toJavaObject(Log.class);

		return logDao.findByLog_id(logMap.getLog_id());
	}

}
