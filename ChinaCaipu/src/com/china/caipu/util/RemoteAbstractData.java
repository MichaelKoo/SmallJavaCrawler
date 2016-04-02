package com.china.caipu.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.china.caipu.constant.Config;
import com.china.caipu.util.db.DBCaiListUtil;
import com.china.caipu.vo.Cai;
import com.mk.log.LOG;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-2
 */
public final class RemoteAbstractData extends AbstractData {

	static RemoteAbstractData instance = new RemoteAbstractData();

	/**
	 * save to db
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T saveContent(Cai cai, Object... args) throws Exception {
		// TODO Auto-generated method stub
		Boolean boo = DBCaiListUtil.addCai(cai);
		return (T) boo;
	}

	@Override
	public String getContent(Object obj, Object... args) throws Exception {
		// TODO Auto-generated method stub
		URL url = new URL(obj.toString());
		HttpURLConnection.setFollowRedirects(true);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn = (HttpURLConnection) initConn(conn, url);
		conn.setInstanceFollowRedirects(true);
		conn.setConnectTimeout(Config.TIME_OUT);
		conn.setReadTimeout(Config.TIME_OUT);
		//
		conn.connect();

		InputStream input = conn.getInputStream();
		String result = Util.stream2String(input);

		input.close();
		conn.disconnect();

		return result;
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	static String genUrl(int index) {
		String urlPre = Config.URL_PRE;
		if (index == 0) {
			return urlPre;
		}
		return urlPre + "index_" + (index + 1) + ".html";
	}

	/**
	 * @deprecated instead of {@link #getContent(Object)}
	 * 
	 * @param api
	 *            {@link #genUrl(int)}
	 * @return
	 * @throws Exception
	 */
	static String getRemoteContent(String api) throws Exception {

		// URL url = new URL(api);
		// HttpURLConnection.setFollowRedirects(true);
		//
		// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//
		// conn = (HttpURLConnection) initConn(conn, url);
		// conn.setInstanceFollowRedirects(true);
		// conn.setConnectTimeout(Config.TIME_OUT);
		// conn.setReadTimeout(Config.TIME_OUT);
		// //
		// conn.connect();
		//
		// InputStream input = conn.getInputStream();
		// String result = Util.stream2String(input);
		// input.close();
		// conn.disconnect();
		// return result;

		return instance.getContent(api);
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

	static void log(String msg) {
		LOG.D(msg);
	}

}// end
