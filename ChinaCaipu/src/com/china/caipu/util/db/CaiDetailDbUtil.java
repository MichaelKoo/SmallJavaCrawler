package com.china.caipu.util.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.china.caipu.vo.Cai;
import com.china.caipu.vo.CaiDetail;
import com.mk.IsUtil;

/**
 * 
 * @author {MichaelKoo, MK520VIP@163.com}
 * 
 *         CaiDetailDbUtil.java
 */
public final class CaiDetailDbUtil {

	private static final String TABLE = " dongbeicaidetail ";

	static final class CDField {
		static final String NAME = "name";
		static final String DESCRIP = "descrip";
		static final String DETAIL = "detail";

		static String getAllField() {
			StringBuilder sb = new StringBuilder();
			Class<?> cls = CDField.class;
			Field[] fs = cls.getDeclaredFields();
			for (Field field : fs) {
				try {
					sb.append(field.get(null) + ",");
				} catch (Exception e) {
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
	public static boolean addCaiDetail(CaiDetail cai) throws Exception {
		boolean result = false;
		if (IsUtil.isNull(cai) || IsUtil.isNull(cai.mName)) {
			return false;
		}
		Connection conn = ConnUtil.getConnection();
		PreparedStatement psmt = null;

		try {
			if (!findIsExists(cai.mName)) {
				StringBuilder sb = new StringBuilder();
				sb.append("INSERT INTO ");
				sb.append(TABLE);
				sb.append(" ( ");
				sb.append(CDField.getAllField());
				sb.append(" ) VALUES ( ");
				sb.append("?,?,?");
				sb.append(" ) ;");

				psmt = conn.prepareStatement(sb.toString());
				psmt.setString(1, cai.mName);
				psmt.setString(2, cai.mDescrip);
				psmt.setString(3, cai.mDetail);
				psmt.execute();

				result = psmt.getUpdateCount() == 1;
			}
		} finally {
			close(psmt);
			ConnUtil.closeConn(conn);
		}

		return result;
	}

	public static boolean delCai(Cai cai) {
		return false;
	}

	public static boolean updateCai(Cai cai) {
		return false;
	}

	public static List<Cai> findCai(Cai cai) {
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<Cai> findAllCaiByName(String name) throws Exception {
		if (IsUtil.isNull(name)) {
			return null;
		}
		name = "%" + name + "%";
		List<Cai> result = new ArrayList<Cai>();
		Connection conn = ConnUtil.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(CDField.NAME);
		sb.append(",");
		sb.append(CDField.DETAIL);
		sb.append(" FROM ");
		sb.append(TABLE);
		sb.append("WHERE ");
		sb.append(CDField.NAME + " like  " + "?");

		PreparedStatement psmt = conn.prepareStatement(sb.toString());
		psmt.setString(1, name);

		ResultSet rs = psmt.executeQuery();
		while (rs.next()) {
			Cai cai = new Cai();
			cai.mName = rs.getString(1);
			cai.mDetail = rs.getString(2);
			result.add(cai);
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
		sb.append(CDField.NAME);
		sb.append(" FROM ");
		sb.append(TABLE);
		sb.append("WHERE " + CDField.NAME);
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
