package cn.systoon.qc.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {

	private static DataSource dataSource = null;

	/**
	 * 数据源 只能被创建一次
	 */
	static {
		dataSource = new ComboPooledDataSource("performance");
	}

	/**
	 * 获取链接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	
	/**
	 * 释放Connection 链接
	 * @param connection
	 */
	public static void releaseConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
