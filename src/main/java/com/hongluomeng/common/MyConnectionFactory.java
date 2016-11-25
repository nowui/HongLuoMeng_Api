package com.hongluomeng.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.jfinal.kit.PropKit;

public class MyConnectionFactory {

	private static interface Singleton {
		final MyConnectionFactory INSTANCE = new MyConnectionFactory();
	}

	private final DataSource dataSource;

	private MyConnectionFactory() {
		PropKit.use("Jdbc.properties");

		Properties properties = new Properties();
		properties.setProperty("user", PropKit.get("user"));
		properties.setProperty("password", PropKit.get("password"));

		GenericObjectPool<PoolableConnection> pool = new GenericObjectPool<PoolableConnection>();
		DriverManagerConnectionFactory connectionFactory = new DriverManagerConnectionFactory(PropKit.get("jdbcUrl"), properties);
		new PoolableConnectionFactory(connectionFactory, pool, null, "SELECT 1", 3, false, false, Connection.TRANSACTION_READ_COMMITTED);

		this.dataSource = new PoolingDataSource(pool);
	}

	public static Connection getDatabaseConnection() throws SQLException {
		return Singleton.INSTANCE.dataSource.getConnection();
	}

}