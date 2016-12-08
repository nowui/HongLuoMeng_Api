package com.hongluomeng.dao;

import java.util.List;

import com.hongluomeng.common.MyDynamicSQL;
import com.jfinal.plugin.activerecord.Db;
import com.hongluomeng.model.Log;

public class LogDao extends BaseDao {

	private Integer count(Log log) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT COUNT(*) FROM " + Log.TABLE_LOG + " WHERE 1 = 1 ");
		myDynamicSQL.isNullOrEmptyForLike("AND " + Log.COLUMN_LOG_URL + " LIKE ? ", log.getLog_url());
		myDynamicSQL.isNullOrEmpty("AND " + Log.COLUMN_LOG_CODE + " = ? ", log.getLog_code());
		myDynamicSQL.isNullOrEmpty("AND " + Log.COLUMN_LOG_PLATFORM + " = ? ", log.getLog_platform());

		Number count = Db.queryFirst(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
		return count.intValue();
	}

	public Integer listByLog_urlAndLog_codeAndLog_platform(String log_url, String log_code, String log_platform) {
		Log log = new Log();
		log.setLog_url(log_url);
		log.setLog_code(log_code);
		log.setLog_platform(log_platform);

		return count(log);
	}

	private List<Log> list(Log log, Integer m, Integer n) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Log.TABLE_LOG + " WHERE 1 = 1 ");
		myDynamicSQL.isNullOrEmptyForLike("AND " + Log.COLUMN_LOG_URL + " LIKE ? ", log.getLog_url());
		myDynamicSQL.isNullOrEmpty("AND " + Log.COLUMN_LOG_CODE + " = ? ", log.getLog_code());
		myDynamicSQL.isNullOrEmpty("AND " + Log.COLUMN_LOG_PLATFORM + " = ? ", log.getLog_platform());
		myDynamicSQL.append("ORDER BY " + Log.COLUMN_LOG_CREATE_TIME + " DESC ");
		myDynamicSQL.appendPagination(m, n);

		return new Log().find(myDynamicSQL.sql.toString(), myDynamicSQL.parameterList.toArray());
	}

	public List<Log> listByLog_urlAndLog_codeAndLog_platform(String log_url, String log_code, String log_platform, Integer m, Integer n) {
		Log log = new Log();
		log.setLog_url(log_url);
		log.setLog_code(log_code);
		log.setLog_platform(log_platform);

		return list(log, m, n);
	}

	private Log find(Log log) {
		MyDynamicSQL myDynamicSQL = new MyDynamicSQL();

		myDynamicSQL.append("SELECT * FROM " + Log.TABLE_LOG + " ");
		myDynamicSQL.isNullOrEmpty("WHERE " + Log.COLUMN_LOG_ID + " = ? ", log.getLog_id());

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
