package com.hongluomeng.service;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.model.Log;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {

	private LogService orderService = new LogService();

	@Before
	public void init() {
		PropKit.use("Jdbc.properties");

		final String URL = PropKit.get("jdbcUrl");
		final String USERNAME = PropKit.get("user");
		final String PASSWORD = PropKit.get("password");
		final Integer INITIALSIZE = PropKit.getInt("initialSize");
		final Integer MIDIDLE = PropKit.getInt("minIdle");
		final Integer MAXACTIVEE = PropKit.getInt("maxActivee");

		DruidPlugin druidPlugin = new DruidPlugin(URL, USERNAME, PASSWORD);
		druidPlugin.set(INITIALSIZE, MIDIDLE, MAXACTIVEE);
		druidPlugin.setFilters("stat,wall");
		druidPlugin.start();

		ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(druidPlugin);
		activeRecordPlugin.addMapping("table_log", "log_id", Log.class);
		activeRecordPlugin.start();
	}

	@Test
	public void testSign() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("page", "1");
		jsonObject.put("limit", "20");

		System.out.println(orderService.list(jsonObject));
	}
}