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
import com.china.caipu.vo.Cai;
import com.mk.IsUtil;

/**菜的相关操作，添加，删除，更新，查找
 * 
 * @author {MichaelKoo}
 *
 *2016-6-10
 */
public final class DBCaiUtil {

	private static final String TABLE = " cai ";

	static final class CPField {
		static final String ID = "caiId";
		static final String CAI_XI_ID = "caixiID";
		static final String NAME = "caiName";

		static final String IMAGE = "caiImage";
		static final String DESCRIP = "caiDescrip";
		static final String DETAIL = "caiDetail";
		static final String DETAIL_DESCRIP = "caiDetailDescrip";

		static String getAllField() {
			StringBuilder sb = new StringBuilder();
			Class<?> cls = CPField.class;
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
	 */
	private static boolean checkCai(Cai cai) {
		if (IsUtil.isNull(cai) || IsUtil.isNull(cai.mCaiID)
				|| IsUtil.isNull(cai.mCaiXiID) || IsUtil.isNull(cai.mName)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param cai
	 * @return
	 * @throws Exception
	 */
	public static boolean addCai(Cai cai) throws Exception {
		boolean result = false;

		if (!checkCai(cai)) {
			return false;
		}

		if (!findIsExists(cai)) {
			Connection conn = ConnUtil.getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ");
			sb.append(TABLE);
			sb.append(" ( ");
			sb.append(CPField.getAllField());
			sb.append(" ) VALUES ( ");
			sb.append("?,?,?,?,?,?,?");
			sb.append(" ) ;");

			PreparedStatement psmt = conn.prepareStatement(sb.toString());
			psmt.setString(1, cai.mCaiID);
			psmt.setString(2, cai.mCaiXiID);
			psmt.setString(3, cai.mName);
			psmt.setString(4, cai.mImage);
			psmt.setString(5, cai.mDescrip);
			psmt.setString(6, cai.mDetail);
			psmt.setString(7, cai.mDetailDescrip);
			psmt.execute();

			result = psmt.getUpdateCount() == 1;

			close(psmt);
			ConnUtil.closeConn(conn);
		}

		return result;
	}

	/**
	 * 
	 * @param cai
	 * @return
	 * @throws Exception
	 */
	public static boolean delCai(Cai cai) throws Exception {
		Connection conn = ConnUtil.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append(" FROM ");
		sb.append(TABLE);
		sb.append("WHERE " + CPField.NAME);
		sb.append(" = ? ");

		PreparedStatement psmt = conn.prepareStatement(sb.toString());
		psmt.setString(1, cai.mName);

		boolean result = false;
		result = psmt.execute();

		close(psmt);
		ConnUtil.closeConn(conn);

		return result;
	}

	/**
	 * 
	 * @param cai
	 * @return
	 */
	public static boolean updateCai(Cai cai) {
		return false;
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Cai findCai(String name) throws Exception {
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
			sb.append(CPField.NAME);
			sb.append("=?");

			psmt = conn.prepareStatement(sb.toString());
			psmt.setString(1, name);

			rs = psmt.executeQuery();
			while (rs.next()) {
				Cai cai = new Cai();
				cai.mName = rs.getString(1);
				cai.mDescrip = rs.getString(2);
				cai.mImage = rs.getString(3);
				cai.mDetail = rs.getString(4);
				return cai;
			}

		} finally {
			close(rs);
			close(psmt);
			ConnUtil.closeConn(conn);
		}

		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<Cai> findAllCai() throws Exception {
		List<Cai> result = new ArrayList<Cai>();
		Connection conn = ConnUtil.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(Config.ALL);
		sb.append(" FROM ");
		sb.append(TABLE);

		PreparedStatement psmt = conn.prepareStatement(sb.toString());

		ResultSet rs = psmt.executeQuery();
		while (rs.next()) {
			Cai cai = new Cai();
			cai.mName = rs.getString(1);
			cai.mDescrip = rs.getString(2);
			cai.mImage = rs.getString(3);
			cai.mDetail = rs.getString(4);
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
	 * @param cai
	 *            caipu name
	 * @return
	 * @throws Exception
	 */
	public static boolean findIsExists(Cai cai) throws Exception {
		Connection conn = ConnUtil.getConnection();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(CPField.NAME);
		sb.append(", " + CPField.CAI_XI_ID);
		sb.append(" FROM ");
		sb.append(TABLE);
		sb.append("WHERE " + CPField.NAME);
		sb.append(" = ? ");

		PreparedStatement psmt = conn.prepareStatement(sb.toString());
		psmt.setString(1, cai.mName);

		ResultSet rs = psmt.executeQuery();
		boolean result = false;
		if (rs.next()) {
			String caixiID = rs.getString(2);
			if (caixiID.equalsIgnoreCase(cai.mCaiXiID)) {
				result = true;
			}
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
