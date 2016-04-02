package com.china.caipu.util.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author {Michael Koo ,Email:MK520VIP@163.com}
 * 
 *         2016-3-26
 * 
 *         get conn and close conn
 */
public final class ConnUtil {

	public static Connection getConnection() {
		AbstractConn conn = new JDBCConn();

		try {
			return conn.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * @param conn
	 */
	public static void closeConn(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}// end
