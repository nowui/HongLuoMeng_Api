package com.hongluomeng.service;

import com.alibaba.fastjson.JSONObject;
import com.hongluomeng.model.Log;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.druid.DruidPlugin;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderTest {

//	private HttpServletRequest request;
//	private HttpServletResponse response;


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

//		request = mock(HttpServletRequest.class);
//		response = mock(HttpServletResponse.class);
	}

	@Test
	public void testSign() {
		Connection connection = null;
		try {
			connection = DbKit.getConfig().getDataSource().getConnection();
			DbKit.getConfig().setThreadLocalConnection(connection);
			connection.setAutoCommit(false);



			JSONObject jsonObject = new JSONObject();
			jsonObject.put("page", "1");
			jsonObject.put("limit", "20");
			new LogService().list(jsonObject);



			connection.commit();
		} catch (RuntimeException | SQLException e) {
			Assert.fail(e.toString());
		} finally {
			try {
				if (null != connection) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				Assert.fail(e1.toString());
			}

			try {
				if (null != connection) {
					connection.close();
				}
			} catch (Exception e2) {
				Assert.fail(e2.toString());
			} finally {
				DbKit.getConfig().removeThreadLocalConnection();
			}
		}

//		when(request.getAttribute("request")).thenReturn(JSON.parse("{\"page\":\"1\",\"limit\":\"20\"}"));
//		logController = new LogController();
//		logController.setHttpServletRequest(request);
//		logController.setHttpServletResponse(response);
//		logController.list();
	}
}