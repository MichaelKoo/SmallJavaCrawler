package com.china.caipu.net;

import java.io.InputStream;
import java.util.Map;

/**
 * doPost,doGet
 * 
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-4
 */
public interface INetHandler {

	public InputStream doPost(String url, Map<String, Object> params)
			throws Exception;

	public InputStream doGet(String url, Map<String, Object> params)
			throws Exception;

}// end
