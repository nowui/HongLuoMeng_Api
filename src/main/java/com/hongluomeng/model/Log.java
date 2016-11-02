package com.hongluomeng.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.jfinal.plugin.activerecord.Model;
import com.hongluomeng.common.Utility;

public class Log extends Model<Log> {

	private static final long serialVersionUID = 1L;

	public static final String KEY_TABLE_LOG = "table_log";
	public static final String KEY_LOG_ID = "log_id";
	public static final String KEY_LOG_URL = "log_url";
	public static final String KEY_LOG_REQUEST = "log_request";
	public static final String KEY_LOG_RESPONSE = "log_response";
	public static final String KEY_AUTHORIZATION_ID = "authorization_id";
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_LOG_CODE = "log_code";
	public static final String KEY_LOG_PLATFORM = "log_platform";
	public static final String KEY_LOG_VERSION = "log_version";
	public static final String KEY_LOG_IP_ADDRESS = "log_ip_address";
	public static final String KEY_LOG_CREATE_TIME = "log_create_time";
	public static final String KEY_LOG_RUN_TIME = "log_run_time";

	public String getLog_id() {
		return getStr(KEY_LOG_ID);
	}

	public void setLog_id(String log_id) {
		set(KEY_LOG_ID, log_id);
	}

	public String getLog_url() {
		return getStr(KEY_LOG_URL);
	}

	public void setLog_url(String log_url) {
		set(KEY_LOG_URL, log_url);
	}

	public String getLog_request() {
		return getStr(KEY_LOG_REQUEST);
	}

	public void setLog_request(String log_request) {
		set(KEY_LOG_REQUEST, log_request);
	}

	public String getLog_response() {
		return getStr(KEY_LOG_RESPONSE);
	}

	public void setLog_response(String log_response) {
		set(KEY_LOG_RESPONSE, log_response);
	}

	public String getAuthorization_id() {
		return getStr(KEY_AUTHORIZATION_ID);
	}

	public void setAuthorization_id(String authorization_id) {
		set(KEY_AUTHORIZATION_ID, authorization_id);
	}

	public String getUser_id() {
		return getStr(KEY_USER_ID);
	}

	public void setUser_id(String user_id) {
		set(KEY_USER_ID, user_id);
	}

	public Long getLog_code() {
		return Utility.getLongValue(get(KEY_LOG_CODE));
	}

	public void setLog_code(Long log_code) {
		set(KEY_LOG_CODE, log_code);
	}

	public String getLog_platform() {
		return getStr(KEY_LOG_PLATFORM);
	}

	public void setLog_platform(String log_platform) {
		set(KEY_LOG_PLATFORM, log_platform);
	}

	public String getLog_version() {
		return getStr(KEY_LOG_VERSION);
	}

	public void setLog_version(String log_version) {
		set(KEY_LOG_VERSION, log_version);
	}

	public String getLog_ip_address() {
		return getStr(KEY_LOG_IP_ADDRESS);
	}

	public void setLog_ip_address(String log_ip_address) {
		set(KEY_LOG_IP_ADDRESS, log_ip_address);
	}

	public String getLog_create_time() {
		return getStr(KEY_LOG_CREATE_TIME);
	}

	public void setLog_create_time(String log_create_time) {
		set(KEY_LOG_CREATE_TIME, log_create_time);
	}

	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	public Long getLog_run_time() {
		return Utility.getLongValue(get(KEY_LOG_RUN_TIME));
	}

	public void setLog_run_time(Long log_run_time) {
		set(KEY_LOG_RUN_TIME, log_run_time);
	}

}
