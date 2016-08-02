package com.china.caipu.util.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.china.caipu.constant.Config;
import com.china.caipu.vo.CaiXi;
import com.mk.IsUtil;

/**
 * 
 * @author {Mark_Sir}
 * 
 *         2016-4-13
 */
public final class DBCaiXiUtil {

	private static final String TABLE = " caixi ";

	static final class DBField {
		static final String ID = "caixiID";
		static final String NAME = "caixiName";
		static final String SRC = "caixiSrc";

		static String getAllField() {
			StringBuilder sb = new StringBuilder();
			Class<?> cls = DBField.class;
			Field[] fs = cls.getDeclaredFields();
			for (Field field : fs) {
				try {
					sb.append(field.get(null) + ",");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return sb.substring(0, sb.length() - 1);
		}
	}

	/**
	 * 
	 * @param cai
	 * @return
	 * @throws Exception
	 */
	public static boolean addCai(CaiXi cai) throws Exception {
		boolean result = false;
		if (IsUtil.isNull(cai) || IsUtil.isNull(cai.mCaiXiID)) {
			return false;
		}

		if (!findIsExists(cai.mCaiXiID)) {
			Connection conn = ConnUtil.getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ");
			sb.append(TABLE);
			sb.append(" ( ");
			sb.append(DBField.getAllField());
			sb.append(" ) VALUES ( ");
			sb.append("?,?,?");
			sb.append(" ) ;");

			PreparedStatement psmt = conn.prepareStatement(sb.toString());
			psmt.setString(1, cai.mCaiXiID);
			psmt.setString(2, cai.mCaiXiName);
			psmt.setString(3, cai.mCaiXiSrc);
			psmt.execute();

			result = psmt.getUpdateCount() == 1;

			close(psmt);
			ConnUtil.closeConn(conn);
		}

		return result;
	}

	public static boolean updateCai(CaiXi cai) {
		return false;
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static CaiXi findCai(String name) throws Exception {
		Connection conn = ConnUtil.getConnection();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ");
			sb.append(Config.ALL);
			sb.append(" FROM ");
			sb.append(TABLE);
			sb.append(" WHERE ");
			sb.append(DBField.NAME);
			sb.append("=?");

			psmt = conn.prepareStatement(sb.toString());
			psmt.setString(1, name);

			rs = psmt.executeQuery();
			while (rs.next()) {
				return handleResultSet(rs);
			}

		} finally {
			close(rs);
			close(psmt);
			ConnUtil.closeConn(conn);
		}

		return null;
	}

	/**
	 * 把ResultSet转为CaiXi
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static CaiXi handleResultSet(ResultSet rs) throws SQLException {
		CaiXi cai = new CaiXi();
		cai.mCaiXiID = rs.getString(1);
		cai.mCaiXiName = rs.getString(2);
		cai.mCaiXiSrc = rs.getString(3);
		return cai;
	}

	/**
	 * 获取所有菜系
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<CaiXi> findAllCai() throws Exception {
		List<CaiXi> result = new ArrayList<CaiXi>();
		Connection conn = ConnUtil.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(Config.ALL);
		sb.append(" FROM ");
		sb.append(TABLE);

		PreparedStatement psmt = conn.prepareStatement(sb.toString());

		ResultSet rs = psmt.executeQuery();
		while (rs.next()) {
			result.add(handleResultSet(rs));
		}

		close(rs);
		close(psmt);
		ConnUtil.closeConn(conn);

		return result;
	}

	/**
	 * 
	 * @param conn
	 *            can null
	 * @param name
	 *            caipu name
	 * @return
	 * @throws Exception
	 */
	public static boolean findIsExists(String name) throws Exception {
		Connection conn = ConnUtil.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(DBField.NAME);
		sb.append(" FROM ");
		sb.append(TABLE);
		sb.append("WHERE " + DBField.NAME);
		sb.append(" = ? ");

		PreparedStatement psmt = conn.prepareStatement(sb.toString());
		psmt.setString(1, name);

		ResultSet rs = psmt.executeQuery();
		boolean result = false;
		if (rs.next()) {
			result = true;
		}
		close(rs);
		close(psmt);
		ConnUtil.closeConn(conn);

		return result;
	}

	static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	static void close(Statement state) {
		if (state != null) {
			try {
				state.close();
				state = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}// end
