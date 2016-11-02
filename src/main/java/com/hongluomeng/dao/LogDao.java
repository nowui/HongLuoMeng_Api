package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Log;

public class LogDao {

	private Integer count(Log log) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM " + Log.KEY_TABLE_LOG + " ");

		Number count = Db.queryFirst(sql.toString(), parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Log log = new Log();

		return count(log);
	}

	private List<Log> list(Log log, Integer m, Integer n) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Log.KEY_TABLE_LOG + " ");
		sql.append("ORDER BY " + Log.KEY_LOG_CREATE_TIME + " DESC ");

		if (n > 0) {
			sql.append("LIMIT ?, ? ");
			parameterList.add(m);
			parameterList.add(n);
		}

		List<Log> logList = log.find(sql.toString(), parameterList.toArray());
		return logList;
	}

	public List<Log> list(Integer m, Integer n) {
		Log log = new Log();

		return list(log, m, n);
	}

	private Log find(Log log) {
		List<Object> parameterList = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer("SELECT * FROM " + Log.KEY_TABLE_LOG + " ");

		Boolean isExit = false;

		if (! Utility.isNullOrEmpty(log.getLog_id())) {
			if(isExit) {
				sql.append(" AND ");
			} else {
				sql.append(" WHERE ");
			}
			sql.append(Log.KEY_LOG_ID + " = ? ");
			parameterList.add(log.getLog_id());

			isExit = true;
		}

		if(! isExit) {
			return null;
		}

		List<Log> logList = log.find(sql.toString(), parameterList.toArray());
		if(logList.size() == 0) {
			return null;
		} else {
			return logList.get(0);
		}
	}

	public Log findByLog_id(String Log_id) {
		Log log = new Log();
		log.setLog_id(Log_id);

		return find(log);
	}

}
