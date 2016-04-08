package com.china.caipu.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.china.caipu.constant.Config;
import com.mk.util.MKUtils;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-8
 */
public final class RemoteUtil {

	/**
	 * 
	 * @param api
	 * @param args
	 *            params
	 * @return
	 * @throws Exception
	 */
	public static String getRemoteContent(String api, Object... args)
			throws Exception {
		URL url = new URL(api);
		HttpURLConnection.setFollowRedirects(true);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn = (HttpURLConnection) initConn(conn, url);
		conn.setInstanceFollowRedirects(true);
		conn.setConnectTimeout(Config.TIME_OUT);
		conn.setReadTimeout(Config.TIME_OUT);
		//
		conn.connect();

		InputStream input = conn.getInputStream();
		String result = MKUtils.stream2String(input, Config.CHARSET);

		input.close();
		conn.disconnect();

		return result;
	}

	/**
	 * 
	 * @param conn
	 * @param url
	 * @return common setting
	 */
	private static URLConnection initConn(URLConnection conn, URL url) {

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
	 * get random accept
	 * 
	 * @return
	 */
	private static String getAccept() {
		String[] accept = Config.ACCEPT;
		int index = (int) (Math.random() * accept.length);
		return accept[index];
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
}// end
