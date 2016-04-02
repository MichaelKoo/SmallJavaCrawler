package com.china.caipu.util;

import java.util.List;

import com.china.caipu.vo.Cai;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-2
 */
public abstract class AbstractData {
	/**
	 * 存储数据
	 * 
	 * @param cai
	 * @return
	 * @throws Exception
	 */
	public abstract boolean saveContent(Cai cai) throws Exception;

	/**
	 * 从本地获取或者网络获取数据
	 * 
	 * @param obj
	 *            class or api
	 * @return
	 * @throws Exception
	 */
	public abstract String getContent(Object obj) throws Exception;

	/**
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public abstract List<Cai> parseListHtml(String data) throws Exception;
}// end
