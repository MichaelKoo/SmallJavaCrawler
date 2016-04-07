package com.china.caipu.list;

import java.util.List;

import com.china.caipu.vo.Cai;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-7
 */
public interface IListHandler {

	/**
	 * 
	 * @param index
	 * @return
	 */
	public String genUrl(int index);

	/**
	 * 
	 * @param url
	 *            {@link #genUrl(int)}
	 * @return
	 * @throws Exception
	 */
	public String getContent(String url) throws Exception;

	/**
	 * 
	 * @param data
	 *            {@link #getContent(String)}
	 * @return
	 * @throws Exception
	 */
	public List<Cai> parseContent(String data) throws Exception;

	/**
	 * 
	 * @param cai
	 *            {@link #parseContent(String)}
	 * @return
	 * @throws Exception
	 */
	public boolean saveContent(Cai cai) throws Exception;

}// end
