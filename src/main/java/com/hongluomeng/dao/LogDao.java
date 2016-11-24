package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.DynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Log;

public class LogDao {

	private Integer count(Log log) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT COUNT(*) FROM " + Log.KEY_TABLE_LOG + " ");

		Number count = Db.queryFirst(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Log log = new Log();

		return count(log);
	}

	private List<Log> list(Log log, Integer m, Integer n) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Log.KEY_TABLE_LOG + " ");
		dynamicSQL.append("ORDER BY " + Log.KEY_LOG_CREATE_TIME + " DESC ");
		dynamicSQL.appendPagination(m, n);

		return new Log().find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
	}

	public List<Log> list(Integer m, Integer n) {
		Log log = new Log();

		return list(log, m, n);
	}

	private Log find(Log log) {
		DynamicSQL dynamicSQL = new DynamicSQL();

		dynamicSQL.append("SELECT * FROM " + Log.KEY_TABLE_LOG + " ");
		dynamicSQL.isNullOrEmpty("WHERE " + Log.KEY_LOG_ID + " = ? ", log.getLog_id());

		List<Log> logList = log.find(dynamicSQL.sql.toString(), dynamicSQL.parameterList.toArray());
		if(logList == null) {
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
