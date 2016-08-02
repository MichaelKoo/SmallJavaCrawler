package com.china.caipu.net;

import java.io.InputStream;
import java.util.Map;

/**
 * doPost,doGet
 * 
 */
public interface INetHandler {

	/**
	 * 
	 * @param url
	 * @param params
	 * @return must close InputStream
	 * @throws Exception
	 */
	public InputStream doPost(String url, Map<String, Object> params)
			throws Exception;

	/**
	 * 
	 * @param url
	 * @param params
	 * @return must close
	 * @throws Exception
	 */
	public InputStream doGet(String url, Map<String, Object> params)
			throws Exception;

}// end
