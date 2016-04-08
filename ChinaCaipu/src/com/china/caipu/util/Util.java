package com.china.caipu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mk.IsUtil;
import com.mk.log.LOG;

/**
 * 
 * @author {Mark Sir}
 * 
 *         2016-4-5
 */
public final class Util {

	/**
	 * 
	 * @param packageName
	 * @param fileName
	 * @return
	 */
	public static String genPath(String packageName, String fileName) {

		StringBuilder sb = new StringBuilder();
		sb.append("/");
		sb.append(packageName.replace(".", "/"));
		sb.append("/");
		sb.append(fileName);

		return sb.toString();
	}

	/**
	 * is exists image
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isExistsImage(String name) {
		File file = new File(PathUtil.getImagePath() + name);
		return file.exists();
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String getImageName(String url) {
		int beginIndex = url.lastIndexOf("/");
		String name = url.substring(beginIndex, url.length());
		return name;
	}

	/**
	 * 
	 * @param input
	 *            not close
	 * @return
	 * @throws Exception
	 */
	public static String stream2String(InputStream input, String charsetName)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		String tmp = null;
		BufferedReader br = null;
		if (IsUtil.isNotNull(charsetName)) {
			br = new BufferedReader(new InputStreamReader(input, charsetName),
					16 * 1024);
		} else {
			new BufferedReader(new InputStreamReader(input), 16 * 1024);
		}

		while ((tmp = br.readLine()) != null) {
			sb.append(tmp);
		}
		if (input != null) {
			input.close();
		}
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
