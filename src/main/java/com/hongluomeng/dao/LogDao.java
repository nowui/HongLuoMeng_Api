package com.hongluomeng.dao;

import java.util.ArrayList;
import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.common.Utility;
import com.hongluomeng.model.Log;

public class LogDao {

	private Integer count(Log log) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + Log.KEY_TABLE_LOG + " ");

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer count() {
		Log log = new Log();

		return count(log);
	}

	private List<Log> list(Log log, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Log.KEY_TABLE_LOG + " ");
		myDynamicSQL.append("ORDER BY " + Log.KEY_LOG_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		return new Log().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<Log> list(Integer m, Integer n) {
		Log log = new Log();

		return list(log, m, n);
	}

	private Log find(Log log) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Log.KEY_TABLE_LOG + " ");
		myDynamicSQL.isNullOrEmpty("WHERE " + Log.KEY_LOG_ID + " = ? ", log.getLog_id());

		List<Log> logList = log.find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		if(logList.size() == 0) {
			return null;
		} else {
			return logList.get(0);
		}
	}

	public Log findByLog_id(String Log_id) {
		Log log = new Log();
		log.setLog_id(Log_id);

		log.checkLog_id();

		return find(log);
	}

}
