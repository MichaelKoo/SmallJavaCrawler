package com.china.caipu.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.china.caipu.constant.Config;
import com.mk.log.LOG;

public final class Util {

	/**
	 * 
	 * @param input
	 *            not close
	 * @return
	 * @throws Exception
	 */
	public static String stream2String(InputStream input) throws Exception {
		StringBuffer sb = new StringBuffer();
		String tmp = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(input,
				Config.CHARSET), 16 * 1024);
		while ((tmp = br.readLine()) != null) {
			sb.append(tmp);
		}
		input.close();

		return sb.toString();
	}

	/**
	 * 
	 * @param map
	 */
	public static void printMap(Map<String, List<String>> map) {

		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			List<String> value = map.get(key);
			for (String data : value) {
				LOG.D(data);
			}
		}
	}
}// end
