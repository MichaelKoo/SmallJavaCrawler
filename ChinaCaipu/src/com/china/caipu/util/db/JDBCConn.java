package com.china.caipu.util.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-7
 */
public final class JDBCConn extends AbstractConn {

	private static final String URL = "jdbc:mysql://localhost:3306/chinacaipu?useSSL=false";
	private static final String USER = "root";
	private static final String PASSWORD = "123456";

	/**
	 * databases:chinacaipu
	 */
	public Connection getConnection() throws Exception {
		Connection conn = null;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(URL, USER, PASSWORD);

		return conn;
	}

}// end
