package com.china.caipu.util;

import java.util.List;

import com.china.caipu.util.db.DBCaiListUtil;
import com.china.caipu.util.parser.CaipuListParser;
import com.china.caipu.vo.Cai;
import com.mk.log.LOG;

/**
 * 
 * 
 * 
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-2
 */
public final class ChinaCaipu {
	private AbstractData mAbstractData;

	private ChinaCaipu(boolean isLocal) {
		if (isLocal) {
			mAbstractData = new LocalAbstractData();
		} else {
			mAbstractData = new RemoteAbstractData();
		}
	}

	private static ChinaCaipu instance = null;

	public static ChinaCaipu getInstance(boolean isLocal) {
		if (instance == null) {
			instance = new ChinaCaipu(isLocal);
		}
		return instance;
	}

	/**
	 * @deprecated
	 * 
	 * @param data
	 * @param pathName
	 * @param append
	 * @return
	 * @throws Exception
	 * 
	 */
	public static String saveLocal(String data, String pathName, boolean append)
			throws Exception {
		return LocalAbstractData.saveLocal(data, pathName, append);
	}

	/**
	 * @deprecated instead of {@link #parseList(String)}
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static List<Cai> parseListHtml(String data) throws Exception {
		return CaipuListParser.parseListHtml(data);
	}

	/**
	 * 
	 * @param data
	 *            需要解析的数据
	 * @return
	 * @throws Exception
	 */
	public List<Cai> parseList(String data) throws Exception {
		return mAbstractData.parseListHtml(data);
	}

	/**
	 * @deprecated
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String getRemoteContent(String url) throws Exception {
		return RemoteAbstractData.getRemoteContent(url);
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public static String genUrl(int index) {
		return RemoteAbstractData.genUrl(index);
	}

	/**
	 * @deprecated
	 * 
	 * @param obj
	 *            class or api
	 * @return
	 * @throws Exception
	 */
	public static String getContent(Object obj) throws Exception {
		// if (obj instanceof String) {
		// return RemoteAbstractData.getRemoteContent(obj.toString());
		// } else if (obj instanceof Class) {
		// return LocalAbstractData.getLocal(obj.getClass());
		// }
		return instance.getCaipuContent(obj);
	}

	/**
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getCaipuContent(Object obj) throws Exception {
		return mAbstractData.getContent(obj);
	}

	/**
	 * @deprecated
	 * 
	 * @param cai
	 * @return
	 * @throws Exception
	 */
	public static boolean saveContent(Cai cai) throws Exception {
		// boolean result = false;
		// result = DBCaiListUtil.addCai(cai);
		// return result;

		return instance.saveCaipuContent(cai);
	}

	public boolean saveCaipuContent(Cai cai) throws Exception {
		return mAbstractData.saveContent(cai);
	}

	/**
	 * delete not guangdong yue cai
	 * 
	 * @throws Exception
	 */
	public static void delNotGuangdong() throws Exception {
		List<Cai> data = DBCaiListUtil.findAllCai();
		for (Cai cai : data) {
			if (!cai.mDetail.contains("guangdong")) {
				DBCaiListUtil.delCai(cai);
				LOG.D("deleted :" + cai.mName + ", " + cai.mDetail);
			}
		}
	}

}// end
