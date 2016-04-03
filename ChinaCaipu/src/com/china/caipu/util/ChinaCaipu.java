package com.china.caipu.util;

import java.util.List;

import com.china.caipu.util.db.DBCaiListUtil;
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

	/**
	 * 
	 * @param isLocal
	 *            true ,load data from local package,else from network
	 */
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
	 * 获取远程数据， {@link #ChinaCaipu(boolean)} is false
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String getCaipuRemoteContent(String url) throws Exception {
		return mAbstractData.getContent(url);
	}

	/**
	 * 获取内容
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public String getCaipuContent(Object obj) throws Exception {
		return mAbstractData.getContent(obj);
	}

	/**
	 * 
	 * @param cai
	 * @return
	 * @throws Exception
	 */
	public <T> T saveCaipuContent(Cai cai, Object... objects) throws Exception {
		return mAbstractData.saveContent(cai, objects);
	}

	/**
	 * save local content
	 * 
	 * @param data
	 * @param pathName
	 * @param append
	 * @return
	 * @throws Exception
	 */
	public String saveCaipuContent(String data, String pathName, boolean append)
			throws Exception {

		return saveCaipuContent(null, pathName, append, data);
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

	/**
	 * 
	 * @param index
	 *            page num
	 * @return
	 */
	public static String genUrl(int index) {
		return RemoteAbstractData.genUrl(index);
	}
}// end
