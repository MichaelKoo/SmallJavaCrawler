package com.china.caipu.net;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import com.china.caipu.constant.Config;
import com.mk.IsUtil;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-4
 */
final class NetHandlerImpl implements INetHandler {

	public NetHandlerImpl() {

	}

	@Override
	public InputStream doPost(String url, Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream doGet(String url, Map<String, Object> params)
			throws Exception {
		// TODO Auto-generated method stub
		if (IsUtil.isNull(url)) {
			throw new NullPointerException("url is null ");
		}
		return getRemoteContent(url, params, Method.GET);
	}

	/**
	 * 
	 * @param api
	 * @param params
	 * @param method
	 *            {@link Method}
	 * @return
	 * @throws Exception
	 */
	private static InputStream getRemoteContent(String api,
			Map<String, Object> params, Method method) throws Exception {
		URL url = new URL(api);
		HttpURLConnection.setFollowRedirects(true);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn = (HttpURLConnection) initConn(conn, url, method);
		conn = initRequestMethod(conn, method);

		conn.setInstanceFollowRedirects(true);
		conn.setConnectTimeout(Config.TIME_OUT);
		conn.setReadTimeout(Config.TIME_OUT);
		//
		conn.connect();
		// output

		//
		InputStream input = conn.getInputStream();
		conn.disconnect();

		return input;
	}

	/**
	 * 
	 * @param conn
	 * @param url
	 * @return common setting
	 */
	private static URLConnection initConn(URLConnection conn, URL url,
			Method method) {

		conn.setRequestProperty("Host", url.getHost());
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Cache-Control", "max-age=0");
		conn.setRequestProperty("Accept", getAccept());

		conn.setRequestProperty("User-Agent", getUserAgent());

		conn.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
		conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");

		conn.setDefaultUseCaches(false);
		conn.setUseCaches(false);

		return conn;
	}

	/**
	 * 
	 * @param conn
	 * @param method
	 * @return
	 * @throws ProtocolException
	 */
	private static HttpURLConnection initRequestMethod(HttpURLConnection conn,
			Method method) throws ProtocolException {

		boolean output = false;
		String requestMethod = "GET";
		switch (method) {
		case POST:
			requestMethod = "POST";
			output = true;
			break;
		case GET:
			requestMethod = "GET";
			break;
		case PUT:
			requestMethod = "PUT";
			output = true;
			break;

		}
		conn.setRequestMethod(requestMethod);
		conn.setDoOutput(output);
		conn.setDoInput(true);

		return conn;
	}

	/**
	 * 
	 * @return
	 */
	private static String getUserAgent() {
		String[] userAgent = Config.USER_AGENT;
		int index = (int) (Math.random() * userAgent.length);
		return userAgent[index];
	}

	/**
	 * get random accept
	 * 
	 * @return
	 */
	private static String getAccept() {
		String[] accept = Config.ACCEPT;
		int index = (int) (Math.random() * accept.length);
		return accept[index];
	}

}// end
